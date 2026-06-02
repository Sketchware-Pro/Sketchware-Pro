package ide.sketchware.codeproject.dependencies;

import android.content.Context;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 * Downloads Maven artifacts with local file-system caching.
 * Cache location: {app_files}/maven_cache/groupPath/artifactId/version/filename
 */
public class ArtifactDownloader {

    private static final int CONNECT_TIMEOUT = 15000;
    private static final int READ_TIMEOUT = 30000;

    private final File cacheDir;

    /**
     * Repositories to try, in order. Google Maven is tried first for Android/Google artifacts.
     */
    private static final MavenRepository[] REPOSITORIES = {
            MavenRepository.GOOGLE_MAVEN,
            MavenRepository.MAVEN_CENTRAL
    };

    public ArtifactDownloader(Context context) {
        this.cacheDir = new File(context.getFilesDir(), "maven_cache");
        if (!cacheDir.exists()) {
            cacheDir.mkdirs();
        }
    }

    /**
     * Downloads an artifact file (jar, aar, or pom).
     * Returns the cached file if it already exists and has non-zero size.
     *
     * @param dep       the dependency declaration
     * @param extension the file extension ("jar", "aar", "pom")
     * @return the local file containing the artifact
     * @throws Exception if the artifact cannot be downloaded from any repository
     */
    public File download(DependencyDeclaration dep, String extension) throws Exception {
        File cachedFile = getCachePath(dep, extension);

        // Return cached file if it exists with non-zero size
        if (cachedFile.exists() && cachedFile.length() > 0) {
            return cachedFile;
        }

        // Determine repository order based on groupId
        MavenRepository[] repos = getRepositoryOrder(dep.getGroupId());

        Exception lastException = null;
        for (MavenRepository repo : repos) {
            String url = repo.getArtifactUrl(dep.getGroupId(), dep.getArtifactId(),
                    dep.getVersion(), extension);
            try {
                downloadFile(url, cachedFile);
                return cachedFile;
            } catch (Exception e) {
                lastException = e;
                // Try next repository
            }
        }

        throw new Exception("Failed to download " + dep + "." + extension
                + ": " + (lastException != null ? lastException.getMessage() : "not found in any repository"));
    }

    /**
     * Returns the local cache path for an artifact.
     */
    File getCachePath(DependencyDeclaration dep, String extension) {
        // cache/com/google/code/gson/gson/2.10.1/gson-2.10.1.jar
        String groupPath = dep.getGroupId().replace('.', File.separatorChar);
        File dir = new File(cacheDir, groupPath + File.separator
                + dep.getArtifactId() + File.separator + dep.getVersion());
        if (!dir.exists()) {
            dir.mkdirs();
        }
        String filename = dep.getArtifactId() + "-" + dep.getVersion() + "." + extension;
        return new File(dir, filename);
    }

    /**
     * Downloads a file from the given URL to the destination.
     */
    private void downloadFile(String urlString, File destination) throws IOException {
        // Ensure parent directory exists
        File parentDir = destination.getParentFile();
        if (parentDir != null && !parentDir.exists()) {
            parentDir.mkdirs();
        }

        HttpURLConnection connection = null;
        InputStream inputStream = null;
        FileOutputStream outputStream = null;

        try {
            URL url = new URL(urlString);
            connection = (HttpURLConnection) url.openConnection();
            connection.setConnectTimeout(CONNECT_TIMEOUT);
            connection.setReadTimeout(READ_TIMEOUT);
            connection.setRequestMethod("GET");
            connection.setInstanceFollowRedirects(true);

            int responseCode = connection.getResponseCode();
            if (responseCode != HttpURLConnection.HTTP_OK) {
                throw new IOException("HTTP " + responseCode + " for " + urlString);
            }

            inputStream = connection.getInputStream();
            outputStream = new FileOutputStream(destination);

            byte[] buffer = new byte[8192];
            int bytesRead;
            while ((bytesRead = inputStream.read(buffer)) != -1) {
                outputStream.write(buffer, 0, bytesRead);
            }
            outputStream.flush();
        } catch (IOException e) {
            // Clean up partial download
            if (destination.exists()) {
                destination.delete();
            }
            throw e;
        } finally {
            if (outputStream != null) {
                try { outputStream.close(); } catch (IOException ignored) {}
            }
            if (inputStream != null) {
                try { inputStream.close(); } catch (IOException ignored) {}
            }
            if (connection != null) {
                connection.disconnect();
            }
        }
    }

    /**
     * Determines repository order based on groupId.
     * Google Maven is tried first for androidx, com.google, and com.android artifacts.
     */
    private MavenRepository[] getRepositoryOrder(String groupId) {
        if (groupId.startsWith("androidx.")
                || groupId.startsWith("com.google.")
                || groupId.startsWith("com.android.")) {
            return new MavenRepository[]{
                    MavenRepository.GOOGLE_MAVEN,
                    MavenRepository.MAVEN_CENTRAL
            };
        }
        return new MavenRepository[]{
                MavenRepository.MAVEN_CENTRAL,
                MavenRepository.GOOGLE_MAVEN
        };
    }
}
