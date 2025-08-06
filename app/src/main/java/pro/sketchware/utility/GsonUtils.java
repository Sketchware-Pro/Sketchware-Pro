package pro.sketchware.utility;

import androidx.annotation.NonNull;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

public class GsonUtils {

    private static final Gson gson = new GsonBuilder()
            .setPrettyPrinting()
            .create();

    @NonNull
    public static Gson getGson() {
        return gson;
    }
}