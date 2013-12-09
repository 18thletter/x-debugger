/*
 * LoadCode.java
 */
package interpreter.bytecodes;

import interpreter.VirtualMachine;
import java.util.List;

/**
 * Pushes the value in the specified offset from the start of the current frame
 * onto the top of the run time stack.  The identifier name is used as a
 * comment - to indicate from which variable the data was loaded from.
 * 
 * @author Raymund Lew
 */
public class LoadCode extends ByteCode {

    private int offset;
    private String identifier;

    @Override
    public void init(List<String> argumentList) {
        offset = Integer.parseInt(argumentList.get(0));
        identifier = argumentList.get(1);
    }

    @Override
    public void execute(VirtualMachine vm) {
        vm.loadRunTimeStack(offset);
    }

    @Override
    public String toString() {
        return "LOAD " + new Integer(offset).toString() + " " + identifier
               + "    <load " + identifier
               + ">";
    }
}
