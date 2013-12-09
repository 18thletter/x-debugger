/**
 * ByteCode.java
 */
package interpreter.bytecodes;

import interpreter.VirtualMachine;
import java.util.List;

/**
 * A byte code that will be contained by the program and executed by the
 * interpreter's virtual machine.
 *
 * @author Raymund Lew
 */
public abstract class ByteCode {

    /**
     * Initializes the byte codes arguments from a list of arguments.
     *
     * @param argumentList
     */
    public abstract void init(List<String> argumentList);

    /**
     * Executes the functionality of the byte code using the virtual machine.
     * 
     * @param vm the virtual machine that the byte code is loaded into
     */
    public abstract void execute(VirtualMachine vm);
}
