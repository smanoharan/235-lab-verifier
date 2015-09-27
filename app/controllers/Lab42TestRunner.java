package controllers;

import models.Lab42RunResult;
import tuataraTMSim.*;
import tuataramods.TM_Simulator;

/**
 * Test a Turing machine with a single test case, for lab 4.2
 *
 * @author Siva
 */
public class Lab42TestRunner extends AbstractTestRunner<Lab42RunResult>
{
    /** @inheritDoc */ @Override
    protected Lab42RunResult testSingleCase(final TMachine tm, final String input, final String exp)
    {
        Tape tape = new CA_Tape(input);
        TM_Simulator sim = new TM_Simulator(tm, tape);

        try
        {
            TMSimulatorState simState = sim.runUntilHaltForLab4Part2(Tester.MAX_ITER, exp);
            return new Lab42RunResult(simState.actual, exp, input, simState.passed,
                    simState.terminated, simState.parked, simState.finalState);
        }
        catch (Exception e)
        {
            boolean halted;
            try
            {
                halted = sim.isHalted();
            }
            catch (NoStartStateException e1)
            {
                halted = false;
            }

            return new Lab42RunResult(e.getMessage(), exp, input, false, halted, sim.getTape().isParked(), "-");
        }
    }
}
