/*
 * FalseBranchCode.java
 */
package interpreter.bytecodes;

import interpreter.VirtualMachine;
import java.util.List;

/**
 * Pops the top of the stack -- if it is false then branch to the label; else
 * continue with execution of the next byte code.
 * 
 * @author Raymund Lew
 */
public class FalseBranchCode extends ByteCode {

    private String label;
    private int address;

    @Override
    public void init(List<String> argumentList) {
        label = argumentList.get(0);
    }

    public String getLabel() {
        return label;
    }

    public void setAddress(int address) {
        this.address = address;
    }

    @Override
    public void execute(VirtualMachine vm) {
        if (vm.popRunTimeStack() == 0) {
            vm.setPc(address - 1);
        }
    }

    @Override
    public String toString() {
        return "FALSEBRANCH " + label;
    }
}
