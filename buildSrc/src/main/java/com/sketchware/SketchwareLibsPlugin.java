package com.sketchware;

import org.gradle.api.Plugin;
import org.gradle.api.Project;
import org.gradle.api.artifacts.Configuration;
import org.gradle.api.artifacts.ResolvedArtifact;
import org.gradle.api.artifacts.ResolvedDependency;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.UncheckedIOException;

import java.nio.file.StandardCopyOption;
import java.nio.file.Files;
import java.nio.file.Path;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.LinkedHashSet;
import java.util.Map;
import java.util.Set;
import java.util.stream.Stream;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;
import java.util.zip.ZipOutputStream;

public class SketchwareLibsPlugin implements Plugin<Project> {

    @Override
    public void apply(Project project) {
        Configuration sketchwareLibs = project.getConfigurations().maybeCreate("sketchwareLibs");

        project.afterEvaluate(p -> {
            Path buildDir = project.getLayout()
                    .getBuildDirectory()
                    .get()
                    .getAsFile()
                    .toPath()
                    .resolve("sketchwareLibs");

            Path libsZip = buildDir.resolve("libs.zip");

            project.getTasks().register("buildSketchwareLibs", task -> task.doLast(t -> {
                try {
                    Files.createDirectories(buildDir);

                    Path libsFolder = buildDir.resolve("libs");
                    Files.createDirectories(libsFolder);

                    Map<String, ArrayList<String>> libsMap = new LinkedHashMap<>();
                    Set<ResolvedDependency> firstLevelDeps = sketchwareLibs.getResolvedConfiguration()
                            .getFirstLevelModuleDependencies();
                    for (ResolvedDependency dep : firstLevelDeps) {
                        LinkedHashSet<String> artifacts = new LinkedHashSet<>();
                        collectDependencyArtifacts(dep, artifacts);
                        libsMap.put(
                                dep.getModuleGroup() + ":" + dep.getModuleName() + ":" + dep.getModuleVersion(),
                                new ArrayList<>(artifacts)
                        );
                    }

                    for (ResolvedArtifact dep : sketchwareLibs.getResolvedConfiguration().getResolvedArtifacts()) {
                        String name = dep.getModuleVersion().getId().getName();
                        Path libFolder = libsFolder.resolve(name);
                        Files.createDirectories(libFolder);

                        File depFile = dep.getFile();
                        if (depFile.getName().endsWith(".aar")) {
                            unzipAAR(depFile, libFolder);
                        } else if (depFile.getName().endsWith(".jar")) {
                            Files.copy(depFile.toPath(), libFolder.resolve("classes.jar"), StandardCopyOption.REPLACE_EXISTING);
                        }
                    }

                    zipFolder(libsFolder.toFile(), libsZip.toFile());

                    Path assetsLibs = project.file("src/main/assets/libs").toPath();
                    Files.createDirectories(assetsLibs);
                    Files.copy(libsZip, assetsLibs.resolve("libs.zip"), StandardCopyOption.REPLACE_EXISTING);

                    Path generatedDir = project.getLayout()
                            .getBuildDirectory()
                            .get()
                            .getAsFile()
                            .toPath()
                            .resolve("generated/source/libraryVersions");

                    Files.createDirectories(generatedDir);

                    LibraryVersionsClassBuilder.generate(generatedDir.toFile(), libsMap);

                } catch (Exception e) {
                    throw new RuntimeException("Error building sketchware libs", e);
                }
            }));

            project.getTasks().named("preBuild").configure(t -> t.dependsOn("buildSketchwareLibs"));
        });
    }

    private void collectDependencyArtifacts(ResolvedDependency dep, Set<String> acc) {
        acc.add(dep.getModuleName() + ":" + dep.getModuleVersion());
        for (ResolvedDependency child : dep.getChildren()) {
            collectDependencyArtifacts(child, acc);
        }
    }

    private void unzipAAR(File aar, Path destDir) throws IOException {
        try (ZipInputStream zis = new ZipInputStream(new FileInputStream(aar))) {
            ZipEntry entry;
            while ((entry = zis.getNextEntry()) != null) {
                Path newPath = destDir.resolve(entry.getName());

                if (entry.isDirectory()) {
                    Files.createDirectories(newPath);
                    continue;
                }

                Files.createDirectories(newPath.getParent());
                Files.copy(zis, newPath, StandardCopyOption.REPLACE_EXISTING);
            }
        }
    }

    private void zipFolder(File sourceDir, File zipFile) throws IOException {
        try (ZipOutputStream zos = new ZipOutputStream(new FileOutputStream(zipFile));
             Stream<Path> files = Files.walk(sourceDir.toPath())) {
            files.filter(Files::isRegularFile).forEach(path -> {
                try {
                    ZipEntry entry = new ZipEntry(sourceDir.toPath().relativize(path).toString());
                    zos.putNextEntry(entry);
                    Files.copy(path, zos);
                    zos.closeEntry();
                } catch (IOException e) {
                    throw new UncheckedIOException(e);
                }
            });

        }
    }

}
