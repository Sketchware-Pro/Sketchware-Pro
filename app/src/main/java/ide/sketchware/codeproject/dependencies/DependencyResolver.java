package ide.sketchware.codeproject.dependencies;

import android.content.Context;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * Resolves Maven dependencies for a Code Project.
 *
 * <p>This is a thin facade over the project's existing dependency-resolution
 * stack ({@code org.cosmic.ide.dependency.resolver}, the same library used by
 * the block editor's library downloader). The heavy lifting — POM parsing,
 * repository fallback, transitive resolution, downloading — lives in that
 * battle-tested library; {@link CosmicDependencyBridge} adapts it to the Code
 * Project IDE by placing resolved JARs into the project's {@code libs/resolved/}
 * directory for the ECJ/D8 classpath.
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

    private final Context context;

    public DependencyResolver(Context context) {
        this.context = context;
    }

    /**
     * Resolves a list of dependency declarations (and their transitive
     * dependencies) using the cosmic-ide resolver, placing resolved JARs into
     * {@code outputDir}. Must be called off the main thread.
     */
    public void resolve(List<DependencyDeclaration> declarations, File outputDir, ResolveListener listener) {
        CosmicDependencyBridge.resolve(context, declarations, outputDir, listener);
    }

    /**
     * Parses a dependencies.txt file and returns a list of DependencyDeclarations.
     * Supports formats:
     * - "groupId:artifactId:version"
     * - "implementation groupId:artifactId:version"
     * Skips empty lines and comments (lines starting with # or //).
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
                if (line.isEmpty()) continue;
                if (line.startsWith("#") || line.startsWith("//")) continue;

                String notation = line;
                if (notation.startsWith("implementation ")) {
                    notation = notation.substring("implementation ".length()).trim();
                } else if (notation.startsWith("api ")) {
                    notation = notation.substring("api ".length()).trim();
                } else if (notation.startsWith("compileOnly ")) {
                    notation = notation.substring("compileOnly ".length()).trim();
                }

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
