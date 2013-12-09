/*
 * DebuggerReturnCode.java
 */
package interpreter.bytecodes.debuggerbytecodes;

import interpreter.VirtualMachine;
import interpreter.bytecodes.ReturnCode;
import interpreter.debugger.DebuggerVirtualMachine;

/**
 * Executes ReturnCode, as well as removing the returning function's variables
 * from the debugger environment record.
 * 
 * @author Raymund Lew
 * @see ReturnCode
 */
public class DebuggerReturnCode extends ReturnCode {

    @Override
    public void execute(VirtualMachine vm) {
        super.execute(vm);
        ((DebuggerVirtualMachine) vm).exitFunction();
    }
}
