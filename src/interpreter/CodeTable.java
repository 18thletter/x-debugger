/*
 * CodeTable.java
 */
package interpreter;

import java.util.HashMap;
import java.util.Map;

/**
 * A table that maps byte code names in the source file to the names of the
 * actual byte code classes.
 * 
 * @author Raymund Lew
 */
public class CodeTable {

    private static Map<String, String> codes = new HashMap<String, String>();

    public static void init() {
        codes.put("HALT", "HaltCode");
        codes.put("POP", "PopCode");
        codes.put("FALSEBRANCH", "FalseBranchCode");
        codes.put("GOTO", "GoToCode");
        codes.put("STORE", "StoreCode");
        codes.put("LOAD", "LoadCode");
        codes.put("LIT", "LitCode");
        codes.put("ARGS", "ArgsCode");
        codes.put("CALL", "CallCode");
        codes.put("RETURN", "ReturnCode");
        codes.put("BOP", "BopCode");
        codes.put("READ", "ReadCode");
        codes.put("WRITE", "WriteCode");
        codes.put("LABEL", "LabelCode");
        codes.put("DUMP", "DumpCode");
    }

    public static void debuggerInit() {
        codes.put("FUNCTION", "debuggerbytecodes.FunctionCode");
        codes.put("FORMAL", "debuggerbytecodes.FormalCode");
        codes.put("LINE", "debuggerbytecodes.LineCode");
        codes.put("CALL", "debuggerbytecodes.DebuggerCallCode");
        codes.put("HALT", "debuggerbytecodes.DebuggerHaltCode");
        codes.put("LIT", "debuggerbytecodes.DebuggerLitCode");
        codes.put("POP", "debuggerbytecodes.DebuggerPopCode");
        codes.put("RETURN", "debuggerbytecodes.DebuggerReturnCode");
    }

    public static String get(String code) {
        return codes.get(code);
    }
}
