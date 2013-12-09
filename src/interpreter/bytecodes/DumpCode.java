/*
 * DumpCode.java
 */
package interpreter.bytecodes;

import interpreter.VirtualMachine;
import java.util.List;

/**
 * Signals the virtual machine whether or not to print the execution of the
 * byte codes.
 * 
 * @author Raymund Lew
 */
public class DumpCode extends ByteCode {

    private String onOrOff;

    @Override
    public void init(List<String> argumentList) {
        onOrOff = argumentList.get(0);
    }

    @Override
    public void execute(VirtualMachine vm) {
        if (onOrOff.equals("ON")) {
            vm.setDumping(true);
        } else {
            vm.setDumping(false);
        }
    }
}
