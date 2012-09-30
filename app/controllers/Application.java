package controllers;

import models.InputMachine;
import tuataraTMSim.Lab42RunResult;
import tuataraTMSim.RunResults;
import tuataraTMSim.Tester;
import play.mvc.Controller;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Application extends Controller 
{
    public static void index() 
    {
      render();
    }

    private static RunResults toResult(InputMachine inputMachine, String type) throws Exception
    {
      String machinePath = inputMachine.file.getFile().toString();
      String testPath = "app/inputs/t" + type + ".txt";
      try
      {
    	RunResults res = Tester.testMachine(machinePath, testPath, type);
    	return res;
      }
      catch (Exception e)
      {
        return new RunResults(false, null, type, Arrays.asList("Exception: [" + e.getClass().getName() + "] " + e.getMessage()));
      }
    }

    public static void checkAll(InputMachine m11, InputMachine m12, InputMachine m13, InputMachine m21, InputMachine m22,
				InputMachine m31, InputMachine m32, InputMachine m41)
    {
      InputMachine[] inputMachines = new InputMachine[] { m11, m12, m13, m21, m22, m31, m32, m41 };
      String[] mtypes = new String[] {  "1.1", "1.2", "1.3", "2.1", "2.2", "3.1", "3.2", "4.1" };

      List<RunResults> allResults = new ArrayList<RunResults>();
      List<Lab42RunResult> lab42RunResults = null;
      boolean lab42allPassed = true;
      int count = 0;
      for (int i=0; i< inputMachines.length; i++)
      {
        if (inputMachines[i] != null && inputMachines[i].file != null)
        {
          count++;
          try 
          { 
            allResults.add(toResult(inputMachines[i], mtypes[i]));
            if (i == inputMachines.length - 1)
            {
              // allResults.add(checkAlphabetAndEndStates) TODO
              lab42RunResults = Tester.testAllLab4Part2(inputMachines[i].file.getFile().toString(), "app/inputs/t4.2.txt");
              for (Lab42RunResult res : lab42RunResults)
              {
                  if (!res.passed)
                  {
                      lab42allPassed = false;
                  }
              }
            }
          }
          catch (Exception e) 
          {
        	String message = "index: " + i + ", qs: " + mtypes[i] + ", error: [" + e.getClass().getName() + "] " + e.getMessage();
            render("Application/index.html", message);
            return;
          }
        }
      }

      if (count > 0) render("Application/results.html", allResults, lab42RunResults, lab42allPassed);
      else
      {
          String message = "Error: Please upload at least one Turing Machine to verify.";
          render("Application/index.html", message);
      }
    }
}
