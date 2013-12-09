/*
 * ListBreakpointsCommand.java
 */
package interpreter.debugger.ui.commands;

import interpreter.debugger.DebuggerVirtualMachine;
import interpreter.debugger.ui.UserInterface;
import java.util.ArrayList;
import java.util.List;

/**
 * Usage: b
 *   Lists all breakpoints.
 * 
 * @author Raymund Lew
 */
public class ListBreakpointsCommand extends InterfaceCommand {

    private List<String> breakpoints;

    @Override
    public boolean init(List<String> argumentList, DebuggerVirtualMachine dvm) {
        if (argumentList.size() > 0) {
            UserInterface.println("This command takes no arguments.");
            HelpCommand.displayHelp(this);
            return false;
        }

        breakpoints = new ArrayList<String>();
        for (int i = 1; i < dvm.getSourceLinesSize(); i++) {
            if (dvm.isBreakpointSet(i)) {
                breakpoints.add(new Integer(i).toString());
            }
        }
        return true;
    }

    @Override
    public void execute(DebuggerVirtualMachine dvm) {
    }

    @Override
    public void display(UserInterface ui) {
        if (breakpoints.isEmpty()) {
            UserInterface.println("There are no breakpoints currently set.\n");
        } else {
            UserInterface.print("Breakpoints are currently set on lines: ");
            for (String breakpoint : breakpoints) {
                UserInterface.print(breakpoint + " ");
            }
            UserInterface.println("\n");
        }
    }
}
