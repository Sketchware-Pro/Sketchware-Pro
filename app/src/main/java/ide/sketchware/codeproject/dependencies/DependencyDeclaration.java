package ide.sketchware.codeproject.dependencies;

/**
 * Represents a Maven dependency declaration in the format groupId:artifactId:version.
 */
public class DependencyDeclaration {

    private static final java.util.regex.Pattern SAFE_COORDINATE =
            java.util.regex.Pattern.compile("[a-zA-Z0-9._-]+");

    private final String groupId;
    private final String artifactId;
    private final String version;

    /**
     * Constructs a dependency declaration, validating each coordinate against a
     * safe-character whitelist. This guards every construction path (including
     * transitive dependencies parsed from remote POMs) against path traversal.
     *
     * @throws IllegalArgumentException if any coordinate is null/empty or contains unsafe characters
     */
    public DependencyDeclaration(String groupId, String artifactId, String version) {
        if (!isSafe(groupId) || !isSafe(artifactId) || !isSafe(version)) {
            throw new IllegalArgumentException(
                    "Invalid Maven coordinates: " + groupId + ":" + artifactId + ":" + version);
        }
        this.groupId = groupId;
        this.artifactId = artifactId;
        this.version = version;
    }

    private static boolean isSafe(String value) {
        return value != null && !value.isEmpty() && SAFE_COORDINATE.matcher(value).matches();
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
        return groupId.equals(that.groupId)
                && artifactId.equals(that.artifactId)
                && version.equals(that.version);
    }

    @Override
    public int hashCode() {
        int result = groupId.hashCode();
        result = 31 * result + artifactId.hashCode();
        result = 31 * result + version.hashCode();
        return result;
    }

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
        if (!isSafe(groupId) || !isSafe(artifactId) || !isSafe(version)) {
            return null;
        }
        return new DependencyDeclaration(groupId, artifactId, version);
    }
}
