package mod.bobur.helpers;

import android.os.AsyncTask;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;

public class Translator {

    private static final String UCID = "981e0b8825434291abdfdaa571c2827b";
    private static final String SRV = "android";

    public static void translate(String text, String sourceLang, String targetLang, String service, TranslateListener listener) {
        new TranslateTask(text, sourceLang, targetLang, service, listener).execute();
    }

    public interface TranslateListener {
        void onTranslateSuccess(String result);

        void onTranslateError(String errorMessage);
    }

    private static class TranslateTask extends AsyncTask<Void, Void, String> {
        private final String text;
        private final String sourceLang;
        private final String targetLang;
        private final String service;
        private final TranslateListener listener;

        public TranslateTask(String text, String sourceLang, String targetLang, String service, TranslateListener listener) {
            this.text = text;
            this.sourceLang = sourceLang;
            this.targetLang = targetLang;
            this.service = service;
            this.listener = listener;
        }

        @Override
        protected String doInBackground(Void... voids) {
            try {
                if (service.equalsIgnoreCase("Google Translate")) {
                    return translateWithGoogle(text, sourceLang, targetLang);
                } else if (service.equalsIgnoreCase("Yandex Translate")) {
                    return translateWithYandex(text, sourceLang, targetLang);
                } else {
                    return "Translator is not selected";
                }
            } catch (Exception e) {
                return null;
            }
        }

        @Override
        protected void onPostExecute(String result) {
            if (result != null) {
                listener.onTranslateSuccess(result);
            } else {
                listener.onTranslateError("Failed to translate");
            }
        }

        private String translateWithGoogle(String text, String sourceLang, String targetLang) {
            try {
                String apiUrl = String.format("https://translate.googleapis.com/translate_a/single?client=gtx&sl=%s&tl=%s&dt=t&ie=UTF-8&oe=UTF-8&q=%s", sourceLang, targetLang, java.net.URLEncoder.encode(text, "UTF-8"));
                URL url = new URL(apiUrl);
                HttpURLConnection connection = (HttpURLConnection) url.openConnection();
                connection.setRequestProperty("User-Agent", "Mozilla/5.0");
                connection.setRequestMethod("GET");

                BufferedReader reader = new BufferedReader(new InputStreamReader(connection.getInputStream()));
                StringBuilder response = new StringBuilder();
                String line;
                while ((line = reader.readLine()) != null) {
                    response.append(line);
                }
                reader.close();

                return parseResponseGoogle(response.toString());
            } catch (Exception ignored) {
            }
            return null;
        }

        private String translateWithYandex(String text, String sourceLang, String targetLang) {
            try {
                String apiUrl = "https://translate.yandex.net/api/v1/tr.json/translate";
                URL url = new URL(apiUrl);
                HttpURLConnection connection = (HttpURLConnection) url.openConnection();
                connection.setRequestMethod("POST");
                connection.setRequestProperty("User-Agent", "Mozilla/5.0 (Linux; Android 14; 23043RP34G Build/UKQ1.230917.001; wv) AppleWebKit/537.36 (KHTML, like Gecko) Version/4.0 Chrome/130.0.6723.107 Safari/537.36");
                connection.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");
                connection.setRequestProperty("X-Requested-With", "XMLHttpRequest");
                connection.setRequestProperty("Connection", "Keep-Alive");
                connection.setRequestProperty("Accept-Encoding", "gzip");

                String postData = String.format("srv=%s&ucid=%s&lang=%s-%s&text=%s", SRV, UCID, sourceLang, targetLang, java.net.URLEncoder.encode(text, "UTF-8"));

                connection.setDoOutput(true);
                try (OutputStream os = connection.getOutputStream()) {
                    byte[] input = postData.getBytes("utf-8");
                    os.write(input, 0, input.length);
                }

                BufferedReader reader = new BufferedReader(new InputStreamReader(connection.getInputStream()));
                StringBuilder response = new StringBuilder();
                String line;
                while ((line = reader.readLine()) != null) {
                    response.append(line);
                }
                reader.close();

                return parseResponseYandex(response.toString());
            } catch (Exception ignored) {
            }
            return null;
        }

        private String parseResponseGoogle(String response) {
            try {
                JSONArray jsonArray = new JSONArray(response);
                JSONArray translations = jsonArray.getJSONArray(0);

                StringBuilder translatedText = new StringBuilder();
                for (int i = 0; i < translations.length(); i++) {
                    JSONArray translationEntry = translations.getJSONArray(i);
                    translatedText.append(translationEntry.getString(0));
                }
                return translatedText.toString().trim();
            } catch (Exception ignored) {
            }
            return null;
        }

        private String parseResponseYandex(String response) {
            try {
                JSONObject jsonObject = new JSONObject(response);
                JSONArray translations = jsonObject.getJSONArray("text");

                StringBuilder translatedText = new StringBuilder();
                for (int i = 0; i < translations.length(); i++) {
                    translatedText.append(translations.getString(i));
                }
                return translatedText.toString().trim(); // Return concatenated translated text
            } catch (Exception ignored) {
            }
            return null;
        }
    }
}
