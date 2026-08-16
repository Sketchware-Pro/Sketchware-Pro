package com.github.megatronking.stringfog.plugin;

import com.github.megatronking.stringfog.IKeyGenerator;
import com.github.megatronking.stringfog.IStringFog;
import com.github.megatronking.stringfog.plugin.kg.HardCodeKeyGenerator;
import com.github.megatronking.stringfog.xor.StringFogImpl;

import org.objectweb.asm.ClassReader;
import org.objectweb.asm.ClassVisitor;
import org.objectweb.asm.ClassWriter;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Stream;

import mod.jbk.util.LogUtil;

public class StringFogInjector {

    public static void processDirectory(File compiledClassesDir, File mappingFile) {
        try {
            IStringFog stringFogImpl = new StringFogImpl();
            IKeyGenerator keyGenerator = new HardCodeKeyGenerator("UTF-8");
            List<String> logs = new ArrayList<>();
            String fogClassName = "com.github.megatronking.stringfog.xor.StringFogImpl";

            if (compiledClassesDir.exists() && compiledClassesDir.isDirectory()) {
                Path baseDirPath = compiledClassesDir.toPath();
                try (Stream<Path> stream = Files.walk(baseDirPath)) {
                    stream.filter(path -> path.toString().endsWith(".class"))
                          .forEach(path -> {
                              try {
                                  String relativePath = baseDirPath.relativize(path).toString();
                                  String className = relativePath.replace(File.separatorChar, '/');
                                  if (className.endsWith(".class")) {
                                      className = className.substring(0, className.length() - 6);
                                  }

                                  byte[] byteCode = Files.readAllBytes(path);
                                  ClassReader reader = new ClassReader(byteCode);
                                  ClassWriter writer = new ClassWriter(reader, ClassWriter.COMPUTE_MAXS);

                                  ClassVisitor visitor = ClassVisitorFactory.create(
                                          stringFogImpl, logs, new String[0], keyGenerator,
                                          fogClassName, className, StringFogMode.base64, writer
                                  );

                                  reader.accept(visitor, ClassReader.EXPAND_FRAMES);
                                  Files.write(path, writer.toByteArray());
                              } catch (Exception e) {
                                  LogUtil.e("StringFog", "Failed to fog class: " + path, e);
                              }
                          });
                }
            }

            if (mappingFile != null) {
                File dir = mappingFile.getParentFile();
                if (dir != null && (dir.exists() || dir.mkdirs())) {
                    try (BufferedWriter writer = new BufferedWriter(new FileWriter(mappingFile))) {
                        writer.write("stringfog impl: " + fogClassName);
                        writer.newLine();
                        writer.write("stringfog mode: base64");
                        writer.newLine();
                        for (String log : logs) {
                            writer.write(log);
                            writer.newLine();
                        }
                    }
                }
            }
        } catch (Exception e) {
            LogUtil.e("StringFog", "Failed to run StringFog", e);
        }
    }
}
