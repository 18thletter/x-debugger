/*
 * DebuggerLitCode.java
 */
package interpreter.bytecodes.debuggerbytecodes;

import interpreter.VirtualMachine;
import interpreter.bytecodes.LitCode;
import interpreter.debugger.DebuggerVirtualMachine;

/**
 * Executes LitCode, and also enters a new variable into the debugger
 * environment.
 *
 * @author Raymund Lew
 * @see LitCode
 */
public class DebuggerLitCode extends LitCode {

    @Override
    public void execute(VirtualMachine vm) {
        super.execute(vm);
        if (identifier != null) {
            ((DebuggerVirtualMachine) vm).enterEnvironmentRecord(identifier,
                                                                 vm.
                    topFrameSizeRunTimeStack() - 1);
        }
    }
}
