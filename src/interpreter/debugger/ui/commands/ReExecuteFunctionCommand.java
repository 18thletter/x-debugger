/*
 * ReExecuteFunctionCommand.java
 */
package interpreter.debugger.ui.commands;

import interpreter.debugger.DebuggerVirtualMachine;
import interpreter.debugger.ui.UserInterface;
import java.util.List;

/**
 * Usage: r
 *   Unravels execution of the current function, and executes it again.
 *   The environment variable values are reset to what they were prior to the current execution.
 * 
 * @author Raymund Lew
 */
public class ReExecuteFunctionCommand extends InterfaceCommand {

    @Override
    public boolean init(List<String> argumentList, DebuggerVirtualMachine dvm) {
        if (argumentList.size() > 0) {
            UserInterface.println("This command takes no arguments.");
            HelpCommand.displayHelp(this);
            return false;
        }
        if (dvm.getFunctionName() == null) {
            UserInterface.println("There is no current function.\n");
            return false;
        }
        return true;
    }

    @Override
    public void execute(DebuggerVirtualMachine dvm) {
        dvm.popFrameRunTimeStack();
        if (!dvm.isEmptyRunTimeStack()) {
            dvm.popRunTimeStack();
        }
        dvm.newFrameAtRunTimeStack(0);
        dvm.exitFunction();
        dvm.saveEnvironmentStackSize();
        dvm.enterFunction();
        for (int arg : dvm.getSavedFunctionArgs()) {
            dvm.pushRunTimeStack(arg);
        }
        dvm.setPc(dvm.getSavedFunctionAddress());
        dvm.setStepInPending(true);
        dvm.setRunning(true);
    }

    @Override
    public void display(UserInterface ui) {
        UserInterface.println(
                "Unraveling execution and re-executing current function.");
        ui.signalNeedToDisplayFunction();
    }
}
