package ide.sketchware.codeproject.dependencies;

import android.content.Context;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;

/**
 * Main orchestrator for Maven dependency resolution.
 * Downloads POM files, parses transitive dependencies, and resolves artifact JARs.
 */
public class DependencyResolver {

    /**
     * Callback interface for dependency resolution progress and results.
     */
    public interface ResolveListener {
        void onProgress(String message);

        void onComplete(List<File> resolvedJars);

        void onError(String error);
    }

    private static final int MAX_DEPTH = 5;

    private final Context context;
    private final ArtifactDownloader downloader;
    private final Set<String> resolvedSet = new HashSet<>();

    public DependencyResolver(Context context) {
        this.context = context;
        this.downloader = new ArtifactDownloader(context);
    }

    /**
     * Resolves a list of dependency declarations by downloading artifacts and their
     * transitive dependencies. Resolved JARs are copied to the outputDir.
     *
     * @param declarations the list of dependencies to resolve
     * @param outputDir    the directory to copy resolved JARs into
     * @param listener     callback for progress, completion, and errors
     */
    public void resolve(List<DependencyDeclaration> declarations, File outputDir, ResolveListener listener) {
        if (!outputDir.exists()) {
            outputDir.mkdirs();
        }

        List<File> resolvedJars = new ArrayList<>();
        List<String> errors = new ArrayList<>();
        resolvedSet.clear();

        for (DependencyDeclaration dep : declarations) {
            if (listener != null) {
                listener.onProgress("Resolving " + dep.getGroupId() + ":" + dep.getArtifactId() + "...");
            }
            try {
                resolveRecursive(dep, outputDir, resolvedJars, listener, 0);
            } catch (Exception e) {
                String msg = dep + ": " + (e.getMessage() != null ? e.getMessage() : "Unknown error");
                errors.add(msg);
            }
        }

        if (listener != null) {
            if (errors.isEmpty()) {
                listener.onComplete(resolvedJars);
            } else if (!resolvedJars.isEmpty()) {
                // Partial success: report resolved jars but also show errors
                listener.onComplete(resolvedJars);
                listener.onError("Some dependencies failed:\n" + String.join("\n", errors));
            } else {
                listener.onError("All dependencies failed:\n" + String.join("\n", errors));
            }
        }
    }

    /**
     * Recursively resolves a single dependency and its transitive dependencies.
     */
    private void resolveRecursive(DependencyDeclaration dep, File outputDir,
                                  List<File> resolvedJars, ResolveListener listener, int depth) throws Exception {
        if (depth > MAX_DEPTH) {
            return; // Prevent infinite recursion
        }

        // Check if already resolved (use groupId:artifactId as key — first successful version wins)
        String key = dep.getGroupId() + ":" + dep.getArtifactId();
        if (resolvedSet.contains(key)) {
            return;
        }

        // 1. Download POM
        if (listener != null) {
            listener.onProgress("Downloading POM: " + dep + "...");
        }
        File pomFile = downloader.download(dep, "pom");

        // 2. Parse POM to get packaging type and transitive deps
        PomParser.PomInfo pomInfo;
        try (InputStream pomStream = new FileInputStream(pomFile)) {
            pomInfo = PomParser.parse(pomStream);
        }

        // 3. Determine artifact extension from packaging
        String extension = "jar";
        if ("aar".equals(pomInfo.packaging)) {
            extension = "aar";
        } else if ("pom".equals(pomInfo.packaging) || "bundle".equals(pomInfo.packaging)) {
            // POM-only parents or bundles (treat bundle as jar)
            if ("bundle".equals(pomInfo.packaging)) {
                extension = "jar";
            } else {
                // POM packaging = no artifact to download, only resolve transitive deps
                extension = null;
            }
        }

        // 4. Download artifact (.jar or .aar) if applicable
        if (extension != null) {
            if (listener != null) {
                listener.onProgress("Downloading " + dep.getArtifactId() + "-" + dep.getVersion() + "." + extension + "...");
            }

            File artifactFile = downloader.download(dep, extension);

            // 5. Handle AAR: extract classes.jar from the zip
            File jarFile;
            if ("aar".equals(extension)) {
                jarFile = extractClassesJarFromAar(artifactFile, dep);
            } else {
                jarFile = artifactFile;
            }

            // 6. Copy to output directory (atomic: write to temp, rename on success)
            if (jarFile != null && jarFile.exists()) {
                // Include groupId so artifacts with the same artifactId+version but
                // different groups don't overwrite each other in libs/resolved.
                String safeGroup = dep.getGroupId().replace('.', '_');
                String outputName = safeGroup + "-" + dep.getArtifactId() + "-" + dep.getVersion() + ".jar";
                File outputFile = new File(outputDir, outputName);
                File tempFile = new File(outputDir, outputName + ".tmp");
                copyFile(jarFile, tempFile);
                if (outputFile.exists()) outputFile.delete();
                if (!tempFile.renameTo(outputFile)) {
                    // Fallback: rename failed (different filesystem), copy directly
                    copyFile(jarFile, outputFile);
                    tempFile.delete();
                }
                resolvedJars.add(outputFile);
            }
        }

        // 7. Recursively resolve transitive dependencies
        // Mark resolved only after the artifact (POM + jar/aar) is handled, so a
        // failed download doesn't poison a later valid path to the same artifact.
        // Added before recursion to keep cycle detection (A→B→A) correct.
        resolvedSet.add(key);
        if (pomInfo.dependencies != null && !pomInfo.dependencies.isEmpty()) {
            for (DependencyDeclaration transitiveDep : pomInfo.dependencies) {
                resolveRecursive(transitiveDep, outputDir, resolvedJars, listener, depth + 1);
            }
        }
    }

    /**
     * Extracts classes.jar from an AAR file (which is a ZIP archive).
     */
    private File extractClassesJarFromAar(File aarFile, DependencyDeclaration dep) throws IOException {
        File extractDir = new File(aarFile.getParentFile(), "extracted");
        if (!extractDir.exists()) {
            extractDir.mkdirs();
        }

        File classesJar = new File(extractDir, dep.getArtifactId() + "-" + dep.getVersion() + "-classes.jar");
        if (classesJar.exists() && classesJar.length() > 0) {
            return classesJar;
        }

        // Write to temp file first to avoid partial extractions being treated as valid
        File tempJar = new File(extractDir, classesJar.getName() + ".tmp");

        try (ZipInputStream zis = new ZipInputStream(new FileInputStream(aarFile))) {
            ZipEntry entry;
            while ((entry = zis.getNextEntry()) != null) {
                if ("classes.jar".equals(entry.getName())) {
                    try (FileOutputStream fos = new FileOutputStream(tempJar)) {
                        byte[] buffer = new byte[8192];
                        int len;
                        while ((len = zis.read(buffer)) != -1) {
                            fos.write(buffer, 0, len);
                        }
                    }
                    // Atomic rename
                    if (classesJar.exists()) classesJar.delete();
                    if (!tempJar.renameTo(classesJar)) {
                        copyFile(tempJar, classesJar);
                        tempJar.delete();
                    }
                    return classesJar;
                }
                zis.closeEntry();
            }
        } finally {
            // Clean up temp if extraction failed partway
            if (tempJar.exists()) tempJar.delete();
        }

        // No classes.jar found in AAR
        return null;
    }

    /**
     * Copies a file from source to destination.
     */
    private void copyFile(File source, File dest) throws IOException {
        try (FileInputStream fis = new FileInputStream(source);
             FileOutputStream fos = new FileOutputStream(dest)) {
            byte[] buffer = new byte[8192];
            int len;
            while ((len = fis.read(buffer)) != -1) {
                fos.write(buffer, 0, len);
            }
        }
    }

    /**
     * Parses a dependencies.txt file and returns a list of DependencyDeclarations.
     * Supports formats:
     * - "groupId:artifactId:version"
     * - "implementation groupId:artifactId:version"
     * Skips empty lines and comments (lines starting with # or //).
     *
     * @param file the dependencies.txt file to parse
     * @return list of parsed dependency declarations
     */
    public static List<DependencyDeclaration> parseDependenciesFile(File file) {
        List<DependencyDeclaration> declarations = new ArrayList<>();
        if (file == null || !file.exists()) {
            return declarations;
        }

        try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
            String line;
            while ((line = reader.readLine()) != null) {
                line = line.trim();

                // Skip empty lines
                if (line.isEmpty()) continue;

                // Skip comments
                if (line.startsWith("#") || line.startsWith("//")) continue;

                // Remove "implementation", "api", "compileOnly" prefix if present
                String notation = line;
                if (notation.startsWith("implementation ")) {
                    notation = notation.substring("implementation ".length()).trim();
                } else if (notation.startsWith("api ")) {
                    notation = notation.substring("api ".length()).trim();
                } else if (notation.startsWith("compileOnly ")) {
                    notation = notation.substring("compileOnly ".length()).trim();
                }

                // Remove surrounding quotes if present
                if (notation.startsWith("'") && notation.endsWith("'")) {
                    notation = notation.substring(1, notation.length() - 1);
                } else if (notation.startsWith("\"") && notation.endsWith("\"")) {
                    notation = notation.substring(1, notation.length() - 1);
                }

                DependencyDeclaration dep = DependencyDeclaration.parse(notation);
                if (dep != null) {
                    declarations.add(dep);
                }
            }
        } catch (IOException e) {
            // Return whatever we have so far
        }

        return declarations;
    }
}
