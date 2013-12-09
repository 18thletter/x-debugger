/*
 * BopCode.java
 */
package interpreter.bytecodes;

import interpreter.VirtualMachine;
import java.util.List;

/**
 * Pops the top two levels of the run time stack and performs the indicated
 * operation on them.
 *
 * The operations are:
 * 1. +
 * 2. -
 * 3. /
 * 4. *
 * 5. ==
 * 6. !=
 * 7. <=
 * 8. >
 * 9. >=
 * 10. <
 * 11. |
 * 12. &
 * 
 * @author Raymund Lew
 */
public class BopCode extends ByteCode {

    private String operator;

    @Override
    public void init(List<String> argumentList) {
        operator = argumentList.get(0);
    }

    @Override
    public void execute(VirtualMachine vm) {
        int operand2 = vm.popRunTimeStack();
        int operand1 = vm.popRunTimeStack();

        if (operator.equals("+")) {
            vm.pushRunTimeStack(operand1 + operand2);
        } else if (operator.equals("-")) {
            vm.pushRunTimeStack(operand1 - operand2);
        } else if (operator.equals("/")) {
            vm.pushRunTimeStack(operand1 / operand2);
        } else if (operator.equals("*")) {
            vm.pushRunTimeStack(operand1 * operand2);
        } else if (operator.equals("==")) {
            if (operand1 == operand2) {
                vm.pushRunTimeStack(1);
            } else {
                vm.pushRunTimeStack(0);
            }
        } else if (operator.equals("!=")) {
            if (operand1 != operand2) {
                vm.pushRunTimeStack(1);
            } else {
                vm.pushRunTimeStack(0);
            }
        } else if (operator.equals("<=")) {
            if (operand1 <= operand2) {
                vm.pushRunTimeStack(1);
            } else {
                vm.pushRunTimeStack(0);
            }
        } else if (operator.equals(">")) {
            if (operand1 > operand2) {
                vm.pushRunTimeStack(1);
            } else {
                vm.pushRunTimeStack(0);
            }
        } else if (operator.equals(">=")) {
            if (operand1 >= operand2) {
                vm.pushRunTimeStack(1);
            } else {
                vm.pushRunTimeStack(0);
            }
        } else if (operator.equals("<")) {
            if (operand1 < operand2) {
                vm.pushRunTimeStack(1);
            } else {
                vm.pushRunTimeStack(0);
            }
        } else if (operator.equals("|")) {
            if (operand1 > 0 || operand2 > 0) {
                vm.pushRunTimeStack(1);
            } else {
                vm.pushRunTimeStack(0);
            }
        } else if (operator.equals("&")) {
            if (operand1 > 0 && operand2 > 0) {
                vm.pushRunTimeStack(1);
            } else {
                vm.pushRunTimeStack(0);
            }
        }
    }

    @Override
    public String toString() {
        return "BOP " + operator;
    }
}
