package com.example.util;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.jar.JarEntry;
import java.util.jar.JarOutputStream;

public class JarUtil{

    public static void main(String[] args) {
        String pojoDir = "src/main/java/com/example/pojo";
        String outputJar = "target/pojo.jar";

        try {
            createJar(pojoDir, outputJar);
            System.out.println("JAR file created successfully at " + outputJar);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void createJar(String sourceDirPath, String outputJarPath) throws IOException {
        Path sourceDir = Paths.get(sourceDirPath);
        try (JarOutputStream jos = new JarOutputStream(new FileOutputStream(outputJarPath))) {
            Files.walk(sourceDir)
                    .filter(Files::isRegularFile)
                    .forEach(path -> {
                        String entryName = sourceDir.relativize(path).toString().replace("\\", "/");
                        try {
                            jos.putNextEntry(new JarEntry(entryName));
                            Files.copy(path, jos);
                            jos.closeEntry();
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    });
        }
    }
}
