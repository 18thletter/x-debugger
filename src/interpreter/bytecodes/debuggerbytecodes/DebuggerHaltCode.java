/*
 * DebuggerHaltCode.java
 */
package interpreter.bytecodes.debuggerbytecodes;

import interpreter.VirtualMachine;
import interpreter.bytecodes.HaltCode;
import interpreter.debugger.DebuggerVirtualMachine;

/**
 * Executes HaltCode, and also tells the debugger virtual machine that execution
 * has completed.
 *
 * @author Raymund Lew
 * @see HaltCode
 */
public class DebuggerHaltCode extends HaltCode {

    @Override
    public void execute(VirtualMachine vm) {
        super.execute(vm);
        ((DebuggerVirtualMachine) vm).executionFinished();
    }
}
