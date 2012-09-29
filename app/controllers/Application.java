package controllers;

import play.*;
import play.mvc.*;

import java.util.*;

import models.*;
import tuataraTMSim.*;

public class Application extends Controller 
{
    public static void index() 
    {
      render();
    }

    private static RunResults toResult(Machine machine, String type) throws Exception
    {
      String machinePath = machine.file.getFile().toString();
      String testPath = "app/inputs/t" + type + ".txt";
      return Tester.testMachine(machinePath, testPath, type);
    }

    public static void checkAll(Machine m11, Machine m12, Machine m13, Machine m21, Machine m22, 
				Machine m31, Machine m32, Machine m41)
    {
      Machine[] machines = new Machine[] { m11, m12, m13, m21, m22, m31, m32, m41 };
      String[] mtypes = new String[] {  "1.1", "1.2", "1.3", "2.1", "2.2", "3.1", "3.2", "4.1" };

      List<RunResults> allResults = new ArrayList<RunResults>();
      int count = 0;
      for (int i=0; i<machines.length; i++)
      {
        if (machines[i] != null && machines[i].file != null)
        {
          count++;
          try 
          { 
            allResults.add(toResult(machines[i], mtypes[i])); 
            if (i == machines.length - 1)
            {
              // allResults.add(checkAlphabetAndEndStates) TODO
            }
          }
          catch (Exception e) 
          { 
            renderText("index: " + i + ", qs: " + mtypes[i] + ", error: " + e.getMessage()); 
          }
        }
      }

      if (count > 0) render("Application/results.html", allResults); 
      else renderText("Error: Please upload at least one Turing Machine to verify.");
    }
}
