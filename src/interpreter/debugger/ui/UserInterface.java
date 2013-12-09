/*
 * UserInterface.java
 */
package interpreter.debugger.ui;

import interpreter.debugger.ui.commands.InterfaceCommand;
import interpreter.debugger.DebuggerVirtualMachine;
import interpreter.debugger.ui.commands.DisplayFunctionCommand;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;
import java.util.StringTokenizer;

/**
 * A user interface for the debugger.  Currently this is a command line
 * interface, but the methods and modules of the interface are implemented
 * in such a way as to facilitate relative ease if the user interface needs
 * to be changed.
 *
 * @author Raymund Lew
 */
public class UserInterface {

    private boolean needToDisplayFunction;

    /**
     * Runs the debugger's virtual machine and allows the user to interact with
     * the debugging environment.
     *
     * @param dvm
     */
    public void run(DebuggerVirtualMachine dvm) {
        enterMessage();
        needToDisplayFunction = true;
        while (dvm.continueUntilFinished()) {
            if (!dvm.getReasonForStopping().isEmpty()) {
                UserInterface.println(dvm.getReasonForStopping());
                UserInterface.println("");
            }
            if (needToDisplayFunction) {
                InterfaceCommand displayFunction =
                                 new DisplayFunctionCommand();
                displayFunction.init(new ArrayList<String>(), dvm);
                displayFunction.display(this);
            }
            needToDisplayFunction = false;
            dvm.setReasonForStopping("");

            InterfaceCommand command;
            if ((command = this.getCommand(dvm)) == null) {
                continue;
            }
            command.execute(dvm);
            command.display(this);
        }
        exitMessage();
    }

    /**
     * Gets a command from the user.  Currently the input is from the keyboard,
     * but this method can be changed to get commands in other ways.  If a valid
     * command is entered, this method checks the arguments as well, displaying
     * any errors.
     *
     * @param dvm needed to check for errors in the command arguments
     * @return the command if the command and its arguments are valid, and null
     *   otherwise
     */
    private InterfaceCommand getCommand(DebuggerVirtualMachine dvm) {
        UserInterface.print(": ");

        StringTokenizer tokenizer = null;
        InterfaceCommand command = null;
        try {
            tokenizer = new StringTokenizer((new BufferedReader(new InputStreamReader(
                                             System.in))).readLine());
        } catch (IOException ex) {
            UserInterface.println(ex);
        }
        if (!tokenizer.hasMoreTokens()) {
            return null;
        }
        String keyboardCommand = (tokenizer.nextToken()).toLowerCase();

        if ((command = CommandTable.get(keyboardCommand)) == null) {
            UserInterface.println("Invalid command.  Enter h for help.\n");
            return null;
        }

        List<String> argumentList = new ArrayList<String>();

        while (tokenizer.hasMoreTokens()) {
            argumentList.add(tokenizer.nextToken().toLowerCase());
        }

        if (!command.init(argumentList, dvm)) {
            return null;
        }

        return command;
    }

    /**
     * The main way that this particular user interface displays debugging
     * execution and interaction.  Output is printed to the screen.
     *
     * @param s what to output to the screen
     */
    public static void print(Object s) {
        System.out.print(s);
    }

    /**
     * The same as UserInterface.print but also appends a new line to the
     * output.
     *
     * @param s
     */
    public static void println(Object s) {
        System.out.println(s);
    }

    /**
     * Some debugging byte codes need to signal the interface to display
     * changes while debugging.
     */
    public void signalNeedToDisplayFunction() {
        needToDisplayFunction = true;
    }

    /**
     * A message displayed when the program is first run.
     */
    public void enterMessage() {
        UserInterface.println("Enter h for help.\n");
    }

    /**
     * An exit message displayed before exiting the debugging session.
     */
    public void exitMessage() {
        UserInterface.println("Debug session finished.");
    }
}
