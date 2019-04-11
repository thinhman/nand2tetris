package vmtranslator;

import java.io.FileNotFoundException;
import java.io.PrintWriter;

//PROJECT 7

//Translates VM commands into Hack assembly code.
public class CodeWriter {
    public PrintWriter outFile;
    public Integer count = 0;
    
    
    public CodeWriter(String fileName) throws FileNotFoundException
    {
        Integer index = fileName.indexOf(".");
        fileName = fileName.substring(0, index) + ".asm";
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
                    String offset = "";
                    Integer value = 16 + Integer.parseInt(number);
                    offset = "" + value;
                    outFile.println("@" + value + "\nD=M\n@SP\nA=M\nM=D\n@SP\nM=M+1");
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
                    String offset = "";
                    Integer value = 16 + Integer.parseInt(number);
                    offset = "" + value;
                    outFile.println("@" + value + "\nD=A\n@R13\nM=D\n@SP\nAM=M-1\nD=M\n@R13\nA=M\nM=D");
                    break;
                default:
                    break;
            }
        }
    }
    public void close()
    {
        outFile.close();
    }
}
