/*
 * CallCode.java
 */
package interpreter.bytecodes;

import interpreter.VirtualMachine;
import java.util.ArrayList;
import java.util.List;

/**
 * Transfers control the the indicated function.
 * 
 * @author Raymund Lew
 */
public class CallCode extends ByteCode {

    private String label;
    protected int address;
    protected List<Integer> functionArgList;

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
        functionArgList = new ArrayList<Integer>();
        vm.pushReturnAddrs();
        vm.setPc(address - 1);
        for (int i = 0; i < vm.topFrameSizeRunTimeStack(); i++) {
            vm.loadRunTimeStack(i);
            functionArgList.add(vm.popRunTimeStack());
        }
    }

    @Override
    public String toString() {
        String returnString = "";
        int indexOfLessThan = label.indexOf("<");
        String truncatedLabel = label;
        if (indexOfLessThan != -1) {
            truncatedLabel = label.substring(0, indexOfLessThan);
        }
        returnString += "CALL " + label + "    " + truncatedLabel + "(";
        for (int i = 0; i < functionArgList.size(); i++) {
            returnString += functionArgList.get(i).toString();
            if (i != functionArgList.size() - 1) {
                returnString += ",";
            }
        }
        return returnString + ")";
    }
}
