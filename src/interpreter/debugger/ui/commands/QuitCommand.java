/*
 * QuitCommand.java
 */
package interpreter.debugger.ui.commands;

import interpreter.debugger.DebuggerVirtualMachine;
import interpreter.debugger.ui.UserInterface;
import java.util.List;

/**
 * Usage: q
 *   Quit the debugging session.
 * 
 * @author Raymund Lew
 */
public class QuitCommand extends InterfaceCommand {

    @Override
    public boolean init(List<String> argumentList, DebuggerVirtualMachine dvm) {
        if (argumentList.size() > 0) {
            UserInterface.println("This command takes no arguments.");
            HelpCommand.displayHelp(this);
            return false;
        }
        return true;
    }

    @Override
    public void execute(DebuggerVirtualMachine dvm) {
        dvm.executionFinished();
    }

    @Override
    public void display(UserInterface ui) {
    }
}
