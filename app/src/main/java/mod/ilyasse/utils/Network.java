package mod.ilyasse.utils;

import android.os.Handler;
import android.os.Looper;

import androidx.annotation.NonNull;

import java.io.IOException;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class Network {

    private final OkHttpClient client;

    public Network() {
        this.client = new OkHttpClient();
    }

    /**
     * Send a GET request to the specified URL
     *
     * @param url     The URL to send the request to
     * @param handler The handler to handle the response.
     *                <br>
     *                Careful: This method respondes on the UI thread
     */
    public void get(String url, ResponseHandler handler) {
        Request request = new Request.Builder()
                .url(url)
                .build();

        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(@NonNull Call call, @NonNull IOException e) {
                e.printStackTrace();
                runOnUiThread(() -> handler.handleResponse(null));
            }

            @Override
            public void onResponse(@NonNull Call call, @NonNull Response response) {
                if (response.isSuccessful()) {
                    try {
                        String responseBody = response.body().string();
                        runOnUiThread(() -> handler.handleResponse(responseBody));
                    } catch (IOException e) {
                        runOnUiThread(() -> handler.handleResponse(null));
                    }
                } else {
                    runOnUiThread(() -> handler.handleResponse(null));
                }
            }
        });
    }

    @FunctionalInterface
    public interface ResponseHandler {
        void handleResponse(String response);
    }

    public static void runOnUiThread(Runnable runnable) {
        Handler mainHandler = new Handler(Looper.getMainLooper());
        mainHandler.post(runnable);
    }
}
