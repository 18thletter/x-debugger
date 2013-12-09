/*
 * DisplayFunctionCommand.java
 */
package interpreter.debugger.ui.commands;

import interpreter.debugger.DebuggerVirtualMachine;
import interpreter.debugger.ui.UserInterface;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

/**
 * Usage: f
 *   Displays the current function being executed.
 * 
 * @author Raymund Lew
 */
public class DisplayFunctionCommand extends InterfaceCommand {

    private String functionName;
    private int startLineNumber;
    private int currentLineNumber;
    private Map<String, Boolean> functionLines;

    @Override
    public boolean init(List<String> argumentList, DebuggerVirtualMachine dvm) {
        functionLines = new LinkedHashMap<String, Boolean>();

        if (argumentList.size() > 0) {
            UserInterface.println("This command takes no arguments.");
            HelpCommand.displayHelp(this);
            return false;
        }
        functionName = dvm.getFunctionName();
        startLineNumber = dvm.getFunctionStartLine();
        int endLineNumber = dvm.getFunctionEndLine();
        currentLineNumber = dvm.getCurrentLine();
        if (currentLineNumber == -1) {
            return true;
        }
        if (startLineNumber == -2) {
            functionName = "none";
            startLineNumber = 1;
            endLineNumber = dvm.getSourceLinesSize() - 1;
        }
        for (int i = startLineNumber; i <= endLineNumber; i++) {
            functionLines.put(dvm.getSourceLine(i), dvm.isBreakpointSet(i));
        }

        return true;
    }

    @Override
    public void execute(DebuggerVirtualMachine dvm) {
    }

    @Override
    public void display(UserInterface ui) {
        UserInterface.println("Current function: " + functionName);
        if (currentLineNumber == -1) {
            UserInterface.println("");
            return;
        }

        int i = startLineNumber;
        for (Map.Entry<String, Boolean> e : functionLines.entrySet()) {
            if (e.getValue()) {
                UserInterface.print("* " + i + ".");
            } else {
                UserInterface.print("  " + i + ".");
            }
            for (int j = 0; j < 3 - new Integer(i).toString().length(); j++) {
                UserInterface.print(" ");
            }
            UserInterface.print(e.getKey());
            if (i++ == currentLineNumber) {
                UserInterface.println(" <---");
            } else {
                UserInterface.println("");
            }
        }
        UserInterface.println("");
    }
}
