package pro.sketchware.activities.resourceseditor.components;

import android.os.Handler;
import android.os.Looper;
import org.json.JSONArray;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import pro.sketchware.activities.resourceseditor.ResourcesEditorActivity;
import pro.sketchware.utility.FileUtil;

public class TranslationString {

    public interface TranslationCallback {
        void onStart();
        void onSuccess();
        void onError(String error);
    }

    public static void translate(ArrayList<HashMap<String, Object>> data, String targetLang, String targetPath, TranslationCallback callback) {
        new Thread(() -> {
            new Handler(Looper.getMainLooper()).post(callback::onStart);
            try {
                ArrayList<HashMap<String, Object>> translatedList = new ArrayList<>();
                Pattern paramPattern = Pattern.compile("%(\\d+\\$)?[-#+ 0,(\\[]*\\d?(\\.\\d+)?[a-zA-Z]");
                for (HashMap<String, Object> map : data) {
                    String originalText = String.valueOf(map.get("text"));
                    List<String> placeholders = new ArrayList<>();
                    Matcher matcher = paramPattern.matcher(originalText);
                    StringBuffer sb = new StringBuffer();
                    int i = 0;
                    while (matcher.find()) {
                        placeholders.add(matcher.group());
                        matcher.appendReplacement(sb, "[[[" + i + "]]]");
                        i++;
                    }
                    matcher.appendTail(sb);
                    String translatedText = fetch(sb.toString(), targetLang);

                    translatedText = translatedText.replaceAll("\\s*\\[\\[\\[\\s*", "[[[").replaceAll("\\s*\\]\\]\\]\\s*", "]]]");
                    for (int j = 0; j < placeholders.size(); j++) {
                        translatedText = translatedText.replace("[[[" + j + "]]]", placeholders.get(j));
                    }

                    HashMap<String, Object> newMap = new HashMap<>(map);
                    newMap.put("text", translatedText);
                    translatedList.add(newMap);
                }
                //Save XML
                saveXml(translatedList, targetPath);
                new Handler(Looper.getMainLooper()).post(callback::onSuccess);

            } catch (Exception e) {
                new Handler(Looper.getMainLooper()).post(() -> callback.onError(e.getMessage()));
            }
        }).start();
    }

    private static String fetch(String text, String targetLang) throws Exception {
        String urlStr = "https://translate.googleapis.com/translate_a/single?client=gtx&sl=auto&tl="
                + targetLang + "&dt=t&q=" + URLEncoder.encode(text, "UTF-8");

        URL url = new URL(urlStr);
        HttpURLConnection con = (HttpURLConnection) url.openConnection();
        con.setRequestProperty("User-Agent", "Mozilla/5.0");

        BufferedReader in = new BufferedReader(new InputStreamReader(con.getInputStream(), "UTF-8"));
        StringBuilder response = new StringBuilder();
        String line;
        while ((line = in.readLine()) != null) response.append(line);
        in.close();

        // Parseo seguro con org.json
        JSONArray jsonArray = new JSONArray(response.toString());
        JSONArray sentences = jsonArray.getJSONArray(0);
        StringBuilder result = new StringBuilder();
        for (int i = 0; i < sentences.length(); i++) {
            result.append(sentences.getJSONArray(i).getString(0));
        }
        return result.toString();
    }

    private static void saveXml(ArrayList<HashMap<String, Object>> list, String path) {
        StringBuilder xml = new StringBuilder("<?xml version=\"1.0\" encoding=\"utf-8\"?>\n<resources>\n");
        for (HashMap<String, Object> map : list) {
            String key = String.valueOf(map.get("key"));
            String val = ResourcesEditorActivity.escapeXml(String.valueOf(map.get("text")));
            xml.append("    <string name=\"").append(key).append("\">").append(val).append("</string>\n");
        }
        xml.append("</resources>");

        // Asegurar que la carpeta existe antes de escribir
        String dir = path.substring(0, path.lastIndexOf("/"));
        if (!FileUtil.isExistFile(dir)) FileUtil.makeDir(dir);
        FileUtil.writeFile(path, xml.toString());
    }
}