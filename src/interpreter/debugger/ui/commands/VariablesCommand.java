/*
 * VariablesCommand.java
 */
package interpreter.debugger.ui.commands;

import interpreter.debugger.DebuggerVirtualMachine;
import interpreter.debugger.ui.UserInterface;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

/**
 * Usage: v [s i]*
 *   Lists all current local variables when used without arguments.
 *   When variable name(s) s and value(s) i is specified, changes those variable(s).
 *   Example:
 *     v             lists all local variables
 *     v i 19        changes the variable i to 19
 *     v i 19 j 2    changes the variable i to 19 and j to 2
 * 
 * @author Raymund Lew
 */
public class VariablesCommand extends InterfaceCommand {

    private Map<String, Integer> variablesWithOffsets;
    private List<Integer> variableValues;
    private Map<String, Integer> variablesWithNewValues;

    @Override
    public boolean init(List<String> argumentList, DebuggerVirtualMachine dvm) {
        variablesWithOffsets = new LinkedHashMap<String, Integer>();
        variablesWithNewValues = new HashMap<String, Integer>();
        variableValues = new ArrayList<Integer>();

        if (argumentList.size() % 2 != 0) {
            UserInterface.println("Invalid arguments.");
            HelpCommand.displayHelp(this);
            return false;
        }

        if (dvm.getFunctionName() == null) {
            UserInterface.println("There is no current function.\n");
            return false;
        }

        for (Map.Entry<String, Object> e : dvm.
                getSymbolsFunctionEnvironmentRecord().entrySet()) {
            variablesWithOffsets.put(e.getKey(), (Integer) e.getValue());
        }

        if (variablesWithOffsets.isEmpty()) {
            UserInterface.println("There are currently no local variables.\n");
            return false;
        }

        String varName = null;
        for (int i = 0; i < argumentList.size(); i++) {
            if (i % 2 == 0) {
                varName = argumentList.get(i);
                if (!dvm.getSymbolsFunctionEnvironmentRecord().keySet().contains(
                        varName)) {
                    UserInterface.println(varName
                                          + " is not a valid variable name.");
                    UserInterface.print("Valid variables are: ");
                    for (String s : variablesWithOffsets.keySet()) {
                        UserInterface.print(s + " ");
                    }
                    UserInterface.println("\n");
                    return false;
                }
            } else {
                try {
                    Integer.parseInt(argumentList.get(i));
                } catch (Exception ex) {
                    UserInterface.println("Invalid new value for variable "
                                          + varName + "\n");
                    return false;
                }
                variablesWithNewValues.put(varName, Integer.parseInt(argumentList.
                        get(i)));
            }
        }
        return true;
    }

    @Override
    public void execute(DebuggerVirtualMachine dvm) {
        for (Map.Entry<String, Integer> e : variablesWithNewValues.entrySet()) {
            dvm.pushRunTimeStack(e.getValue());
            dvm.storeRunTimeStack(variablesWithOffsets.get(e.getKey()));
        }
        for (int offset : variablesWithOffsets.values()) {
            dvm.loadRunTimeStack(offset);
            variableValues.add(dvm.popRunTimeStack());
        }
    }

    @Override
    public void display(UserInterface ui) {
        if (!variablesWithNewValues.isEmpty()) {
            UserInterface.print("New values were set for variables: ");
            for (String v : variablesWithNewValues.keySet()) {
                UserInterface.print(v + " ");
            }
            UserInterface.println("");
        }
        UserInterface.println("Local variables:");
        int i = 0;
        for (String v : variablesWithOffsets.keySet()) {
            UserInterface.print("  " + v);
            for (int j = 0; j < 30 - v.length(); j++) {
                UserInterface.print(" ");
            }
            UserInterface.println(variableValues.get(i++));
        }
        UserInterface.println("");
    }
}
