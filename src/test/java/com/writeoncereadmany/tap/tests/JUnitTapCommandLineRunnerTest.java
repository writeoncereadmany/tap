package com.writeoncereadmany.tap.tests;

import java.io.ByteArrayOutputStream;
import java.util.Collections;

import com.writeoncereadmany.tap.TapFormatter;
import com.writeoncereadmany.tap.TapListener;
import com.writeoncereadmany.tap.JUnitTapCommandLineRunner;

import com.writeoncereadmany.tap.examples.PassingTest;
import com.writeoncereadmany.tap.examples.FailingTest;
import com.writeoncereadmany.tap.examples.ThrowingTest;
import com.writeoncereadmany.tap.examples.IgnoredTest;
import com.writeoncereadmany.tap.examples.InvalidTest;


import org.hamcrest.Matchers;
import org.junit.Test;

import static org.junit.Assert.assertThat;

public class JUnitTapCommandLineRunnerTest
{
    @Test
    public void passingTestOutputsSuccess() throws Exception
    {
        String testResults = runTestAndGetTapOutput(PassingTest.class);

        assertThat(testResults, Matchers.containsString("1..1"));
        assertThat(testResults, Matchers.containsString("ok 1 com.writeoncereadmany.tap.examples.PassingTest.aTestThatPasses"));
    }

    @Test
    public void failingTestOutputsFailureWithFailureMessage() throws Exception
    {
        String testResults = runTestAndGetTapOutput(FailingTest.class);

        assertThat(testResults, Matchers.containsString("1..1"));
        assertThat(testResults, Matchers.containsString("not ok 1 com.writeoncereadmany.tap.examples.FailingTest.aTestThatFails"));
        assertThat(testResults, Matchers.containsString("Failure message"));
    }

    @Test
    public void throwingTestOutputsFailureWithExceptionTypeMessageAndStacktrace() throws Exception
    {
        String testResults = runTestAndGetTapOutput(ThrowingTest.class);

        assertThat(testResults, Matchers.containsString("1..1"));
        assertThat(testResults, Matchers.containsString("not ok 1 com.writeoncereadmany.tap.examples.ThrowingTest.aTestThatThrows"));
        assertThat(testResults, Matchers.containsString("RuntimeException"));
        assertThat(testResults, Matchers.containsString("Exception message"));
        assertThat(testResults, Matchers.containsString("com.writeoncereadmany.tap.examples.ThrowingTest.aTestThatThrows(ThrowingTest.java:10)"));
    }

    @Test
    public void ignoredTestIsSkipped() throws Exception
    {
        String testResults = runTestAndGetTapOutput(IgnoredTest.class);

        assertThat(testResults, Matchers.containsString("1..1"));
        assertThat(testResults, Matchers.containsString("ok 1 com.writeoncereadmany.tap.examples.IgnoredTest.aTestWhichIsIgnored # skip Test ignored"));

    }

    @Test
    public void invalidTestIsSkipped() throws Exception
    {
        String testResults = runTestAndGetTapOutput(InvalidTest.class);

        assertThat(testResults, Matchers.containsString("1..1"));
        assertThat(testResults, Matchers.containsString("ok 1 com.writeoncereadmany.tap.examples.InvalidTest.aTestWhichIsInvalid # skip The world is flat"));
    }



    private String runTestAndGetTapOutput(final Class<?> testClass) throws Exception
    {
        ByteArrayOutputStream os = new ByteArrayOutputStream();
        Class[] testClasses = new Class[] {testClass};

        JUnitTapCommandLineRunner.runTestsWithListeners(testClasses, Collections.singletonList(new TapListener(os, new TapFormatter())));

        return os.toString("UTF-8");
    }
}
