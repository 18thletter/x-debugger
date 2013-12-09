/*
 * SymbolTable.java
 *
 * This file was taken from Table.java written by Barry Levine and modified by
 *   Raymund Lew
 */
package interpreter.debugger;

import java.util.HashMap;
import java.util.Map;

/**
 * Binder objects group 3 fields
 * 1. a value
 * 2. the next link in the chain of symbols in the current scope.
 * 3. the next link of a previous Binder for the same identifier in a previous scope
 *
 * @author Barry Levine
 */
class Binder {

    private Object value;
    private String previousTop;
    private Binder tail;

    Binder(Object v, String p, Binder t) {
        value = v;
        previousTop = p;
        tail = t;
    }

    Object getValue() {
        return value;
    }

    String getPreviousTop() {
        return previousTop;
    }

    Binder getTail() {
        return tail;
    }
}

/**
 * The SymbolTable class is similar to java.util.Dictionary, except that each
 * key must be a String and there is a scope mechanism.
 *
 * @author Barry Levine
 */
public class SymbolTable {

    private Map<String, Binder> symbols = new HashMap<String, Binder>();
    private String top;
    private Binder marks;

    /**
     *
     * @param key the symbol
     * @return the object associated with the key
     */
    public Object get(String key) {
        return symbols.get(key).getValue();
    }

    /**
     * Puts the specified value into the SymbolTable, bound to the specified
     * String key.  Maintains a list of symbols in the current scope.  Adds to
     * list of symbols in prior scope with the same symbol.
     *
     * @param key
     * @param value
     */
    public void put(String key, Object value) {
        symbols.put(key, new Binder(value, top, symbols.get(key)));
        top = key;
    }

    /**
     * Remembers the current state of the SymbolTable; pushes the new mark onto
     * the mark stack.
     */
    public void beginScope() {
        marks = new Binder(null, top, marks);
        top = null;
    }

    /**
     * Pops the specified number of symbols from the SymbolTable.  Old symbols
     * with the same identifier are restored.
     *
     * @param numberToRemove
     */
    public void popEntries(int numberToRemove) {
        for (int i = 0; i < numberToRemove; i++) {
            Binder b = symbols.get(top);
            if (b.getTail() != null) {
                symbols.put(top, b.getTail());
            } else {
                symbols.remove(top);
            }
            top = b.getPreviousTop();
        }
    }

    /**
     *
     * @return a Map containing the symbols in the current scope as keys, mapped
     *   to their corresponding values
     */
    public Map<String, Object> getSymbols() {
        Map<String, Object> symbolsAndValues = new HashMap<String, Object>();
        for (Map.Entry<String, Binder> e : symbols.entrySet()) {
            symbolsAndValues.put(e.getKey(), e.getValue().getValue());
        }
        return symbolsAndValues;
    }
}
