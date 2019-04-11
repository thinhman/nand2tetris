package vm_translator;

//Translates VM commands into Hack assembly code.
//PROJECT 8
import java.io.FileNotFoundException;
import java.io.PrintWriter;

public class CodeWriter {
    public PrintWriter outFile;
    public Integer count = 0;
    String filename, function;
    String Class;
    
    public CodeWriter(String fileName) throws FileNotFoundException
    {
        Integer index = fileName.indexOf(".");
        filename = fileName.substring(0, index); 
        fileName = filename + ".asm";
        outFile = new PrintWriter(fileName);
    }
    
    public void setFileName(String fileName) 
    {
        System.out.print("Translation of " + fileName);
        Integer index = fileName.indexOf(".");
        fileName = fileName.substring(0, index) + ".asm";
        System.out.println(" to -> " + fileName);
    }
    
    public void writeArithmetic(String command) 
    {
        count++;
        switch (command) {
            case "add":
                outFile.println("@SP\nAM=M-1\nD=M\nA=A-1\nM=D+M");
                break;
            case "sub":
                outFile.println("@SP\nAM=M-1\nD=M\nA=A-1\nM=M-D");
                break;
            case "eq":
                outFile.println("@SP\n" + "AM=M-1\n" + "D=M\n" + "A=A-1\n" + "D=M-D\n" + "@EQUAL" + count +"\n" + "D;JEQ\n" +
                        "@SP\n" + "A=M-1\n" + "M=0\n@EXIT" + count + "\n" + "0;JMP\n" +
                        "(EQUAL" + count + ")\n" + "@SP\n" + "A=M-1\n" + "M=-1\n(EXIT" + count + ")");
                break;
            case "lt":
                outFile.println("@SP\n" + "AM=M-1\n" + "D=M\n" + "A=A-1\n" + "D=M-D\n" + "@LESS" + count + "\n" + "D;JLT\n" +
                        "@SP\n" + "A=M-1\n" + "M=0\n@EXIT" + count + "\n" + "0;JMP\n" +
                        "(LESS" + count + ")\n" + "@SP\n" + "A=M-1\n" + "M=-1\n(EXIT" + count + ")");
                break;
            case "gt":
                outFile.println("@SP\n" + "AM=M-1\n" + "D=M\n" + "A=A-1\n" + "D=M-D\n" + "@GREATER" + count + "\n" + "D;JGT\n" +
                        "@SP\n" + "A=M-1\n" + "M=0\n@EXIT" + count + "\n" + "0;JMP\n" +
                        "(GREATER" + count + ")\n" + "@SP\n" + "A=M-1\n" + "M=-1\n(EXIT" + count + ")");
                break;
            case "neg":
                outFile.println("@SP\nA=M-1\nD=0\nM=D-M");
                break;
            case "and":
                outFile.println("@SP\nAM=M-1\nD=M\nA=A-1\nM=D&M");
                break;
            case "or":
                outFile.println("@SP\nAM=M-1\nD=M\nA=A-1\nM=D|M");
                break;
            case "not":
                outFile.println("@SP\nA=M-1\nM=!M");
                break;
            default:
                System.err.println("Not Arithmetic");
                break;
        }
        
    
    }
    public void writePushPop(String command, String segment, String number)
    {
        if(command.equals("push"))
        {
            switch (segment) {
                case "constant":
                    outFile.println("@" + number + "\nD=A\n@SP\nA=M\nM=D\n@SP\nM=M+1");
                    break;
                case "local":
                    outFile.println("@LCL\nD=M\n@" + number + "\nA=D+A\nD=M\n@SP\nA=M\nM=D\n@SP\nM=M+1");
                    break;
                case "argument":
                    outFile.println("@ARG\nD=M\n@" + number + "\nA=D+A\nD=M\n@SP\nA=M\nM=D\n@SP\nM=M+1");
                    break;
                case "this":
                    outFile.println("@THIS\nD=M\n@" + number + "\nA=D+A\nD=M\n@SP\nA=M\nM=D\n@SP\nM=M+1");
                    break;
                case "that":
                    outFile.println("@THAT\nD=M\n@" + number + "\nA=D+A\nD=M\n@SP\nA=M\nM=D\n@SP\nM=M+1");
                    break;
                case "temp":
                    outFile.println("@R5\nD=A\n@" + number + "\nA=D+A\nD=M\n@SP\nA=M\nM=D\n@SP\nM=M+1");
                    break;
                case "pointer":
                    if(number.equals("0"))
                    {
                        outFile.println("@THIS\nD=M\n@SP\nA=M\nM=D\n@SP\nM=M+1");
                    }
                    else
                        outFile.println("@THAT\nD=M\n@SP\nA=M\nM=D\n@SP\nM=M+1");
                    break;
                case "static":
                    outFile.println("@" + Class + "." + number + "\nD=M\n@SP\nA=M\nM=D\n@SP\nM=M+1");
                    break;
                default:
                    break;
            }
        }
        else if (command.equals("pop"))
        {
            switch (segment) {
                case "local":
                    outFile.println("@LCL\nD=M\n@" + number + "\nD=D+A\n@R13\nM=D\n@SP\nAM=M-1\nD=M\n@R13\nA=M\nM=D");
                    break;
                case "argument":
                    outFile.println("@ARG\nD=M\n@" + number + "\nD=D+A\n@R13\nM=D\n@SP\nAM=M-1\nD=M\n@R13\nA=M\nM=D");
                    break;
                case "this":
                    outFile.println("@THIS\nD=M\n@" + number + "\nD=D+A\n@R13\nM=D\n@SP\nAM=M-1\nD=M\n@R13\nA=M\nM=D");
                    break;
                case "that":
                    outFile.println("@THAT\nD=M\n@" + number + "\nD=D+A\n@R13\nM=D\n@SP\nAM=M-1\nD=M\n@R13\nA=M\nM=D");
                    break;
                case "temp":
                    outFile.println("@R5\nD=A\n@" + number + "\nD=D+A\n@R13\nM=D\n@SP\nAM=M-1\nD=M\n@R13\nA=M\nM=D");
                    break;
                case "pointer":
                    if(number.equals("0"))
                    {
                        outFile.println("@THIS\nD=A\n@R13\nM=D\n@SP\nAM=M-1\nD=M\n@R13\nA=M\nM=D");
                    }
                    else
                        outFile.println("@THAT\nD=A\n@R13\nM=D\n@SP\nAM=M-1\nD=M\n@R13\nA=M\nM=D");
                    break;
                case "static":
                    outFile.println("@" + Class + "." + number + "\nD=A\n@R13\nM=D\n@SP\nAM=M-1\nD=M\n@R13\nA=M\nM=D");
                    break;
                default:
                    break;
            }
        }
    }
    
    public void writeInt()
    {
        outFile.println("@256\nD=A\n@SP\nM=D");
        writeCall("Sys.init",0);
    }
    
    public void writeLabel(String label)
    {
        outFile.println("(" + label + ")");
    }
    
    public void writeGoTo(String label)
    {
        outFile.println("@" + label +"\n0;JMP");
    }
    
    public void writeIf(String label)
    {
        outFile.println("@SP\nAM=M-1\nD=M\nA=A-1\n@" + label + "\nD;JNE");
    }
    
    public void writeCall(String functionName, Integer numArgs)
    {
        count++;
        outFile.println("@Return" + count + "\nD=A\n@SP\nA=M\nM=D\n@SP\nM=M+1");
        outFile.println("@LCL\nD=M\n@SP\nA=M\nM=D\n@SP\nM=M+1");
        outFile.println("@ARG\nD=M\n@SP\nA=M\nM=D\n@SP\nM=M+1");
        outFile.println("@THIS\nD=M\n@SP\nA=M\nM=D\n@SP\nM=M+1");
        outFile.println("@THAT\nD=M\n@SP\nA=M\nM=D\n@SP\nM=M+1");
        outFile.println("@SP\nD=M\n@5\nD=D-A\n@" + numArgs + "\nD=D-A\n@ARG\nM=D\n@SP\nD=M\n@LCL\nM=D\n@" + functionName + "\n0;JMP\n(Return" + count + ")");
    }
    
    public void writeReturn()
    {
        outFile.println("@LCL\nD=M\n@R7\nM=D");
        outFile.println("@5\nA=D-A\nD=M\n@R8\nM=D");
        writePushPop("pop", "argument", "0");
        outFile.println("@ARG\nD=M\n@SP\nM=D+1");
        outFile.println("@R7\nD=M-1\nAM=D\nD=M\n@THAT\nM=D");
        outFile.println("@R7\nD=M-1\nAM=D\nD=M\n@THIS\nM=D");
        outFile.println("@R7\nD=M-1\nAM=D\nD=M\n@ARG\nM=D");
        outFile.println("@R7\nD=M-1\nAM=D\nD=M\n@LCL\nM=D");
        outFile.println("@R8\nA=M\n0;JMP");
    }
    
    public void writeFunction(String functionName, Integer numArgs)
    {
        function = functionName;
        outFile.println("(" + functionName + ")");
        for (int x = 0; x < numArgs; x++)
        {
            writePushPop("push", "constant", "0");
        }
    }
    
    
    public void close()
    {
        outFile.close();
    }
}
