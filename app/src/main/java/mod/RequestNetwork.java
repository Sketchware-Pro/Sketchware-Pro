package mod;

import android.app.Activity;

import java.util.HashMap;

public class RequestNetwork {
    private final HashMap<String, Object> params = new HashMap<>();
    private final HashMap<String, Object> headers = new HashMap<>();

    private final Activity activity;

    public RequestNetwork(Activity activity) {
        this.activity = activity;
    }

    public HashMap<String, Object> getParams() {
        return params;
    }

    public HashMap<String, Object> getHeaders() {
        return headers;
    }

    public Activity getActivity() {
        return activity;
    }

    public int getRequestType() {
        return 0;
    }

    public void startRequestNetwork(String method, String url, String tag, RequestListener requestListener) {
        RequestNetworkController.getInstance().execute(this, method, url, tag, requestListener);
    }

    public interface RequestListener {
        void onResponse(String tag, String response, HashMap<String, Object> responseHeaders);

        void onErrorResponse(String tag, String message);
    }
}
