package com.writeoncereadmany.tap.examples;

import org.junit.Test;

public class ThrowingTest
{
    @Test
    public void aTestThatThrows()
    {
        throw new RuntimeException("Exception message");
    }
}
