package mod.hey.studios.project.library;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.net.Uri;
import android.os.AsyncTask;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.android.tools.r8.D8;
import com.google.gson.Gson;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;

import a.a.a.bB;
import mod.agus.jcoderz.dx.command.dexer.Main;
import mod.agus.jcoderz.lib.FileUtil;
import mod.hey.studios.lib.JarCheck;
import mod.hey.studios.lib.prdownloader.PRDownloader;
import mod.hey.studios.lib.prdownloader.PRDownloader.OnDownloadListener;
import mod.hey.studios.lib.prdownloader.PRDownloader.Status;
import mod.hey.studios.util.Helper;

//changed in 6.3.0

public class LibraryDownloader {

    private final String downloadPath;
    private final ArrayList<String> repoUrls = new ArrayList<>();
    private final ArrayList<String> repoNames = new ArrayList<>();
    Activity context;
    boolean use_d8;
    private OnCompleteListener listener;
    private AlertDialog dialog;
    private boolean isAarAvailable = false, isAarDownloaded = false;
    private int downloadId;
    private String libName = "";
    private String currentRepo = "";
    private double counter = 0;
    private ArrayList<HashMap<String, Object>> repoMap = new ArrayList<>();
    private ProgressDialog progressDialog;

    public LibraryDownloader(Activity context, boolean use_d8) {
        this.context = context;
        this.use_d8 = use_d8;

        downloadPath = FileUtil.getExternalStorageDir() + "/.sketchware/libs/local_libs/";
    }

    private static void mkdirs(File file, String str) {
        File file2 = new File(file, str);
        if (!file2.exists()) {
            file2.mkdirs();
        }
    }

    private static String dirpart(String str) {
        int lastIndexOf = str.lastIndexOf(File.separatorChar);
        if (lastIndexOf == -1) {
            return null;
        }

        return str.substring(0, lastIndexOf);
    }

    private static void extractFile(ZipInputStream zipInputStream, File file, String str) throws IOException {
        byte[] bArr = new byte[4096];
        BufferedOutputStream bufferedOutputStream = new BufferedOutputStream(new FileOutputStream(new File(file, str)));
        while (true) {
            int read = zipInputStream.read(bArr);

            if (read == -1) {
                bufferedOutputStream.close();
                return;
            }

            bufferedOutputStream.write(bArr, 0, read);
        }
    }

    @SuppressLint("ResourceType")
    public void showDialog(OnCompleteListener listener) {
        this.listener = listener;

        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        LayoutInflater inflater = context.getLayoutInflater();
        View view = inflater.inflate(0x7F0B01DF /* R.layout.library_downloader_dialog */, null);

        final LinearLayout linear1 = view.findViewById(0x7F080680); // R.id.linear1
        final LinearLayout linear3 = view.findViewById(0x7F0806FE); // R.id.linear3
        final ProgressBar progressbar1 = view.findViewById(0x7F0806FF); // R.id.progressbar1
        final LinearLayout linear4 = view.findViewById(0x7F080681); // R.id.linear4
        final TextView textview3 = view.findViewById(0x7F080682); // R.id.textview3
        final LinearLayout linear8 = view.findViewById(0x7F080701); // R.id.linear8
        final LinearLayout linear9 = view.findViewById(0x7F08068F); // R.id.linear9
        final LinearLayout linear10 = view.findViewById(0x7F080692); // R.id.linear10
        final LinearLayout linear11 = view.findViewById(0x7F080694); // R.id.linear11
        final EditText edittext1 = view.findViewById(0x7F080702); // R.id.edittext1

        linear1.removeView(linear3);

        builder.setView(view);
        dialog = builder.create();
        dialog.show();

        linear8.setOnClickListener(v1 -> {
            if (edittext1.getText().toString().trim().isEmpty()) {
                bB.a(context, "Dependency can't be empty", 0).show();

            } else if (!edittext1.getText().toString().contains(":")) {
                bB.a(context, "Invalid dependency", 0).show();

            } else {
                libName = downloadPath + _getLibName(edittext1.getText().toString());

                if (!FileUtil.isExistFile(libName)) {
                    FileUtil.makeDir(libName);
                }

                isAarDownloaded = false;
                isAarAvailable = false;

/* Disable and hide exittext and buttons when start button clicked 
This is to prevent accidental double click in which the Library downloader triggers multiple times */

                edittext1.setEnabled(false);
                
                linear8.setEnabled(false);
                linear8.setVisibility(View.GONE);

                linear9.setEnabled(false);
                linear9.setVisibility(View.GONE);

                linear10.setEnabled(false);
                linear10.setVisibility(View.GONE);

                linear11.setEnabled(true);
                linear11.setVisibility(View.VISIBLE);
         

                _getRepository();
                counter = 0;
                currentRepo = repoUrls.get((int) counter);

                downloadId = _download(
                        currentRepo.concat(_getAarDownloadLink(edittext1.getText().toString())),
                        downloadPath,
                        _getLibName(edittext1.getText().toString()).concat(".zip"),
                        edittext1,
                        textview3,
                        linear3,
                        linear4,
                        linear8,
                        linear9,
                        linear10,
                        linear11,
                        progressbar1
                );
            }
        });

        linear9.setOnClickListener(v1 -> {
            if (PRDownloader.getStatus(downloadId) == Status.RUNNING) {
                PRDownloader.pause(downloadId);
            }
        });

        linear10.setOnClickListener(v1 -> {
            if (PRDownloader.getStatus(downloadId) == Status.PAUSED) {
                PRDownloader.resume(downloadId);
            }
        });

        linear11.setOnClickListener(v1 -> {
            PRDownloader.cancel(downloadId);
            edittext1.setEnabled(false);
            dialog.dismiss();
        });
    }

    private String _getAarDownloadLink(String str) {
        String[] split = str.split(":");
        String str2 = "/";

        for (int i = 0; i < split.length - 1; i++) {
            str2 = str2.concat(split[i].replace(".", "/") + "/");
        }

        return str2.concat(split[split.length - 1]).concat("/").concat(_getAarName(str));
    }

    private String _getAarName(String str) {
        String[] split = str.split(":");
        return split[split.length - 2] + "-" + split[split.length - 1] + ".aar";
    }

    private String _getLibName(String str) {
        String[] split = str.split(":");
        return split[split.length - 2] + "_V_" + split[split.length - 1];
    }

    private void _jar2dex(String _path) throws Exception {
        try {
            // 6.3.0
            if (use_d8) {
                File libs = new File(context.getFilesDir(), "libs");

                ArrayList<String> cm = new ArrayList<>();
                cm.add("--release");
                cm.add("--intermediate");

                cm.add("--lib");
                cm.add(new File(libs, "android.jar").getAbsolutePath());

                cm.add("--classpath");
                cm.add(new File(libs, "core-lambda-stubs.jar").getAbsolutePath());

                cm.add("--output");
                cm.add(new File(_path).getParentFile().getAbsolutePath());

                // Input
                cm.add(_path);

                D8.main(cm.toArray(new String[0]));
            } else {
                // 6.3.0 fix2
                Main.dexOutputArrays = new ArrayList<>();
                Main.dexOutputFutures = new ArrayList<>();

                // dx
                Main.main(new String[]{
                        // 6.3.0 fix1
                        // "--dex",
                        "--debug",
                        "--verbose",
                        "--multi-dex",
                        "--output=" + new File(_path).getParentFile().getAbsolutePath(),
                        _path
                });
            }
        } catch (Exception e) {
            throw e;
        }
    }

    private void _unZipFile(String str, String str2) {
        try {
            File file = new File(str2);
            ZipInputStream zipInputStream = new ZipInputStream(new FileInputStream(str));

            while (true) {
                ZipEntry nextEntry = zipInputStream.getNextEntry();

                if (nextEntry == null) {
                    zipInputStream.close();
                    return;
                }

                String name = nextEntry.getName();

                if (nextEntry.isDirectory()) {
                    mkdirs(file, name);
                } else {
                    String dirpart = dirpart(name);

                    if (dirpart != null) {
                        mkdirs(file, dirpart);
                    }

                    extractFile(zipInputStream, file, name);
                }
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private String getLastSegment(String path) {
        return Uri.parse(path).getLastPathSegment();
    }

    private String findPackageName(String path, String defaultValue) {
        ArrayList<String> files = new ArrayList<>();
        FileUtil.listDir(path, files);

        // Method 1: use manifest
        for (String f : files) {
            if (getLastSegment(f).equals("AndroidManifest.xml")) {
                String content = FileUtil.readFile(f);

                Pattern p = Pattern.compile("<manifest.*package=\"(.*?)\"", Pattern.DOTALL);
                Matcher m = p.matcher(content);

                if (m.find()) {
                    return m.group(1);
                }
            }
        }

        // Method 2: screw manifest. use dependency
        if (defaultValue.contains(":")) {
            return defaultValue.split(":")[0];
        }

        // Method 3: nothing worked. return empty string (lmao) (yeah lmao)
        return "";
    }

    private void deleteUnnecessaryFiles(String path) {

        // 6.3.0
        String[] list = {
                "res",
                "classes.dex",
                "classes.jar",
                "config",
                "AndroidManifest.xml",
                "jni",
                "assets",
                "proguard.txt"
        };

        List<String> validFiles = new ArrayList<>(Arrays.asList(list));
        ArrayList<String> files = new ArrayList<>();
        FileUtil.listDir(path, files);

        for (String f : files) {
            // 6.3.0
            // Skip all dex files
            String p = getLastSegment(f);

            if (p.startsWith("classes") && p.endsWith(".dex")) continue;
            if (!validFiles.contains(p)) FileUtil.deleteFile(f);
        }
    }

    private int _download(
            final String url,
            final String path,
            final String name,

            final EditText edittext1 /* edittext */,
            final TextView textview3 /* info */,
            final LinearLayout linear3, /* progressbar linear */
            final LinearLayout linear4, /* edittext linear */

            final LinearLayout linear8 /* start */,
            final LinearLayout linear9 /* pause */,
            final LinearLayout linear10 /* resume */,
            final LinearLayout linear11 /* cancel */,

            final ProgressBar progressbar1) {

        return PRDownloader
                .download(url, path, name)
                .build()
                .setOnStartOrResumeListener(() -> {
                    textview3.setText("Library found. Downloading...");
                    edittext1.setEnabled(false);
                    linear4.removeAllViews();
                    linear4.addView(linear3);

                    linear8.setEnabled(false);
                    linear8.setVisibility(View.GONE);

                    linear9.setEnabled(true);
                    linear9.setVisibility(View.VISIBLE);

                    linear10.setEnabled(false);
                    linear10.setVisibility(View.GONE);

                    linear11.setEnabled(true);
                    linear11.setVisibility(View.VISIBLE);
                })
                .setOnPauseListener(() -> {
                    textview3.setText("Downloading paused.");

                    linear8.setEnabled(false);
                    linear8.setVisibility(View.GONE);

                    linear9.setEnabled(false);
                    linear9.setVisibility(View.GONE);

                    linear10.setEnabled(true);
                    linear10.setVisibility(View.VISIBLE);

                    linear11.setEnabled(true);
                    linear11.setVisibility(View.VISIBLE);
                })
                .setOnCancelListener(() -> {
                    edittext1.setEnabled(true);

                    linear8.setEnabled(true);
                    linear8.setVisibility(View.VISIBLE);

                    linear9.setEnabled(false);
                    linear9.setVisibility(View.GONE);

                    linear10.setEnabled(false);
                    linear10.setVisibility(View.GONE);

                    linear11.setEnabled(false);
                    linear11.setVisibility(View.GONE);
                })
                .setOnProgressListener(progress -> {
                    int progressPercent = (int) (progress.currentBytes * 100 / progress.totalBytes);
                    progressbar1.setProgress(progressPercent);
                })
                .start(new OnDownloadListener() {
                    @Override
                    public void onDownloadComplete() {
                        isAarAvailable = true;
                        isAarDownloaded = true;

                        StringBuilder path2 = new StringBuilder();
                        //path2.append(FileUtil.getExternalStorageDir());
                        //path2.append("/Manager/libs/");
                        path2.append(downloadPath);
                        path2.append(_getLibName(edittext1.getText().toString()).concat(".zip"));

                        if (isAarDownloaded && isAarAvailable) {
                            _unZipFile(path2.toString(), libName);
                            if (FileUtil.isExistFile(libName.concat("/classes.jar"))) {
                                if (use_d8 || JarCheck.checkJar(libName.concat("/classes.jar"), 44, 51)) {
                                    textview3.setText("Download completed.");

                                    String[] test = new String[]{libName.concat("/classes.jar")};
                                    new BackTask().execute(test);
                                    FileUtil.deleteFile(path2.toString());

                                    FileUtil.writeFile(libName + "/config", findPackageName(libName + "/", edittext1.getText().toString()));

                                    deleteUnnecessaryFiles(libName + "/");

                                } else {
                                    textview3.setText("This jar is not supported by Dx since Dx only supports up to Java 1.7. In order to proceed, you need to switch to D8 (if your Android version is 8+)");
                                    FileUtil.deleteFile(path2.toString());
                                }
                            } else {
                                textview3.setText("Library doesn't contain a jar file.");
                                FileUtil.deleteFile(path2.toString());
                            }
                        }

                        edittext1.setEnabled(true);
                        linear8.setEnabled(true);
                        linear8.setVisibility(View.VISIBLE);

                        linear9.setEnabled(false);
                        linear9.setVisibility(View.GONE);

                        linear10.setEnabled(false);
                        linear10.setVisibility(View.GONE);

                        linear11.setEnabled(true);
                        linear11.setVisibility(View.VISIBLE);
                    }

                    @Override
                    public void onError(PRDownloader.Error e) {
                        if (e.isServerError()) {
                            if (!(isAarDownloaded || isAarAvailable)) {
                                if (counter < repoUrls.size()) {
                                    currentRepo = repoUrls.get((int) counter);
                                    String name = repoNames.get((int) counter);

                                    counter++;
                                    textview3.setText("Searching... " + counter + "/" + repoUrls.size() + " [" + name + "]");

                                    downloadId = _download(
                                            currentRepo + _getAarDownloadLink(edittext1.getText().toString()),
                                            downloadPath,
                                            _getLibName(edittext1.getText().toString()) + ".zip",
                                            edittext1,
                                            textview3,
                                            linear3,
                                            linear4,
                                            linear8,
                                            linear9,
                                            linear10,
                                            linear11,
                                            progressbar1
                                    );

                                } else {
                                    FileUtil.deleteFile(libName);
                                    textview3.setText("Library is not found in loaded repositories");
                                    edittext1.setEnabled(true);
                                    linear8.setEnabled(true);
                                    linear8.setVisibility(View.VISIBLE);

                                    linear9.setEnabled(false);
                                    linear9.setVisibility(View.GONE);

                                    linear10.setEnabled(false);
                                    linear10.setVisibility(View.GONE);

                                    linear11.setEnabled(true);
                                    linear11.setVisibility(View.VISIBLE);
                                }
                            }
                        } else {
                            if (e.isConnectionError()) {
                                textview3.setText("Downloading failed. No network");
                                edittext1.setEnabled(true);
                                linear8.setEnabled(true);
                                linear8.setVisibility(View.VISIBLE);

                                linear9.setEnabled(false);
                                linear9.setVisibility(View.GONE);

                                linear10.setEnabled(false);
                                linear10.setVisibility(View.GONE);

                                linear11.setEnabled(true);
                                linear11.setVisibility(View.VISIBLE);

                            }
                        }
                    }
                });
    }

    private void _getRepository() {
        repoUrls.clear();
        repoMap.clear();
        repoNames.clear();
        counter = 0;
        
        /* Extracted Default Library Repo Links So User Can Modify If Want */
 
        if ((!FileUtil.isExistFile(FileUtil.getExternalStorageDir() + "/.sketchware/libs/repo_map.json")) || FileUtil.readFile(FileUtil.getExternalStorageDir() + "/.sketchware/libs/repo_map.json").trim().equals("")) {
	FileUtil.writeFile(FileUtil.getExternalStorageDir() + "/.sketchware/libs/repo_map.json", "[{\"url\":\"https://repo.hortonworks.com/content/repositories/releases\",\"name\":\"HortanWorks\"},{\"url\":\"https://maven.atlassian.com/content/repositories/atlassian-public\",\"name\":\"Atlassian\"},{\"url\":\"https://jitpack.io\",\"name\":\"JitPack\"},{\"url\":\"https://jcenter.bintray.com\",\"name\":\"JCenter\"},{\"url\":\"https://oss.sonatype.org/content/repositories/releases\",\"name\":\"Sonatype\"},{\"url\":\"https://repo.spring.io/plugins-release\",\"name\":\"Spring Plugins\"},{\"url\":\"https://repo.spring.io/libs-milestone\",\"name\":\"Spring Milestone\"},{\"url\":\"https://repo.maven.apache.org/maven2\",\"name\":\"Apache Maven\"},{\"url\":\"https://dl.google.com/dl/android/maven2\",\"name\":\"Google Maven\"},{\"url\":\"https://repo1.maven.org/maven2\",\"name\":\"Maven Central\"}]");
         }

        try{
	repoMap = new Gson().fromJson(FileUtil.readFile(FileUtil.getExternalStorageDir() + "/.sketchware/libs/repo_map.json" , Helper.TYPE_MAP_LIST);
        }catch(Exception e){
	repoMap = new Gson().fromJson("[{\"url\":\"https://repo.hortonworks.com/content/repositories/releases\",\"name\":\"HortanWorks\"},{\"url\":\"https://maven.atlassian.com/content/repositories/atlassian-public\",\"name\":\"Atlassian\"},{\"url\":\"https://jitpack.io\",\"name\":\"JitPack\"},{\"url\":\"https://jcenter.bintray.com\",\"name\":\"JCenter\"},{\"url\":\"https://oss.sonatype.org/content/repositories/releases\",\"name\":\"Sonatype\"},{\"url\":\"https://repo.spring.io/plugins-release\",\"name\":\"Spring Plugins\"},{\"url\":\"https://repo.spring.io/libs-milestone\",\"name\":\"Spring Milestone\"},{\"url\":\"https://repo.maven.apache.org/maven2\",\"name\":\"Apache Maven\"},{\"url\":\"https://dl.google.com/dl/android/maven2\",\"name\":\"Google Maven\"},{\"url\":\"https://repo1.maven.org/maven2\",\"name\":\"Maven Central\"}]", Helper.TYPE_MAP_LIST);	
        bB.a(context, "Failed to load repo list from sdcard. Using default repo lists.\nError:" + e.toString(), 0).show();
        } 
     
  for (int _repeat14 = 0; _repeat14 < repoMap.size(); _repeat14++) {
          if (repoMap.containsKey("url") && repoMap.containsKey("name")) {
            repoUrls.add(repoMap.get((int) counter).get("url").toString());
            repoNames.add(repoMap.get((int) counter).get("name").toString());
            }
            counter++;
        }
    }

    public interface OnCompleteListener {
        void onComplete();
    }

    private class BackTask extends AsyncTask<String, String, String> {
        boolean success = false;

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            progressDialog = new ProgressDialog(context);
            progressDialog.setTitle("Please wait");
            progressDialog.setMessage((use_d8 ? "D8" : "Dx") + " is running...");
            progressDialog.setCancelable(false);
            progressDialog.show();
        }

        @Override
        protected String doInBackground(String... params) {
            try {
                _jar2dex(params[0]);
                success = true;
            } catch (Exception e) {
                success = false;
                return e.toString();
            }

            return "true";
        }

        @Override
        protected void onPostExecute(String s) {
            if (success) {
                bB.a(context, "The library has been downloaded and imported to local libraries successfully.", 1).show();
                listener.onComplete();
            } else {
                bB.a(context, "Dexing failed: " + s, 1).show();
            }

            if (dialog != null && dialog.isShowing()) {
                dialog.dismiss();
            }

            progressDialog.dismiss();
        }
    }
}
