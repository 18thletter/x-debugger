/*
 * LabelCode.java
 */
package interpreter.bytecodes;

import interpreter.VirtualMachine;
import java.util.List;

/**
 * Target for the branching byte codes.
 * 
 * @author Raymund Lew
 */
public class LabelCode extends ByteCode {

    private String label;

    @Override
    public void init(List<String> argumentList) {
        label = argumentList.get(0);
    }

    public String getLabel() {
        return label;
    }

    @Override
    public void execute(VirtualMachine vm) {
        
    }

    @Override
    public String toString() {
        return "LABEL " + label;
    }
}
