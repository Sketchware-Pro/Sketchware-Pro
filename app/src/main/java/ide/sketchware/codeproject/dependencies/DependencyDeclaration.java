package ide.sketchware.codeproject.dependencies;

/**
 * Represents a Maven dependency declaration in the format groupId:artifactId:version.
 */
public class DependencyDeclaration {

    private final String groupId;
    private final String artifactId;
    private final String version;

    public DependencyDeclaration(String groupId, String artifactId, String version) {
        this.groupId = groupId;
        this.artifactId = artifactId;
        this.version = version;
    }

    public String getGroupId() {
        return groupId;
    }

    public String getArtifactId() {
        return artifactId;
    }

    public String getVersion() {
        return version;
    }

    @Override
    public String toString() {
        return groupId + ":" + artifactId + ":" + version;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        DependencyDeclaration that = (DependencyDeclaration) o;
        return groupId.equals(that.groupId) && artifactId.equals(that.artifactId);
    }

    @Override
    public int hashCode() {
        return 31 * groupId.hashCode() + artifactId.hashCode();
    }

    private static final java.util.regex.Pattern SAFE_COORDINATE =
            java.util.regex.Pattern.compile("[a-zA-Z0-9._-]+");

    /**
     * Parses a dependency notation string in the format "groupId:artifactId:version".
     * Validates that coordinates contain only safe characters (no path traversal).
     *
     * @param notation the dependency notation (e.g., "com.google.code.gson:gson:2.10.1")
     * @return the parsed DependencyDeclaration, or null if the notation is invalid
     */
    public static DependencyDeclaration parse(String notation) {
        if (notation == null || notation.trim().isEmpty()) {
            return null;
        }
        String trimmed = notation.trim();
        String[] parts = trimmed.split(":");
        if (parts.length != 3) {
            return null;
        }
        String groupId = parts[0].trim();
        String artifactId = parts[1].trim();
        String version = parts[2].trim();
        if (groupId.isEmpty() || artifactId.isEmpty() || version.isEmpty()) {
            return null;
        }
        // Reject coordinates with path traversal or unsafe characters
        if (!SAFE_COORDINATE.matcher(groupId).matches()
                || !SAFE_COORDINATE.matcher(artifactId).matches()
                || !SAFE_COORDINATE.matcher(version).matches()) {
            return null;
        }
        return new DependencyDeclaration(groupId, artifactId, version);
    }
}
