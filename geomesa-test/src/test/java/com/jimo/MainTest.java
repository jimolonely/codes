package com.jimo;

import java.io.File;
import java.util.Objects;
import java.util.regex.Pattern;

public class MainTest {

    @org.junit.Test
    public void testDelete() {

        String path = "D:\\maven_repository\\com\\urban-computing\\geomesa-back";

        // sources.jar
        String pattern = ".*\\.(jar|txt)$";
        final Pattern p = Pattern.compile(pattern);
        final File dir = new File(path);
        deleteFile(dir, p);
    }

    private static void deleteFile(File d, Pattern pattern) {
        for (File f : Objects.requireNonNull(d.listFiles())) {
            if (f.isDirectory()) {
                deleteFile(f, pattern);
            } else {
                if (pattern.matcher(f.getName()).find()) {
                    System.out.println("删除：" + f.getAbsolutePath());
                    f.delete();
                }
            }
        }
    }
}
