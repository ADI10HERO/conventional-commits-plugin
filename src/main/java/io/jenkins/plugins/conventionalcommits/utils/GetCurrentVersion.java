package io.jenkins.plugins.conventionalcommits.utils;

import com.github.zafarkhaja.semver.Version;
import java.lang.ProcessBuilder;
import java.io.File;
import java.io.IOException;
import java.nio.charset.StandardCharsets;

import org.apache.commons.io.IOUtils;

public class GetCurrentVersion {


    private Version getCurrentVersionMaven(File dir) throws IOException, InterruptedException {

        String os = System.getProperty("os.name");
        String commandName = "mvn";

        if (os.contains("Windows")) {
            commandName += ".cmd";
        }

        ProcessBuilder processBuilder = new ProcessBuilder(
                commandName, "help:evaluate",
                "-Dexpression=project.version", "-q", "-DforceStdout"
        );

        processBuilder.directory(dir);
        Process process = processBuilder.start();

        String results = IOUtils.toString(process.getInputStream(), StandardCharsets.UTF_8);
        process.waitFor();

        return Version.valueOf(results);
    }

    private Version getCurrentVersionTag(String latestTag){
        return Version.valueOf(latestTag.isEmpty() ? "0.0.0" : latestTag);
    }

    public Version getCurrentVersion(File directory, String latestTag) throws IOException, InterruptedException {

        GetProjectType projectType = new GetProjectType();
        if (projectType.checkMaven(directory)){
            return getCurrentVersionMaven(directory);
        }
        // To loop over supported types to check which type of project it is
        // Return "current" version once value is true...

        return getCurrentVersionTag(latestTag);
    }

}
