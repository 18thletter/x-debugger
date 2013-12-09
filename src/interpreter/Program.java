/*
 * Program.java
 */
package interpreter;

import interpreter.bytecodes.ByteCode;
import interpreter.bytecodes.CallCode;
import interpreter.bytecodes.FalseBranchCode;
import interpreter.bytecodes.GoToCode;
import interpreter.bytecodes.LabelCode;
import interpreter.bytecodes.debuggerbytecodes.DebuggerCallCode;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

/**
 * Holds the byte codes and resolves the addresses of the branching byte codes.
 *
 * @author Raymund Lew
 */
public class Program {

    private List<ByteCode> bytecodes = new ArrayList<ByteCode>();

    /**
     *
     * @param code the new byte code to be added to the program
     */
    public void addCode(ByteCode code) {
        bytecodes.add(code);
    }

    /**
     *
     * @param index
     * @return the byte code retrieved at the specified index
     */
    public ByteCode getCode(int index) {
        return bytecodes.get(index);
    }

    /**
     *
     * @return the size of the program
     */
    public int size() {
        return bytecodes.size();
    }

    /**
     * Matches up all the branching byte codes with the addresses of the labels
     * to which they correspond to.
     */
    public void resolveAddress() {
        List<Integer> falsebranchIndexes = new ArrayList<Integer>();
        List<Integer> gotoIndexes = new ArrayList<Integer>();
        List<Integer> callIndexes = new ArrayList<Integer>();
        List<Integer> debuggercallIndexes = new ArrayList<Integer>();
        Map<String, Integer> labels = new TreeMap<String, Integer>();

        for (int i = 0; i < bytecodes.size(); i++) {
            if (bytecodes.get(i).getClass() == FalseBranchCode.class) {
                falsebranchIndexes.add(i);
            }
            if (bytecodes.get(i).getClass() == GoToCode.class) {
                gotoIndexes.add(i);
            }
            if (bytecodes.get(i).getClass() == CallCode.class) {
                callIndexes.add(i);
            }
            if (bytecodes.get(i).getClass() == DebuggerCallCode.class) {
                debuggercallIndexes.add(i);
            }
            if (bytecodes.get(i).getClass() == LabelCode.class) {
                labels.put(((LabelCode) (bytecodes.get(i))).getLabel(), i);
            }
        }

        for (int i = 0; i < falsebranchIndexes.size(); i++) {
            ((FalseBranchCode) (bytecodes.get(falsebranchIndexes.get(i)))).
                    setAddress(labels.get(
                    ((FalseBranchCode) (bytecodes.get(falsebranchIndexes.get(i)))).
                    getLabel()));
        }

        for (int i = 0; i < gotoIndexes.size(); i++) {
            ((GoToCode) (bytecodes.get(gotoIndexes.get(i)))).setAddress(labels.
                    get(((GoToCode) (bytecodes.get(gotoIndexes.get(i)))).
                    getLabel()));
        }
        for (int i = 0; i < callIndexes.size(); i++) {
            ((CallCode) (bytecodes.get(callIndexes.get(i)))).setAddress(labels.
                    get(((CallCode) (bytecodes.get(callIndexes.get(i)))).
                    getLabel()));
        }
        for (int i = 0; i < debuggercallIndexes.size(); i++) {
            ((DebuggerCallCode) (bytecodes.get(debuggercallIndexes.get(i)))).setAddress(labels.
                    get(((DebuggerCallCode) (bytecodes.get(debuggercallIndexes.get(i)))).
                    getLabel()));
        }
    }
}
