package com.writeoncereadmany.tap.examples;

import org.junit.Assert;
import org.junit.Test;

public class FailingTest
{
    @Test
    public void aTestThatFails()
    {
        Assert.fail("Failure message");
    }
}
