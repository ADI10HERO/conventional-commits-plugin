package io.jenkins.plugins.conventionalcommits.utils;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.TemporaryFolder;

import java.io.File;
import java.io.IOException;

import static org.junit.Assert.*;

public class ProjectTypeTest {

  @Rule public TemporaryFolder rootFolder = new TemporaryFolder();

  @Test
  public void isMavenProject() throws IOException {

    File mavenDir = rootFolder.newFolder("SampleMavenProject");
    rootFolder.newFile(mavenDir.getName() + File.separator + "pom.xml");

    ProjectType projectType = new MavenProjectType();
    assertEquals(true, projectType.check(mavenDir));
  }

  @Test
  public void isNotMavenProject() throws IOException {

    File mavenDir = rootFolder.newFolder("SampleMavenProject");
    ProjectType projectType = new MavenProjectType();
    assertEquals(false, projectType.check(mavenDir));
  }

  @Test
  public void isGradleProject() throws IOException {

    File gradleDir = rootFolder.newFolder("SampleGradleProject");
    rootFolder.newFile(gradleDir.getName() + File.separator + "build.gradle");

    ProjectType projectType = new GradleProjectType();
    assertEquals(true, projectType.check(gradleDir));
  }

  @Test
  public void isNotGradleProject() throws IOException {

    File gradleDir = rootFolder.newFolder("SampleGradleProject");
    ProjectType projectType = new GradleProjectType();
    assertEquals(false, projectType.check(gradleDir));
  }

  @Test
  public void isMakeProject() throws IOException {

    File makeDir = rootFolder.newFolder("SampleMakeProject");
    rootFolder.newFile(makeDir.getName() + File.separator + "Makefile");

    ProjectType projectType = new MakeProjectType();
    assertEquals(true, projectType.check(makeDir));
  }

  @Test
  public void isNotMakeProject() throws IOException {

    File makeDir = rootFolder.newFolder("SampleMakeProject");

    ProjectType projectType = new MakeProjectType();
    assertEquals(false, projectType.check(makeDir));
  }

  @Test
  public void should_is_npm_project() throws IOException {
    File npmDir = rootFolder.newFolder("SampleNpmFolder");
    rootFolder.newFile(npmDir.getName() + File.separator + "package.json");

    ProjectType projectType = new NpmProjectType();

    assertEquals(true, projectType.check(npmDir));
  }

  @Test
  public void should_is_not_npm_project() throws IOException {
    File npmDir = rootFolder.newFolder("SampleNpmFolder");

    ProjectType projectType = new NpmProjectType();

    assertEquals(false, projectType.check(npmDir));
  }

  @Test
  public void should_is_helm_project() throws IOException {
    File helmDir = rootFolder.newFolder("SampleHelmFolder");
    rootFolder.newFile(helmDir.getName() + File.separator + "Chart.yaml");

    ProjectType projectType = new HelmProjectType();

    assertTrue(projectType.check(helmDir));
  }

  @Test
  public void should_is_not_helm_project() throws IOException {
    File helmDir = rootFolder.newFolder("SampleHelmFolder");

    ProjectType projectType = new HelmProjectType();

    assertFalse(projectType.check(helmDir));
  }
}
