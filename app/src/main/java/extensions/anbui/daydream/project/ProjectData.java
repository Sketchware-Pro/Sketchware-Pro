package extensions.anbui.daydream.project;

import androidx.annotation.Nullable;

import com.google.common.reflect.TypeToken;
import com.google.gson.Gson;

import java.lang.reflect.Type;
import java.util.Map;

public class ProjectData {
    @Nullable
    public static Map<String, Object> readDataWithDataType(String dataType, String data) {
        //Find the location of type
        int compatIndex = data.indexOf(dataType);
        if (compatIndex == -1) return null;

        //Find the nearest { after @compat
        int jsonStart = data.indexOf("{", compatIndex);
        int braceCount = 0;
        int jsonEnd = -1;

        //Find the correct } at the end of JSON
        for (int i = jsonStart; i < data.length(); i++) {
            char c = data.charAt(i);
            if (c == '{') braceCount++;
            else if (c == '}') braceCount--;

            if (braceCount == 0) {
                jsonEnd = i;
                break;
            }
        }

        if (jsonStart == -1 || jsonEnd == -1) return null;

        String jsonString = data.substring(jsonStart, jsonEnd + 1);

        //Convert JSON to Map
        Gson gson = new Gson();
        Type type = new TypeToken<Map<String, Object>>() {}.getType();
        return gson.fromJson(jsonString, type);
    }
}
