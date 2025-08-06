package mod.agus.jcoderz.editor.manage.permission;

import android.util.Log;

import java.lang.reflect.Field;
import java.util.ArrayList;

public class ListPermission {

    public static ArrayList<String> getPermissions() {
        ArrayList<String> permissions = new ArrayList<>();
        try {
            for (Field permission : Class.forName("android.Manifest$permission").getDeclaredFields()) {
                if (permission.get(null) instanceof String) {
                    permissions.add((String) permission.get(null));
                }
            }
        } catch (ClassNotFoundException e) {
            Log.e("ListPermission", "Couldn't find class android.Manifest.permission!");
        } catch (IllegalAccessException ignored) {
        }
        return permissions;
    }
}
