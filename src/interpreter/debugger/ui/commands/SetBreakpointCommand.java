/*
 * SetBreakpointCommand.java
 */
package interpreter.debugger.ui.commands;

import interpreter.debugger.DebuggerVirtualMachine;
import interpreter.debugger.ui.UserInterface;
import java.util.ArrayList;
import java.util.List;

/**
 * Usage: b+ n*
 *   Lists valid line numbers that a breakpoint can be placed when used without arguments.
 *   When line number(s) n is specified, sets breakpoints at line(s) n.
 *   Example:
 *     b+            lists line numbers that breakpoints can be set at
 *     b+ 1          sets a breakpoint at line 1
 *     b+ 1 3        sets breakpoints at lines 1 and 3
 * 
 * @author Raymund Lew
 */
public class SetBreakpointCommand extends InterfaceCommand {

    private List<String> breakpointLinesToSet;
    private List<String> breakableLineNumbers;

    @Override
    public boolean init(List<String> argumentList, DebuggerVirtualMachine dvm) {
        breakpointLinesToSet = new ArrayList<String>();
        breakableLineNumbers = new ArrayList<String>();

        for (int i = 1; i < dvm.getSourceLinesSize(); i++) {
            if (dvm.isBreakableLine(i)) {
                breakableLineNumbers.add(new Integer(i).toString());
            }
        }
        for (String arg : argumentList) {
            if (!breakableLineNumbers.contains(arg)) {
                UserInterface.println(
                        arg + " is not a valid breakpoint line number.");
                UserInterface.print("Breakpoints can be set on lines: ");
                for (String lineNum : breakableLineNumbers) {
                    UserInterface.print(lineNum + " ");
                }
                UserInterface.println("\n");
                return false;
            }
            breakpointLinesToSet.add(arg);
        }
        return true;
    }

    @Override
    public void execute(DebuggerVirtualMachine dvm) {
        for (String breakpointLine : breakpointLinesToSet) {
            dvm.setBreakpoint(Integer.parseInt(breakpointLine));
        }
    }

    @Override
    public void display(UserInterface ui) {
        if (breakpointLinesToSet.isEmpty()) {
            UserInterface.print("Breakpoints can be set on lines: ");
            for (String breakpointLine : breakableLineNumbers) {
                UserInterface.print(breakpointLine + " ");
            }
        } else {
            UserInterface.print("Breakpoints set on lines: ");
            for (String breakpointLine : breakpointLinesToSet) {
                UserInterface.print(breakpointLine + " ");
            }
            ui.signalNeedToDisplayFunction();
        }
        UserInterface.println("\n");
    }
}
