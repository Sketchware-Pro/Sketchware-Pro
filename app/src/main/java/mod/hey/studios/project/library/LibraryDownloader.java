package mod.hey.studios.project.library;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Environment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.android.tools.r8.D8;
import com.google.gson.Gson;
import com.google.gson.JsonParseException;
import com.sketchware.remod.R;

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

import a.a.a.Dp;
import a.a.a.bB;
import mod.SketchwareUtil;
import mod.agus.jcoderz.dx.command.dexer.Main;
import mod.agus.jcoderz.lib.FileUtil;
import mod.hey.studios.lib.JarCheck;
import mod.hey.studios.lib.prdownloader.PRDownloader;
import mod.hey.studios.lib.prdownloader.PRDownloader.OnDownloadListener;
import mod.hey.studios.lib.prdownloader.PRDownloader.Status;
import mod.hey.studios.util.Helper;
import mod.jbk.build.BuildProgressReceiver;
import mod.jbk.build.BuiltInLibraries;

//changed in 6.3.0

public class LibraryDownloader {

    public static final File CONFIGURED_REPOSITORIES_FILE = new File(Environment.getExternalStorageDirectory(),
            ".sketchware" + File.separator + "libs" + File.separator + "repositories.json");
    private static final String DEFAULT_REPOSITORIES_FILE_CONTENT = "[{\"url\":\"https://repo.hortonworks.com/content/repositories/releases\",\"name\":\"HortanWorks\"},{\"url\":\"https://maven.atlassian.com/content/repositories/atlassian-public\",\"name\":\"Atlassian\"},{\"url\":\"https://jitpack.io\",\"name\":\"JitPack\"},{\"url\":\"https://jcenter.bintray.com\",\"name\":\"JCenter\"},{\"url\":\"https://oss.sonatype.org/content/repositories/releases\",\"name\":\"Sonatype\"},{\"url\":\"https://repo.spring.io/plugins-release\",\"name\":\"Spring Plugins\"},{\"url\":\"https://repo.spring.io/libs-milestone\",\"name\":\"Spring Milestone\"},{\"url\":\"https://repo.maven.apache.org/maven2\",\"name\":\"Apache Maven\"},{\"url\":\"https://dl.google.com/dl/android/maven2\",\"name\":\"Google Maven\"},{\"url\":\"https://repo1.maven.org/maven2\",\"name\":\"Maven Central\"}]";
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
    private int counter = 0;
    private ArrayList<HashMap<String, Object>> repoMap = new ArrayList<>();

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

    public void showDialog(OnCompleteListener listener) {
        this.listener = listener;

        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        LayoutInflater inflater = context.getLayoutInflater();
        View view = inflater.inflate(R.layout.library_downloader_dialog, null);

        final LinearLayout linear1 = view.findViewById(R.id.linear1);
        final LinearLayout progressBarContainer = view.findViewById(R.id.linear3);
        final ProgressBar progressbar1 = view.findViewById(R.id.progressbar1);
        final LinearLayout libraryContainer = view.findViewById(R.id.linear4);
        final TextView message = view.findViewById(R.id.textview3);
        final LinearLayout start = view.findViewById(R.id.linear8);
        final LinearLayout pause = view.findViewById(R.id.linear9);
        final LinearLayout resume = view.findViewById(R.id.linear10);
        final LinearLayout cancel = view.findViewById(R.id.linear11);
        final EditText library = view.findViewById(R.id.edittext1);

        linear1.removeView(progressBarContainer);

        builder.setView(view);
        dialog = builder.show();

        pause.setEnabled(false);
        pause.setVisibility(View.INVISIBLE);
        resume.setEnabled(false);
        resume.setVisibility(View.INVISIBLE);
        cancel.setEnabled(false);
        cancel.setVisibility(View.INVISIBLE);

        start.setOnClickListener(startView -> {
            String dependency = library.getText().toString();

            if (dependency.isEmpty()) {
                SketchwareUtil.toastError("Dependency can't be empty");
            } else if (!dependency.contains(":")) {
                SketchwareUtil.toastError("Invalid dependency");
            } else {
                libName = downloadPath + _getLibName(dependency);

                if (!FileUtil.isExistFile(libName)) {
                    FileUtil.makeDir(libName);
                }

                isAarDownloaded = false;
                isAarAvailable = false;

                library.setEnabled(false);

                start.setEnabled(false);
                start.setVisibility(View.INVISIBLE);

                cancel.setEnabled(true);
                cancel.setVisibility(View.VISIBLE);

                _getRepository();
                counter = 0;
                currentRepo = repoUrls.get(counter);

                downloadId = _download(
                        currentRepo.concat(_getAarDownloadLink(dependency)),
                        downloadPath,
                        _getLibName(dependency + ".zip"),
                        library,
                        message,
                        progressBarContainer,
                        libraryContainer,
                        start,
                        pause,
                        resume,
                        cancel,
                        progressbar1
                );
            }
        });

        pause.setOnClickListener(pauseView -> {
            if (PRDownloader.getStatus(downloadId) == Status.RUNNING) {
                PRDownloader.pause(downloadId);
            }
        });

        resume.setOnClickListener(resumeView -> {
            if (PRDownloader.getStatus(downloadId) == Status.PAUSED) {
                PRDownloader.resume(downloadId);
            }
        });

        cancel.setOnClickListener(cancelView -> {
            PRDownloader.cancel(downloadId);
            library.setEnabled(false);
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
        // 6.3.0
        if (use_d8) {
            File libs = BuiltInLibraries.EXTRACTED_COMPILE_ASSETS_PATH;

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
            Main.clearInternTables();

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

            final EditText library,
            final TextView message,
            final LinearLayout progressBarContainer,
            final LinearLayout libraryContainer,

            final LinearLayout start,
            final LinearLayout pause,
            final LinearLayout resume,
            final LinearLayout cancel,

            final ProgressBar progressbar1) {

        return PRDownloader
                .download(url, path, name)
                .build()
                .setOnStartOrResumeListener(() -> {
                    message.setText("Library found. Downloading...");
                    library.setEnabled(false);
                    libraryContainer.removeAllViews();
                    libraryContainer.addView(progressBarContainer);

                    start.setEnabled(false);
                    start.setVisibility(View.INVISIBLE);

                    pause.setEnabled(true);
                    pause.setVisibility(View.VISIBLE);

                    resume.setEnabled(false);
                    resume.setVisibility(View.INVISIBLE);

                    cancel.setEnabled(true);
                    cancel.setVisibility(View.VISIBLE);
                })
                .setOnPauseListener(() -> {
                    message.setText("Downloading paused.");

                    start.setEnabled(false);
                    start.setVisibility(View.INVISIBLE);

                    pause.setEnabled(false);
                    pause.setVisibility(View.INVISIBLE);

                    resume.setEnabled(true);
                    resume.setVisibility(View.VISIBLE);

                    cancel.setEnabled(true);
                    cancel.setVisibility(View.VISIBLE);
                })
                .setOnCancelListener(() -> {
                    library.setEnabled(true);

                    start.setEnabled(true);
                    start.setVisibility(View.VISIBLE);

                    pause.setEnabled(false);
                    pause.setVisibility(View.INVISIBLE);

                    resume.setEnabled(false);
                    resume.setVisibility(View.INVISIBLE);

                    cancel.setEnabled(false);
                    cancel.setVisibility(View.INVISIBLE);
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
                        path2.append(downloadPath);
                        path2.append(_getLibName(library.getText().toString()).concat(".zip"));

                        if (isAarDownloaded && isAarAvailable) {
                            _unZipFile(path2.toString(), libName);
                            if (FileUtil.isExistFile(libName.concat("/classes.jar"))) {
                                if (use_d8 || JarCheck.checkJar(libName.concat("/classes.jar"), 44, 51)) {
                                    message.setText("Download completed.");

                                    String[] test = new String[]{libName.concat("/classes.jar")};
                                    new BackTask().execute(test);
                                    FileUtil.deleteFile(path2.toString());

                                    FileUtil.writeFile(libName + "/config", findPackageName(libName + "/", library.getText().toString()));

                                    deleteUnnecessaryFiles(libName + "/");

                                } else {
                                    message.setText("This jar is not supported by Dx since Dx only supports up to Java 1.7. In order to proceed, you need to switch to D8 (if your Android version is 8+)");
                                    FileUtil.deleteFile(path2.toString());
                                }
                            } else {
                                message.setText("Library doesn't contain a jar file.");
                                FileUtil.deleteFile(path2.toString());
                            }
                        }

                        library.setEnabled(true);
                        start.setEnabled(true);
                        start.setVisibility(View.VISIBLE);

                        pause.setEnabled(false);
                        pause.setVisibility(View.INVISIBLE);

                        resume.setEnabled(false);
                        resume.setVisibility(View.INVISIBLE);

                        cancel.setEnabled(false);
                        cancel.setVisibility(View.INVISIBLE);
                    }

                    @Override
                    public void onError(PRDownloader.Error e) {
                        if (e.isServerError()) {
                            if (!(isAarDownloaded || isAarAvailable)) {
                                if (counter < repoUrls.size()) {
                                    currentRepo = repoUrls.get(counter);
                                    String name = repoNames.get(counter);

                                    counter++;
                                    message.setText("Searching... " + counter + "/" + repoUrls.size() + " [" + name + "]");

                                    downloadId = _download(
                                            currentRepo + _getAarDownloadLink(library.getText().toString()),
                                            downloadPath,
                                            _getLibName(library.getText().toString()) + ".zip",
                                            library,
                                            message,
                                            progressBarContainer,
                                            libraryContainer,
                                            start,
                                            pause,
                                            resume,
                                            cancel,
                                            progressbar1
                                    );

                                } else {
                                    FileUtil.deleteFile(libName);
                                    message.setText("Library was not found in loaded repositories");
                                    library.setEnabled(true);
                                    start.setEnabled(true);
                                    start.setVisibility(View.VISIBLE);

                                    pause.setEnabled(false);
                                    pause.setVisibility(View.INVISIBLE);

                                    resume.setEnabled(false);
                                    resume.setVisibility(View.INVISIBLE);

                                    cancel.setEnabled(false);
                                    cancel.setVisibility(View.INVISIBLE);
                                }
                            }
                        } else {
                            if (e.isConnectionError()) {
                                message.setText("Downloading failed. No network");
                                library.setEnabled(true);
                                start.setEnabled(true);
                                start.setVisibility(View.VISIBLE);

                                pause.setEnabled(false);
                                pause.setVisibility(View.INVISIBLE);

                                resume.setEnabled(false);
                                resume.setVisibility(View.INVISIBLE);

                                cancel.setEnabled(false);
                                cancel.setVisibility(View.INVISIBLE);

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

        readRepositories:
        {
            String repositories;
            if (CONFIGURED_REPOSITORIES_FILE.exists() && !(repositories = FileUtil.readFile(CONFIGURED_REPOSITORIES_FILE.getAbsolutePath())).isEmpty()) {
                try {
                    repoMap = new Gson().fromJson(repositories, Helper.TYPE_MAP_LIST);

                    if (repoMap != null) {
                        break readRepositories;
                    }
                } catch (JsonParseException ignored) {
                    // fall-through to shared error toast
                }

                SketchwareUtil.showFailedToParseJsonDialog(context, CONFIGURED_REPOSITORIES_FILE, "Custom Repositories", v -> _getRepository());
            } else {
                FileUtil.writeFile(CONFIGURED_REPOSITORIES_FILE.getAbsolutePath(), DEFAULT_REPOSITORIES_FILE_CONTENT);
            }

            repoMap = new Gson().fromJson(DEFAULT_REPOSITORIES_FILE_CONTENT, Helper.TYPE_MAP_LIST);
        }

        for (HashMap<String, Object> configuration : repoMap) {
            Object repoUrl = configuration.get("url");

            if (repoUrl instanceof String) {
                Object repoName = configuration.get("name");

                if (repoName instanceof String) {
                    repoUrls.add((String) repoUrl);
                    repoNames.add((String) repoName);
                }
            }

            counter++;
        }
    }

    public interface OnCompleteListener {
        void onComplete();
    }

    private class BackTask extends AsyncTask<String, String, String> implements BuildProgressReceiver {
        private ProgressDialog progressDialog;
        boolean success = false;

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            progressDialog = new ProgressDialog(context);
            progressDialog.setTitle("Please wait");
            progressDialog.setCancelable(false);
            progressDialog.show();
        }

        @Override
        protected String doInBackground(String... params) {
            try {
                Dp.maybeExtractAndroidJar(this);
                publishProgress((use_d8 ? "D8" : "Dx") + " is running...");
                _jar2dex(params[0]);
                success = true;
            } catch (Exception e) {
                success = false;
                return Log.getStackTraceString(e);
            }

            return "true";
        }

        @Override
        protected void onPostExecute(String s) {
            if (success) {
                bB.a(context, "The library has been downloaded and imported to local libraries successfully.", 1).show();
                listener.onComplete();
            } else {
                SketchwareUtil.showAnErrorOccurredDialog(context, s);
            }

            if (dialog != null && dialog.isShowing()) {
                dialog.dismiss();
            }

            progressDialog.dismiss();
        }

        @Override
        protected void onProgressUpdate(String... values) {
            progressDialog.setMessage(values[0]);
        }

        @Override
        public void onProgress(String progress) {
            publishProgress(progress);
        }
    }
}
