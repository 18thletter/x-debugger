/* * LineCode.java */
package interpreter.bytecodes.debuggerbytecodes;

import interpreter.VirtualMachine;
import interpreter.bytecodes.ByteCode;
import interpreter.debugger.DebuggerVirtualMachine;
import java.util.List;

/**
 * Indicates a line in the .x source code.
 *
 * @author Raymund Lew */
public class LineCode extends ByteCode {

    int lineNumber;

    @Override
    public void init(List<String> argumentList) {
        lineNumber = Integer.parseInt(argumentList.get(0));
    }

    @Override
    public void execute(VirtualMachine vm) {
        DebuggerVirtualMachine dvm = (DebuggerVirtualMachine) vm;
        dvm.setCurrentLine(lineNumber);
    }

    /**
     * This method is used by the DebuggerVirtualMachine to set the breakable
     * lines.
     *
     * @return the line number
     */
    public int getLineNumber() {
        return lineNumber;
    }

    @Override
    public String toString() {
        return "LINE " + lineNumber;
    }
}
