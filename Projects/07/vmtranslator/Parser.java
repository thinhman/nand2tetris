package vmtranslator;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;

//PROJECT 7

/*Handles the parsing of a single .vm file, and encapsulates access to the input code. It reads VM
commands, parses them, and provides convenient access to their components. In addition, it removes all
white space and comments.*/

public class Parser {
    
    public Scanner inputFile;
    String current;
    public static List<String> arithmetic = Arrays.asList("add", "sub", "neg", "eq", "gt", "lt", "and", "or", "not");
    public String Command, Arg1, Arg2;
    String Type;
    
    public Parser(File fileName) throws FileNotFoundException
    {
       inputFile = new Scanner(fileName);
    }
    
    public boolean hasMoreCommands()
    {
        return inputFile.hasNextLine();
    }
    
    public void advance()
    {
        do
        {
            current = inputFile.nextLine();
            current = current.replaceAll("//.*", "");
        }while(current.length() == 0 && current != null);
        Type = commandType();
        
    }
    
    public String commandType()
    { 
      String[] segments = current.split(" ");
        
        if (segments.length > 3)
        {
            throw new IllegalArgumentException("Illegal Instruction.");
        }
        else if (segments.length > 1) 
        {
            Arg2 = segments[2];
            Arg1 = segments[1];
            Command =segments[0];
        }
        else
            Command =segments[0];
        if(arithmetic.contains(segments[0]))
        {
            return "C_ARITHMETIC";
        }
        else if ("push".equals(segments[0]))
        {
            return "C_PUSH";
        }
        else if ("pop".equals(segments[0]))
        {
            return "C_POP";
        }
        else if ("return".equals(segments[0]))
        {
            return "C_RETURN";
        }
        return null;
    }
    
    public String arg1()
    {
        if (!"RETURN".equals(commandType())){

            return Arg1;

        }
        else
            return "ERROR";
    }
    
    public String arg2(){

        if ("PUSH".equals(commandType()) || "POP".equals(commandType()))
        {

            return Arg2;

        }
        else
            return "ERROR";

    }
}
