/*
 * ByteCodeLoader.java
 */
package interpreter;

import interpreter.bytecodes.ByteCode;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.StringTokenizer;

/**
 * The ByteCodeLoader creates and initializes byte codes from the .x.cod file
 * and places them into the program.
 *
 * @author Raymund Lew
 */
public class ByteCodeLoader {

    private BufferedReader source;

    /**
     *
     * @param fileName the .x.cod file to create byte codes from
     * @throws IOException
     */
    public ByteCodeLoader(String fileName) throws IOException {
        source = new BufferedReader(new FileReader(fileName));
    }

    /**
     * Uses the .x.cod file to create byte codes and load them into the program.
     * 
     * @return the program that the byte codes were loaded into
     */
    public Program loadCodes() {
        Program program = new Program();
        String line;
        try {
            while ((line = source.readLine()) != null) {
                StringTokenizer tokenizer = new StringTokenizer(line);
                List<String> argumentList = new ArrayList<String>();
                ByteCode bytecode = (ByteCode) (Class.forName("interpreter.bytecodes." + CodeTable.
                                                get(tokenizer.nextToken()))).
                        newInstance();
                while (tokenizer.hasMoreTokens()) {
                    argumentList.add(tokenizer.nextToken());
                }
                bytecode.init(argumentList);
                program.addCode(bytecode);
            }
            program.resolveAddress();
        } catch (Exception e) {
            System.out.println(e);
        }
        return program;
    }
}
