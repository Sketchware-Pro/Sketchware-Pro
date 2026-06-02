package ide.sketchware.codeproject.dependencies;

/**
 * Represents a Maven repository with a base URL for resolving artifact paths.
 */
public class MavenRepository {

    public static final MavenRepository MAVEN_CENTRAL =
            new MavenRepository("https://repo1.maven.org/maven2/");
    public static final MavenRepository GOOGLE_MAVEN =
            new MavenRepository("https://dl.google.com/android/maven2/");

    private final String baseUrl;

    public MavenRepository(String baseUrl) {
        if (!baseUrl.endsWith("/")) {
            baseUrl = baseUrl + "/";
        }
        this.baseUrl = baseUrl;
    }

    public String getBaseUrl() {
        return baseUrl;
    }

    /**
     * Constructs the full URL for an artifact.
     *
     * @param groupId    the group ID (e.g., "com.google.code.gson")
     * @param artifactId the artifact ID (e.g., "gson")
     * @param version    the version (e.g., "2.10.1")
     * @param extension  the file extension (e.g., "jar", "aar", "pom")
     * @return the full URL to the artifact
     */
    public String getArtifactUrl(String groupId, String artifactId, String version, String extension) {
        // Convert groupId dots to path separators
        String groupPath = groupId.replace('.', '/');
        // Construct: baseUrl/groupPath/artifactId/version/artifactId-version.extension
        return baseUrl + groupPath + "/" + artifactId + "/" + version + "/"
                + artifactId + "-" + version + "." + extension;
    }

    /**
     * Constructs the URL to the POM file for a given artifact.
     */
    public String getPomUrl(String groupId, String artifactId, String version) {
        return getArtifactUrl(groupId, artifactId, version, "pom");
    }
}
