package mod.khaled.librarymanager;

import android.app.Activity;
import android.app.ProgressDialog;
import android.os.Build;

import androidx.annotation.Nullable;

import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import mod.agus.jcoderz.lib.FilePathUtil;
import mod.agus.jcoderz.lib.FileUtil;
import mod.hey.studios.lib.JarCheck;
import mod.hey.studios.lib.prdownloader.PRDownloader;
import mod.hey.studios.project.library.LibraryDownloader;

//Wrapper for mod.hey.studios.project.library.LibraryDownloader
//TODO: Better implementation maybe
public class ExternalLibraryDownloader {

    private final ArrayList<Repository> repositoriesList = new ArrayList<>();

    private final LibraryDownloader libraryDownloader;

    private final ProgressDialog progressDialog;

    private int libraryDownloaderInstanceId;

    private final boolean shouldUseD8 = Build.VERSION.SDK_INT >= Build.VERSION_CODES.O;


    public ExternalLibraryDownloader(Activity activity) {
        libraryDownloader = new LibraryDownloader(activity, shouldUseD8);
        initializeRepositories();
        progressDialog = new ProgressDialog(activity);
        progressDialog.setCancelable(false);
    }


    void saveLibraryToDisk() {

    }

    void startDownloadingLibrary(ExternalLibraryItem libraryItem, DownloadStatusListener downloadStatusListener) {
        libraryDownloaderInstanceId = tryDownloadAAR(libraryItem, 0, downloadStatusListener);
    }

    void cancelDownloadingLibrary() {
        PRDownloader.cancel(libraryDownloaderInstanceId);
    }

    private int tryDownloadAAR(ExternalLibraryItem libraryItem, final int currentRepo, DownloadStatusListener downloadStatusListener) {
        final String fullAARUrl = repositoriesList.get(currentRepo).url + getAAREndpoint(libraryItem.getLibraryPkg());
        final File libraryTempFile = new File(FilePathUtil.getExternalLibraryDir(libraryItem.getLibraryFolderName()).concat(".zip"));

        if (!progressDialog.isShowing())
            progressDialog.show();


        return PRDownloader
                .download(fullAARUrl, FilePathUtil.getExternalLibrariesDir(), libraryTempFile.getName())
                .build()
                .setOnStartOrResumeListener(() -> {
                    progressDialog.setMessage("Library found. Downloading...");
                    progressDialog.setProgressStyle(ProgressDialog.STYLE_HORIZONTAL);
                    progressDialog.setIndeterminate(false);
                })
                .setOnCancelListener(() -> {

                })
                .setOnProgressListener(progress -> {
                    int progressPercent = (int) (progress.currentBytes * 100 / progress.totalBytes);
                    progressDialog.setProgress(progressPercent);
                    downloadStatusListener.onProgressChange(progressPercent, null);
                })
                .start(new PRDownloader.OnDownloadListener() {
                    @Override
                    public void onDownloadComplete() {

                        final String libraryTargetDir = FilePathUtil.getExternalLibraryDir(libraryItem.getLibraryPkg());
                        if (FileUtil.isExistFile(libraryTargetDir))
                            FileUtil.deleteFile(libraryTargetDir);
                        FileUtil.makeDir(libraryTargetDir);
                        libraryDownloader._unZipFile(libraryTempFile.getAbsolutePath(), libraryTargetDir);


                        if (!FileUtil.isExistFile(libraryItem.getJarPath())) {
                            progressDialog.setMessage("Library doesn't contain a jar file.");
                            FileUtil.deleteFile(libraryTempFile.getAbsolutePath());
                            FileUtil.deleteFile(libraryTargetDir);
                            downloadStatusListener.onError("Library doesn't contain a jar file.");
                            return;
                        }

                        if (!shouldUseD8 && !JarCheck.checkJar(libraryItem.getLibraryName(), 44, 51)) {
                            progressDialog.setMessage("This jar is not supported by your device's compiler.");
                            FileUtil.deleteFile(libraryTempFile.getAbsolutePath());
                            FileUtil.deleteFile(libraryTargetDir);
                            downloadStatusListener.onError("This jar is not supported by your device's compiler.");
                            return;
                        }

                        progressDialog.setMessage("Download completed.");
                        downloadStatusListener.onDownloadComplete(libraryTempFile.getAbsolutePath());

                        //TODO: Send file path for confirmation weather to save.
                        String[] test = {libraryItem.getJarPath()};
                        libraryDownloader.new BackTask().execute(test);

                        FileUtil.writeFile(libraryItem.getPackageNamePath(), libraryItem.getPackageName());
                        libraryItem.renameLibrary(libraryItem.getLibraryName());

                        FileUtil.deleteFile(libraryTempFile.getAbsolutePath());
                        deleteResidueAfterBuildingLibrary(FilePathUtil.getExternalLibraryDir(libraryItem.getLibraryFolderName()));
                        progressDialog.dismiss();
                    }

                    @Override
                    public void onError(PRDownloader.Error e) {
                        if (e.isConnectionError()) {
                            progressDialog.setMessage("Downloading failed. No network");
                            return;
                        }

                        if (!e.isServerError()) return;

                        if (repositoriesList.size() <= currentRepo) {
                            FileUtil.deleteFile(libraryTempFile.getAbsolutePath());
                            progressDialog.setMessage("Library was not found in any of the loaded repositories.");
                            return;
                        }

                        Repository nextRepository = repositoriesList.get(currentRepo + 1);
                        String progressMessage = "Searching... " + (currentRepo + 1) + "/" + repositoriesList.size() + " [" + nextRepository.name + "]";

                        downloadStatusListener.onProgressChange(0, progressMessage);
                        progressDialog.setMessage(progressMessage);

                        libraryDownloaderInstanceId = tryDownloadAAR(libraryItem, currentRepo + 1, downloadStatusListener);
                    }
                });

    }

    private String getAAREndpoint(String libraryPkg) {
        String[] split = libraryPkg.split(":");
        String str2 = "/";

        for (int i = 0; i < split.length - 1; i++) {
            str2 = str2.concat(split[i].replace(".", "/") + "/");
        }

        return str2.concat(split[split.length - 1]).concat("/").concat(getAARName(libraryPkg));
    }

    private String getAARName(String libraryPkg) {
        String[] split = libraryPkg.split(":");
        return split[split.length - 2] + "-" + split[split.length - 1] + ".aar";
    }


    private void initializeRepositories() {
        repositoriesList.clear();
        repositoriesList.add(new Repository("HortanWorks", "https://repo.hortonworks.com/content/repositories/releases"));
        repositoriesList.add(new Repository("Atlassin", "https://maven.atlassian.com/content/repositories/atlassian-public"));
        repositoriesList.add(new Repository("JitPack", "https://jitpack.io"));
        repositoriesList.add(new Repository("JCenter", "https://jcenter.bintray.com"));
        repositoriesList.add(new Repository("Sonatype", "https://oss.sonatype.org/content/repositories/releases"));
        repositoriesList.add(new Repository("Spring Plugins", "https://repo.spring.io/plugins-release"));
        repositoriesList.add(new Repository("Spring Milestone", "https://repo.spring.io/libs-milestone"));
        repositoriesList.add(new Repository("Apache Maven", "https://repo.maven.apache.org/maven2"));
        repositoriesList.add(new Repository("Google Maven", "https://dl.google.com/dl/android/maven2"));
        repositoriesList.add(new Repository("Maven Central", "https://repo1.maven.org/maven2"));
        //TODO: Load from file somewhere
    }

    private void deleteResidueAfterBuildingLibrary(String path) {
//TODO
        if (true) return;

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
            String p = "getLastSegment(f);";

            if (p.startsWith("classes") && p.endsWith(".dex")) continue;
            if (!validFiles.contains(p)) FileUtil.deleteFile(f);
        }
    }

    private static class Repository {
        String name;
        String url;

        public Repository(String name, String url) {
            this.name = name;
            this.url = url;
        }
    }

    interface DownloadStatusListener {
        void onDownloadComplete(String tempLibPath);

        void onError(String errMessage);

        void onProgressChange(int newProgress, @Nullable String newMessage);
    }
}
