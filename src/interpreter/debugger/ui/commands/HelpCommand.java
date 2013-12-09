/*
 * HelpCommand.java
 */
package interpreter.debugger.ui.commands;

import interpreter.debugger.DebuggerVirtualMachine;
import interpreter.debugger.ui.CommandTable;
import interpreter.debugger.ui.UserInterface;
import java.util.List;

/**
 * Usage: h c?
 *   Provides a list of commands with brief descriptions when used without arguments.
 *   When a command c is specified, provides a detailed description of the command.
 *   Example:
 *     h             general command list
 *     h c           detailed help on the 'c' (Continue) command
 * 
 * @author Raymund Lew
 */
public class HelpCommand extends InterfaceCommand {

    private InterfaceCommand command;

    @Override
    public boolean init(List<String> argumentList, DebuggerVirtualMachine dvm) {
        command = null;
        if (argumentList.size() > 1) {
            UserInterface.println("Too many arguments.");
            HelpCommand.displayHelp(this);
            return false;
        }
        if (argumentList.size() == 1) {
            if ((command = CommandTable.get(argumentList.get(0))) == null) {
                UserInterface.println(argumentList.get(0)
                                      + " is not a valid command.");
                UserInterface.println(
                        "Commands are: h, c, s, si, so, f, b, b+, b-, v, r, q\n");
                return false;
            }
        }
        return true;
    }

    @Override
    public void execute(DebuggerVirtualMachine dvm) {
    }

    @Override
    public void display(UserInterface ui) {
        if (command == null) {
            UserInterface.println("Valid commands are:");
            UserInterface.println(
                    "  h c?            command list (no arguments), or help on a specific command c");
            UserInterface.println("  c               continue");
            UserInterface.println("  s               step over");
            UserInterface.println("  si              step in");
            UserInterface.println("  so              step out");
            UserInterface.println("  f               display current function");
            UserInterface.println("  b               list breakpoints");
            UserInterface.println(
                    "  b+ n*           list valid breakpoint lines (no arguments), or set breakpoint");
            UserInterface.println("                    at line(s) n");
            UserInterface.println(
                    "  b- n*           clear all breakpoints (no arguments), or a list of");
            UserInterface.println(
                    "                    breakpoints at line(s) n");
            UserInterface.println(
                    "  v [s i]*        display local variables (no arguments), or change variable");
            UserInterface.println(
                    "                    name(s) s followed by the new value(s) i");
            UserInterface.println(
                    "  r               re-execute the current function");
            UserInterface.println("  q               quit\n");
        } else {
            HelpCommand.displayHelp(command);
        }
    }

    /**
     * Displays detailed help on a specific command.
     * 
     * @param command
     */
    public static void displayHelp(InterfaceCommand command) {
        if (command.getClass() == ClearBreakpointCommand.class) {
            UserInterface.println("Usage: b- n*");
            UserInterface.println(
                    "  Clears all breakpoints when used without arguments.");
            UserInterface.println(
                    "  When line number(s) n is specified, clears breakpoints at line(s) n.\n");
            UserInterface.println("  Example:");
            UserInterface.println("    b-            clears all breakpoints");
            UserInterface.println(
                    "    b- 1          clears breakpoint at line 2");
            UserInterface.println(
                    "    b- 1 3        clears breakpoints at lines 1 and 3\n");
        } else if (command.getClass() == ContinueCommand.class) {
            UserInterface.println("Usage: c");
            UserInterface.println(
                    "  This command will execute the program until a breakpoint is hit, or execution");
            UserInterface.println("  is complete.\n");
        } else if (command.getClass() == DisplayFunctionCommand.class) {
            UserInterface.println("Usage: f");
            UserInterface.println(
                    "  Displays the current function being executed.\n");
        } else if (command.getClass() == HelpCommand.class) {
            UserInterface.println("Usage: h c?");
            UserInterface.println(
                    "  Provides a list of commands with brief descriptions when used without");
            UserInterface.println("  arguments.");
            UserInterface.println(
                    "  When a command c is specified, provides a detailed description of the");
            UserInterface.println("  command.\n");
            UserInterface.println("  Example:");
            UserInterface.println("    h             general command list");
            UserInterface.println(
                    "    h c           detailed help on the 'c' (Continue) command\n");
        } else if (command.getClass() == ListBreakpointsCommand.class) {
            UserInterface.println("Usage: b");
            UserInterface.println("  Lists all breakpoints.\n");
        } else if (command.getClass() == QuitCommand.class) {
            UserInterface.println("Usage: q");
            UserInterface.println("  Quit the debugging session.\n");
        } else if (command.getClass() == ReExecuteFunctionCommand.class) {
            UserInterface.println("Usage: r");
            UserInterface.println(
                    "  Unravels execution of the current function, and executes it again.");
            UserInterface.println(
                    "  The environment variable values are reset to what they were prior to the");
            UserInterface.println("  current execution.\n");
        } else if (command.getClass() == SetBreakpointCommand.class) {
            UserInterface.println("Usage: b+ n*");
            UserInterface.println(
                    "  Lists valid line numbers that a breakpoint can be placed when used without");
            UserInterface.println("  arguments.");
            UserInterface.println(
                    "  When line number(s) n is specified, sets breakpoints at line(s) n.\n");
            UserInterface.println("  Example:");
            UserInterface.println(
                    "    b+            lists line numbers that breakpoints can be set at");
            UserInterface.println(
                    "    b+ 1          sets a breakpoint at line 1");
            UserInterface.println(
                    "    b+ 1 3        sets breakpoints at lines 1 and 3\n");
        } else if (command.getClass() == StepInCommand.class) {
            UserInterface.println("Usage: si");
            UserInterface.println(
                    "  Flags the debugger to step into the next function call if there is one on the");
            UserInterface.println("  current line.");
            UserInterface.println(
                    "  If there is no function call on the current line, this command simply acts as");
            UserInterface.println("  a 's' (Step over) command.\n");
        } else if (command.getClass() == StepOutCommand.class) {
            UserInterface.println("Usage: so");
            UserInterface.println(
                    "  Flags the debugger to step out of the current function.");
            UserInterface.println(
                    "  If there is no function to step out of, this command simply acts as a 'c'");
            UserInterface.println("  (Continue) command.\n");
        } else if (command.getClass() == StepOverCommand.class) {
            UserInterface.println("Usage: s");
            UserInterface.println(
                    "  Continues execution of the debugger until the next line or a breakpoint is");
            UserInterface.println("  hit.\n");
        } else if (command.getClass() == VariablesCommand.class) {
            UserInterface.println("Usage: v [s i]*");
            UserInterface.println(
                    "  Lists all current local variables when used without arguments.");
            UserInterface.println(
                    "  When variable name(s) s and value(s) i is specified, changes those");
            UserInterface.println("  variable(s).\n");
            UserInterface.println("  Example:");
            UserInterface.println(
                    "    v             lists all local variables");
            UserInterface.println(
                    "    v i 19        changes the variable i to 19");
            UserInterface.println(
                    "    v i 19 j 2    changes the variable i to 19 and j to 2\n");
        }
    }
}
