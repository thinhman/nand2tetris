package jackcompiler;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;


public class JackCompiler 
{
    public static void main(String[] args) throws FileNotFoundException 
    {
        //File file = new File("SquareGame.jack");
        File f = new File(args[0]);
        String filePath = f.getAbsolutePath();
        int num;
        if (filePath.contains(".jack"))
        {
            num = filePath.indexOf(".");
            filePath = filePath.substring(0, num);
            CompilationEngine compiler = new CompilationEngine(filePath, f);
            compiler.compileClass();
            compiler.close();
        }
        else if (f.isDirectory())
        {
            searchDirectory(f);
        }
        else
            System.out.println("Incorrect file/directory");
        //JackTokenizer jack = new JackTokenizer(file);
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
                else if (file.getCanonicalPath().contains(".jack")) 
                {
                    int num = file.toString().indexOf(".");
                    String fileName = file.toString().substring(0 , num);
                    CompilationEngine compiler = new CompilationEngine(fileName, file);
                    compiler.compileClass();
                    compiler.close();  
		}
            }
	} catch (IOException e) 
        {
            e.printStackTrace();
        }
    }
    
}
