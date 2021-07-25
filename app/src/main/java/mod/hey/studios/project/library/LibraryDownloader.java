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
import android.widget.Button;
import android.graphics.*;
import android.graphics.drawable.*;
import com.android.tools.r8.D8;
import com.google.gson.Gson;
import com.google.android.material.textfield.*;
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
    private AlertDialog.Builder InfoDialog;
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
        View view = inflater.inflate(R.layout.library_downloader_dialog, null);
        final LinearLayout linear1 = view.findViewById(R.id.linear1); // body
        final ImageView img1 = view.findViewById(R.id.img1); // info
        final ProgressBar progressbar1 = view.findViewById(R.id.progressbar1); // progressbar
        final ImageView img2 = view.findViewById(R.id.img2); // pause and resume
        final TextView textview2 = view.findViewById(R.id.textview2); // status info 
        final Button btn1 = view.findViewById(R.id.btn1); // start
        final Button btn2 = view.findViewById(R.id.btn2); // cancel
        final TextInputLayout textinputlayout1 = view.findViewById(R.id.textinputlayout1); // textininputlayout
        final LinearLayout linear4 = view.findViewById(R.id.linear4); // progressbar holder
        final EditText edittext1 = view.findViewById(R.id.edittext1); // edittext
       

        /* Dialog Designs*/
        textinputlayout1.setBoxBackgroundMode(TextInputLayout.BOX_BACKGROUND_OUTLINE);
        textinputlayout1.setBoxCornerRadii((float)10, (float)10, (float)10, (float)10);
        textinputlayout1.setBoxStrokeColor(0xFF2196F3);
        textinputlayout1.setErrorEnabled(true);
        btn1.setBackground(new GradientDrawable() { public GradientDrawable getIns(int a, int b) { this.setCornerRadius(a); this.setColor(b); return this; } }.getIns((int)10, 0xFF2196F3));
        btn2.setBackground(new GradientDrawable() { public GradientDrawable getIns(int a, int b, int c, int d) { this.setCornerRadius(a); this.setStroke(b, c); this.setColor(d); return this; } }.getIns((int)10, (int)2, 0xFF2196F3, 0xFFE3F2FD));
        img1.setColorFilter(0xFF9E9E9E, PorterDuff.Mode.MULTIPLY);
        img2.setColorFilter(0xFF9E9E9E, PorterDuff.Mode.MULTIPLY);

        linear4.setVisibility(View.GONE); // progress bar linear
        textinputlayout1.setErrorEnabled(true);

        builder.setView(view);
        dialog = builder.create();
        dialog.show();

        btn1.setOnClickListener(v1 -> {
            if (edittext1.getText().toString().trim().isEmpty()) {
               textinputlayout1.setError("Dependency can't be empty");
            } else{ 
		    if (!edittext1.getText().toString().contains(":")) {
                textinputlayout1.setError("Invalid Dependency");
            } else {
                libName = downloadPath + _getLibName(edittext1.getText().toString());
                if (!FileUtil.isExistFile(libName)) {
                    FileUtil.makeDir(libName);
 }
                isAarDownloaded = false;
                isAarAvailable = false;
                _getRepository();
                counter = 0;
                currentRepo = repoUrls.get((int) counter);
                downloadId = _download(
                        currentRepo.concat(_getAarDownloadLink(edittext1.getText().toString())),
                        downloadPath,
                        _getLibName(edittext1.getText().toString()).concat(".zip"),
                        edittext1,
                        textview2,
                        linear4,
                        textinputlayout1,
                        btn1,
                        btn2,
                        img2,
                        progressbar1
                );
            }
        });
		

	 img1.setOnClickListener(v1 -> {
		InfoDialog.setTitle("Info");
		InfoDialog.setIcon(R.drawable.ic_info_outline_grey);
		InfoDialog.setMessage("You can find the dependency of a library in Github, typically in the Readme file. However, please note that this is not Gradle system, so it won't download inner dependencies of a library. If the library you download has inner dependencies, you will need to download them seperately.");
		InfoDialog.setPositiveButton("Done", new DialogInterface.OnClickListener() {

		        @Override
			public void onClick(DialogInterface _dialog, int _which) {
                           // obviously do nothing
					}
				});
				InfoDialog.create().show();
			}
		});

        img2.setOnClickListener(v1 -> {
            if (PRDownloader.getStatus(downloadId) == Status.RUNNING) {
                PRDownloader.pause(downloadId);
                 img2.setImageResource(R.drawable.ic_play_grey);
            } else 
            if (PRDownloader.getStatus(downloadId) == Status.PAUSED) {
                PRDownloader.resume(downloadId);
                img2.setImageResource(R.drawable.ic_pause_grey);
            }
        });

        btn2.setOnClickListener(v1 -> {
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
                cm.add(new File(libs, "jdk/rt.jar").getAbsolutePath());
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
            final TextView textview2 /* info */,
            final LinearLayout linear4, /* progressbar linear */
            final TextInputLayout textinputlayout1, /* edittext linear */
            final Button btn1 /* start */,
            final ImageView img2 /* pause */,
            final Button btn2 /* cancel */,
            final ProgressBar progressbar1) {
        return PRDownloader
                .download(url, path, name)
                .build()
                .setOnStartOrResumeListener(() -> {
                    textview2.setText("Library found. Downloading...");
                    textinputlayout1.setVisibility(View.GONE);
                    linear4.setVisibility(View.VISIBLE);
                    btn1.setEnabled(false);
                    btn2.setEnabled(true);          
                    linear4.setEnabled(true);
                })

                .setOnPauseListener(() -> {
                    textview2.setText("Downloading paused.");
                    btn1.setEnabled(false);
                    btn2.setEnabled(true);
                    linear4.setEnabled(true);

                })
                .setOnCancelListener(() -> {
                    btn1.setEnabled(true);
                    btn2.setEnabled(false);
                    linear4.setEnabled(false);

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
                                    textview2.setText("Download completed.");
                                    String[] test = new String[]{libName.concat("/classes.jar")};
                                    new BackTask().execute(test);
                                    FileUtil.deleteFile(path2.toString());
                                    FileUtil.writeFile(libName + "/config", findPackageName(libName + "/", edittext1.getText().toString()));
                                    deleteUnnecessaryFiles(libName + "/");
                                } else {
                                    textinputlayout1.setError("This jar is not supported by Dx since Dx only supports up to Java 1.7. In order to proceed, you need to switch to D8 (if your Android version is 8+)");
                                    FileUtil.deleteFile(path2.toString());
                                }
                            } else {
                                textinputlayout1.setError("Library doesn't contain a jar file.");
                                FileUtil.deleteFile(path2.toString());
                            }
                        }
                        btn1.setEnabled(true);
                        btn2.setEnabled(false);
                        linear4.setEnabled(true);
                    }

                    @Override
                    public void onError(PRDownloader.Error e) {
                        if (e.isServerError()) {
                            if (!(isAarDownloaded || isAarAvailable)) {
                                if (counter < repoUrls.size()) {
                                    currentRepo = repoUrls.get((int) counter);
                                    String name = repoNames.get((int) counter);
                                    counter++;
                                    textview2.setText("Searching... " + counter + "/" + repoUrls.size() + " [" + name + "]");
                                    downloadId = _download(
                                            currentRepo + _getAarDownloadLink(edittext1.getText().toString()),
                                            downloadPath,
                                            _getLibName(edittext1.getText().toString()) + ".zip",
                                            edittext1,
                                            textview2,
                                            linear4,
                                            textinputlayout1,
                                            btn1,
                                            btn2,
                                            img2,
                                            progressbar1
                                    );
                                } else {
                                    FileUtil.deleteFile(libName);
                                    textinputlayout1.setError("Library is not found in saved repositories");
                                    btn1.setEnabled(true);
                                    btn2.setEnabled(false);
                                    linear4.setEnabled(true);
                                }
                            }
                        } else {
                            if (e.isConnectionError()) {
                                textview2.setText("Downloading failed. No network");
                                btn1.setEnabled(true);
                                btn2.setEnabled(false);
                                linear4.setEnabled(true);
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
        repoMap = new Gson().fromJson("[{\"url\":\"https://repo.hortonworks.com/content/repositories/releases\",\"name\":\"HortanWorks\"},{\"url\":\"https://maven.atlassian.com/content/repositories/atlassian-public\",\"name\":\"Atlassian\"},{\"url\":\"https://jitpack.io\",\"name\":\"JitPack\"},{\"url\":\"https://jcenter.bintray.com\",\"name\":\"JCenter\"},{\"url\":\"https://oss.sonatype.org/content/repositories/releases\",\"name\":\"Sonatype\"},{\"url\":\"https://repo.spring.io/plugins-release\",\"name\":\"Spring Plugins\"},{\"url\":\"https://repo.spring.io/libs-milestone\",\"name\":\"Spring Milestone\"},{\"url\":\"https://repo.maven.apache.org/maven2\",\"name\":\"Apache Maven\"},{\"url\":\"https://dl.google.com/dl/android/maven2\",\"name\":\"Google Maven\"},{\"url\":\"https://repo1.maven.org/maven2\",\"name\":\"Maven Central\"}]",
                Helper.TYPE_MAP_LIST);
        for (int _repeat14 = 0; _repeat14 < repoMap.size(); _repeat14++) {
            repoUrls.add(repoMap.get((int) counter).get("url").toString());
            repoNames.add(repoMap.get((int) counter).get("name").toString());
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

