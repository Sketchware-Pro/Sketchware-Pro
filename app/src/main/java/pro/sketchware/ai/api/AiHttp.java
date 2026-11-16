package pro.sketchware.ai.api;

import androidx.annotation.NonNull;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParseException;
import com.google.gson.JsonParser;

import java.io.IOException;
import java.io.InterruptedIOException;
import java.net.ConnectException;
import java.net.UnknownHostException;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;
import okhttp3.ResponseBody;

/**
 * The HTTP plumbing shared by all {@link AiClient}s: sends JSON, returns parsed JSON, and turns
 * every kind of failure into an {@link AiException} with a readable message.
 */
final class AiHttp {

    private static final MediaType JSON = MediaType.parse("application/json; charset=utf-8");
    private static final int MAX_ERROR_LENGTH = 400;
    private static final int MAX_CONNECT_ATTEMPTS = 3;
    private static final long RETRY_DELAY_MS = 2000;

    /**
     * Generating a whole project takes a while, so reading is given far more time than connecting.
     */
    private static final OkHttpClient CLIENT = new OkHttpClient.Builder()
            .connectTimeout(30, TimeUnit.SECONDS)
            .writeTimeout(60, TimeUnit.SECONDS)
            .readTimeout(5, TimeUnit.MINUTES)
            .build();

    private AiHttp() {
    }

    @NonNull
    static JsonObject get(@NonNull String url, @NonNull Map<String, String> headers) throws AiException {
        return execute(newRequest(url, headers).get().build());
    }

    @NonNull
    static JsonObject post(@NonNull String url, @NonNull Map<String, String> headers, @NonNull JsonObject body) throws AiException {
        return execute(newRequest(url, headers).post(RequestBody.create(body.toString(), JSON)).build());
    }

    /**
     * @return <code>baseUrl + path</code>, tolerating a trailing slash in the base URL.
     */
    @NonNull
    static String joinUrl(@NonNull String baseUrl, @NonNull String path) {
        String base = baseUrl.trim();
        while (base.endsWith("/")) {
            base = base.substring(0, base.length() - 1);
        }
        return base + path;
    }

    private static Request.Builder newRequest(String url, Map<String, String> headers) throws AiException {
        try {
            Request.Builder builder = new Request.Builder().url(url);
            headers.forEach(builder::header);
            return builder;
        } catch (IllegalArgumentException e) {
            throw new AiException("Invalid URL or API key: " + e.getMessage(), e);
        }
    }

    /**
     * Mobile connections drop out for a moment when switching networks. Requests that never
     * reached the server are safe to send again, so they're retried a few times before failing.
     */
    private static JsonObject execute(Request request) throws AiException {
        for (int attempt = 1; ; attempt++) {
            try {
                return executeOnce(request);
            } catch (AiException e) {
                boolean neverArrived = e.getCause() instanceof UnknownHostException || e.getCause() instanceof ConnectException;
                if (!neverArrived || attempt >= MAX_CONNECT_ATTEMPTS) {
                    throw e;
                }
                try {
                    Thread.sleep(RETRY_DELAY_MS * attempt);
                } catch (InterruptedException interrupted) {
                    Thread.currentThread().interrupt();
                    throw e;
                }
            }
        }
    }

    private static JsonObject executeOnce(Request request) throws AiException {
        try (Response response = CLIENT.newCall(request).execute()) {
            ResponseBody responseBody = response.body();
            String body = responseBody == null ? "" : responseBody.string();
            if (!response.isSuccessful()) {
                throw new AiException(describeError(response.code(), body), response.code(), null);
            }
            try {
                return JsonParser.parseString(body).getAsJsonObject();
            } catch (JsonParseException | IllegalStateException e) {
                throw new AiException("The server didn't answer with JSON. Check the base URL.", response.code(), e);
            }
        } catch (UnknownHostException e) {
            throw new AiException("Couldn't reach " + request.url().host() + ". Check your connection and the base URL.", e);
        } catch (InterruptedIOException e) {
            throw new AiException("The request timed out. Try again, or pick a faster model.", e);
        } catch (IOException e) {
            throw new AiException("Network error: " + e.getMessage(), e);
        }
    }

    /**
     * Providers disagree on the shape of an error body, so this looks in all the usual places:
     * <code>{"error": {"message": ..}}</code>, <code>{"error": ".."}</code> and
     * <code>{"message": ".."}</code>.
     */
    private static String describeError(int statusCode, String body) {
        String message = null;
        try {
            JsonElement root = JsonParser.parseString(body);
            if (root.isJsonObject()) {
                JsonElement error = root.getAsJsonObject().get("error");
                if (error != null && error.isJsonObject()) {
                    message = optString(error.getAsJsonObject(), "message");
                } else if (error != null && error.isJsonPrimitive()) {
                    message = error.getAsString();
                }
                if (message == null) {
                    message = optString(root.getAsJsonObject(), "message");
                }
            }
        } catch (JsonParseException | IllegalStateException ignored) {
            // Not JSON, fall through to the raw body
        }
        if (message == null || message.trim().isEmpty()) {
            message = describeNonJsonError(body.trim());
        }
        if (message.length() > MAX_ERROR_LENGTH) {
            message = message.substring(0, MAX_ERROR_LENGTH) + "…";
        }
        return "HTTP " + statusCode + ": " + message;
    }

    private static String describeNonJsonError(String body) {
        if (body.isEmpty()) {
            return "No details given";
        }
        // A web page instead of an API error means the request went to a website, not to an API
        if (body.startsWith("<")) {
            return "The server answered with a web page. Check the base URL.";
        }
        return body;
    }

    /**
     * @return The string at <code>key</code>, or null if it's absent or not a string.
     */
    static String optString(@NonNull JsonObject object, @NonNull String key) {
        JsonElement element = object.get(key);
        if (element != null && element.isJsonPrimitive()) {
            return element.getAsString();
        }
        return null;
    }
}
