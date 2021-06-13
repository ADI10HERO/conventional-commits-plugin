package io.jenkins.plugins.conventionalcommits.utils;

import com.github.zafarkhaja.semver.Version;

import java.io.File;
import java.io.IOException;
import java.util.Arrays;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.TemporaryFolder;

import static org.hamcrest.MatcherAssert.*;
import static org.junit.Assert.assertEquals;

public class GetProjectTypeTest {

    @Rule
    public TemporaryFolder rootFolder = new TemporaryFolder();

    @Test
    public void isMavenProject() throws IOException {

        File mavenDir = rootFolder.newFolder("SampleMavenProject");
        rootFolder.newFile(mavenDir.getName() + File.separator + "pom.xml");

        GetProjectType projectType = new GetProjectType();
        System.out.println();
        assertEquals(true, projectType.checkMaven(mavenDir));
    }

    @Test
    public void isNotMavenProject() throws IOException {

        File mavenDir = rootFolder.newFolder("SampleMavenProject");
        GetProjectType projectType = new GetProjectType();

        assertEquals(false, projectType.checkMaven(mavenDir));
    }

}
