package pro.sketchware.ai.config;

import android.content.Context;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.google.gson.Gson;
import com.google.gson.annotations.SerializedName;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import pro.sketchware.utility.FileUtil;

/**
 * Persists the user's {@link AiConfiguration}s and which one of them is selected.
 * <p>
 * The file lives in the app's private storage and not in <code>/.sketchware</code>, because it
 * contains API keys that other apps with storage access shouldn't be able to read.
 */
public class AiConfigurationRepository {

    private static final String TAG = "AiConfigurationRepo";
    private static final String STORE_PATH = "ai/configurations.json";

    private final File storeFile;
    private final Gson gson = new Gson();

    public AiConfigurationRepository(@NonNull Context context) {
        storeFile = new File(context.getApplicationContext().getFilesDir(), STORE_PATH);
    }

    @NonNull
    public synchronized List<AiConfiguration> getAll() {
        return new ArrayList<>(read().configurations);
    }

    @Nullable
    public synchronized AiConfiguration get(String id) {
        for (AiConfiguration configuration : read().configurations) {
            if (configuration.getId().equals(id)) {
                return configuration;
            }
        }
        return null;
    }

    /**
     * Adds the configuration, or replaces the stored one with the same ID.
     */
    public synchronized void save(@NonNull AiConfiguration configuration) {
        Store store = read();
        int index = indexOf(store, configuration.getId());
        if (index >= 0) {
            store.configurations.set(index, configuration);
        } else {
            store.configurations.add(configuration);
        }
        if (store.selectedId == null) {
            store.selectedId = configuration.getId();
        }
        write(store);
    }

    public synchronized void delete(String id) {
        Store store = read();
        int index = indexOf(store, id);
        if (index < 0) {
            return;
        }
        store.configurations.remove(index);
        if (id.equals(store.selectedId)) {
            store.selectedId = store.configurations.isEmpty() ? null : store.configurations.get(0).getId();
        }
        write(store);
    }

    /**
     * @return The configuration to generate with: the selected one, else the first one, else null.
     */
    @Nullable
    public synchronized AiConfiguration getSelected() {
        Store store = read();
        int index = indexOf(store, store.selectedId);
        if (index >= 0) {
            return store.configurations.get(index);
        }
        return store.configurations.isEmpty() ? null : store.configurations.get(0);
    }

    public synchronized void setSelected(String id) {
        Store store = read();
        if (indexOf(store, id) >= 0) {
            store.selectedId = id;
            write(store);
        }
    }

    private int indexOf(Store store, String id) {
        if (id == null) {
            return -1;
        }
        for (int i = 0; i < store.configurations.size(); i++) {
            if (id.equals(store.configurations.get(i).getId())) {
                return i;
            }
        }
        return -1;
    }

    @NonNull
    private Store read() {
        if (!storeFile.exists()) {
            return new Store();
        }
        try {
            Store store = gson.fromJson(FileUtil.readFile(storeFile.getAbsolutePath()), Store.class);
            if (store != null && store.configurations != null) {
                return store;
            }
        } catch (RuntimeException e) {
            Log.e(TAG, "Failed to read the AI configurations, starting with none", e);
        }
        return new Store();
    }

    private void write(Store store) {
        FileUtil.writeFile(storeFile.getAbsolutePath(), gson.toJson(store));
    }

    private static class Store {
        @SerializedName("selected_id")
        private String selectedId;

        @SerializedName("configurations")
        private ArrayList<AiConfiguration> configurations = new ArrayList<>();
    }
}
