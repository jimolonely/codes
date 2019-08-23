package com.jimo;

import org.apache.maven.model.Resource;
import org.apache.maven.plugin.AbstractMojo;
import org.apache.maven.plugin.MojoExecutionException;
import org.apache.maven.plugin.MojoFailureException;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

/**
 * goal是必须的
 *
 * @author jimo
 * @date 19-8-23 下午4:10
 * @goal count
 */
public class CountMojo extends AbstractMojo {

    private static final String[] INCLUDES_DEFAULT = {"java", "xml"};

    /**
     * @parameter property="project.basedir"
     * @required
     * @readonly
     */
    private File baseDir;

    /**
     * @parameter property="project.build.sourceDirectory"
     * @required
     * @readonly
     */
    private File sourceDirectory;

    /**
     * @parameter property="project.build.testSourceDirectory"
     * @required
     * @readonly
     */
    private File testSourceDirectory;

    /**
     * @parameter property="project.build.resources"
     * @required
     * @readonly
     */
    private List<Resource> resources;

    /**
     * @parameter property="project.build.testResources"
     * @required
     * @readonly
     */
    private List<Resource> testResources;

    /**
     * 参数：
     *
     * @parameter
     */
    private String[] includes;

    public void execute() throws MojoExecutionException, MojoFailureException {
        if (includes == null || includes.length == 0) {
            includes = INCLUDES_DEFAULT;
        }

        countDir(sourceDirectory);

        countDir(testSourceDirectory);

        for (Resource resource : resources) {
            countDir(new File(resource.getDirectory()));
        }

        for (Resource testResource : testResources) {
            countDir(new File(testResource.getDirectory()));
        }
    }

    private void countDir(File dir) {
        if (!dir.exists()) {
            return;
        }
        List<File> files = new ArrayList<File>();
        countFiles(files, dir);

        int lines = 0;

        for (File file : files) {
            lines += countLine(file);
        }

        String path = dir.getAbsolutePath().substring(baseDir.getAbsolutePath().length());

        getLog().info(path + ":" + lines + " lines of code in " + files.size() + " files");
    }

    private int countLine(File file) {
        try {
            BufferedReader reader = new BufferedReader(new FileReader(file));
            int line = 0;
            while (reader.ready()) {
                reader.readLine();
                line++;
            }
            return line;
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return 0;
    }

    private void countFiles(List<File> files, File f) {
        if (f.isFile()) {
            for (String include : includes) {
                if (f.getName().endsWith("." + include)) {
                    files.add(f);
                    break;
                }
            }
        } else {
            for (File file : f.listFiles()) {
                countFiles(files, file);
            }
        }
    }

}
