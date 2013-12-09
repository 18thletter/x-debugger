/*
 * ArgsCode.java
 */
package interpreter.bytecodes;

import interpreter.VirtualMachine;
import java.util.List;

/**
 * Instructs the interpreter to set up a new frame that will include a number
 * of arguments.  Used prior to calling a function.  Also tells the virtual
 * machine how many arguments were called.
 *
 * @author Raymund Lew
 */
public class ArgsCode extends ByteCode {

    private int numberOfArguments;

    @Override
    public void init(List<String> argumentList) {
        numberOfArguments = Integer.parseInt(argumentList.get(0));
    }

    @Override
    public void execute(VirtualMachine vm) {
        vm.newFrameAtRunTimeStack(numberOfArguments);
    }

    @Override
    public String toString() {
        return "ARGS " + new Integer(numberOfArguments).toString();
    }
}
