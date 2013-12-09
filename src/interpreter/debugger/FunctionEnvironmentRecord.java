/*
 * FunctionEnvironmentRecord.java
 */
package interpreter.debugger;

import java.util.Map;

/**
 * A record of the current scope, containing:
 * 1. a SymbolTable of symbols
 * 2. the function name of the current scope
 * 3. the starting line number of the function
 * 4. the ending line number of the function
 * 5. the current line number executing
 *
 * @author Raymund Lew
 */
public class FunctionEnvironmentRecord {

    private int endLineNumber = -2;
    private int currentLineNumber = -2;
    private String functionName = null;
    private SymbolTable symbolTable = new SymbolTable();
    private int startLineNumber = -2;

    /**
     * Creates a new FunctionEnvironmentRecord and begins the scope
     */
    public FunctionEnvironmentRecord() {
        this.beginScopeSymbolTable();
    }

    public int getCurrentLineNumber() {
        return currentLineNumber;
    }

    public int getEndLineNumber() {
        return endLineNumber;
    }

    public String getFunctionName() {
        return functionName;
    }

    public int getStartLineNumber() {
        return startLineNumber;
    }

    public void setEndLineNumber(int endLineNumber) {
        this.endLineNumber = endLineNumber;
    }

    public void setFunctionName(String functionName) {
        this.functionName = functionName;
    }

    public void setStartLineNumber(int startLineNumber) {
        this.startLineNumber = startLineNumber;
    }

    public void setCurrentLineNumber(int currentLineNumber) {
        this.currentLineNumber = currentLineNumber;
    }

    /**
     * Begins a new scope in the environment
     */
    private void beginScopeSymbolTable() {
        symbolTable.beginScope();
    }

    /**
     * Enters a new symbol into the FunctionEnvironmentRecord
     *
     * @param key
     * @param value
     */
    public void putSymbolTable(String key, Object value) {
        symbolTable.put(key, value);
    }

    /**
     * Removes a specified number of symbols from the FunctionEnvironmentRecord
     *
     * @param numberToRemove
     */
    public void popEntriesSymbolTable(int numberToRemove) {
        symbolTable.popEntries(numberToRemove);
    }

    /**
     *
     * @return a Map containing the symbols in the current scope as keys, mapped
     *   to their corresponding values
     */
    public Map<String, Object> getSymbolsSymbolTable() {
        return symbolTable.getSymbols();
    }
}
