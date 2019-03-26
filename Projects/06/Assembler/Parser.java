package project6;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;


public class Parser {
    
    public BufferedReader inputFile;
    public String instr;
    
    public Parser(String filename){
        try {
            inputFile = new BufferedReader(new FileReader(filename));
        } catch (FileNotFoundException ex) {
            Logger.getLogger(Assembler.class.getName()).log(Level.SEVERE, null, ex);
        }
        
    }
    public String next() throws IOException {
        String line;
        while(true) {
            line = inputFile.readLine();
            if (line == null) {
                close();
                instr = line;
                return line;
            }
            line = line.replaceAll("\\s","");
            line = line.replaceAll("//.*", "");
            if (line.length() == 0)
            {
                continue;
            }
            else
            {
                instr = line;
                return line;
            }
            
        }
    }
    
    public void advance() throws IOException {
        String line;
        while(true) {
            line = inputFile.readLine();
            if (line == null) {
                close();
                instr = line;
                break;
            }
            line = line.replaceAll("\\s","");
            line = line.replaceAll("//.*", "");
            if (line.length() == 0)
            {
                continue;
            }
            else
            {
                instr = line;
                break;
            }
            
        }
        
    }
    public void close() {
        try {
            inputFile.close();
	} catch (IOException e) {
            // No operation.
        }
    }
    public String commandType()
    {
        if (instr != null)
        {
            if (instr.charAt(0) == '@')
                return "A_COMMAND";
            if (instr.charAt(0) == '(')
                return "L_COMMAND";
            return "C_COMMAND";
        }
        return "";
        
    }
    public String symbol()
    {
        String temp = instr;
        if (commandType() == "A_COMMAND")
        {
            temp = temp.replaceAll("@", "");
            return temp;
        }
        else if (commandType() == "L_COMMAND")
        {
            temp = temp.replace("(", "");
            temp = temp.replace(")", "");
            return temp;
        }
        return null;
    }
    public String comp()
    {
        String temp = instr;
        int equal = temp.indexOf("=");
        if (equal != -1) 
        {
            temp = temp.substring(equal + 1);
        }
        int semi = temp.indexOf(";");
        if (semi != -1) 
        {
            return temp.substring(0, semi);
        }
        else
            return temp;
        
    }
    public String dest()
    {
        String temp = instr;
        int equal = temp.indexOf("=");
        if (equal != -1) 
        {
            return temp.substring(0,equal);
        }
        else
            return "null";
    }
    public String jump()
    {
        String temp = instr;
        int semi = temp.indexOf(";");
        if (semi != -1) 
        {
            return temp.substring(semi + 1);
        }
        else
            return "null";
    }
}
