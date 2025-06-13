package com.sketchware;

import java.io.*;
import java.util.*;
import java.nio.file.*;
import java.io.IOException;

public class LibraryVersionsClassBuilder {

    private static String buildLibraryFields(Set<String> uniqueLibs) {
        StringBuilder sb = new StringBuilder();

        for (String lib : uniqueLibs) {
            String[] parts = lib.split(":");

            String group = parts[0].substring(0, parts[0].indexOf(".")).toUpperCase().replace('.', '_');
            String artifact = parts[1].toUpperCase().replace('-', '_');
            String fieldName = group + "_" + artifact;

            sb.append(String.format("""
                                public static String %s = "%s:%s";
                            """,
                    fieldName, parts[1], parts[2]));
        }
        return sb.toString();
    }

    public static void generate(File outputDir, List<String> libs) throws IOException {
        File javaDir = new File(outputDir, "pro/sketchware");

        if (!javaDir.exists()) {
            Files.createDirectories(javaDir.toPath());
        }
        File javaFile = new File(javaDir, "LibraryVersions.java");

        Set<String> uniqueLibs = new LinkedHashSet<>(libs);
        String fields = buildLibraryFields(uniqueLibs);

        String content = String.format("""
                /**
                 * Automatically generated file. DO NOT MODIFY
                 */
                package pro.sketchware;
                
                public final class LibraryVersions {
                
                    %s
                
                }
                """, fields.trim());

        Files.writeString(javaFile.toPath(), content);
    }


}
