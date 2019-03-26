package project6;

import java.io.IOException;



public class Project6 {

    public static void main(String[] args) throws IOException, Exception {
		//Please use the correct directory to the files in your computer
		//Assembler(inputfile, outputfile)
        Assembler Asm = new Assembler("Rect.asm", "Prog.hack"); 
        Asm.firstPass();
        Asm.secondPass();
    }
    
}
