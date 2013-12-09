/*
 * FormalCode.java
 */
package interpreter.bytecodes.debuggerbytecodes;

import interpreter.VirtualMachine;
import interpreter.bytecodes.ByteCode;
import interpreter.debugger.DebuggerVirtualMachine;
import java.util.List;

/**
 * Records a new variable in the debugger environment record.
 * 
 * @author Raymund Lew
 */
public class FormalCode extends ByteCode {

    String identifier;
    int offset;

    @Override
    public void init(List<String> argumentList) {
        identifier = argumentList.get(0);
        offset = Integer.parseInt(argumentList.get(1));
    }

    @Override
    public void execute(VirtualMachine vm) {
        ((DebuggerVirtualMachine) vm).enterEnvironmentRecord(identifier, offset);
    }

    @Override
    public String toString() {
        return "FORMAL " + identifier + " " + offset;
    }
}
