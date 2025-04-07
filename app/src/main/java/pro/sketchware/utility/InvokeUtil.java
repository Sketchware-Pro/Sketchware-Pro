package pro.sketchware.utility;

import android.content.Context;
import android.view.View;

import androidx.annotation.NonNull;

import java.lang.reflect.Constructor;
import java.lang.reflect.Method;

public class InvokeUtil {

    public static final String[] ANDROID_CLASS_PREFIX = {
            "android.widget.", "android.view.", "android.webkit."
    };

    public static View createView(Context context, @NonNull String name) {
        View v = null;
        if (name.contains(".")) {
            v = create(context, name);
        } else {
            for (String prefix : ANDROID_CLASS_PREFIX) {
                v = create(context, prefix + name);
                if (v != null) {
                    break;
                }
            }
        }
        return v;
    }

    private static View create(Context context, @NonNull String name) {
        try {
            Class<?> clazz = Class.forName(name);
            Constructor<?> con = clazz.getDeclaredConstructor(Context.class);
            con.setAccessible(true);
            return (View) con.newInstance(context);
        } catch (Exception ignored) {
        }
        return null;
    }

    public static Object invoke(Object v, String name, Class<?>[] types, Object... params) {
        try {
            Class<?> clazz = v.getClass();
            Method method = getMethod(clazz, name, types);
            if (method == null) return null;
            method.setAccessible(true);
            return method.invoke(v, params);

        } catch (Exception e) {

        }
        return null;
    }

    private static Method getMethod(Class<?> clazz, String name, Class<?>... types) {
        for (Class<?> superClass = clazz;
             superClass != Object.class;
             superClass = superClass.getSuperclass()) {
            try {
                return superClass.getDeclaredMethod(name, types);
            } catch (Exception e) {

            }
        }
        return null;
    }
}
