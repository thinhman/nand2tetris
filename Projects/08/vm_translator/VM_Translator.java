package vm_translator;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;


//PROJECT 8

public class VM_Translator {

    
    public static void main(String[] args) throws FileNotFoundException 
    {
        File f = new File(args[0]);     //Pass the file location OR a directory to compile all files inside
        
        String filePath = f.getAbsolutePath();
        if (filePath.contains(".vm"))
        {
            Parser P = new Parser(f);
            CodeWriter CW = new CodeWriter(filePath);
            while (P.hasMoreCommands())
            {
                P.advance();
                switch (P.Type) {
                    case "C_ARITHMETIC":
                        CW.writeArithmetic(P.Command);
                        break;
                    case "C_LABEL":
                        CW.writeLabel(P.Arg1);
                        break;
                    case "C_IF":
                        CW.writeIf(P.Arg1);
                        break;
                    case "C_GOTO":
                        CW.writeGoTo(P.Arg1);
                        break;
                    case "C_FUNCTION":
                        CW.writeFunction(P.Arg1, Integer.parseInt(P.Arg2));
                        break;
                    case "C_RETURN":
                        CW.writeReturn();
                        break;
                    case "C_CALL":
                        CW.writeCall(P.Arg1, Integer.parseInt(P.Arg2));
                        break;
                    default:
                        CW.writePushPop(P.Command, P.Arg1, P.Arg2);
                        break;
                }
            }
            CW.close();
        }
        else if (f.isDirectory())
        {
            String path = f.getAbsolutePath() + "/" + f.getName() + ".vm";
            ArrayList<File> allVMFiles = new ArrayList<File>();
            searchDirectory(f, allVMFiles);
            translate(path, allVMFiles);
            
        }
        else
            System.out.println("Incorrect file");
    }
    public static void searchDirectory(File dir, ArrayList<File> VM_Files) 
    {
        try {
            File[] files = dir.listFiles();
            for (File file : files) {
		if (file.isDirectory()) 
                {
                    System.out.println("No .vm file in this Directory");
		} 
                else if (file.getCanonicalPath().contains(".vm")) 
                {
                    VM_Files.add(file);
                }   
            }
        }
	catch (IOException e) 
        {
            e.printStackTrace();
        }
    }
    public static void translate(String dir, ArrayList<File> VM_Files) 
    {
        try {
            CodeWriter CW = new CodeWriter(dir);
            CW.setFileName(dir);
            if (dir.contains("FunctionCalls") && !dir.contains("SimpleFunction"))
            {
                CW.writeInt();
            }

            for (File file : VM_Files) {
		
                if (file.getCanonicalPath().contains(".vm")) 
                {
                    Parser P = new Parser(file);
                    CW.Class = file.getName();
                    while (P.hasMoreCommands())
                    {
                        P.advance();
                        switch (P.Type) {
                            case "C_ARITHMETIC":
                                CW.writeArithmetic(P.Command);
                                break;
                            case "C_LABEL":
                                CW.writeLabel(P.Arg1);
                                break;
                            case "C_IF":
                                CW.writeIf(P.Arg1);
                                break;
                            case "C_GOTO":
                                CW.writeGoTo(P.Arg1);
                                break;
                            case "C_FUNCTION":
                                CW.writeFunction(P.Arg1, Integer.parseInt(P.Arg2));
                                break;
                            case "C_RETURN":
                                CW.writeReturn();
                                break;
                            case "C_CALL":
                                CW.writeCall(P.Arg1, Integer.parseInt(P.Arg2));
                                break;
                            default:
                                CW.writePushPop(P.Command, P.Arg1, P.Arg2);
                                break;
                        }
                    }
                    
		}
            }
        CW.close(); 
	} catch (IOException e) 
        {
            e.printStackTrace();
        }  
    }
    
}
