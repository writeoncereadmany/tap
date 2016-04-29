package com.writeoncereadmany.tap.examples;

import org.junit.Ignore;
import org.junit.Test;

import static org.junit.Assert.assertTrue;



public class IgnoredTest
{
    @Test
    @Ignore
    public void aTestWhichIsIgnored()
    {
        assertTrue("Nothing really matters, Anyone can see, Nothing really matters to meeeeee", true);
    }
}
