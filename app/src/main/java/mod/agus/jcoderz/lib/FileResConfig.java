package mod.agus.jcoderz.lib;

import com.google.gson.Gson;

import java.lang.ref.Reference;
import java.lang.reflect.Array;
import java.util.ArrayList;

import mod.hey.studios.util.Helper;

public class FileResConfig {

    public FilePathUtil fpu = new FilePathUtil();
    public ArrayList<String> listBroadcastManifest = new ArrayList<>();
    public ArrayList<String> listFileAssets = new ArrayList<>();
    public ArrayList<String> listFileBroadcast = new ArrayList<>();
    public ArrayList<String> listFileImport = new ArrayList<>();
    public ArrayList<String> listFileJava = new ArrayList<>();
    public ArrayList<String> listFileNativeLibs = new ArrayList<>();
    public ArrayList<String> listFilePermission = new ArrayList<>();
    public ArrayList<String> listFileResource = new ArrayList<>();
    public ArrayList<String> listFileService = new ArrayList<>();
    public ArrayList<String> listJavaManifest = new ArrayList<>();
    public ArrayList<String> listServiceManifest = new ArrayList<>();
    public String numProj;

    public FileResConfig(String sc_id) {
        numProj = sc_id;
        if ((sc_id == null || sc_id.isEmpty())) return;

        String permissions = FileUtil.readFile(fpu.getPathPermission(numProj));
        if (permissions.isEmpty()) return;

        Object object = new Gson().fromJson(FileUtil.readFile(fpu.getPathPermission(numProj)), Helper.TYPE_STRING);
        if (object instanceof ArrayList) {
            listFilePermission = (ArrayList<String>) object;
        }
    }

    public ArrayList<String> getNativelibsFile(String str) {
        listFileNativeLibs.clear();
        FileUtil.listDir(str, listFileNativeLibs);
        return listFileNativeLibs;
    }

    public String getPackageNameProject() {
        return "mod.agus.jcoderz";
    }

    /**
     * This helper method will clear and list a specified path into the existing list
     * @return The listed files
     */
    private ArrayList<String> listDir(String path, ArrayList<String> existing_list) {
        existing_list.clear();
        FileUtil.listDir(path, existing_list);
        return existing_list;
    }

    public ArrayList<String> getJavaFile() {
        return listDir(new FilePathUtil().getPathJava(numProj), listFileJava);
    }

    public ArrayList<String> getAssetsFile() {
        return listDir(new FilePathUtil().getPathAssets(numProj), listFileAssets);
    }

    public ArrayList<String> getResourceFile(String str) {
        return listDir(str, listFileResource);
    }

    public ArrayList<String> getPermissionList() {
        return listFilePermission;
    }

    public ArrayList<String> getImportList() {
        String readFile = FileUtil.readFile(fpu.getPathImport(numProj));
        if (readFile.isEmpty()) return listFileImport;

        Object object = new Gson().fromJson(readFile, Helper.TYPE_STRING);
        if (object instanceof ArrayList) {
            listFileImport = (ArrayList<String>) object;
        }

        return listFileImport;
    }

    public ArrayList<String> getBroadcastFile() {
        return listDir(new FilePathUtil().getPathBroadcast(numProj), listFileBroadcast);
    }

    public ArrayList<String> getServiceFile() {
        return listDir(new FilePathUtil().getPathService(numProj), listFileService);
    }

    public ArrayList<String> getJavaManifestList() {
        String readFile = FileUtil.readFile(fpu.getManifestJava(numProj));
        if (readFile.isEmpty()) return listJavaManifest;

        Object object = new Gson().fromJson(readFile, Helper.TYPE_STRING);
        if (object instanceof ArrayList) {
            listJavaManifest = (ArrayList<String>) object;
        }

        return listJavaManifest;
    }

    public ArrayList<String> getBroadcastManifestList() {
        String readFile = FileUtil.readFile(fpu.getManifestBroadcast(numProj));
        if (readFile.isEmpty()) return listBroadcastManifest;

        Object object = new Gson().fromJson(readFile, Helper.TYPE_STRING);
        if (object instanceof ArrayList) {
            listBroadcastManifest = (ArrayList<String>) object;
        }

        return listBroadcastManifest;
    }

    public ArrayList<String> getServiceManifestList() {
        String readFile = FileUtil.readFile(fpu.getManifestService(numProj));
        if (readFile.isEmpty()) return listServiceManifest;

        Object object = new Gson().fromJson(readFile, Helper.TYPE_STRING);
        if (object instanceof ArrayList) {
            listServiceManifest = (ArrayList<String>) object;
        }

        return listServiceManifest;
    }
}