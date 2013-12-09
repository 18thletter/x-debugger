/*
 * CommandTable.java
 */
package interpreter.debugger.ui;

import java.util.HashMap;
import java.util.Map;
import interpreter.debugger.ui.commands.*;

/**
 * A table that maps the debugger command names used by the debugger user
 * interface to the names of the actual command classes.
 * 
 * @author Raymund Lew
 */
public class CommandTable {

    private static final Map<String, InterfaceCommand> commands = new HashMap<String, InterfaceCommand>();

    static {
        commands.put("h", new HelpCommand());
        commands.put("q", new QuitCommand());
        commands.put("s", new StepOverCommand());
        commands.put("si", new StepInCommand());
        commands.put("so", new StepOutCommand());
        commands.put("b", new ListBreakpointsCommand());
        commands.put("b+", new SetBreakpointCommand());
        commands.put("b-", new ClearBreakpointCommand());
        commands.put("v", new VariablesCommand());
        commands.put("c", new ContinueCommand());
        commands.put("r", new ReExecuteFunctionCommand());
        commands.put("f", new DisplayFunctionCommand());
    }

    public static InterfaceCommand get(String command) {
        return commands.get(command);
    }
}
