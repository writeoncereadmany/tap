package com.writeoncereadmany.tap;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;

import org.junit.runner.JUnitCore;
import org.junit.runner.Result;
import org.junit.runner.notification.RunListener;


import static java.util.Collections.singletonList;


public class JUnitTapCommandLineRunner
{
    public static void main(String... args)
    {
        // assumes all command line parameters are classes: maybe we could validate that? exception thrown if violated is pretty clear though
        final Class<?>[] testClasses = Arrays.stream(args).map(JUnitTapCommandLineRunner::loadClass).toArray(Class[]::new);

        File tapOutputFile = new File(System.getProperty("tap_output_file"));

        try (final FileOutputStream tapOutputStream = new FileOutputStream(tapOutputFile))
        {
            final TapListener listener = new TapListener(tapOutputStream, new TapFormatter());

            final Result result = runTestsWithListeners(testClasses, singletonList(listener));
            System.exit(result.wasSuccessful() ? 0 : 1);
        }
        catch (IOException ex)
        {
            throw new RuntimeException(ex);
        }
    }

    public static Result runTestsWithListeners(final Class<?>[] testClasses, final List<RunListener> listeners)
    {
        final JUnitCore jUnitCore = new JUnitCore();
        listeners.forEach(jUnitCore::addListener);
        return jUnitCore.run(testClasses);
    }

    private static Class<?> loadClass(final String name)
    {
        try
        {
            return Class.forName(name);
        }
        catch (ClassNotFoundException ex)
        {
            throw new IllegalArgumentException("Class not found : " + name, ex);
        }
    }
}
