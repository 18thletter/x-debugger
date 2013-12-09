/*
 * PopCode.java
 */
package interpreter.bytecodes;

import interpreter.VirtualMachine;
import java.util.List;

/**
 * Pop a number of items off the runtime stack.
 * 
 * @author Raymund Lew
 */
public class PopCode extends ByteCode {

    protected int levelsOfStackToPop;

    @Override
    public void init(List<String> argumentList) {
        levelsOfStackToPop = Integer.parseInt(argumentList.get(0));
    }

    @Override
    public void execute(VirtualMachine vm) {
        for (int i = 0; i < levelsOfStackToPop; i++) {
            vm.popRunTimeStack();
        }
    }

    @Override
    public String toString() {
        return "POP " + new Integer(levelsOfStackToPop).toString();
    }
}
