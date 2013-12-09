/*
 * ReturnCode.java
 */
package interpreter.bytecodes;

import interpreter.VirtualMachine;
import java.util.List;

/**
 * Returns from the current function.  The function name is used as a comment
 * to indicate the current function.
 * 
 * @author Raymund Lew
 */
public class ReturnCode extends ByteCode {

    private String functionName = null;
    private int storedValueForPrinting;

    @Override
    public void init(List<String> argumentList) {
        if (argumentList.size() > 0) {
            functionName = argumentList.get(0);
        }
    }

    @Override
    public void execute(VirtualMachine vm) {
        storedValueForPrinting = vm.peekRunTimeStack();
        vm.popFrameRunTimeStack();
        vm.popReturnAddrs();
    }

    @Override
    public String toString() {
        String returnString = "RETURN";
        if (functionName != null) {
            int indexOfLessThan = functionName.indexOf("<");
            String truncatedFunctionName = functionName;
            if (indexOfLessThan != -1) {
                truncatedFunctionName = functionName.substring(0,
                                                               indexOfLessThan);
            }
            returnString += " " + functionName + "    exit "
                            + truncatedFunctionName + ": "
                            + new Integer(storedValueForPrinting).toString();
        }
        return returnString;
    }
}
