/*
 * StepInCommand.java
 */
package interpreter.debugger.ui.commands;

import interpreter.debugger.DebuggerVirtualMachine;
import interpreter.debugger.ui.UserInterface;
import java.util.List;

/**
 * Usage: si
 *   Flags the debugger to step into the next function call if there is one on the current line.
 *   If there is no function call on the current line, this command simply acts as a 's' (Step over) command.
 * 
 * @author Raymund Lew
 */
public class StepInCommand extends InterfaceCommand {

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
        dvm.setStepInPending(true);
        dvm.setRunning(true);
    }

    @Override
    public void display(UserInterface ui) {
        ui.signalNeedToDisplayFunction();
    }
}
