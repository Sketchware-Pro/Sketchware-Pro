package com.sketchware;

import org.gradle.api.*;
import org.gradle.api.artifacts.Configuration;

import java.io.*;
import java.util.*;
import java.util.stream.Stream;
import java.util.zip.*;
import java.nio.file.*;
import java.io.UncheckedIOException;
import java.nio.file.StandardCopyOption;

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

                    List<String> gradleLibs = new ArrayList<>();
                    for (org.gradle.api.artifacts.ResolvedArtifact dep : sketchwareLibs.getResolvedConfiguration().getResolvedArtifacts()) {
                        String group = dep.getModuleVersion().getId().getGroup();
                        String name = dep.getModuleVersion().getId().getName();
                        String version = dep.getModuleVersion().getId().getVersion();

                        String gradleId = group + ":" + name + ":" + version;
                        gradleLibs.add(gradleId);

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

                    LibraryVersionsClassBuilder.generate(generatedDir.toFile(), gradleLibs);

                } catch (Exception e) {
                    throw new RuntimeException("Error building sketchware libs", e);
                }
            }));

            project.getTasks().named("preBuild").configure(t -> t.dependsOn("buildSketchwareLibs"));
        });
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
        try (ZipOutputStream zos = new ZipOutputStream(new FileOutputStream(zipFile)); Stream<Path> files = Files.walk(sourceDir.toPath())) {
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
