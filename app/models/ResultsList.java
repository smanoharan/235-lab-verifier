package models;
import java.util.List;

/**
 * Represents a list of results, along with some information about the overall test,
 *  such as the machine used, whether all tests passed and any warnings.
 *
 * @author Siva
 */
public class ResultsList<T extends RunResult>
{
    public final boolean allPassed;
    public final List<T> results;
    public final String type;
    public final List<String> errors;
    public final List<String> warnings;

    private ResultsList(
            final boolean allPassed, final List<T> results, final String type,
            final List<String> errors, final List<String> warnings)
    {
        this.allPassed = allPassed;
        this.results = results;
        this.type = type;
        this.errors = errors;
        this.warnings = warnings;
    }

    /**
     * Build a list of results representing a successful run.
     *
     * @param allPassed Whether all tests passed.
     * @param results The results for each test.
     * @param type The machine type (e.g. 4.1 for Lab4.1)
     * @param warnings A list of warnings
     * @param <E> The result type.
     * @return The list of results.
     */
    public static <E extends RunResult> ResultsList<E> newValidResultList(
            final boolean allPassed, final List<E> results, final String type, final List<String> warnings)
    {
        return new ResultsList<E>(allPassed, results, type, null, warnings);
    }

    /**
     * Build a list of results representing a unsuccessful run.
     *
     * @param errors The list of errors.
     * @param type The machine type (e.g. 4.1 for Lab4.1)
     * @param warnings A list of warnings.
     * @param <E> The result type.
     * @return The list of results.
     */
    public static <E extends RunResult> ResultsList<E> newErrorResultList(
            final List<String> errors, final String type, final List<String> warnings)
    {
        return new ResultsList<E>(false, null, type, errors, warnings);
    }

    @Override
    public String toString()
    {
        StringBuilder str = new StringBuilder();

        str.append(allPassed ? "All tests passed" : "Failed").append("\n");

        if (warnings != null && warnings.size() > 0)
        {
            appendList("Warnings", warnings, str);
        }

        if (errors != null && errors.size() > 0)
        {
            appendList("Errors", errors, str);
        }

        if (results != null && results.size() > 0)
        {
            appendList("Results", results, str);
        }

        return str.toString();
    }

    /**
     * Append a list of elements to a stringBuilder by calling toString() on each element of the list.
     * @param listName The name of the list, to use as a header.
     * @param list The list to append.
     * @param str The string builder to append to.
     * @param <E> The type of the list.
     */
    private static <E> void appendList(String listName, List<E> list, StringBuilder str)
    {
        str.append(listName).append(": \n");
        for (E elem : list)
        {
            str.append("\t").append(elem.toString()).append("\n");
        }
        str.append("\n");
    }
}
