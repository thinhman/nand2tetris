package vm_translator;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;


/*Handles the parsing of a single .vm file, and encapsulates access to the input code. It reads VM
commands, parses them, and provides convenient access to their components. In addition, it removes all
white space and comments.*/

//PROJECT 8

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
        try {
            Type = commandType();
        } catch (Exception ex) {
            Logger.getLogger(Parser.class.getName()).log(Level.SEVERE, null, ex);
        }
        
    }
    
    public String commandType() throws Exception
    { 
      String[] segments = current.split(" ");
        
        if (segments.length > 3)
        {
            throw new Exception("Illegal Command!");
        }
        Command =segments[0];
        if (segments.length == 2)
        {
            Arg1 = segments[1];
            Command =segments[0];
        }
        else if(segments.length == 3) 
        {
            Arg2 = segments[2];
            Arg1 = segments[1];
        }
          
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
        else if ("label".equals(segments[0]))
        {
            return "C_LABEL";
        }
        else if ("goto".equals(segments[0]))
        {
            return "C_GOTO";
        }
        else if ("if-goto".equals(segments[0]))
        {
            return "C_IF";
        }
        else if ("function".equals(segments[0]))
        {
            return "C_FUNCTION";
        }
        else if ("call".equals(segments[0]))
        {
            return "C_CALL";
        }
        return null;
    }
    
    public String arg1() throws Exception
    {
        if (!"RETURN".equals(commandType())){

            return Arg1;
        }
        else
            return "ERROR";
    }
    
    public String arg2() throws Exception{

        if ("PUSH".equals(commandType()) || "POP".equals(commandType()))
        {

            return Arg2;

        }
        else
            return "ERROR";

    }
}
