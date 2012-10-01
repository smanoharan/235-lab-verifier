package controllers;

import models.ResultsList;
import models.RunResult;
import tuataraTMSim.*;

import java.io.BufferedReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * Test a Turing machine with a single test case.
 *
 * @author Siva
 */
public abstract class AbstractTestRunner<T extends RunResult>
{
    /**
     * Check for known errors:
     *  - 2 or more start states
     *  - Alphabet (only for Lab4)
     *
     * @param tm The Turing machine to check
     * @param isLab4 Whether this machine corresponds to lab 4
     * @return A list of errors (empty list if no errors found).
     */
    protected static List<String> checkForErrors(TMachine tm, final boolean isLab4)
    {
        List<String> errors = new ArrayList<String>();
        String err;

        // alphabet (for lab 4.1)
        if (isLab4)
        {
            err = TMachineHelper.AssertAlphabetIsBinary(tm);
            if (err != null)
            {
                errors.add(err);
            }
        }

        // too many start states
        err = TMachineHelper.AssertThereExistsAUniqueStartState(tm);
        if (err != null)
        {
            errors.add(err);
        }

        return errors;
    }

    /**
     * Check for known warnings:
     *  - Non-determinism in any other state (e.g. two transitions with the same precondition)
     *
     * @param tm The Turing machine to check
     * @return A list of warnings (empty list if no warnings found).
     */
    protected static List<String> checkForWarnings(TMachine tm)
    {
        return TMachineHelper.AssertDeterminism(tm);
    }

    /**
     * Test the machine against all cases in the testFile.
     *
     * @param tm The Turing machine
     * @param reader The reader wrapped around the test file.
     * @param type The type of the machine (e.g. 4.1 for Lab 4.1)
     * @return The list of results, each corresponding to a test case.
     * @throws java.io.IOException
     */
     public ResultsList<T> testAllCases(final TMachine tm, final BufferedReader reader,
                                                 final String type) throws IOException
     {
        ArrayList<T> results = new ArrayList<T>();
        boolean passedSoFar = true;

        List<String> warnings = checkForWarnings(tm);
        if (warnings.size() == 0)
        {
            warnings = null; // clear if no warnings found.
        }

        List<String> errors = checkForErrors(tm, type.equals("4.1"));
        if (errors.size() > 0)
        {
            return ResultsList.newErrorResultList(errors, type, warnings);
        }

        // loop through the lines in the test-file
        while(true)
        {
            String line = reader.readLine();
            if (line == null)
            {
                break;
            }

            // split the line (parts are input-string and expected-output-string)
            String[] parts = line.split(" ");
            T result = testSingleCase(tm, parts[0], parts[1]);
            results.add(result);

            if (!result.match)
            {
                passedSoFar = false;
            }
        }

        return ResultsList.newValidResultList(passedSoFar, results, type, warnings);
     }

    /**
     * Test the machine against a single test case.
     *
     * @param tm The Turing machine.
     * @param input The tape input.
     * @param exp The expected tape output.
     * @return The runResult representing the result of this test run.
     */
    protected abstract T testSingleCase(final TMachine tm, final String input, final String exp);
}
