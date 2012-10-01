package controllers;

import models.RunResult;
import tuataraTMSim.CA_Tape;
import tuataraTMSim.TM_Simulator;
import tuataraTMSim.TMachine;
import tuataraTMSim.Tape;

/**
 * Test a Turing machine with a single test case, for labs 1.1 to 4.1
 *
 * @author Siva
 */
public class NormalTestRunner extends AbstractTestRunner<RunResult>
{
    /** @inheritDoc */ @Override
    protected RunResult testSingleCase(final TMachine tm, final String input, final String exp)
    {
        Tape tape = new CA_Tape(input);
        TM_Simulator sim = new TM_Simulator(tm, tape);

        String act;
        boolean match;
        try
        {
            boolean didHalt = sim.runUntilHalt(Tester.MAX_ITER, true);
            if (didHalt)
            {
                act = sim.getTapeString();
                match = (act.equals(exp));
            }
            else
            {
                act = sim.getErrMsg();
                match = false;
            }
        }
        catch (Exception e)
        {
            act = e.getMessage();
            match = false;
        }
        return new RunResult(act, exp, input, match);
    }
}
