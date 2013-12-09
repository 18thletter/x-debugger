/*
 * ClearBreakpointCommand.java
 */
package interpreter.debugger.ui.commands;

import interpreter.debugger.DebuggerVirtualMachine;
import interpreter.debugger.ui.UserInterface;
import java.util.ArrayList;
import java.util.List;

/**
 * Usage: b- n*
 *   Clears all breakpoints when used without arguments.
 *   When line number(s) n is specified, clears breakpoints at line(s) n.
 *   Example:
 *     b-            clears all breakpoints
 *     b- 1          clears breakpoint at line 2
 *     b- 1 3        clears breakpoints at lines 1 and 3
 *
 * @author Raymund Lew
 */
public class ClearBreakpointCommand extends InterfaceCommand {

    private List<String> breakpointLinesToClear;

    @Override
    public boolean init(List<String> argumentList, DebuggerVirtualMachine dvm) {
        breakpointLinesToClear = new ArrayList<String>();
        List<String> currentBreakpoints = new ArrayList<String>();

        for (int i = 1; i < dvm.getSourceLinesSize(); i++) {
            if (dvm.isBreakpointSet(i)) {
                currentBreakpoints.add(new Integer(i).toString());
            }
        }

        if (currentBreakpoints.isEmpty()) {
            UserInterface.println("There are no breakpoints currently set.\n");
            return false;
        }

        for (String arg : argumentList) {
            if (!currentBreakpoints.contains(arg)) {
                UserInterface.println(
                        arg + " is not a valid breakpoint line number.");
                UserInterface.print("Breakpoints can be cleared on lines: ");
                for (String lineNum : currentBreakpoints) {
                    UserInterface.print(lineNum + " ");
                }
                UserInterface.println("\n");
                return false;
            }
            breakpointLinesToClear.add(arg);
        }

        if (argumentList.isEmpty()) {
            breakpointLinesToClear = currentBreakpoints;
        }

        return true;
    }

    @Override
    public void execute(DebuggerVirtualMachine dvm) {
        for (String breakpointLine : breakpointLinesToClear) {
            dvm.clearBreakpoint(Integer.parseInt(breakpointLine));
        }
    }

    @Override
    public void display(UserInterface ui) {
        UserInterface.print("Breakpoints cleared on lines: ");
        for (String breakpointLine : breakpointLinesToClear) {
            UserInterface.print(breakpointLine + " ");
        }
        UserInterface.println("\n");
        ui.signalNeedToDisplayFunction();
    }
}
