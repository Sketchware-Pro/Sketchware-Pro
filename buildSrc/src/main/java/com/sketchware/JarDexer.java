package com.sketchware;

import java.io.*;
import java.nio.file.*;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;

public class JarDexer {

    public static String getCacheDexName(String group, String name, String version) {
        String safeGroup = group == null ? "" : group.replaceAll("[^a-zA-Z0-9_]", "_");
        String safeName = name.replaceAll("[^a-zA-Z0-9_]", "_");
        String safeVersion = version.replaceAll("[^a-zA-Z0-9_]", "_");
        return safeGroup + "_" + safeName + "_" + safeVersion + ".dex";
    }

    public static void extractAndDexWithCache(File depFile, Path libFolder, Path cacheDexPath, String buildToolsDir) throws IOException, InterruptedException {
        Path jarPath = null;
        if (depFile.getName().endsWith(".aar")) {
            unzipAAR(depFile, libFolder);
            Path possibleJar = libFolder.resolve("classes.jar");
            if (Files.exists(possibleJar)) jarPath = possibleJar;
        } else if (depFile.getName().endsWith(".jar")) {
            jarPath = libFolder.resolve("classes.jar");
            Files.copy(depFile.toPath(), jarPath, StandardCopyOption.REPLACE_EXISTING);
        }
        // D8
        if (jarPath != null && Files.exists(jarPath)) {
            runD8(jarPath, cacheDexPath, buildToolsDir);
        }
    }

    public static void unzipAAR(File aar, Path destDir) throws IOException {
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

    public static void runD8(Path jarPath, Path outDexPath, String buildToolsDir) throws IOException, InterruptedException {
        String d8Path = new File(buildToolsDir, isWindows() ? "d8.bat" : "d8").getAbsolutePath();
        Files.createDirectories(outDexPath.getParent());

        File tmpOutDir = Files.createTempDirectory("d8out").toFile();
        try {
            ProcessBuilder pb = new ProcessBuilder(
                    d8Path,
                    "--output", tmpOutDir.getAbsolutePath(),
                    jarPath.toAbsolutePath().toString()
            );
            pb.redirectErrorStream(true);

            Process proc = pb.start();
            int exit = proc.waitFor();
            if (exit != 0) throw new RuntimeException("D8 failed with exit code " + exit);

            File[] dexFiles = tmpOutDir.listFiles((dir, fileName) -> fileName.endsWith(".dex"));
            if (dexFiles != null && dexFiles.length != 0) {
                Files.move(dexFiles[0].toPath(), outDexPath, StandardCopyOption.REPLACE_EXISTING);
            }
        } finally {
            deleteDirectory(tmpOutDir.toPath());
        }
    }

    private static boolean isWindows() {
        return System.getProperty("os.name").toLowerCase().contains("win");
    }

    private static void deleteDirectory(Path dir) throws IOException {
        if (Files.isDirectory(dir)) {
            try (DirectoryStream<Path> entries = Files.newDirectoryStream(dir)) {
                for (Path entry : entries) {
                    deleteDirectory(entry);
                }
            }
        }
        Files.delete(dir);
    }

}