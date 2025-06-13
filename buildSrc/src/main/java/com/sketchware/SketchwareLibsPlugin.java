package com.sketchware;

import org.gradle.api.Plugin;
import org.gradle.api.Project;
import org.gradle.api.artifacts.Configuration;
import org.gradle.api.artifacts.ResolvedArtifact;
import org.gradle.api.artifacts.ResolvedDependency;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardCopyOption;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.LinkedHashSet;
import java.util.Map;
import java.util.Set;

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
            Path dexsZip = buildDir.resolve("dexs.zip");

            Path dexCacheDirPath = project.getRootProject().file(".jar2dex-cache").toPath();
            try {
                Files.createDirectories(dexCacheDirPath);
            } catch (IOException e) {
                throw new RuntimeException("Failed to create dex cache directory: " + dexCacheDirPath, e);
            }

            project.getTasks().register("buildSketchwareLibs", task -> task.doLast(t -> {
                try {
                    Files.createDirectories(buildDir);

                    Path libsFolder = buildDir.resolve("libs");
                    Path dexsFolder = buildDir.resolve("dexs");

                    Files.createDirectories(libsFolder);
                    Files.createDirectories(dexsFolder);

                    Map<String, ArrayList<String>> libsMap = new LinkedHashMap<>();
                    Set<ResolvedDependency> firstLevelDeps = sketchwareLibs.getResolvedConfiguration().getFirstLevelModuleDependencies();
                    for (ResolvedDependency dep : firstLevelDeps) {
                        LinkedHashSet<String> artifacts = new LinkedHashSet<>();
                        collectDependencyArtifacts(dep, artifacts);
                        libsMap.put(
                                dep.getModuleGroup() + ":" + dep.getModuleName() + ":" + dep.getModuleVersion(),
                                new ArrayList<>(artifacts)
                        );
                    }

                    File localPropsFile = project.getRootProject().file("local.properties");
                    String sdkDir = IOUtils.getProperty(localPropsFile, "sdk.dir");
                    if (sdkDir == null) throw new RuntimeException("sdk.dir not found in local.properties");

                    String buildToolsDir = IOUtils.getLatestBuildToolsDir(sdkDir);

                    for (ResolvedArtifact dep : sketchwareLibs.getResolvedConfiguration().getResolvedArtifacts()) {
                        String group = dep.getModuleVersion().getId().getGroup();
                        String name = dep.getModuleVersion().getId().getName();
                        String version = dep.getModuleVersion().getId().getVersion();
                        Path libFolder = libsFolder.resolve(name);
                        Files.createDirectories(libFolder);

                        File depFile = dep.getFile();
                        // for dexs.zip : we must use DEX caching because dexing is a heavy task. It took 2 minutes for just two libs with their sub-dependencies
                        // for libs.zip : Gradle's caching will handle this, so no extra code is needed

                        String dexCacheName = JarDexer.getCacheDexName(group, name, version);
                        File cachedDex = dexCacheDirPath.resolve(dexCacheName).toFile();

                        Path dexOutputPath = dexsFolder.resolve(dexCacheName);

                        if (cachedDex.exists()) {
                            Files.copy(cachedDex.toPath(), dexOutputPath, StandardCopyOption.REPLACE_EXISTING);
                        } else {
                            JarDexer.extractAndDexWithCache(depFile, libFolder, cachedDex.toPath(), buildToolsDir);

                            if (cachedDex.exists()) {
                                Files.copy(cachedDex.toPath(), dexOutputPath, StandardCopyOption.REPLACE_EXISTING);
                            }
                        }
                    }

                    IOUtils.zipFolder(libsFolder.toFile(), libsZip.toFile());
                    IOUtils.zipFolder(dexsFolder.toFile(), dexsZip.toFile());

                    Path assetsLibs = project.file("src/main/assets/libs").toPath();

                    Files.createDirectories(assetsLibs);
                    Files.copy(libsZip, assetsLibs.resolve("libs.zip"), StandardCopyOption.REPLACE_EXISTING);
                    Files.copy(dexsZip, assetsLibs.resolve("dexs.zip"), StandardCopyOption.REPLACE_EXISTING);

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

    private void collectDependencyArtifacts(org.gradle.api.artifacts.ResolvedDependency dep, Set<String> acc) {
        acc.add(dep.getModuleName() + ":" + dep.getModuleVersion());
        for (ResolvedDependency child : dep.getChildren()) {
            collectDependencyArtifacts(child, acc);
        }
    }

}
