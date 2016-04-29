package com.writeoncereadmany.tap;

public final class TapResult
{
    private final boolean passed;
    private final String description;
    private final String directive;
    private final Throwable cause;

    private TapResult(final boolean passed, final String description, final String directive, final Throwable cause)
    {
        this.passed = passed;
        this.description = description;
        this.directive = directive;
        this.cause = cause;
    }

    public boolean passed()
    {
        return passed;
    }

    public String getDescription()
    {
        return description;
    }

    public String getDirective()
    {
        return directive;
    }

    public Throwable getCause()
    {
        return cause;
    }

    public static TapResult success(final String description)
    {
        return new TapResult(true, description, "", null);
    }

    public static TapResult failure(final String description, final Throwable cause)
    {
        return new TapResult(false, description, "", cause);
    }

    public static TapResult failure(final String failureMessage)
    {
        return failure(failureMessage, null);
    }

    public static TapResult ignore(final String description, final Throwable cause)
    {
        return new TapResult(true,
                             description,
                             "# skip " + (cause == null ? "Test ignored" : cause.getMessage()),
                             null);
    }

}
