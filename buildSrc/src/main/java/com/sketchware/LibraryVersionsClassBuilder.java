package com.sketchware;

import java.io.*;
import java.util.*;
import java.nio.file.*;
import java.util.stream.Collectors;

public class LibraryVersionsClassBuilder {

    private static String buildLibraryFields(Map<String, ArrayList<String>> libsMap) {
        StringBuilder sb = new StringBuilder();

        for (Map.Entry<String, ArrayList<String>> entry : libsMap.entrySet()) {
            String mainLib = entry.getKey();
            List<String> deps = entry.getValue();

            String fieldName = getFieldName(mainLib);

            String mainVersion = deps.getFirst();
            String subDepsList = deps.stream()
                    .skip(1)
                    .map(s -> "\"" + s + "\"")
                    .collect(Collectors.joining(", "));

            if (!subDepsList.isEmpty()) {
                sb.append(String.format(
                        """
                                    public static final DependencyBundle %s = new DependencyBundle("%s", %s);
                                """,
                        fieldName, mainVersion, subDepsList
                ));
            } else {
                sb.append(String.format(
                        """
                                    public static final DependencyBundle %s = new DependencyBundle("%s");
                                """,
                        fieldName, mainVersion
                ));
            }
        }
        return sb.toString();
    }

    private static String getFieldName(String mainLib) {
        String[] parts = mainLib.split(":");
        String group = parts[0];
        String artifact = parts[1];
        String groupPart = group.contains(".")
                ? group.substring(0, group.indexOf(".")).toUpperCase().replace('.', '_')
                : group.toUpperCase().replace('.', '_');
        String artifactPart = artifact.toUpperCase().replace('-', '_');
        return groupPart + "_" + artifactPart;
    }

    public static void generate(File outputDir, Map<String, ArrayList<String>> libsMap) throws IOException {
        File javaDir = new File(outputDir, "pro/sketchware");

        if (!javaDir.exists()) {
            Files.createDirectories(javaDir.toPath());
        }
        File javaFile = new File(javaDir, "LibraryVersions.java");

        String fields = buildLibraryFields(libsMap);

        String content = String.format("""
                /**
                 * Automatically generated file. DO NOT MODIFY
                 */
                package pro.sketchware;
                
                import java.util.ArrayList;
                import java.util.Arrays;
                
                public final class LibraryVersions {
                
                    %s
                
                    public static record DependencyBundle(String libraryVersion, String... subDependencies) {
                
                        public DependencyBundle(String libraryVersion) {
                            this(libraryVersion, new String[0]);
                        }
                
                        public ArrayList<String> getAllDependencies() {
                            ArrayList<String> allDependencies = new ArrayList<>();
                            allDependencies.add(libraryVersion);
                            allDependencies.addAll(Arrays.asList(subDependencies));
                            return allDependencies;
                        }
                
                    }

                }
                """, fields.trim());

        Files.writeString(javaFile.toPath(), content);
    }

}
