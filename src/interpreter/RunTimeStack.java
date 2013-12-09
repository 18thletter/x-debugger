/*
 * RunTimeStack.java
 */
package interpreter;

import java.util.ArrayList;
import java.util.List;
import java.util.Stack;

/**
 * The run time stack that is used by the virtual machine in the interpreter.
 *
 * @author Raymund Lew
 */
public class RunTimeStack {

    private Stack<Integer> framePointers = new Stack<Integer>();
    private List<Integer> runStack = new ArrayList<Integer>();

    public RunTimeStack() {
        framePointers.push(0);
    }

    /**
     * Prints out the contents of the run time stack, including separations
     * for frames.
     */
    public void dump() {
        for (int i = 0; i < framePointers.size(); i++) {
            System.out.print("[");
            int endFrame = runStack.size();
            if (i < framePointers.size() - 1) {
                endFrame = framePointers.get(i + 1);
            }
            for (int j = framePointers.get(i); j < endFrame; j++) {
                System.out.print(runStack.get(j));
                if (j < endFrame - 1) {
                    System.out.print(",");
                }
            }
            System.out.print("] ");
        }
        System.out.print("\n");
    }

    public boolean isEmpty() {
        return runStack.isEmpty();
    }

    /**
     * Looks at, but does not remove the top item of the stack.
     *
     * @return the top item of the run time stack
     */
    public int peek() {
        return runStack.get(runStack.size() - 1);
    }

    /**
     * Removes and returns the top item of the stack
     *
     * @return the top item of the run time stack
     */
    public int pop() {
        return runStack.remove(runStack.size() - 1);
    }

    /**
     * Pushes a new item on top of the run time stack
     *
     * @param item a new item to be placed on top of the stack
     * @return the item that was just pushed on top
     */
    public int push(int item) {
        runStack.add(item);
        return item;
    }

    /**
     * Creates a new frame at the specified offset.
     *
     * @param offset the offset at which to create the frame
     */
    public void newFrameAt(int offset) {
        if (runStack.size() - offset >= 0) {
            framePointers.add(runStack.size() - offset);
        }
    }

    /**
     * Removes the top frame from the run time stack.
     */
    public void popFrame() {
        if (!runStack.isEmpty() && framePointers.peek() < runStack.size()) {
            runStack.subList(framePointers.pop(), runStack.size() - 1).clear();
        } else {
            framePointers.pop();
        }
    }

    /**
     * Stores the top item of the stack to the specified offset.  The top item
     * is subsequently removed.
     *
     * @param offset where to store the top item
     * @return the item just stored
     */
    public int store(int offset) {
        runStack.set(framePointers.peek() + offset, this.peek());
        return this.pop();
    }

    /**
     * Loads the item at the specified offset to the top of the stack
     *
     * @param offset where to load the item from
     * @return the item that was just loaded
     */
    public int load(int offset) {
        return this.push(runStack.get(framePointers.peek() + offset));
    }

    /**
     *
     * @return number of arguments in the current frame
     */
    public int topFrameSize() {
        return runStack.size() - framePointers.peek();
    }
}
