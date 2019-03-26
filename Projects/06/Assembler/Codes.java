
package project6;

import java.util.HashMap;


public class Codes {
    public HashMap<String, String> comp = new HashMap();
    public HashMap<String, String> dest = new HashMap();
    public HashMap<String, String> jump = new HashMap();
    
    public Codes()
    {
        //a=0
        comp.put("0",	"1110101010");
        comp.put("1",	"1110111111");
        comp.put("-1", 	"1110111010");
        comp.put("D",	"1110001100");
        comp.put("A",	"1110110000");
        comp.put("!D", 	"1110001101");
        comp.put("!A", 	"1110110001");
        comp.put("-D", 	"1110001111");
        comp.put("-A", 	"1110110011");
        comp.put("D+1", "1110011111");
        comp.put("A+1", "1110110111");
        comp.put("D-1", "1110001110");
        comp.put("A-1", "1110110010");
        comp.put("D+A", "1110000010");
        comp.put("D-A", "1110010011");
        comp.put("A-D", "1110000111");
        comp.put("D&A", "1110000000");
        comp.put("D|A", "1110010101");
        //Comp (a=1)
        comp.put("M",	"1111110000");
        comp.put("!M", 	"1111110001");
        comp.put("-M", 	"1111110011");
        comp.put("M+1", "1111110111");
        comp.put("M-1", "1111110010");
        comp.put("D+M", "1111000010");
        comp.put("D-M", "1111010011");
        comp.put("M-D", "1111000111");
        comp.put("D&M", "1111000000");
        comp.put("D|M", "1111010101");
       
        dest.put("null", "000");
        dest.put("M",  "001");
        dest.put("D",  "010");
        dest.put("MD",  "011");
        dest.put("A",  "100");
        dest.put("AM",  "101");
        dest.put("AD",  "110");
        dest.put("AMD",	 "111");
        
        jump.put("null", "000");
        jump.put("JGT",  "001");
        jump.put("JEQ",  "010");
        jump.put("JGE",  "011");
        jump.put("JLT",  "100");
        jump.put("JNE",  "101");
        jump.put("JLE",  "110");
        jump.put("JMP",	 "111");
    }
    
    public String Comp(String compLine)
    {
        if(comp.containsKey(compLine))
        {
            return comp.get(compLine);
        }
        else
            return "ERROR";
    }
    
    public String Dest(String destLine)
    {
        if(dest.containsKey(destLine))
        {
            return dest.get(destLine);
        }
        else
            return "ERROR";
    }
     
    public String Jump(String jumpLine)
    {
        if(jump.containsKey(jumpLine))
        {
            return jump.get(jumpLine);
        }
        else
            return "ERROR";
    }
}
