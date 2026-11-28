package mod.hey.studios.project.backup;

import android.content.Context;
import android.os.Environment;
import android.util.Log;

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
import pro.sketchware.R;
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
                // Zip-slip guard: a malicious .swb can contain entries like "../mysc/list/<id>/project"
                // that would escape the extraction folder and overwrite other projects' data.
                try {
                    if (!destFile.getCanonicalPath().startsWith(
                            destinationDir.getCanonicalPath() + File.separator)) {
                        Log.e(BackupFactory.class.getSimpleName(),
                                "zip entry escapes destination: " + entryName);
                        return false;
                    }
                } catch (IOException e) {
                    return false;
                }
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
    public static boolean copySafe(File source, File destination) {
        if (!source.exists()) {
            destination.mkdirs();
            createNomediaFileIn(destination);
            return true;
        } else {
            return copy(source, destination);
        }
    }

    /**
     * Recursively copies a file or directory.
     *
     * @return true if everything was copied, false if reading or writing any file failed
     * (in which case the destination may contain partial data).
     */
    public static boolean copy(File source, File destination) {
        if (source.isDirectory()) {
            if (!destination.exists() && !destination.mkdirs() && !destination.exists()) {
                return false;
            }

            String[] files = source.list();
            if (files != null) {

                for (String file : files) {
                    File srcFile = new File(source, file);
                    File destFile = new File(destination, file);

                    if (!copy(srcFile, destFile)) {
                        return false;
                    }
                }
                return true;
            }
            // null = enumeration I/O error (an empty directory returns an empty array)
            return false;
        } else {
            //skip .nomedia files
            if (source.getName().equals(".nomedia")) return true;

            try (InputStream in = new FileInputStream(source);
                 OutputStream out = new FileOutputStream(destination)) {
                byte[] buffer = new byte[1024];
                int length;
                while ((length = in.read(buffer)) > 0) {
                    out.write(buffer, 0, length);
                }
                return true;
            } catch (IOException e) {
                e.printStackTrace();
                return false;
            }
        }
    }

    public static boolean zipContainsFile(String zipPath, String fileName) {

        try (ZipInputStream zp = new ZipInputStream(new FileInputStream(zipPath))) {
            ZipEntry en;

            while ((en = zp.getNextEntry()) != null) {
                String name = en.getName();

                if (name.equals(fileName) || name.startsWith(fileName + File.separator)) {
                    return true;
                }
            }
        } catch (Exception ignored) {
        }

        return false;
    }

    /************************ BACKUP ************************/

    public void backup(Context context, String project_name) {
        String customFileName = ConfigActivity.getBackupFileName();

        String versionName = yB.c(lC.b(sc_id), "sc_ver_name");
        String versionCode = yB.c(lC.b(sc_id), "sc_ver_code");
        String pkgName = yB.c(lC.b(sc_id), "my_sc_pkg_name");
        String sanitizedProjectName = project_name.replace(File.separator, "");
        String projectNameOnly = sanitizedProjectName;
        while (projectNameOnly.endsWith("_d")) {
            projectNameOnly = projectNameOnly.substring(0, projectNameOnly.length() - 2);
        }
        // Derive the duplicate suffix from the sanitized name too: slicing the original
        // name at the sanitized length could leave a File.separator (and extra "_d" text)
        // inside the suffix, producing an output path with a separator in it.
        String duplicateSuffix = sanitizedProjectName.substring(projectNameOnly.length());
        String finalFileName;

        try {
            finalFileName = customFileName
                    .replace("$projectName", projectNameOnly)
                    .replace("$versionCode", versionCode)
                    .replace("$versionName", versionName)
                    .replace("$pkgName", pkgName)
                    .replace("$versionCode", versionCode)
                    .replace("$timeInMs", String.valueOf(Calendar.getInstance(Locale.ENGLISH).getTimeInMillis()));
            Matcher matcher = Pattern.compile("\\$time\\((.*?)\\)").matcher(customFileName);
            while (matcher.find()) {
                finalFileName = finalFileName.replaceFirst(Pattern.quote(Objects.requireNonNull(matcher.group(0))), getFormattedDateFrom(matcher.group(1)));
            }
        } catch (Exception ignored) {
            SketchwareUtil.toastError(Helper.getResString(R.string.backup_factory_backup_filename_parse_failed));
            // Example name: InternalDemo v1.0 (com.jbk.internal.demo, 1) 2021-12-31T125827
            finalFileName = projectNameOnly + " v" + versionName + " (" + pkgName + ", " + versionCode + ") " + getFormattedDateFrom("yyyy-M-dd'T'HHmmss");
        }
        createBackupsFolder();

        // Init temporary backup folder
        File outFolder = new File(getBackupDir(),
                project_name + "_temp");

        // Init output zip file
        File outZip = new File(getBackupDir() + File.separator + projectNameOnly, finalFileName +
                //Adds the accumulated _d duplicate suffixes (if any)
                duplicateSuffix + "." + EXTENSION);

        // Create a duplicate if already exists (impossible now :3)
        if (outZip.exists()) {
            backup(context, project_name + "_d");
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
        if (!copySafe(getDataDir(), dataF)) {
            error = "couldn't copy project data";
            outPath = null;
            return;
        }

        // Copy res
        File resF = new File(outFolder, "resources");
        FileUtil.makeDir(resF.getAbsolutePath());

        for (String subfolder : resSubfolders) {
            File resSubf = new File(resF, subfolder);
            FileUtil.makeDir(resSubf.getAbsolutePath());

            //6.3.0 fix1
            if (!copySafe(getResDir(subfolder), resSubf)) {
                error = "couldn't copy project resources (" + subfolder + ")";
                outPath = null;
                return;
            }

            // Write an empty file inside each folder (except icons)
            if (!subfolder.equals("icons")) {
                //6.3.0 fix1
                createNomediaFileIn(resSubf);
                //FileUtil.writeFile(new File(resSubf, ".nomedia").getAbsolutePath(), "");
            }
        }

        // Copy project
        File projectF = new File(outFolder, "project");
        if (!copy(getProjectPath(), projectF)) {
            error = "couldn't copy the project file";
            outPath = null;
            return;
        }

        // Find local libs used and include them in the backup
        if (backupLocalLibs) {
            File localLibs = getLocalLibsPath();

            if (localLibs.exists()) {
                JSONArray ja;
                try {
                    ja = new JSONArray(FileUtil.readFile(localLibs.getAbsolutePath()));
                } catch (Exception e) {
                    error = "couldn't read the local library registry: " + e.getMessage();
                    return;
                }

                File libsF = new File(outFolder, "local_libs");
                libsF.mkdirs();

                // Resolve the lib directory by name (the registry folder name is the
                // library's identity); deriving it from dexPath silently skipped
                // JAR-only libraries (no classes.dex) and aborted the whole copy
                // on the first such entry, leaving backups without their libraries.
                for (int i = 0; i < ja.length(); i++) {
                    try {
                        JSONObject jo = ja.getJSONObject(i);
                        String name = jo.getString("name");

                        if (name.isEmpty() || name.equals(".") || name.equals("..") || name.contains(File.separator)) {
                            continue;
                        }

                        File f = new File(getAllLocalLibsDir(), name);
                        if (f.exists()) {
                            if (!copy(f, new File(libsF, name))) {
                                error = "couldn't copy local library: " + name;
                                outPath = null;
                                return;
                            }
                        }
                    } catch (Exception e) {
                        Log.e(BackupFactory.class.getSimpleName(),
                                "skipping local library #" + i + " during backup", e);
                    }
                }
            }
        }

        // Find custom blocks used and include them in the backup
        if (backupCustomBlocks) {
            CustomBlocksManager cbm = new CustomBlocksManager(context, sc_id);

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

//            StringBuilder sb = new StringBuilder();
//            for (StackTraceElement el : e.getStackTrace()) {
//                sb.append(el.toString());
//                sb.append("\n");
//            }

            error = Log.getStackTraceString(e);
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

    private boolean restore(File swbPath, String name) {

        createBackupsFolder();

        // Init temporary restore folder for unzipping
        File outFolder = new File(getBackupDir(),
                name);

        // Create a duplicate if already exists
        if (outFolder.exists()) {
            return restore(swbPath, name + "_d");
        }

        // Unzip
        if (!unzip(swbPath, outFolder)) {
            error = Helper.getResString(R.string.backup_factory_backup_unzip_failed);
            restoreSuccess = false;
            FileUtil.deleteFile(outFolder.getAbsolutePath());
            return false;
        }

        // Init files
        File project = new File(outFolder, "project");
        File data = new File(outFolder, "data");
        File res = new File(outFolder, "resources");

        HashMap<String, Object> map = getProject(project);

        if (map == null) {
            error = Helper.getResString(R.string.backup_factory_backup_read_project_failed);
            restoreSuccess = false;
            FileUtil.deleteFile(outFolder.getAbsolutePath());
            return false;
        }

        // Put new sc_id
        map.put("sc_id", sc_id);

        // Write new file
        if (!writeEncrypted(project, new Gson().toJson(map))) {
            error = Helper.getResString(R.string.backup_factory_backup_write_project_failed);
            restoreSuccess = false;
            FileUtil.deleteFile(outFolder.getAbsolutePath());
            return false;
        }

        // Copy data.
        // Snapshot pre-existing children so a failed copy can be rolled back: leaving
        // half-copied sc_id folders in the live data dir leaks files into the next
        // project that reuses the same id (copy never deletes destination-only files).
        // A null snapshot means the listing itself failed - abort rather than risk
        // rolling back pre-existing children later.
        java.util.HashSet<String> preExistingData = listChildNames(getDataDir());
        if (preExistingData == null) {
            error = "couldn't list existing project data";
            restoreSuccess = false;
            FileUtil.deleteFile(outFolder.getAbsolutePath());
            return false;
        }
        if (!copy(data, getDataDir())) {
            error = "couldn't copy project data";
            restoreSuccess = false;
            rollbackNewChildren(getDataDir(), preExistingData);
            FileUtil.deleteFile(outFolder.getAbsolutePath());
            return false;
        }

        // Copy res
        java.util.ArrayList<Object[]> resourceRollbacks = new java.util.ArrayList<>();
        for (String subfolder : resSubfolders) {
            File subf = new File(res, subfolder);
            File resDir = getResDir(subfolder);
            java.util.HashSet<String> preExistingRes = listChildNames(resDir);
            if (preExistingRes == null) {
                error = "couldn't list existing project resources (" + subfolder + ")";
                restoreSuccess = false;
                rollbackRestoreArtifacts(preExistingData, resourceRollbacks);
                FileUtil.deleteFile(outFolder.getAbsolutePath());
                return false;
            }

            if (!copySafe(subf, resDir)) {
                error = "couldn't copy project resources (" + subfolder + ")";
                restoreSuccess = false;
                rollbackRestoreArtifacts(preExistingData, resourceRollbacks);
                FileUtil.deleteFile(outFolder.getAbsolutePath());
                return false;
            }
            resourceRollbacks.add(new Object[]{resDir, preExistingRes});
        }

        // Restore local libraries BEFORE publishing the project file: the project must
        // never appear in the project list with required libraries missing. A library
        // merge failure leaves the shared local-libs dir untouched for existing libs
        // (mergeMissingFiles only adds missing files), so aborting here is safe.
        if (backupLocalLibs) {
            File local_libs = new File(outFolder, "local_libs");

            if (local_libs.exists()) {
                File[] local_libs_content = local_libs.listFiles();
                // null = enumeration I/O error (an empty directory returns an empty array).
                // Skipping silently would publish a project with missing libraries.
                if (local_libs_content == null) {
                    error = "couldn't list extracted local libraries";
                    restoreSuccess = false;
                    rollbackRestoreArtifacts(preExistingData, resourceRollbacks);
                    FileUtil.deleteFile(outFolder.getAbsolutePath());
                    return false;
                }

                    for (File local_lib : local_libs_content) {

                        File local_lib_real_path = new File(getAllLocalLibsDir(), local_lib.getName());

                        if (!local_lib_real_path.exists()) {
                            // Stage into a temp dir and publish with a rename: a failed
                            // straight copy leaves a partial library in the live dir, and a
                            // retry's mergeMissingFiles() would then skip the corrupt file.
                            File staging = new File(getAllLocalLibsDir(), local_lib.getName() + ".restore_tmp");
                            FileUtil.deleteFile(staging.getAbsolutePath());
                            if (!copy(local_lib, staging)
                                    || !staging.renameTo(local_lib_real_path)) {
                                error = "couldn't restore local library: " + local_lib.getName();
                                restoreSuccess = false;
                                FileUtil.deleteFile(staging.getAbsolutePath());
                                rollbackRestoreArtifacts(preExistingData, resourceRollbacks);
                                FileUtil.deleteFile(outFolder.getAbsolutePath());
                                return false;
                            }
                        } else {
                            // The library already exists. Merge any files that are missing
                            // from the installed copy (e.g. a partially unpacked library),
                            // but never overwrite existing files with backup versions.
                            if (!mergeMissingFiles(local_lib, local_lib_real_path)) {
                                error = "failed to restore local library: " + local_lib.getName();
                                restoreSuccess = false;
                                rollbackRestoreArtifacts(preExistingData, resourceRollbacks);
                                FileUtil.deleteFile(outFolder.getAbsolutePath());
                                return false;
                            }
                        }
                    }
            }
        }

        // Copy project (this "publishes" the restored project - it runs last on purpose).
        // Copy through a temp file first: copy() truncates the destination up front, so a
        // mid-copy I/O error would destroy an existing project at this sc_id.
        getProjectPath().getParentFile().mkdirs();
        boolean projectExisted = getProjectPath().exists();
        File publishTemp = new File(getProjectPath().getParentFile(), getProjectPath().getName() + ".restore_tmp");
        boolean published = false;
        if (copy(project, publishTemp)) {
            if (!projectExisted || getProjectPath().delete()) {
                published = publishTemp.renameTo(getProjectPath());
            }
        }
        if (!published) {
            error = "couldn't copy the project file";
            restoreSuccess = false;
            FileUtil.deleteFile(publishTemp.getAbsolutePath());
            if (!projectExisted) {
                FileUtil.deleteFile(getProjectPath().getAbsolutePath());
            }
            rollbackRestoreArtifacts(preExistingData, resourceRollbacks);
            FileUtil.deleteFile(outFolder.getAbsolutePath());
            return false;
        }

        // Delete temp folder
        FileUtil.deleteFile(outFolder.getAbsolutePath());

        restoreSuccess = true;
        return true;
    }

    /** Rolls back data and resource children copied by a failed restore. */
    private void rollbackRestoreArtifacts(java.util.HashSet<String> preExistingData,
            java.util.ArrayList<Object[]> resourceRollbacks) {
        rollbackNewChildren(getDataDir(), preExistingData);
        for (Object[] rollback : resourceRollbacks) {
            rollbackNewChildren((File) rollback[0], (java.util.HashSet<String>) rollback[1]);
        }
    }

    /**
     * Copies {@code source} to {@code destination} if the destination does not exist, through a
     * temporary file so that an interrupted copy never leaves a partial file at the destination.
     *
     * @return true if the destination exists afterwards (pre-existing or copied successfully),
     * false if the copy failed and nothing was published.
     */
    private boolean copyIfMissing(File source, File destination) {
        if (destination.exists()) {
            return true;
        }

        File temp = new File(destination.getParentFile(),
                destination.getName() + ".tmp_" + System.currentTimeMillis());
        try (InputStream in = new FileInputStream(source);
             OutputStream out = new FileOutputStream(temp)) {
            byte[] buffer = new byte[1024];
            int length;
            while ((length = in.read(buffer)) > 0) {
                out.write(buffer, 0, length);
            }
        } catch (IOException e) {
            FileUtil.deleteFile(temp.getAbsolutePath());
            return false;
        }

        // Another restore may have created the destination meanwhile; prefer the existing
        // file. Deleting our temp copy is a success - the file the backup wanted is there.
        if (destination.exists()) {
            temp.delete();
            return true;
        }
        return temp.renameTo(destination);
    }

    /**
     * @return names of the direct children of {@code dir}, an empty set if it doesn't exist,
     * or {@code null} if listing an existing directory failed (I/O error) - callers must
     * distinguish these, otherwise a failed snapshot would look like "nothing existed" and
     * a later rollback could delete pre-existing children.
     */
    private static java.util.HashSet<String> listChildNames(File dir) {
        java.util.HashSet<String> names = new java.util.HashSet<>();
        File[] children = dir.listFiles();
        if (children == null) {
            if (dir.exists()) return null;
            return names;
        }
        for (File child : children) {
            names.add(child.getName());
        }
        return names;
    }

    /** Best-effort rollback: deletes direct children of {@code dir} that weren't in {@code preExisting}. */
    private static void rollbackNewChildren(File dir, java.util.HashSet<String> preExisting) {
        File[] children = dir.listFiles();
        if (children == null) return;
        for (File child : children) {
            if (!preExisting.contains(child.getName())) {
                FileUtil.deleteFile(child.getAbsolutePath());
            }
        }
    }

    private boolean mergeMissingFiles(File source, File destination) {
        File[] files = source.listFiles();
        // null = enumeration I/O error (an empty directory returns an empty array, not null)
        if (files == null) return false;

        for (File file : files) {
            File destFile = new File(destination, file.getName());

            if (file.isDirectory()) {
                if (!destFile.exists() && !destFile.mkdirs() && !destFile.exists()) {
                    return false;
                }
                if (!mergeMissingFiles(file, destFile)) {
                    return false;
                }
            } else if (!copyIfMissing(file, destFile)) {
                return false;
            }
        }
        return true;
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
