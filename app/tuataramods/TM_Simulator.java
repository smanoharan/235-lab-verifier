/*
 * TM_Simulator.java
 *
 * Created on November 7, 2006, 3:20 PM
 *
 */

//  ------------------------------------------------------------------
//
//  Copyright (c) 2006-2007 James Foulds and the University of Waikato
//
//  ------------------------------------------------------------------
//  This file is part of Tuatara Turing Machine Simulator.
//
//  Tuatara Turing Machine Simulator is free software: you can redistribute
//  it and/or modify it under the terms of the GNU General Public License as
//  published by the Free Software Foundation, either version 3 of the License,
//  or (at your option) any later version.
//
//  Tuatara Turing Machine Simulator is distributed in the hope that it will be
//  useful, but WITHOUT ANY WARRANTY; without even the implied warranty of
//  MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
//  GNU General Public License for more details.
//
//  You should have received a copy of the GNU General Public License
//  along with Tuatara Turing Machine Simulator.  If not, see
//  <http://www.gnu.org/licenses/>.
//
//  author email: jf47 (at) waikato (dot) ac (dot) nz
//
//  ------------------------------------------------------------------


package tuataramods;

import tuataraTMSim.*;

public class TM_Simulator extends tuataraTMSim.TM_Simulator
{
    private String errMsg;

    /**
     * Creates a new instance of TM_Simulator
     */
    public TM_Simulator(TMachine turingMachine, Tape tape)
    {
        super(turingMachine, tape);
    }

    @Override
    public String getErrMsg()
    {
        return errMsg;
    }

    /**
     * Executes the machine, running until it either halts, encounters a problem or maxSteps is reached.
     *
     * @param maxSteps The maximum number of machine iterations allowed for the computation (0 for no limit).
     *                 If this number is reached, the computation will be aborted and will return false.
     * @return True if the machine halts with the head parked. An exception will be thrown for know errors (e.g. going
     * past the start of the tape). Anything else will return false (including not terminating in the given number of
     * steps).
     */
    private boolean executeMachine(int maxSteps) throws NoStartStateException, TapeBoundsException, UndefinedTransitionException
    {
        try
        {
            // An exception is thrown by step() on success or failure, so in most cases this won't actually loop until maxSteps.
            for (int currentStep = 0; currentStep <= maxSteps || maxSteps == 0; currentStep++)
            {
                step();
            }

            // ..but if it does, return false and set the error message
            this.errMsg = "The machine did not halt within " + maxSteps + " steps. Aborting.";
            return false;
        }
        catch (ComputationCompletedException e)
        {
            // The machine halted correctly
            errMsg = "";
            return true;
        }
        catch (TapeBoundsException e)
        {
            this.errMsg = "R/W head went past the start of the tape";
            throw e;
        }
        catch (UndefinedTransitionException e)
        {
            this.errMsg = e.getMessage();
            throw e;
        }
        catch (NoStartStateException e)
        {
            this.errMsg = "No start state";
            throw e;
        }
    }

    /**
     * Runs until the machine halts.  Returns true only if the machine terminates successfully with the
     * head parked within the specified number of steps.
     * <br>
     * Modified from the original Tuatara method to work the same as the GUI - the Tuatara version of this method
     * doesn't catch any exceptions, instead using its own methods to detect success of failure, which causes
     * some inconsistency in a small number of cases. This version works in the same way as the code that runs
     * when the 'execute' button is pressed in Tuatara, so should be more consistent.
     *
     * @param maxSteps        The maximum number of machine iterations allowed for the computation (0 for no limit).
     *                        If this number is reached, the computation will be aborted and will return false.
     * @param doConsoleOutput Determines if we output the configurations and computation result to standard out console.
     */
    @Override
    public boolean runUntilHalt(int maxSteps, boolean doConsoleOutput)
    {
        boolean passed;
        try
        {
            passed = this.executeMachine(maxSteps);
        }
        catch (TapeBoundsException | UndefinedTransitionException | NoStartStateException e)
        {
            passed = false;
        }

        return passed;
    }

    @Override
    public TMSimulatorState runUntilHaltForLab4Part2(int maxSteps, String exp) throws NoStartStateException
    {
        boolean ignoreOutput = exp.equals("-");
        this.setCurrentState(this.getMachine().getStartState());

        boolean passed = false;

        try
        {
            passed = this.executeMachine(maxSteps);
        }
        catch (TapeBoundsException | NoStartStateException e)
        {
            passed = false;
        }
        catch (UndefinedTransitionException e)
        {
            if (ignoreOutput)
            {
                passed = true;
            }
        }

        TMSimulatorState state = new TMSimulatorState(this.isHalted(), this.getTape().isParked(),
                this.getCurrentState().getLabel(), passed, passed ? this.getTapeString() : this.getErrMsg());

        return state;
    }
}