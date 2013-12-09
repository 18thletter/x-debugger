/*
 * LitCode.java
 */
package interpreter.bytecodes;

import interpreter.VirtualMachine;
import java.util.List;

/**
 * Pushes a literal value onto the run time stack.  If an identifier argument
 * is specified, the literal value is still pushed, but it is also an indication
 * that a new variable was created and initialized to 0.
 * 
 * @author Raymund Lew
 */
public class LitCode extends ByteCode {

    protected int literalValue;
    protected String identifier = null;

    @Override
    public void init(List<String> argumentList) {
        literalValue = Integer.parseInt(argumentList.get(0));
        if (argumentList.size() > 1) {
            identifier = argumentList.get(1);
        }
    }

    @Override
    public void execute(VirtualMachine vm) {
        vm.pushRunTimeStack(literalValue);
    }

    @Override
    public String toString() {
        String returnString = "";
        returnString += "LIT " + new Integer(literalValue).toString();
        if (identifier != null) {
            returnString += " " + identifier + "    int " + identifier;
        }
        return returnString;
    }
}
