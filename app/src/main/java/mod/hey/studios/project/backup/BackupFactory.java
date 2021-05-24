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
import java.util.ArrayList;
import java.util.Collections;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.zip.ZipEntry;
import java.util.zip.ZipFile;
import java.util.zip.ZipInputStream;
import java.util.zip.ZipOutputStream;

import javax.crypto.Cipher;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;

import mod.agus.jcoderz.lib.FileUtil;
import mod.hey.studios.editor.manage.block.ExtraBlockInfo;
import mod.hey.studios.editor.manage.block.v2.BlockLoader;
import mod.hey.studios.project.custom_blocks.CustomBlocksManager;
import mod.hey.studios.util.Helper;
import mod.hilal.saif.activities.tools.ConfigActivity;

public class BackupFactory {
    public static final String EXTENSION = "swb";
    public static final String DEF_PATH = ".sketchware/backups/";

    private static final String[] resSubfolders = {
            "fonts", "icons", "images", "sounds"
    };

    String sc_id;
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
            RandomAccessFile raf = new RandomAccessFile(file, "r");
            byte[] encrypted = new byte[(int) raf.length()];
            raf.readFully(encrypted);
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
            RandomAccessFile raf = new RandomAccessFile(path, "rw");
            raf.setLength(0);
            raf.write(encrypted);

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
        Collections.sort(list, String.CASE_INSENSITIVE_ORDER);

        int id = list.size() == 0 ? 600 : Integer.parseInt(new File(list.get(list.size() - 1)).getName());
        return String.valueOf(id + 1);
    }

    /************************ UTILITIES ************************/

    public static boolean unzip(File zipFile, File destinationDir) {
        int DEFAULT_BUFFER = 2048;
        ZipFile zip = null;
        try {
            destinationDir.mkdirs();
            zip = new ZipFile(zipFile);
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
                    BufferedInputStream is = new BufferedInputStream(zip.getInputStream(entry));
                    int currentByte;
                    byte[] data = new byte[DEFAULT_BUFFER];
                    FileOutputStream fos = new FileOutputStream(destFile);
                    BufferedOutputStream dest = new BufferedOutputStream(fos, DEFAULT_BUFFER);
                    while ((currentByte = is.read(data, 0, DEFAULT_BUFFER)) != -1/*EOF*/) {
                        dest.write(data, 0, currentByte);
                    }
                    dest.flush();
                    dest.close();
                    is.close();
                }
            }
        } catch (Exception e) {
            return false;
        } finally {
            if (zip != null) {
                try {
                    zip.close();
                } catch (IOException ignored) {
                }
            }
        }
        return true;
    }

    public static void zipFolder(File srcFolder, File destZipFile) throws Exception {
        /*try (FileOutputStream fileWriter = new FileOutputStream(destZipFile);
        ZipOutputStream zip = new ZipOutputStream(fileWriter)) {

            addFolderToZip(srcFolder, srcFolder, zip);
        }*/

        ZipOutputStream zip = null;
        FileOutputStream fileWriter = null;

        fileWriter = new FileOutputStream(destZipFile);
        zip = new ZipOutputStream(fileWriter);

        addFolderToZip(srcFolder, srcFolder, zip);

        zip.flush();
        zip.close();
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
        for (File fileName : srcFolder.listFiles()) {
            addFileToZip(rootPath, fileName, zip);
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
            if (!destination.exists()) {
                destination.mkdirs();
            }

            String[] files = source.list();

            for (String file : files) {
                File srcFile = new File(source, file);
                File destFile = new File(destination, file);

                copy(srcFile, destFile);
            }
        } else {
            //skip .nomedia files
            if (source.getName().equals(".nomedia")) {
                return;
            }

            InputStream in = null;
            OutputStream out = null;

            try {
                in = new FileInputStream(source);
                out = new FileOutputStream(destination);

                byte[] buffer = new byte[1024];

                int length;
                while ((length = in.read(buffer)) > 0) {
                    out.write(buffer, 0, length);
                }
            } catch (Exception e) {
                try {
                    if (in != null)
                        in.close();
                } catch (IOException e1) {
                    e1.printStackTrace();
                }

                try {
                    if (out != null)
                        out.close();
                } catch (IOException e1) {
                    e1.printStackTrace();
                }
            }
        }
    }

    public static boolean zipContainsFile(String zipPath, String fileName) {

        try {
            ZipInputStream zp = new ZipInputStream(new FileInputStream(new File(zipPath)));

            ZipEntry en = null;

            while ((en = zp.getNextEntry()) != null) {
                String name = en.getName();

                if (name.equals(fileName) || name.startsWith(fileName + File.separator)) {
                    try {
                        zp.close();
                    } catch (Exception e) {
                    }

                    return true;
                }
            }

            try {
                zp.close();
            } catch (Exception e) {
            }

        } catch (Exception e) {
        }

        return false;

    }

    /************************ BACKUP ************************/

    public void backup(String app_name) {

        createBackupsFolder();

        //init temporary backup folder
        File outFolder = new File(getBackupDir(),
                app_name);

        //init output zip file
        File outZip = new File(getBackupDir(),
                app_name + "." + EXTENSION);

        //create a duplicate if already exists
        if (outFolder.exists() || outZip.exists()) {
            backup(app_name + "_d");
            return;
        }

        //create temp folder
        FileUtil.makeDir(outFolder.getAbsolutePath());

        //copy data
        File dataF = new File(outFolder, "data");
        FileUtil.makeDir(dataF.getAbsolutePath());
        //6.3.0 fix1
        copySafe(getDataDir(), dataF);

        //copy res
        File resF = new File(outFolder, "resources");
        FileUtil.makeDir(resF.getAbsolutePath());

        for (String subfolder : resSubfolders) {
            File resSubf = new File(resF, subfolder);
            FileUtil.makeDir(resSubf.getAbsolutePath());

            //6.3.0 fix1
            copySafe(getResDir(subfolder), resSubf);

            //write an empty file inside each folder (except icons)
            if (!subfolder.equals("icons")) {
                //6.3.0 fix1
                createNomediaFileIn(resSubf);
                //FileUtil.writeFile(new File(resSubf, ".nomedia").getAbsolutePath(), "");
            }
        }

        //copy project
        File projectF = new File(outFolder, "project");
        copy(getProjectPath(), projectF);

        //find local libs used and include them in the backup
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

        //find custom blocks used and include them in the backup
        if (backupCustomBlocks) {
            CustomBlocksManager cbm = new CustomBlocksManager(sc_id);

            ArrayList<ExtraBlockInfo> blocks = new ArrayList<>();
            for (BlockBean bean : cbm.getUsedBlocks()) {
                blocks.add(BlockLoader.getBlockInfo(bean.opCode));
            }

            String json = new Gson().toJson(blocks);

            File customBlocksF = new File(dataF, "custom_blocks");
            FileUtil.writeFile(customBlocksF.getAbsolutePath(), json);
        }

        //zip final folder
        try {
            zipFolder(outFolder, outZip);
        } catch (Exception e) {
            //error occurred

            StringBuilder sb = new StringBuilder();
            for (StackTraceElement el : e.getStackTrace()) {
                sb.append(el.toString());
                sb.append("\n");
            }

            error = sb.toString();
            outPath = null;

            // throw e;

            return;
        }

        //delete the temporary folder
        FileUtil.deleteFile(outFolder.getAbsolutePath());

        //put outZip to global variable
        outPath = outZip;
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

        //init temporary restore folder for unzipping
        File outFolder = new File(getBackupDir(),
                name);

        //create a duplicate if already exists
        if (outFolder.exists()) {
            restore(swbPath, name + "_d");
            return;
        }

        //unzip
        if (unzip(swbPath, outFolder)) {

            //init files
            File project = new File(outFolder, "project");
            File data = new File(outFolder, "data");
            File res = new File(outFolder, "resources");

            HashMap<String, Object> map = getProject(project);

            if (map != null) {

                //put new sc_id
                map.put("sc_id", sc_id);

                //write new file
                if (writeEncrypted(project, new Gson().toJson(map))) {

                    //copy data
                    copy(data, getDataDir());

                    //copy res
                    for (String subfolder : resSubfolders) {
                        File subf = new File(res, subfolder);

                        copySafe(subf, getResDir(subfolder));
                    }

                    //create parent folder
                    getProjectPath().getParentFile().mkdirs();

                    //copy project
                    copy(project, getProjectPath());


                    //copy local libs if they do not exist
                    if (backupLocalLibs) {

                        File local_libs = new File(outFolder, "local_libs");

                        if (local_libs.exists()) {
                            for (File local_lib : local_libs.listFiles()) {

                                File local_lib_real_path = new File(getAllLocalLibsDir(), local_lib.getName());

                                if (!local_lib_real_path.exists()) {
                                    local_lib_real_path.mkdirs();
                                    copy(local_lib, local_lib_real_path);
                                }
                            }
                        }
                    }


                    //delete temp folder
                    FileUtil.deleteFile(outFolder.getAbsolutePath());

                    restoreSuccess = true;
                    return;

                } else {
                    error = "couldn't write to the project file";
                    restoreSuccess = false;
                    return;
                }

            } else {
                error = "couldn't read the project file";
                restoreSuccess = false;
                return;
            }

        } else {
            error = "couldn't unzip the backup";
            restoreSuccess = false;
            return;
        }
    }

    public String getError() {
        return error;
    }

    public boolean isRestoreSuccess() {
        return restoreSuccess;
    }

    /************************ SW METHODS ************************/

    private void createBackupsFolder() {
        // create the backups folder if not exists
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
