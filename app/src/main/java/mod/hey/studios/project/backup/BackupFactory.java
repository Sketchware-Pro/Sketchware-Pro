package mod.hey.studios.project.backup;

import android.os.Environment;

import com.besome.sketch.beans.BlockBean;
import com.google.gson.Gson;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.RandomAccessFile;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Locale;
import java.util.Objects;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.zip.ZipEntry;
import java.util.zip.ZipFile;
import java.util.zip.ZipInputStream;
import java.util.zip.ZipOutputStream;

import javax.crypto.Cipher;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;

import a.a.a.lC;
import a.a.a.yB;
import mod.hey.studios.editor.manage.block.ExtraBlockInfo;
import mod.hey.studios.editor.manage.block.v2.BlockLoader;
import mod.hey.studios.project.custom_blocks.CustomBlocksManager;
import mod.hey.studios.util.Helper;
import mod.hilal.saif.activities.tools.ConfigActivity;
import pro.sketchware.utility.FileUtil;
import pro.sketchware.utility.SketchwareUtil;

public class BackupFactory {
    public static final String EXTENSION = "swb";
    public static final String DEF_PATH = ".sketchware/backups/";

    private static final String[] resSubfolders = {
            "fonts", "icons", "images", "sounds"
    };

    final String sc_id;
    File outPath;
    boolean backupLocalLibs;
    boolean backupCustomBlocks;
    String error = "";
    boolean restoreSuccess = true;

    /**
     * @param sc_id For backing up, the target project's ID,
     *              for restoring, the new project ID
     */
    public BackupFactory(String sc_id) {
        this.sc_id = sc_id;
    }

    public static String getBackupDir() {
        return new File(Environment.getExternalStorageDirectory(), ConfigActivity.getBackupPath())
                .getAbsolutePath();
    }

    private static File getAllLocalLibsDir() {
        return new File(Environment.getExternalStorageDirectory(),
                ".sketchware/libs/local_libs");
    }

    private static HashMap<String, Object> getProject(File file) {
        try {
            Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5Padding");
            byte[] key = "sketchwaresecure".getBytes();
            cipher.init(2, new SecretKeySpec(key, "AES"), new IvParameterSpec(key));
            byte[] encrypted;
            try (RandomAccessFile raf = new RandomAccessFile(file, "r")) {
                encrypted = new byte[(int) raf.length()];
                raf.readFully(encrypted);
            }
            byte[] decrypted = cipher.doFinal(encrypted);
            String decryptedString = new String(decrypted);

            return new Gson().fromJson(decryptedString.trim(), Helper.TYPE_MAP);
        } catch (Exception e) {
            return null;
        }
    }

    private static boolean writeEncrypted(File file, String string) {
        String path = file.getAbsolutePath();

        try {
            Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5Padding");
            byte[] key = "sketchwaresecure".getBytes();
            cipher.init(1, new SecretKeySpec(key, "AES"), new IvParameterSpec(key));
            byte[] encrypted = cipher.doFinal((string.trim()).getBytes());
            try (RandomAccessFile raf = new RandomAccessFile(path, "rw")) {
                raf.setLength(0);
                raf.write(encrypted);
            }

            return true;
        } catch (Exception e) {
            return false;
        }
    }

    public static String getNewScId() {
        File myscList = new File(Environment.getExternalStorageDirectory(),
                ".sketchware/mysc/list/");

        ArrayList<String> list = new ArrayList<>();
        FileUtil.listDir(myscList.getAbsolutePath(), list);
        //noinspection Java8ListSort
        Collections.sort(list, String.CASE_INSENSITIVE_ORDER);

        int id = list.isEmpty() ? 600 : Integer.parseInt(new File(list.get(list.size() - 1)).getName());
        return String.valueOf(id + 1);
    }

    /************************ UTILITIES ************************/

    public static boolean unzip(File zipFile, File destinationDir) {
        int DEFAULT_BUFFER = 2048;
        try (ZipFile zip = new ZipFile(zipFile)) {
            destinationDir.mkdirs();
            Enumeration<? extends ZipEntry> zipFileEntries = zip.entries();
            while (zipFileEntries.hasMoreElements()) {
                ZipEntry entry = zipFileEntries.nextElement();
                String entryName = entry.getName();
                File destFile = new File(destinationDir, entryName);
                File destinationParent = destFile.getParentFile();
                if (destinationParent != null && !destinationParent.exists()) {
                    destinationParent.mkdirs();
                }
                if (!entry.isDirectory()) {
                    try (BufferedInputStream is = new BufferedInputStream(zip.getInputStream(entry))) {
                        int currentByte;
                        byte[] data = new byte[DEFAULT_BUFFER];
                        try (FileOutputStream fos = new FileOutputStream(destFile)) {
                            try (BufferedOutputStream dest = new BufferedOutputStream(fos, DEFAULT_BUFFER)) {
                                while ((currentByte = is.read(data, 0, DEFAULT_BUFFER)) != -1 /*EOF*/) {
                                    dest.write(data, 0, currentByte);
                                }
                                dest.flush();
                            }
                        }
                    }
                }
            }
        } catch (IOException e) {
            return false;
        }
        return true;
    }

    public static void zipFolder(File srcFolder, File destZipFile) throws Exception {
        try (FileOutputStream fileWriter = new FileOutputStream(destZipFile)) {
            try (ZipOutputStream zip = new ZipOutputStream(fileWriter)) {
                addFolderToZip(srcFolder, srcFolder, zip);
                zip.flush();
            }
        }
    }

    private static void addFileToZip(File rootPath, File srcFile, ZipOutputStream zip) throws Exception {

        if (srcFile.isDirectory()) {
            addFolderToZip(rootPath, srcFile, zip);
        } else {
            byte[] buf = new byte[1024];
            int len;
            try (FileInputStream in = new FileInputStream(srcFile)) {
                String name = srcFile.getPath();
                name = name.replace(rootPath.getPath() + "/", "");
                zip.putNextEntry(new ZipEntry(name));
                while ((len = in.read(buf)) > 0) {
                    zip.write(buf, 0, len);
                }
            }
        }
    }

    private static void addFolderToZip(File rootPath, File srcFolder, ZipOutputStream zip) throws Exception {
        File[] srcFolderFiles = srcFolder.listFiles();
        if (srcFolderFiles != null) {
            for (File fileName : srcFolderFiles) {
                addFileToZip(rootPath, fileName, zip);
            }
        }
    }

    //6.3.0 fix1
    public static void createNomediaFileIn(File dir) {
        FileUtil.writeFile(new File(dir, ".nomedia").getAbsolutePath(), "");
    }

    //6.3.0 fix1
    public static void copySafe(File source, File destination) {
        if (!source.exists()) {
            destination.mkdirs();
            createNomediaFileIn(destination);
        } else {
            copy(source, destination);
        }
    }

    public static void copy(File source, File destination) {
        if (source.isDirectory()) {
            if (!destination.exists()) destination.mkdirs();

            String[] files = source.list();
            if (files != null) {

                for (String file : files) {
                    File srcFile = new File(source, file);
                    File destFile = new File(destination, file);

                    copy(srcFile, destFile);
                }
            }
        } else {
            //skip .nomedia files
            if (source.getName().equals(".nomedia")) return;

            try (InputStream in = new FileInputStream(source);
                 OutputStream out = new FileOutputStream(destination)) {
                byte[] buffer = new byte[1024];
                int length;
                while ((length = in.read(buffer)) > 0) {
                    out.write(buffer, 0, length);
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public static boolean zipContainsFile(String zipPath, String fileName) {

        try {
            ZipInputStream zp = new ZipInputStream(new FileInputStream(zipPath));

            ZipEntry en;

            while ((en = zp.getNextEntry()) != null) {
                String name = en.getName();

                if (name.equals(fileName) || name.startsWith(fileName + File.separator)) {
                    zp.close();
                    return true;
                }
            }

            zp.close();

        } catch (Exception ignored) {
        }

        return false;
    }

    /************************ BACKUP ************************/

    public void backup(String project_name) {
        String customFileName = ConfigActivity.getBackupFileName();

        String versionName = yB.c(lC.b(sc_id), "sc_ver_name");
        String versionCode = yB.c(lC.b(sc_id), "sc_ver_code");
        String pkgName = yB.c(lC.b(sc_id), "my_sc_pkg_name");
        String projectNameOnly = project_name.replace("_d", "").replace(File.separator, "");
        String finalFileName;

        try {
            finalFileName = customFileName
                    .replace("$projectName", projectNameOnly)
                    .replace("$versionCode", versionCode)
                    .replace("$versionName", versionName)
                    .replace("$pkgName", pkgName)
                    .replace("$versionCode", versionCode)
                    .replace("$timeInMs", String.valueOf(Calendar.getInstance(Locale.ENGLISH).getTimeInMillis()));
            final Matcher matcher = Pattern.compile("\\$time\\((.*?)\\)").matcher(customFileName);
            while (matcher.find()) {
                finalFileName = finalFileName.replaceFirst(Pattern.quote(Objects.requireNonNull(matcher.group(0))), getFormattedDateFrom(matcher.group(1)));
            }
        } catch (Exception ignored) {
            SketchwareUtil.toastError("Failed To Parse Custom Filename For Backup. Using default");
            // Example name: InternalDemo v1.0 (com.jbk.internal.demo, 1) 2021-12-31T125827
            finalFileName = projectNameOnly + " v" + versionName + " (" + pkgName + ", " + versionCode + ") " + getFormattedDateFrom("yyyy-M-dd'T'HHmmss");
        }
        createBackupsFolder();

        // Init temporary backup folder
        File outFolder = new File(getBackupDir(),
                project_name + "_temp");

        // Init output zip file
        File outZip = new File(getBackupDir() + File.separator + projectNameOnly, finalFileName +
                //Adds all the _d if exists. Otherwise its possible that there'll be an infinite loop
                (project_name.contains("_d") ? project_name.replace(projectNameOnly, "") : "") + "." + EXTENSION);

        // Create a duplicate if already exists (impossible now :3)
        if (outZip.exists()) {
            backup(project_name + "_d");
            return;
        }
        //delete temp dir if exist
        if (outFolder.exists()) {
            FileUtil.deleteFile(outFolder.getAbsolutePath());
        }

        // Create necessary folders
        FileUtil.makeDir(outFolder.getAbsolutePath());
        FileUtil.makeDir(new File(getBackupDir() + File.separator + projectNameOnly).getAbsolutePath());

        // Copy data
        File dataF = new File(outFolder, "data");
        FileUtil.makeDir(dataF.getAbsolutePath());
        //6.3.0 fix1
        copySafe(getDataDir(), dataF);

        // Copy res
        File resF = new File(outFolder, "resources");
        FileUtil.makeDir(resF.getAbsolutePath());

        for (String subfolder : resSubfolders) {
            File resSubf = new File(resF, subfolder);
            FileUtil.makeDir(resSubf.getAbsolutePath());

            //6.3.0 fix1
            copySafe(getResDir(subfolder), resSubf);

            // Write an empty file inside each folder (except icons)
            if (!subfolder.equals("icons")) {
                //6.3.0 fix1
                createNomediaFileIn(resSubf);
                //FileUtil.writeFile(new File(resSubf, ".nomedia").getAbsolutePath(), "");
            }
        }

        // Copy project
        File projectF = new File(outFolder, "project");
        copy(getProjectPath(), projectF);

        // Find local libs used and include them in the backup
        if (backupLocalLibs) {
            File localLibs = getLocalLibsPath();

            if (localLibs.exists()) {
                try {
                    JSONArray ja = new JSONArray(FileUtil.readFile(localLibs.getAbsolutePath()));

                    File libsF = new File(outFolder, "local_libs");
                    libsF.mkdirs();

                    for (int i = 0; i < ja.length(); i++) {
                        JSONObject jo = ja.getJSONObject(i);

                        File f = new File(jo.getString("dexPath")).getParentFile();
                        copy(f, new File(libsF, f.getName()));

                    }

                } catch (Exception ignored) {
                }
            }
        }

        // Find custom blocks used and include them in the backup
        if (backupCustomBlocks) {
            CustomBlocksManager cbm = new CustomBlocksManager(sc_id);

            Set<ExtraBlockInfo> blocks = new HashSet<>();
            Set<String> block_names = new HashSet<>();
            for (BlockBean bean : cbm.getUsedBlocks()) {
                if (!block_names.contains(bean.opCode)) {
                    block_names.add(bean.opCode);
                    if (cbm.contains(bean.opCode)) {
                        blocks.add(cbm.getExtraBlockInfo(bean.opCode));
                    } else {
                        var block = BlockLoader.getBlockInfo(bean.opCode);
                        blocks.add(block);
                    }
                }
            }

            String json = new Gson().toJson(blocks);

            File customBlocksF = new File(dataF, "custom_blocks");
            FileUtil.writeFile(customBlocksF.getAbsolutePath(), json);
        }

        // Zip final folder
        try {
            zipFolder(outFolder, outZip);
        } catch (Exception e) {
            // An error occurred

            StringBuilder sb = new StringBuilder();
            for (StackTraceElement el : e.getStackTrace()) {
                sb.append(el.toString());
                sb.append("\n");
            }

            error = sb.toString();
            outPath = null;

            return;
        }

        // Delete the temporary folder
        FileUtil.deleteFile(outFolder.getAbsolutePath());

        // Put outZip to global variable
        outPath = outZip;
    }

    private String getFormattedDateFrom(String format) {
        return new SimpleDateFormat(format, Locale.ENGLISH).format(Calendar.getInstance().getTime());
    }

    public File getOutFile() {
        return outPath;
    }

    public void setBackupLocalLibs(boolean b) {
        backupLocalLibs = b;
    }

    public void setBackupCustomBlocks(boolean b) {
        backupCustomBlocks = b;
    }

    /************************ RESTORE ************************/

    public void restore(File swbPath) {
        String name = swbPath.getName();
        if (name.contains(".")) {
            name = name.substring(0, name.lastIndexOf("."));
        }

        restore(swbPath, name);
    }

    private void restore(File swbPath, String name) {

        createBackupsFolder();

        // Init temporary restore folder for unzipping
        File outFolder = new File(getBackupDir(),
                name);

        // Create a duplicate if already exists
        if (outFolder.exists()) {
            restore(swbPath, name + "_d");
            return;
        }

        // Unzip
        if (!unzip(swbPath, outFolder)) {
            error = "couldn't unzip the backup";
            restoreSuccess = false;
            return;
        }

        // Init files
        File project = new File(outFolder, "project");
        File data = new File(outFolder, "data");
        File res = new File(outFolder, "resources");

        HashMap<String, Object> map = getProject(project);

        if (map == null) {
            error = "couldn't read the project file";
            restoreSuccess = false;
            return;
        }

        // Put new sc_id
        map.put("sc_id", sc_id);

        // Write new file
        if (!writeEncrypted(project, new Gson().toJson(map))) {
            error = "couldn't write to the project file";
            restoreSuccess = false;
            return;
        }

        // Copy data
        copy(data, getDataDir());

        // Copy res
        for (String subfolder : resSubfolders) {
            File subf = new File(res, subfolder);

            copySafe(subf, getResDir(subfolder));
        }

        // Create parent folder
        getProjectPath().getParentFile().mkdirs();

        // Copy project
        copy(project, getProjectPath());

        // Copy local libs if they do not exist
        if (backupLocalLibs) {
            File local_libs = new File(outFolder, "local_libs");

            if (local_libs.exists()) {
                File[] local_libs_content = local_libs.listFiles();
                if (local_libs_content != null) {

                    for (File local_lib : local_libs_content) {

                        File local_lib_real_path = new File(getAllLocalLibsDir(), local_lib.getName());

                        if (!local_lib_real_path.exists()) {
                            local_lib_real_path.mkdirs();
                            copy(local_lib, local_lib_real_path);
                        }
                    }
                }
            }
        }

        // Delete temp folder
        FileUtil.deleteFile(outFolder.getAbsolutePath());

        restoreSuccess = true;
    }

    public String getError() {
        return error;
    }

    public boolean isRestoreSuccess() {
        return restoreSuccess;
    }

    /************************ SW METHODS ************************/

    private void createBackupsFolder() {
        // Create the backups folder if it doesn't exist
        String backupsPath = getBackupDir();

        FileUtil.makeDir(backupsPath);
    }

    private File getDataDir() {
        return new File(Environment.getExternalStorageDirectory(),
                ".sketchware/data/" + sc_id);
    }

    private File getResDir(String subfolder) {
        return new File(Environment.getExternalStorageDirectory(),
                ".sketchware/resources/" + subfolder + "/" + sc_id);
    }

    private File getProjectPath() {
        return new File(Environment.getExternalStorageDirectory(),
                ".sketchware/mysc/list/" + sc_id + "/project");
    }

    private File getLocalLibsPath() {
        return new File(Environment.getExternalStorageDirectory(),
                ".sketchware/data/" + sc_id + "/local_library");
    }
}
