/*
 * InterfaceCommand.java
 */
package interpreter.debugger.ui.commands;

import interpreter.debugger.DebuggerVirtualMachine;
import interpreter.debugger.ui.UserInterface;
import java.util.List;

/**
 * A command that is used by the user interface for debugging.
 * 
 * @author Raymund Lew
 */
public abstract class InterfaceCommand {

    /**
     * Initializes the commands from a list of arguments.  All error checking
     * of the arguments is done in this method.
     *
     * @param argumentList
     * @param dvm
     * @return true on a successful initialization, false if there are errors
     */
    public abstract boolean init(List<String> argumentList,
                                 DebuggerVirtualMachine dvm);

    /**
     * Executes the command using the debugger virtual machine.  This method
     * works only with the virtual machine and knows nothing about the user
     * interface.
     *
     * @param dvm
     */
    public abstract void execute(DebuggerVirtualMachine dvm);

    /**
     * Displays the output of the command to the user.  This method works only
     * with the user interface and knows nothing about the virtual machine.  If
     * the user interface changes, this is the method that would need to be
     * modified.
     * 
     * @param ui
     */
    public abstract void display(UserInterface ui);
}
