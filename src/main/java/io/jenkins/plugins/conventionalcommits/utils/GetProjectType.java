package io.jenkins.plugins.conventionalcommits.utils;

import java.io.File;
import hudson.FilePath;

public class GetProjectType {

    public boolean checkMaven(File directory) {
        return new File(directory, "pom.xml").exists();
    }

    // planning to have such check functions for all supported file types.
}
