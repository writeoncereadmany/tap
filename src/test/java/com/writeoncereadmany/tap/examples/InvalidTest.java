package com.writeoncereadmany.tap.examples;

import org.junit.Assume;
import org.junit.Test;

public class InvalidTest
{
    @Test
    public void aTestWhichIsInvalid()
    {
        Assume.assumeTrue("The world is flat", false);
    }
}
