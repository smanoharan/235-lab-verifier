package controllers;

import models.ResultsList;
import models.RunResult;
import tuataraTMSim.TMachine;

import java.io.*;
import java.util.Arrays;

/**
 * Utility class for running tests, either via Web UI or the command line.
 *
 * @author Siva
 */
public class Tester
{
    /** Maximum number of steps to execute the machine for */
    public static final int MAX_ITER = 5000;

    /**
     * Test the machine against all cases in the testFile.
     *
     * @param machineFile The path to the machine
     * @param testFile The path to the testfile
     * @param type The type of the machine (e.g. 4.1 for Lab 4.1)
     * @return The list of results, each corresponding to a test case.
     * @throws Exception
     */
    public static <T extends RunResult> ResultsList<T> testMachine(
            final String machineFile, final String testFile, final String type, final AbstractTestRunner<T> testRunner)
    {

        ObjectInputStream in = null;
        BufferedReader reader = null;
        ResultsList<T> results = null;

        try
        {
            in = new ObjectInputStream(new FileInputStream(machineFile));
            TMachine tm = (TMachine)in.readObject();
            reader = new BufferedReader(new FileReader(testFile));

            results = testRunner.testAllCases(tm, reader, type);
        }
        catch (StreamCorruptedException se)
        {
            String msg = "The uploaded file could not be read as a Turing machine: " + se.getMessage();
            results = ResultsList.newErrorResultList(Arrays.asList(msg), type, null);
        }
        catch (IOException ie)
        {
            String msg = "Exception while reading the machine: " + ie.getMessage();
            results = ResultsList.newErrorResultList(Arrays.asList(msg), type, null);
        }
        catch (Exception e)
        {
            String msg = "Exception: [" + e.getClass().getName() + "] " + e.getMessage();
            results = ResultsList.newErrorResultList(Arrays.asList(msg), type, null);
        }
        finally
        {
            try { in.close(); } catch (Exception e) {}
            try { reader.close(); } catch (Exception e) {}
        }

        return results;
    }

    /**
     * For testing via command line
     *
     * @param args The arguments: [machine-file-path] [test-file-path]
     * @throws Exception
     */
    public static void main(String[] args) throws Exception
    {
        if (args.length < 2)
        {
            System.out.println("Usage: Tester <machine> <test-file>");
        }
        else
        {
            System.out.println("Currently at: " + System.getProperty("user.dir"));
            System.out.println(testMachine(args[0], args[1], null, new NormalTestRunner()));
            System.out.println("Done.");
        }
    }
}
