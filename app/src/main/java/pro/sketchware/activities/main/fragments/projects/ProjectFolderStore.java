package pro.sketchware.activities.main.fragments.projects;

import com.google.gson.Gson;

import java.io.File;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.UUID;

import a.a.a.wq;

/**
 * Keeps the folder tree of the Projects manager and which folder each project lives in.
 * Stored in a separate JSON file so the project files themselves are never touched.
 */
public class ProjectFolderStore {

    private static final String FILE_NAME = "folders.json";

    private final File file;
    private Data data;

    public ProjectFolderStore() {
        file = new File(wq.q(), FILE_NAME);
        load();
    }

    private void load() {
        data = null;
        if (file.exists()) {
            try {
                String json = new String(Files.readAllBytes(file.toPath()), StandardCharsets.UTF_8);
                data = new Gson().fromJson(json, Data.class);
            } catch (IOException | RuntimeException ignored) {
                data = null;
            }
        }
        if (data == null) data = new Data();
        if (data.folders == null) data.folders = new ArrayList<>();
        if (data.assignments == null) data.assignments = new HashMap<>();
    }

    private void save() {
        try {
            File parent = file.getParentFile();
            if (parent != null && !parent.exists()) parent.mkdirs();
            Files.write(file.toPath(), new Gson().toJson(data).getBytes(StandardCharsets.UTF_8));
        } catch (IOException ignored) {
        }
    }

    /**
     * @return direct sub-folders of the given parent (null = root), sorted by name.
     */
    public List<Folder> getChildFolders(String parentId) {
        List<Folder> result = new ArrayList<>();
        for (Folder folder : data.folders) {
            if (equal(folder.parentId, parentId)) result.add(folder);
        }
        Collections.sort(result, (a, b) -> a.name.toLowerCase().compareTo(b.name.toLowerCase()));
        return result;
    }

    public Folder getFolder(String id) {
        if (id == null) return null;
        for (Folder folder : data.folders) {
            if (id.equals(folder.id)) return folder;
        }
        return null;
    }

    /**
     * @return the folder a project is in, or null when it is at the root (or its folder no longer exists).
     */
    public String getProjectFolderId(String scId) {
        String folderId = data.assignments.get(scId);
        return folderId != null && getFolder(folderId) != null ? folderId : null;
    }

    /**
     * @return true when {@code folderId} is {@code ancestorId} itself or somewhere inside it.
     * A null ancestor means "everything" (the root).
     */
    public boolean isInside(String folderId, String ancestorId) {
        if (ancestorId == null) return true;
        String current = folderId;
        int guard = 0;
        while (current != null && guard++ < 1000) {
            if (current.equals(ancestorId)) return true;
            Folder folder = getFolder(current);
            current = folder == null ? null : folder.parentId;
        }
        return false;
    }

    public int countChildFolders(String parentId) {
        return getChildFolders(parentId).size();
    }

    public Folder createFolder(String name, String parentId) {
        Folder folder = new Folder();
        folder.id = "f" + UUID.randomUUID().toString().replace("-", "");
        folder.name = name.trim();
        folder.parentId = parentId;
        data.folders.add(folder);
        save();
        return folder;
    }

    public void renameFolder(String id, String name) {
        Folder folder = getFolder(id);
        if (folder != null) {
            folder.name = name.trim();
            save();
        }
    }

    /**
     * Puts a project into a folder, or back at the root when {@code folderId} is null.
     */
    public void assign(String scId, String folderId) {
        if (folderId == null) {
            data.assignments.remove(scId);
        } else {
            data.assignments.put(scId, folderId);
        }
        save();
    }

    public void removeProject(String scId) {
        if (data.assignments.remove(scId) != null) save();
    }

    /**
     * @return ids of the folder and every folder nested inside it.
     */
    public Set<String> getFolderTreeIds(String id) {
        Set<String> ids = new HashSet<>();
        collectTree(id, ids);
        return ids;
    }

    private void collectTree(String id, Set<String> ids) {
        if (!ids.add(id)) return;
        for (Folder folder : data.folders) {
            if (id.equals(folder.parentId)) collectTree(folder.id, ids);
        }
    }

    /**
     * Forgets the folder tree (folders and the assignments pointing into it).
     * Projects inside must be deleted by the caller.
     */
    public void removeFolderTree(String id) {
        Set<String> ids = getFolderTreeIds(id);
        data.folders.removeIf(folder -> ids.contains(folder.id));
        data.assignments.values().removeIf(ids::contains);
        save();
    }

    private static boolean equal(String a, String b) {
        return a == null ? b == null : a.equals(b);
    }

    public static class Folder {
        public String id;
        public String name;
        public String parentId;
    }

    private static class Data {
        List<Folder> folders = new ArrayList<>();
        Map<String, String> assignments = new HashMap<>();
    }
}
