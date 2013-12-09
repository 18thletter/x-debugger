/*
 * SourceLine.java
 */
package interpreter.debugger;

/**
 * Represents a line in a .x source code file.
 * Each line contains:
 * 1. the line itself
 * 2. whether or not a breakpoint is set on that line
 * 3. whether or not a breakpoint can be set on that line
 * 
 * @author Raymund Lew
 */
public class SourceLine {

    private String sourceLine;
    private boolean breakpointSet;
    private boolean possibleBreakpoint;

    public SourceLine(String line) {
        sourceLine = line;
        possibleBreakpoint = false;
        breakpointSet = false;
    }

    public void setBreakpoint() {
        breakpointSet = true;
    }

    public void clearBreakpoint() {
        breakpointSet = false;
    }

    public boolean isBreakpointSet() {
        return breakpointSet;
    }

    public boolean isPossibleBreakpoint() {
        return possibleBreakpoint;
    }

    public String getSourceLine() {
        return sourceLine;
    }

    void setAsPossibleBreakPoint() {
        possibleBreakpoint = true;
    }
}
