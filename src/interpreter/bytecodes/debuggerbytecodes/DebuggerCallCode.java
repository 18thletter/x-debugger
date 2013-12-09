/*
 * DebuggerCallCode.java
 */
package interpreter.bytecodes.debuggerbytecodes;

import interpreter.VirtualMachine;
import interpreter.bytecodes.CallCode;
import interpreter.debugger.DebuggerVirtualMachine;

/**
 * Executes CallCode, and tells the debugger that a new function has been
 * entered, in addition to saving the function arguments and program
 * counter address (for possible future re-execution).
 *
 * @author Raymund Lew
 * @see CallCode
 */
public class DebuggerCallCode extends CallCode {

    @Override
    public void execute(VirtualMachine vm) {
        super.execute(vm);
        ((DebuggerVirtualMachine) vm).enterFunction();
        ((DebuggerVirtualMachine) vm).saveFunctionArgs(functionArgList);
        ((DebuggerVirtualMachine) vm).saveFunctionAddress(address);
    }
}
