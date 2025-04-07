package pro.sketchware.utility;

import android.os.Handler;
import android.os.Looper;

import androidx.annotation.NonNull;

import java.io.IOException;
import java.util.Map;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.FormBody;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class Network {

    private final OkHttpClient client;

    public Network() {
        this.client = new OkHttpClient();
    }

    public static void runOnUiThread(Runnable runnable) {
        Handler mainHandler = new Handler(Looper.getMainLooper());
        mainHandler.post(runnable);
    }

    public void request(String method, String url, Map<String, String> headers, String body, Map<String, String> formData, ResponseHandler handler) {
        Request.Builder requestBuilder = new Request.Builder().url(url);

        if (headers != null) {
            headers.forEach(requestBuilder::addHeader);
        }

        RequestBody requestBody = null;

        if (!method.equalsIgnoreCase("GET") && !method.equalsIgnoreCase("HEAD")) {
            if (formData != null && !formData.isEmpty()) {
                FormBody.Builder formBuilder = new FormBody.Builder();
                formData.forEach(formBuilder::add);
                requestBody = formBuilder.build();
            } else if (body != null) {
                requestBody = RequestBody.create(body, MediaType.parse("application/json; charset=utf-8"));
            }
        }

        requestBuilder.method(method, requestBody);

        Request request = requestBuilder.build();

        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(@NonNull Call call, @NonNull IOException e) {
                e.printStackTrace();
                runOnUiThread(() -> handler.handleResponse(null));
            }

            @Override
            public void onResponse(@NonNull Call call, @NonNull Response response) {
                try {
                    String responseBody = response.body() != null ? response.body().string() : null;
                    runOnUiThread(() -> handler.handleResponse(responseBody));
                } catch (IOException e) {
                    runOnUiThread(() -> handler.handleResponse(null));
                }
            }
        });
    }

    public void get(String url, ResponseHandler handler) {
        request("GET", url, null, null, null, handler);
    }

    public void get(String url, Map<String, String> headers, ResponseHandler handler) {
        request("GET", url, headers, null, null, handler);
    }

    public void post(String url, Map<String, String> headers, String body, ResponseHandler handler) {
        request("POST", url, headers, body, null, handler);
    }

    public void postForm(String url, Map<String, String> headers, Map<String, String> formData, ResponseHandler handler) {
        request("POST", url, headers, null, formData, handler);
    }

    public void delete(String url, Map<String, String> headers, String body, ResponseHandler handler) {
        request("DELETE", url, headers, body, null, handler);
    }

    @FunctionalInterface
    public interface ResponseHandler {
        void handleResponse(String response);
    }
}
