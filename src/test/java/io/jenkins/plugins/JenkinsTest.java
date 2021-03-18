package io.jenkins.plugins;

import hudson.model.Result;
import org.jenkinsci.plugins.workflow.cps.CpsFlowDefinition;
import org.jenkinsci.plugins.workflow.job.WorkflowJob;
import org.jenkinsci.plugins.workflow.job.WorkflowRun;
import org.junit.Rule;
import org.junit.Test;
import org.jvnet.hudson.test.JenkinsRule;

import static org.hamcrest.MatcherAssert.*;
import static org.hamcrest.CoreMatchers.*;

public class JenkinsTest {

    @Rule
    public JenkinsRule rule = new JenkinsRule();

    @Test
    public void testPipelineCompatibility() throws Exception {
        WorkflowJob p = rule.jenkins.createProject(WorkflowJob.class, "p");
        p.setDefinition(new CpsFlowDefinition(
                "pipeline {\n"
                + "    agent any\n"
                + "\n"
                + "    stages {\n"
                + "        stage('Hello') {\n"
                + "            steps {\n"
                + "                nextVersion(startTag: '0.0.1')\n"
                + "            }\n"
                + "        }\n"
                + "    }\n"
                + "}", true));

        WorkflowRun b = rule.assertBuildStatus(Result.SUCCESS, p.scheduleBuild2(0).get());

        System.out.println(rule.getLog(b));
        assertThat(rule.getLog(b), containsString("Started"));
        assertThat(rule.getLog(b), containsString("nextVersion"));
        assertThat(rule.getLog(b), containsString("Current Tag is: 0.0.1"));
        assertThat(rule.getLog(b), containsString("0.0.2"));
    }

    @Test
    public void testPipelineCompatibility_environment() throws Exception {
        WorkflowJob p = rule.jenkins.createProject(WorkflowJob.class, "p");
        p.setDefinition(new CpsFlowDefinition(
                "pipeline {\n"
                + "    agent any\n"
                + "    \n"
                + "    environment {\n"
                + "        NEXT_VERSION = nextVersion(startTag: '0.0.1')\n"
                + "    }\n"
                + "\n"
                + "    stages {\n"
                + "        stage('Hello') {\n"
                + "            steps {\n"
                + "                echo \"next version = ${NEXT_VERSION}\"\n"
                + "            }\n"
                + "        }\n"
                + "    }\n"
                + "}", true));

        WorkflowRun b = rule.assertBuildStatus(Result.SUCCESS, p.scheduleBuild2(0).get());

        System.out.println(rule.getLog(b));
        assertThat(rule.getLog(b), containsString("Started"));
        assertThat(rule.getLog(b), containsString("nextVersion"));
        assertThat(rule.getLog(b), containsString("Current Tag is: 0.0.1"));
        assertThat(rule.getLog(b), containsString("next version = 0.0.2"));
    }

}
