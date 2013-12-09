/*
 * ContinueCommand.java
 */
package interpreter.debugger.ui.commands;

import interpreter.debugger.DebuggerVirtualMachine;
import interpreter.debugger.ui.UserInterface;
import java.util.List;

/**
 * Usage: c
 *   This command will execute the program until a breakpoint is hit.
 * 
 * @author Raymund Lew
 */
public class ContinueCommand extends InterfaceCommand {

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
        dvm.setRunning(true);
    }

    @Override
    public void display(UserInterface ui) {
        ui.signalNeedToDisplayFunction();
    }
}
