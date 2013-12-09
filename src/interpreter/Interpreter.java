/*
 * Interpreter.java
 *
 * This file was written by Barry Levine and modified by Raymund Lew
 */
package interpreter;

import interpreter.debugger.DebuggerVirtualMachine;
import interpreter.debugger.SourceLine;
import interpreter.debugger.ui.UserInterface;
import java.io.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Interpreter class runs the interpreter:
 *     1. Perform all initializations
 *     2. Load the bytecodes from file
 *     3. Run the virtual machine
 */
public class Interpreter {

    protected ByteCodeLoader bcl;
    private List<SourceLine> sourceLines;

    /**
     *
     * @param fileName the name of the .x.cod file
     */
    public Interpreter(String fileName, boolean debugMode) {
        CodeTable.init();
        if (debugMode) {
            CodeTable.debuggerInit();
        }
        try {
            if (debugMode) {
                bcl = new ByteCodeLoader(fileName + ".x.cod");
                loadSourceLines(fileName);
            } else {
                bcl = new ByteCodeLoader(fileName);
            }
        } catch (IOException e) {
            System.out.println("**** " + e);
        }
    }

    /**
     * Runs the interpreter, which executes the byte codes in the virtual
     * machine.  If debugMode is on, the debugger virtual machine is executed
     * instead, on a UserInterface.
     *
     * @param debugMode true if debugging is on, false if not
     */
    public void run(boolean debugMode) {
        Program program = bcl.loadCodes();
        VirtualMachine vm;
        if (debugMode) {
            vm = new DebuggerVirtualMachine(program, sourceLines);
            (new UserInterface()).run((DebuggerVirtualMachine) vm);
        } else {
            vm = new VirtualMachine(program);
            vm.executeProgram();
        }
    }

    /**
     *
     * @param args the .x.cod file is passed as the argument through the
     *   command line
     */
    public static void main(String args[]) {
        if (args.length != 1 && args.length != 2
            || (args.length == 2 && !args[0].equals("-d"))) {
            System.out.println(
                    "Incorrect usage, try: java interpreter.Interpreter [-d] <file>");
            System.exit(1);
        }
        String fileName = args[0];
        boolean debugMode = false;
        if (args.length == 2) {
            fileName = args[1];
            debugMode = true;
        }
        (new Interpreter(fileName, debugMode)).run(debugMode);
    }

    /**
     * Reads and stores the source code lines of the .x file.
     *
     * @param baseFileName file name without the ".x"
     * @throws IOException
     */
    private void loadSourceLines(String baseFileName) throws IOException {
        sourceLines = new ArrayList<SourceLine>();
        sourceLines.add(new SourceLine(""));
        BufferedReader reader = new BufferedReader(new FileReader(baseFileName
                                                                  + ".x"));
        String line;
        for (int i = 1; (line = reader.readLine()) != null; i++) {
            sourceLines.add(new SourceLine(line));
        }
    }
}
