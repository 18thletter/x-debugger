/*
 * GoToCode.java
 */
package interpreter.bytecodes;

import interpreter.VirtualMachine;
import java.util.List;

/**
 * Branch to the indicated label.
 * 
 * @author Raymund Lew
 */
public class GoToCode extends ByteCode {

    private String label;
    private int address;

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
        vm.setPc(address - 1);
    }

    @Override
    public String toString() {
        return "GOTO " + label;
    }
}
