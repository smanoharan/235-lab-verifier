package models;

/**
 * Result of a test case for Lab 4.2
 *
 * @author Siva
 */
public class Lab42RunResult extends RunResult
{
    public final boolean terminated;
    public final boolean parked;
    public final String finalState;

    public Lab42RunResult(final String actual, final String expected, final String input, final boolean match,
                          final boolean terminated, final boolean parked, final String finalState)
    {
        super(actual, expected, input, match);
        this.terminated = terminated;
        this.parked = parked;
        this.finalState = finalState;
    }

    @Override
    public String toString()
    {
        return String.format(
                "Match: %s, \t\tInput: %s, \t\tState: %s, \t\tExpectedOutput: %s," +
                        " \t\tActualOutput: %s, \t\tParked: %s, \t\tHalted: %s",
                match, input, finalState, expected, actual, parked, terminated);
    }
}
