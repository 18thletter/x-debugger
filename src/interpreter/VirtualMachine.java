/*
 * VirtualMachine.java
 */
package interpreter;

import interpreter.bytecodes.ByteCode;
import interpreter.bytecodes.DumpCode;
import java.util.Stack;

/**
 * Contains the program holding the byte codes and executes the program.
 *
 * @author Raymund Lew
 */
public class VirtualMachine {

    protected RunTimeStack runStack;
    protected int pc;
    protected Stack<Integer> returnAddrs;
    protected boolean running;
    protected Program program;
    protected boolean dumping;

    protected VirtualMachine() {
    }

    public VirtualMachine(Program program) {
        this.program = program;
    }

    /**
     * Executes the byte codes contained in the program.  Also prints out
     * the execution if dumping is turned on.
     */
    public void executeProgram() {
        runStack = new RunTimeStack();
        pc = 0;
        returnAddrs = new Stack<Integer>();
        running = true;
        dumping = false;
        while (running) {
            ByteCode code = program.getCode(pc);
            code.execute(this);
            if (dumping && code.getClass() != DumpCode.class) {
                System.out.println(code);
                runStack.dump();
            }
            pc++;
        }
    }

    public boolean isEmptyRunTimeStack() {
        return runStack.isEmpty();
    }

    /**
     * Looks at, but does not remove, the top item of the run time stack
     * and returns it.
     *
     * @return the top item of the run time stack
     */
    public int peekRunTimeStack() {
        return runStack.peek();
    }

    /**
     * Removes and returns the top item of the run time stack.
     *
     * @return the top item of the run time stack
     */
    public int popRunTimeStack() {
        return runStack.pop();
    }

    /**
     * Pushes the specified item onto the run time stack.
     *
     * @param item the item to be pushed
     * @return the item just pushed
     */
    public int pushRunTimeStack(int item) {
        return runStack.push(item);
    }

    /**
     * Creates a new frame on the run time stack at the specified offset
     *
     * @param offset where to create the new frame
     */
    public void newFrameAtRunTimeStack(int offset) {
        runStack.newFrameAt(offset);
    }

    /**
     * Pops the top frame off the run time stack.
     */
    public void popFrameRunTimeStack() {
        runStack.popFrame();
    }

    /**
     * Stores the top item of the run time stack to the specified offset
     * location.
     *
     * @param offset where to store the top item
     * @return the item just stored
     */
    public int storeRunTimeStack(int offset) {
        return runStack.store(offset);
    }

    /**
     * Loads the item at the specified offset to the top of the run time stack.
     *
     * @param offset where to get the item to load
     * @return the item just loaded
     */
    public int loadRunTimeStack(int offset) {
        return runStack.load(offset);
    }

    /**
     *
     * @return number of arguments in the current frame
     */
    public int topFrameSizeRunTimeStack() {
        return runStack.topFrameSize();
    }

    /**
     *
     * @param branchTo the new value for the program counter
     */
    public void setPc(int branchTo) {
        pc = branchTo;
    }

    /**
     * 
     * @param b true to turn on dumping, false to turn off
     */
    public void setDumping(boolean b) {
        dumping = b;
    }

    /**
     * Saves the address to return to after a function call completes.
     */
    public void pushReturnAddrs() {
        returnAddrs.push(pc);
    }

    /**
     * Loads the address after a return from a function call so the virtual
     * machine knows where to continue execution.
     */
    public void popReturnAddrs() {
        pc = returnAddrs.pop();
    }

    /**
     *
     * @param b true to start execution, false to stop it
     */
    public void setRunning(boolean b) {
        running = b;
    }
}
