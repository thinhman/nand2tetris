package vmtranslator;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;

//PROJECT 7

public class VMtranslator {

    public static void main(String[] args) throws FileNotFoundException 
    {
        File f = new File(args[0]);
        
        String filePath = f.getAbsolutePath();
        if (filePath.contains(".vm"))
        {
            Parser P = new Parser(f);
            CodeWriter CW = new CodeWriter(filePath);
            while (P.hasMoreCommands())
            {
                P.advance();
                if (P.Type.equals("C_ARITHMETIC"))
                {
                    CW.writeArithmetic(P.Command);
                }
                else
                    CW.writePushPop(P.Command, P.Arg1, P.Arg2);
            }
            CW.close();
        }
        else if (f.isDirectory())
        {
            searchDirectory(f);
        }
        else
            System.out.println("Incorrect file");
    }
    
    public static void searchDirectory(File dir) 
    {
        try {
            File[] files = dir.listFiles();
            for (File file : files) {
		if (file.isDirectory()) {
                    System.out.println("directory:" + file.getCanonicalPath());
                    searchDirectory(file);
		} 
                else if (file.getCanonicalPath().contains(".vm")) 
                {
                    Parser P = new Parser(file);
                    CodeWriter CW = new CodeWriter(file.toString());
                    CW.setFileName(file.getName());
                    while (P.hasMoreCommands())
                    {
                        P.advance();
                        if (P.Type.equals("C_ARITHMETIC"))
                        {
                            CW.writeArithmetic(P.Command);
                        }
                        else
                            CW.writePushPop(P.Command, P.Arg1, P.Arg2);
                    }
                    CW.close();   
		}
            }
	} catch (IOException e) 
        {
            e.printStackTrace();
        }
    }
    
}
