/*
 * HaltCode.java
 */
package interpreter.bytecodes;

import interpreter.VirtualMachine;
import java.util.List;

/**
 * Stops execution of byte codes by the virtual machine.
 * 
 * @author Raymund Lew
 */
public class HaltCode extends ByteCode {

    @Override
    public void init(List<String> argumentList) {
    }

    @Override
    public void execute(VirtualMachine vm) {
        vm.setRunning(false);
    }

    @Override
    public String toString() {
        return "HALT";
    }
}
