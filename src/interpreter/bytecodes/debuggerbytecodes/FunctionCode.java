/*
 * FunctionCode.java
 */
package interpreter.bytecodes.debuggerbytecodes;

import interpreter.VirtualMachine;
import interpreter.bytecodes.ByteCode;
import interpreter.debugger.DebuggerVirtualMachine;
import java.util.List;

/**
 * Tells the debugger a new function is executing.
 *
 * @author Raymund Lew
 */
public class FunctionCode extends ByteCode {

    String functionName;
    int startLineNumber;
    int endLineNumber;

    @Override
    public void init(List<String> argumentList) {
        functionName = argumentList.get(0);
        if (functionName.indexOf("<") != -1) {
            functionName = functionName.substring(0, functionName.indexOf("<"));
        }
        startLineNumber = Integer.parseInt(argumentList.get(1));
        endLineNumber = Integer.parseInt(argumentList.get(2));
    }

    @Override
    public void execute(VirtualMachine vm) {
        DebuggerVirtualMachine dvm = (DebuggerVirtualMachine) vm;
        dvm.setFunctionInfo(functionName, startLineNumber, endLineNumber);
    }

    @Override
    public String toString() {
        return "FUNCTION " + functionName + " " + startLineNumber + " "
               + endLineNumber;
    }
}
