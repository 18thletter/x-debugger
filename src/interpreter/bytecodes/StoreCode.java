/*
 * StoreCode.java
 */
package interpreter.bytecodes;

import interpreter.VirtualMachine;
import java.util.List;

/**
 * Pops the top item from the run time stack and stores it into the specified
 * offset from the start of the current frame.  The identifier is used as a
 * comment - to indicate the variable name into which the item was stored.
 * 
 * @author Raymund Lew
 */
public class StoreCode extends ByteCode {

    private int offset;
    private String identifier;
    private int storedValueForPrinting;

    @Override
    public void init(List<String> argumentList) {
        offset = Integer.parseInt(argumentList.get(0));
        identifier = argumentList.get(1);
    }

    @Override
    public void execute(VirtualMachine vm) {
        storedValueForPrinting = vm.peekRunTimeStack();
        vm.storeRunTimeStack(offset);
    }

    @Override
    public String toString() {
        return "STORE " + new Integer(offset).toString() + " " + identifier
               + "    "
               + identifier + " = " + new Integer(storedValueForPrinting).
                toString();
    }
}
