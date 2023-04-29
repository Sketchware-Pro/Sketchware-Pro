package mod.RockAriful.AndroXStudio;

import android.os.AsyncTask;
import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

public class GitHubRepoChecker extends AsyncTask<String, Void, Boolean> {
    private static final String TAG = GitHubRepoChecker.class.getSimpleName();
    private String accessToken;

    public GitHubRepoChecker(String accessToken) {
        this.accessToken = accessToken;
    }

    @Override
    protected Boolean doInBackground(String... params) {
        String owner = params[0];
        String repo = params[1];
        String path = params[2];

        try {
            URL url = new URL(String.format("https://api.github.com/repos/%s/%s/contents/%s", owner, repo, path));
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("GET");
            connection.setRequestProperty("Accept", "application/vnd.github.v3+json");
            connection.setRequestProperty("Authorization", "Bearer " + accessToken);
            connection.setRequestProperty("User-Agent", "MySketchware");

            InputStream inputStream = connection.getInputStream();
            BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));
            StringBuilder builder = new StringBuilder();
            String line;
            while ((line = reader.readLine()) != null) {
                builder.append(line);
            }
            String response = builder.toString();
            JSONArray jsonArray = new JSONArray(response);

            return jsonArray.length() > 0;
        } catch (IOException | JSONException e) {
            Log.e(TAG, "Error checking repository: " + e.getMessage());
            return false;
        }
    }
}
