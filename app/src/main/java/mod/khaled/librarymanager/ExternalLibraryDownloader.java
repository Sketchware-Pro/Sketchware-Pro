package mod.khaled.librarymanager;

import android.app.Activity;
import android.os.Build;
import android.webkit.URLUtil;

import androidx.annotation.Nullable;

import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import mod.SketchwareUtil;
import mod.agus.jcoderz.lib.FilePathUtil;
import mod.agus.jcoderz.lib.FileUtil;
import mod.hey.studios.lib.JarCheck;
import mod.hey.studios.lib.prdownloader.PRDownloader;
import mod.hey.studios.project.library.LibraryDownloader;

//Wrapper for mod.hey.studios.project.library.LibraryDownloader
//TODO: Better implementation maybe
public class ExternalLibraryDownloader {

    private final List<Repository> repositoriesList = new ArrayList<>();

    private int libraryDownloaderInstanceId;

    private final boolean shouldUseD8 = Build.VERSION.SDK_INT >= Build.VERSION_CODES.O;


    public ExternalLibraryDownloader() {
        initializeRepositories();
    }


    public void saveLibraryToDisk(Activity activity, ExternalLibraryItem libraryItem, OnSaveToDiskListener saveToDiskListener) {
        String[] test = {libraryItem.getJarPath()};
        new LibraryDownloader.BackTask(activity, false, null, shouldUseD8, (error) -> {
            if (error == null) {
                FileUtil.writeFile(libraryItem.getPackageNamePath(), libraryItem.getPackageName());
                libraryItem.renameLibrary(libraryItem.getLibraryName());
                deleteResidueAfterBuildingLibrary(FilePathUtil.getExternalLibraryDir(libraryItem.getLibraryFolderName()));
            } else {
                SketchwareUtil.showAnErrorOccurredDialog(activity, error);
                libraryItem.deleteLibraryFromStorage();
            }

            saveToDiskListener.onSaveComplete();
        }).execute(test);
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

        final String searchingMessage = "Searching... " + (currentRepo) + "/" + repositoriesList.size()
                + " [" + repositoriesList.get(currentRepo).name + "]";
        downloadStatusListener.onProgressChange(0, searchingMessage);

        return PRDownloader
                .download(fullAARUrl, FilePathUtil.getExternalLibrariesDir(), libraryTempFile.getName())
                .build()
                .setOnStartOrResumeListener(() -> {
                    final String progressMessage = "[" + repositoriesList.get(currentRepo).name + "] Library found. Downloading...";
                    downloadStatusListener.onProgressChange(0, progressMessage);
                })
                .setOnProgressListener(progress -> {
                    int progressPercent = (int) (progress.currentBytes * 100 / progress.totalBytes);
                    downloadStatusListener.onProgressChange(progressPercent, null);
                })
                .setOnCancelListener(() -> {
                    FileUtil.deleteFile(FilePathUtil.getExternalLibraryDir(libraryItem.getLibraryPkg()));
                    FileUtil.deleteFile(libraryTempFile.getAbsolutePath());
                })
                .start(new PRDownloader.OnDownloadListener() {
                    @Override
                    public void onDownloadComplete() {

                        final String libraryTargetDir = FilePathUtil.getExternalLibraryDir(libraryItem.getLibraryPkg());
                        if (FileUtil.isExistFile(libraryTargetDir))
                            FileUtil.deleteFile(libraryTargetDir);
                        FileUtil.makeDir(libraryTargetDir);
                        LibraryDownloader._unZipFile(libraryTempFile.getAbsolutePath(), libraryTargetDir);


                        if (!FileUtil.isExistFile(libraryItem.getJarPath())) {
                            FileUtil.deleteFile(libraryTempFile.getAbsolutePath());
                            FileUtil.deleteFile(libraryTargetDir);
                            downloadStatusListener.onError("Library doesn't contain a jar file.");
                            return;
                        }

                        if (!shouldUseD8 && !JarCheck.checkJar(libraryItem.getLibraryName(), 44, 51)) {
                            FileUtil.deleteFile(libraryTempFile.getAbsolutePath());
                            FileUtil.deleteFile(libraryTargetDir);
                            downloadStatusListener.onError("This jar is not supported by your device's compiler.");
                            return;
                        }

                        final String progressMessage = "[" + repositoriesList.get(currentRepo).name + "] Library downloaded.";
                        downloadStatusListener.onProgressChange(100, progressMessage);
                        downloadStatusListener.onDownloadComplete(libraryItem);
                        FileUtil.deleteFile(libraryTempFile.getAbsolutePath());
                    }

                    @Override
                    public void onError(PRDownloader.Error e) {
                        if (e.isConnectionError()) {
                            downloadStatusListener.onError("Download failed. No internet.");
                            return;
                        }

                        if (!e.isServerError()) return;

                        if (currentRepo + 1 >= repositoriesList.size()) {
                            FileUtil.deleteFile(libraryTempFile.getAbsolutePath());
                            downloadStatusListener.onError("Library was not found in any of the loaded repositories.");
                            return;
                        }

                        libraryDownloaderInstanceId = tryDownloadAAR(libraryItem, currentRepo + 1, downloadStatusListener);
                    }
                });

    }

    private String getAAREndpoint(String libraryPkg) {
        String[] split = libraryPkg.split(":");
        if (split.length < 2) return "";

        String str2 = "/";

        for (int i = 0; i < split.length - 1; i++) {
            str2 = str2.concat(split[i].replace(".", "/") + "/");
        }

        return str2.concat(split[split.length - 1]).concat("/").concat(getAARName(libraryPkg));
    }

    private String getAARName(String libraryPkg) {
        String[] split = libraryPkg.split(":");
        if (split.length < 2) return "";

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

        //Load custom repositories
        if (FileUtil.readFile(FilePathUtil.getCustomExternalRepositoriesFile()).isBlank())
            writeCustomRepositoryFile();

        String[] customRepositoryData = FileUtil.readFile(FilePathUtil.getCustomExternalRepositoriesFile()).split("\n");
        for (String customRepo : customRepositoryData) {
            if (customRepo.startsWith("#")) continue; //Skip comments
            if (customRepo.split("\\|").length != 2) continue; //Skip broken stuffs

            String name = customRepo.split("\\|")[0].trim();
            String url = customRepo.split("\\|")[1].trim();
            if (name.isBlank() || !URLUtil.isValidUrl(url)) continue;
            repositoriesList.add(new Repository(name, url));
        }
    }

    private void deleteResidueAfterBuildingLibrary(String libraryPath) {
        List<String> validFiles = Arrays.asList(
                "res",
                "classes.dex",
                "classes.jar",
                "packageName",
                "AndroidManifest.xml",
                "jni",
                "assets",
                "proguard.txt",
                "libraryName"
        );

        ArrayList<String> filePaths = new ArrayList<>();
        FileUtil.listDir(libraryPath, filePaths);

        for (String filePath : filePaths) {
            File file = new File(filePath);
            // 6.3.0 Skip all dex files
            if (file.getName().startsWith("classes") && file.getName().endsWith(".dex")) continue;
            if (!validFiles.contains(file.getName())) FileUtil.deleteFile(filePath);
        }
    }

    public static void writeCustomRepositoryFile() {
        String customRepoCommentsBuilder =
                "# To add custom repos, just add\n" +
                        "# a new line with following format\n" +
                        "# Repository Name | https://repo.url\n" +
                        "# Example: JitPack | https://jitpack.io\n" +
                        "# NOTE: Any line starting with # will be ignored.\n#\n";

        FileUtil.writeFile(FilePathUtil.getCustomExternalRepositoriesFile(), customRepoCommentsBuilder);

    }

    private static class Repository {
        String name;
        String url;

        public Repository(String name, String url) {
            this.name = name;
            this.url = url;
        }
    }

    interface OnSaveToDiskListener {
        void onSaveComplete();
    }

    interface DownloadStatusListener {
        void onDownloadComplete(ExternalLibraryItem libraryItem);

        void onError(String errMessage);

        void onProgressChange(int newProgress, @Nullable String newMessage);
    }
}
