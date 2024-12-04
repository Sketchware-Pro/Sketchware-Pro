package pro.sketchware.managers.inject;

import android.util.Pair;

import a.a.a.wq;

import com.google.gson.Gson;
import com.google.gson.annotations.SerializedName;
import com.google.gson.reflect.TypeToken;

import pro.sketchware.utility.FileUtil;

import java.util.LinkedHashMap;
import java.util.Map;

public class InjectRootLayoutManager {
    private final String path;

    public InjectRootLayoutManager(String sc_id) {
        path = wq.b(sc_id) + "/view_root";
    }

    public void set(String name, Root layout) {
        Map<String, Root> data = get();
        if (data == null) {
            data = new LinkedHashMap<>();
        }
        data.put(name, layout);
        save(data);
    }

    private void save(Map<String, Root> data) {
        FileUtil.writeFile(path, new Gson().toJson(data));
    }

    public Root getLayoutByName(String name) {
        return get().getOrDefault(name, getDefaultRootLayout());
    }

    private Root getDefaultRootLayout() {
        Map<String, String> attrs = new LinkedHashMap<>();
        attrs.put("android:layout_width", "match_parent");
        attrs.put("android:layout_height", "match_parent");
        attrs.put("android:orientation", "vertical");
        return new Root("LinearLayout", attrs);
    }

    public Map<String, Root> get() {
        if (FileUtil.isExistFile(path)) {
            return new Gson()
                    .fromJson(
                            FileUtil.readFile(path),
                            new TypeToken<LinkedHashMap<String, Root>>() {}.getType());
        }
        return new LinkedHashMap<>();
    }

    public static Root toRoot(Pair<String, Map<String, String>> root) {
        return new Root(root.first, root.second);
    }

    public static class Root {
        @SerializedName("class_name")
        private String className;

        @SerializedName("attributes")
        private Map<String, String> attrs;

        public Root() {}

        public Root(String className, Map<String, String> attrs) {
            this.className = className;
            this.attrs = attrs;
        }

        public String getClassName() {
            return className;
        }

        public Map<String, String> getAttributes() {
            return attrs;
        }
    }
}
