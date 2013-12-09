/*
 * DebuggerPopCode.java
 */
package interpreter.bytecodes.debuggerbytecodes;

import interpreter.VirtualMachine;
import interpreter.bytecodes.PopCode;
import interpreter.debugger.DebuggerVirtualMachine;

/**
 * Executes PopCode, as well as removing variables from the debugger environment
 * record.
 *
 * @author Raymund Lew
 * @see PopCode
 */
public class DebuggerPopCode extends PopCode {

    @Override
    public void execute(VirtualMachine vm) {
        super.execute(vm);
        ((DebuggerVirtualMachine) vm).removeSymbols(levelsOfStackToPop);
    }
}
