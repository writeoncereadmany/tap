package com.writeoncereadmany.tap;

import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.junit.runner.Description;
import org.junit.runner.Result;
import org.junit.runner.notification.Failure;
import org.junit.runner.notification.RunListener;

public class TapListener extends RunListener
{
    private final Set<Description> alreadyReportedTests = new HashSet<>();
    private final List<TapResult> testResults = new ArrayList<>();
    private final OutputStream tapOutputStream;
    private final TapFormatter tapFormatter;

    public TapListener(final OutputStream tapOutputStream, final TapFormatter tapFormatter)
    {
        this.tapOutputStream = tapOutputStream;
        this.tapFormatter = tapFormatter;
    }

    @Override
    public void testRunFinished(final Result result)
    {
        tapFormatter.writeResults(new PrintWriter(new OutputStreamWriter(tapOutputStream, StandardCharsets.UTF_8)), testResults);
    }

    @Override
    public void testFinished(final Description description)
    {
        if(!alreadyReportedTests.contains(description))
        {
            testResults.add(TapResult.success(display(description)));
            alreadyReportedTests.add(description);
        }
    }

    @Override
    public void testFailure(final Failure failure)
    {
        final Description description = failure.getDescription();

        expectNotAlreadyReported(description);

        testResults.add(TapResult.failure(display(description), failure.getException()));
        alreadyReportedTests.add(description);
    }

    @Override
    public void testAssumptionFailure(final Failure failure)
    {
        final Description description = failure.getDescription();

        expectNotAlreadyReported(description);

        testResults.add(TapResult.ignore(display(description), failure.getException()));
        alreadyReportedTests.add(description);
    }

    @Override
    public void testIgnored(final Description description)
    {
        expectNotAlreadyReported(description);

        testResults.add(TapResult.ignore(display(description), null));
    }

    private String display(Description description)
    {
        return description.getClassName() + "." + description.getMethodName();
    }

    private void expectNotAlreadyReported(final Description description)
    {
        if(alreadyReportedTests.contains(description))
        {
            testResults.add(TapResult.failure("Framework issue: test " + description.getDisplayName() + " has been reported twice", null));
        }
    }
}
