/*
 * ReadCode.java
 */
package interpreter.bytecodes;

import interpreter.VirtualMachine;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.List;

/**
 * Prompts the user for an integer input, and pushes it onto the top of the
 * run time stack.
 * 
 * @author Raymund Lew
 */
public class ReadCode extends ByteCode {

    @Override
    public void init(List<String> argumentList) {
    }

    @Override
    public void execute(VirtualMachine vm) {
        int i = 0;
        for (;;) {
            System.out.print("? ");
            try {
                i = Integer.parseInt((new BufferedReader(new InputStreamReader(
                                      System.in))).readLine());
            } catch (Exception e) {
                System.out.println("Invalid input.  Please enter an integer.\n");
                continue;
            }
            break;
        }
        vm.pushRunTimeStack(i);
    }

    @Override
    public String toString() {
        return "READ";
    }
}
