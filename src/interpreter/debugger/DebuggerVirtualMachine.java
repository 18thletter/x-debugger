/*
 * DebuggerVirtualMachine.java
 */
package interpreter.debugger;

import interpreter.Program;
import interpreter.RunTimeStack;
import interpreter.VirtualMachine;
import interpreter.bytecodes.ByteCode;
import interpreter.bytecodes.DumpCode;
import interpreter.bytecodes.debuggerbytecodes.FormalCode;
import interpreter.bytecodes.debuggerbytecodes.FunctionCode;
import interpreter.bytecodes.debuggerbytecodes.LineCode;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.Stack;

/**
 * Executes the program holding the byte codes, including byte codes specific
 * to the debugger.  Functionality is added beyond VirtualMachine to track
 * variables, functions, execution pausing, and tracking execution according
 * to the .x source file.
 *
 * @author Raymund Lew
 * @see VirtualMachine
 */
public class DebuggerVirtualMachine extends VirtualMachine {

    private Stack<FunctionEnvironmentRecord> environmentStack;
    private List<SourceLine> sourceLines;
    private boolean stepOverPending;
    private boolean stepInPending;
    private boolean stepOutPending;
    private boolean executionFinished;
    private int savedEnvironmentStackSize;
    private String reasonForStopping;
    private List<Integer> savedArgs;
    private int savedFunctionAddress;

    public DebuggerVirtualMachine(Program program,
                                  Collection sourceLines) {
        this.program = program;
        this.sourceLines = (List<SourceLine>) sourceLines;
        setBreakableLines();
        this.restart();
    }

    /**
     * The VirtualMachine has functionality to restart execution of byte codes.
     * This method serves a similar purpose.
     */
    public final void restart() {
        environmentStack = new Stack<FunctionEnvironmentRecord>();
        environmentStack.add(new FunctionEnvironmentRecord());
        savedEnvironmentStackSize = environmentStack.size();
        savedArgs = new ArrayList<Integer>();
        reasonForStopping = "";
        stepOverPending = false;
        stepInPending = false;
        stepOutPending = false;
        executionFinished = false;
        runStack = new RunTimeStack();
        pc = 0;
        returnAddrs = new Stack<Integer>();
        running = false;
        dumping = false;
        savedFunctionAddress = 0;
    }

    /**
     * Used to record which lines in the .x source file are allowed to have
     * breakpoints set.
     */
    private void setBreakableLines() {
        for (int i = 0; i < program.size(); i++) {
            if (program.getCode(i).getClass() == LineCode.class && ((LineCode) program.
                                                                    getCode(i)).
                    getLineNumber() > 0) {
                sourceLines.get(((LineCode) program.getCode(i)).getLineNumber()).
                        setAsPossibleBreakPoint();
            }
        }
    }

    /**
     * Executes the byte codes contained in the program, printing the byte codes
     * if dumping is turned on.  Some byte codes may halt execution.
     *
     * @return whether or not the entire program has completed execution
     */
    public boolean continueUntilFinished() {
        while (running) {
            if (!stepInPending && !stepOutPending && !stepOverPending) {
                savedEnvironmentStackSize = environmentStack.size();
            }
            ByteCode code = program.getCode(pc);
            code.execute(this);
            this.checkForBreaksAndSteps(code);
            if (dumping && code.getClass() != DumpCode.class) {
                System.out.println(code);
                runStack.dump();
            }
            pc++;
        }
        if (executionFinished) {
            return false;
        }
        return true;
    }

    /**
     * Checks to see whether execution should stop due to breakpoints or step
     * pending.  If conditions are correct, execution halts.
     *
     * @param code the current ByteCode being executed
     */
    private void checkForBreaksAndSteps(ByteCode code) {
        if (code.getClass() == LineCode.class && environmentStack.peek().
                getCurrentLineNumber() > 0) {
            if (sourceLines.get(environmentStack.peek().getCurrentLineNumber()).
                    isBreakpointSet()) {
                running = false;
                savedEnvironmentStackSize = environmentStack.size();
                reasonForStopping = "Breakpoint reached on line " + environmentStack.
                        peek().getCurrentLineNumber() + ".";
                stepInPending = false;
                stepOutPending = false;
                stepOverPending = false;
            }
        }
        if (stepInPending) {
            if ((environmentStack.size() > savedEnvironmentStackSize && code.
                 getClass() == FunctionCode.class) || (environmentStack.size()
                                                       == savedEnvironmentStackSize
                                                       && code.getClass()
                                                          == LineCode.class)
                || (environmentStack.size() < savedEnvironmentStackSize)) {
                if (environmentStack.size() > savedEnvironmentStackSize && code.
                        getClass() == FunctionCode.class) {
                    ByteCode c;
                    while ((c = program.getCode(++pc)).getClass()
                           == FormalCode.class) {
                        c.execute(this);
                    }
                    pc--;
                    reasonForStopping = "Stepped into function " + environmentStack.
                            peek().getFunctionName() + ".";
                } else {
                    reasonForStopping = "Stepped to line " + environmentStack.
                            peek().getCurrentLineNumber() + ".";
                }
                running = false;
                stepInPending = false;
                savedEnvironmentStackSize = environmentStack.size();
            }
        }
        if (stepOutPending) {
            if (environmentStack.size() < savedEnvironmentStackSize) {
                running = false;
                stepOutPending = false;
                savedEnvironmentStackSize = environmentStack.size();
                reasonForStopping = "Stepped out to line " + environmentStack.
                        peek().getCurrentLineNumber() + ".";
            }
        }
        if (stepOverPending) {
            if ((environmentStack.size() == savedEnvironmentStackSize && code.
                 getClass() == LineCode.class) || (environmentStack.size()
                                                   < savedEnvironmentStackSize)) {
                running = false;
                stepOverPending = false;
                savedEnvironmentStackSize = environmentStack.size();
                reasonForStopping = "Stepped to line " + environmentStack.peek().
                        getCurrentLineNumber() + ".";
            }
        }
    }

    public void setBreakpoint(int lineNumber) {
        sourceLines.get(lineNumber).setBreakpoint();
    }

    public void clearBreakpoint(int lineNumber) {
        sourceLines.get(lineNumber).clearBreakpoint();
    }

    public boolean isBreakpointSet(int lineNumber) {
        return sourceLines.get(lineNumber).isBreakpointSet();
    }

    public boolean isBreakableLine(int lineNumber) {
        return sourceLines.get(lineNumber).isPossibleBreakpoint();
    }

    public String getSourceLine(int lineNumber) {
        return sourceLines.get(lineNumber).getSourceLine();
    }

    /**
     *
     * @return the number of lines in the .x source file
     */
    public int getSourceLinesSize() {
        return sourceLines.size();
    }

    public void saveEnvironmentStackSize() {
        savedEnvironmentStackSize = environmentStack.size();
    }

    /**
     * Used when exiting a function, to remove one FunctionEnvironmentRecord.
     */
    public void exitFunction() {
        environmentStack.pop();
    }

    /**
     * Used when entering a function, to enter one FunctionEnvironmentRecord
     */
    public void enterFunction() {
        environmentStack.push(new FunctionEnvironmentRecord());
    }

    /**
     * Used to enter a new variable into the environment record.
     *
     * @param identifier the variable name
     * @param offset where the variable is located on the RunTimeStack
     */
    public void enterEnvironmentRecord(String identifier, int offset) {
        environmentStack.peek().putSymbolTable(identifier, offset);
    }

    /**
     * Sets the current line executing.
     *
     * @param lineNumber
     */
    public void setCurrentLine(int lineNumber) {
        environmentStack.peek().setCurrentLineNumber(lineNumber);
    }

    /**
     * Removes the indicated number of variables from the environment record.
     *
     * @param numberToRemove
     */
    public void removeSymbols(int numberToRemove) {
        environmentStack.peek().popEntriesSymbolTable(numberToRemove);
    }

    public int getFunctionStartLine() {
        return environmentStack.peek().getStartLineNumber();
    }

    public int getFunctionEndLine() {
        return environmentStack.peek().getEndLineNumber();
    }

    public int getCurrentLine() {
        return environmentStack.peek().getCurrentLineNumber();
    }

    /**
     *
     * @return the name of the current function executing
     */
    public String getFunctionName() {
        return environmentStack.peek().getFunctionName();
    }

    /**
     *
     * @return the names and RunTimeStack offsets of all the variables in the
     *   current function environment.
     */
    public Map<String, Object> getSymbolsFunctionEnvironmentRecord() {
        return environmentStack.peek().getSymbolsSymbolTable();
    }

    public void setFunctionInfo(String functionName, int startLineNumber,
                                int endLineNumber) {
        environmentStack.peek().setEndLineNumber(endLineNumber);
        environmentStack.peek().setFunctionName(functionName);
        environmentStack.peek().setStartLineNumber(startLineNumber);
    }

    public void setStepOverPending(boolean b) {
        stepOverPending = b;
    }

    public void setStepInPending(boolean b) {
        stepInPending = b;
    }

    public void setStepOutPending(boolean b) {
        stepOutPending = b;
    }

    /**
     * This method is used when execution halts for some reason, keeping track
     * of why execution halted.
     *
     * @param reason
     */
    public void setReasonForStopping(String reason) {
        reasonForStopping = reason;
    }

    public String getReasonForStopping() {
        return reasonForStopping;
    }

    public void saveFunctionArgs(List<Integer> functionArgList) {
        savedArgs = functionArgList;
    }

    public List<Integer> getSavedFunctionArgs() {
        return savedArgs;
    }

    /**
     * Tells the debugger that the program has completely finished executing.
     */
    public void executionFinished() {
        executionFinished = true;
    }

    /**
     * Saves the program counter address of the currently executing function.
     *
     * @param address
     */
    public void saveFunctionAddress(int address) {
        savedFunctionAddress = address;
    }

    public int getSavedFunctionAddress() {
        return savedFunctionAddress;
    }
}
