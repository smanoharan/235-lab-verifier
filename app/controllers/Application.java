package controllers;

import models.InputMachine;
import models.Lab42RunResult;
import models.ResultsList;
import models.RunResult;
import play.mvc.Controller;

import java.util.ArrayList;
import java.util.List;

/**
 * The main controller for handling uploaded Turing machines.
 *
 * @author Siva
 */
public class Application extends Controller 
{
    public static void index()
    {
        render();
    }

    // for running labs1.1 to 4.1
    private static ResultsList<RunResult> toResult(InputMachine inputMachine, String type)
    {
        String machinePath = inputMachine.file.getFile().toString();
        String testPath = "app/inputs/t" + type + ".txt";
        return Tester.testMachine(machinePath, testPath, type, new NormalTestRunner());
    }

    // for running lab 4.2
    private static ResultsList<Lab42RunResult> testLab42(InputMachine inputMachine)
    {
        String machinePath = inputMachine.file.getFile().toString();
        String testPath = "app/inputs/t4.2.txt";
        return Tester.testMachine(machinePath, testPath, "4.2", new Lab42TestRunner());
    }

    public static void checkAll(InputMachine m11, InputMachine m12, InputMachine m13, InputMachine m21,
                                InputMachine m22, InputMachine m31, InputMachine m32, InputMachine m41)
    {
        final String[] mtypes = new String[] {  "1.1", "1.2", "1.3", "2.1", "2.2", "3.1", "3.2", "4.1", "4.2" };
        int count = 0;
        int index = 0;

        try
        {
            InputMachine[] inputMachines = new InputMachine[] { m11, m12, m13, m21, m22, m31, m32, m41 };
            List<ResultsList<RunResult>> allResults = new ArrayList<ResultsList<RunResult>>();

            // test each uploaded machine:
            for (index=0; index<inputMachines.length; index++)
            {
                if (inputMachines[index] != null && inputMachines[index].file != null)
                {
                    count++;
                    allResults.add(toResult(inputMachines[index], mtypes[index]));
                }
            }

            if (count == 0) // no machines uploaded
            {
                String message = "Error: Please upload at least one Turing Machine to verify.";
                render("Application/index.html", message);
            }
            else if (m41 != null && m41.file != null) // include Lab 4.2
            {
                final ResultsList<Lab42RunResult> lab42results = testLab42(m41);
                render("Application/lab42results.html", allResults, lab42results);
            }
            else // no need to include Lab 4.2
            {
                render("Application/results.html", allResults);
            }
        }
        catch (Exception e)
        {
            String message = "Lab: " + mtypes[index] + ", Exception: [" + e.getClass().getName() + "] " + e.getMessage();
            render("Application/index.html", message);
        }
    }
}
