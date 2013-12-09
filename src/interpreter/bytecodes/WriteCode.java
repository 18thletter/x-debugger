/*
 * WriteCode.java
 */
package interpreter.bytecodes;

import interpreter.VirtualMachine;
import java.util.List;

/**
 * Writes the value on the top of the run time stack to output, leaving the
 * written value on top of the stack.
 * 
 * @author Raymund Lew
 */
public class WriteCode extends ByteCode {

    @Override
    public void init(List<String> argumentList) {
    }

    @Override
    public void execute(VirtualMachine vm) {
        System.out.println(vm.peekRunTimeStack());
    }

    @Override
    public String toString() {
        return "WRITE";
    }
}
