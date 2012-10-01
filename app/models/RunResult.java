package models;

/**
 * The result of a single test case.
 *
 * @author Siva
 */
public class RunResult
{
    public final String actual;
    public final String expected;
    public final String input;
    public final boolean match;

    public RunResult(final String actual, final String expected,
                     final String input, final boolean match)
    {
        this.actual = actual;
        this.expected = expected;
        this.input = input;
        this.match = match;
    }

    @Override
    public String toString()
    {
        return String.format(
                "Match: %s, \t\tInput: %s, \t\tExpected: %s, \t\tActual: %s",
                match, input, expected, actual);
    }
}
