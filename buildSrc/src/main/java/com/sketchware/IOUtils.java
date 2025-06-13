package com.sketchware;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.UncheckedIOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Arrays;
import java.util.Properties;
import java.util.stream.Stream;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

public class IOUtils {

    public static String getProperty(File propertiesFile, String key) throws IOException {
        Properties props = new Properties();
        try (FileInputStream fis = new FileInputStream(propertiesFile)) {
            props.load(fis);
        }
        return props.getProperty(key);
    }

    public static String getLatestBuildToolsDir(String sdkDir) {
        File buildToolsDir = new File(sdkDir, "build-tools");
        if (!buildToolsDir.isDirectory()) throw new RuntimeException("build-tools dir not found in SDK");
        File[] versions = buildToolsDir.listFiles(File::isDirectory);
        if (versions == null || versions.length == 0) throw new RuntimeException("No build-tools versions found");
        // Sort by version descending
        Arrays.sort(versions, (a, b) -> b.getName().compareTo(a.getName()));
        return versions[0].getAbsolutePath();
    }

    public static void zipFolder(File sourceDir, File zipFile) throws IOException {
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