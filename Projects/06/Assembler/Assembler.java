package project6;


import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.HashMap;
import java.util.logging.Level;
import java.util.logging.Logger;

public class Assembler {
    
    public HashMap<String, Integer> symbolTable = new HashMap();
    public String inputFile;
    int variable = 16;
    public PrintWriter outputFile;
    public Assembler(String inFilename, String outFileName )
    {
        try {
            outputFile = new PrintWriter(outFileName);
        } catch (FileNotFoundException ex) {
            Logger.getLogger(Assembler.class.getName()).log(Level.SEVERE, null, ex);
        }
        inputFile = inFilename;
        symbolTable.put("SP", 0);
        symbolTable.put("LCL", 1);
        symbolTable.put("ARG", 2);
        symbolTable.put("THIS", 3);
        symbolTable.put("THAT", 4);
        symbolTable.put("R0", 0);
        symbolTable.put("R1", 1);
        symbolTable.put("R2", 2);
        symbolTable.put("R3", 3);
        symbolTable.put("R4", 4);
        symbolTable.put("R5", 5);
        symbolTable.put("R6", 6);
        symbolTable.put("R7", 7);
        symbolTable.put("R8", 8);
        symbolTable.put("R9", 9);
        symbolTable.put("R10", 10);
        symbolTable.put("R11", 11);
        symbolTable.put("R12", 12);
        symbolTable.put("R13", 13);
        symbolTable.put("R14", 14);
        symbolTable.put("R15", 15);
        symbolTable.put("SCREEN", 16384);
        symbolTable.put("KBD", 24576);
        
        
    }
    public void firstPass() throws Exception {
        int lineNum = 0;
        Parser p = new Parser(inputFile);
        Codes c = new Codes();
        String s = p.next();
        while(s != null) {
          
            if (p.commandType() == "L_COMMAND") {
                if (!symbolTable.containsKey(s.substring(1, s.length() - 1)))
                {
                    symbolTable.put(s.substring(1, s.length() - 1), lineNum);
                }
            } else
                lineNum += 1;
            s = p.next();
    }
    return;
  }
    public void secondPass() throws IOException {
        Parser p = new Parser(inputFile);
        Codes c = new Codes();
        
        do
        {
            p.advance();
            if (p.commandType() == "A_COMMAND")
            {
                String symbol = p.symbol();
                Character first = symbol.charAt(0);
		boolean isInt = (Character.isDigit(first));
                if(isInt)
                {
                    symbol = String.format("%16s", Integer.toBinaryString(Integer.parseInt(symbol))).replace(' ', '0');
                    //System.out.println(symbol);
                    outputFile.println(symbol);
                }
                else if (symbolTable.containsKey(p.symbol()))
                {
                    //System.out.println(String.format("%16s", Integer.toBinaryString(symbolTable.get(p.symbol()))).replace(' ', '0'));
                    outputFile.println(String.format("%16s", Integer.toBinaryString(symbolTable.get(p.symbol()))).replace(' ', '0'));
                }
                else
                {
                    symbolTable.put(p.symbol(), variable);
                    //System.out.println(String.format("%16s", Integer.toBinaryString(symbolTable.get(p.symbol()))).replace(' ', '0'));
                    outputFile.println(String.format("%16s", Integer.toBinaryString(symbolTable.get(p.symbol()))).replace(' ', '0'));
                    variable +=1;
                }
                
            }
            else if (p.commandType() == "C_COMMAND")
            {
                //System.out.println(p.dest());
                //System.out.println(p.comp());
                //System.out.println(p.jump());
                //System.out.println(c.Comp(p.comp()) + c.Dest(p.dest()) + c.Jump(p.jump()));
                outputFile.println(c.Comp(p.comp()) + c.Dest(p.dest()) + c.Jump(p.jump()));
            }
//            else if (p.commandType() == "L_COMMAND")
//            {
//                if(symbolTable.containsKey(p.symbol()))
//                {
//                    System.out.println(String.format("%16s", Integer.toBinaryString(symbolTable.get(p.symbol()))).replace(' ', '0'));
//                    
//                }
//                else
//                    System.out.println("ERROR");
//            }
            
        }while(p.instr != null);
        
        outputFile.close();
	
    }
    
}
