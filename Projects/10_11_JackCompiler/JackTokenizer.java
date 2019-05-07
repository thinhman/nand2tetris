package jackcompiler;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;


public class JackTokenizer 
{
    public Scanner inputFile;
    public String current, code;
    public ArrayList<String> keyWords;
    public ArrayList<String> symbols;
    public ArrayList<String> tokens;
    public String currentToken, currentType, cKeyWord, cIdentifier, cStringVal, cIntVal;
    public char cSymbol;
    public String num;
    public int index;
    
    public JackTokenizer(File inFile) 
    {
        keyWords = new ArrayList<>();
        symbols = new ArrayList<>();
        keyWords.add("class");
        keyWords.add("constructor");
        keyWords.add("function");
        keyWords.add("method");
        keyWords.add("field");
        keyWords.add("static");
        keyWords.add("var");
        keyWords.add("int");
        keyWords.add("char");
        keyWords.add("boolean");
        keyWords.add("void");
        keyWords.add("true");
        keyWords.add("false");
        keyWords.add("null");
        keyWords.add("this");
        keyWords.add("let");
        keyWords.add("do");
        keyWords.add("if");
        keyWords.add("else");
        keyWords.add("while");
        keyWords.add("return");
        symbols.add("{");
        symbols.add("}");
        symbols.add("(");
        symbols.add(")");
        symbols.add("[");
        symbols.add("]");
        symbols.add(".");
        symbols.add(",");
        symbols.add(";");
        symbols.add("+");
        symbols.add("-");
        symbols.add("*");
        symbols.add("/");
        symbols.add("&");
        symbols.add("|");
        symbols.add("<");
        symbols.add(">");
        symbols.add("=");
        symbols.add("-");
        symbols.add("~");
        num = "0123456789";
        code = "";
        current = "";
        try 
        {
            inputFile = new Scanner(inFile);
            while (inputFile.hasNext())
            {
                current = inputFile.nextLine();
                if(current.contains("/*"))
                {
                    while (!current.contains("*/"))
                    {
                        current = inputFile.nextLine();
                    }
                    current = inputFile.nextLine();
                }
                current = current.trim();
                current = current.replaceAll("//.*", "");
                //current = current.replaceAll("//.*?\n","");
                current = current.replaceAll("(?s)/\\*.*?\\*/","");
                if (!current.isEmpty())
                {
                    code += " " + current;
                }
            }
            tokens = new ArrayList<>();
            while (code.length() > 0)
            {
                int count = 0;
                currentToken = "";
                code = code.trim();
                if (symbols.contains(Character.toString(code.charAt(0))) )
                {
                    currentToken += code.charAt(0);
                    tokens.add(currentToken);
                    code = code.substring(1);
                }
                else
                {
                    count = code.indexOf(" ");
                    String temp = code.substring(0, count);
                    code = code.substring(count);
                    code = code.trim();
                    count = 0;
                    while(count < temp.length())
                    {
                        if (Character.isDigit(temp.charAt(count)))
                        {
                            if (!currentToken.isEmpty())
                            {
                                if (!keyWords.contains(currentToken))
                                {
                                    tokens.add("_" + currentToken);
                                }
                                else
                                    tokens.add(currentToken);
                            }
                            while(Character.isDigit(temp.charAt(count)))
                            {
                                currentToken += temp.charAt(count);
                                count++;
                            }
                            tokens.add(currentToken);
                            temp = temp.substring(count);
                            currentToken = "";
                            count = 0;
                        }
                        if (symbols.contains(Character.toString(temp.charAt(count))))
                        {
                            currentToken = currentToken.replaceAll("\\s+", "");
                            if (!currentToken.isEmpty())
                            {
                                if (!keyWords.contains(currentToken))
                                {
                                    tokens.add("_" + currentToken);
                                }
                                else
                                    tokens.add(currentToken);
                                currentToken = "";
                            }
                            tokens.add(Character.toString(temp.charAt(count)));
                            temp = temp.substring(count);
                            count = 0;
                        }
                        else if (temp.startsWith("\""))
                        {
                            currentToken = currentToken.replaceAll("\\s+", "");
                            if (!currentToken.isEmpty())
                            {
                                if (!keyWords.contains(currentToken))
                                {
                                    tokens.add("_" + currentToken);
                                }
                                else
                                    tokens.add(currentToken);
                                currentToken = "";
                            }
                            temp = temp.substring(1);
                            currentToken += "*" + temp + " ";
                            count = code.indexOf(" ");
                            temp = code.substring(0, count);
                            code = code.substring(count);
                            code = code.trim();
                            count = temp.indexOf(";");
                            currentToken += temp.substring(0, count - 1);
                            tokens.add(currentToken);
                            tokens.add(Character.toString(temp.charAt(count)));
                            currentToken = "";
                            temp = "";
                            count = 0;
                        }
                        else if(temp.startsWith("(\""))
                        {
                            currentToken = currentToken.replaceAll("\\s+", "");
                            if (!currentToken.isEmpty())
                            {
                                if (!keyWords.contains(currentToken))
                                {
                                    tokens.add("_" + currentToken);
                                }
                                else
                                    tokens.add(currentToken);
                                currentToken = "";
                            }
                            temp = temp.substring(2);
                            count  = code.indexOf(")");
                            currentToken += temp + " "; 
                            temp = code.substring(0, count - 1);
                            currentToken += temp;
                            tokens.add("*" + currentToken);
                            code = code.substring(count);
                            temp = "";
                            currentToken = "";
                            count = 0;

                        }
                        else if (Character.isDigit(temp.charAt(0)))
                        {
                            int x = 0;
                            while(Character.isDigit(temp.charAt(x)))
                            {
                                currentToken += temp.charAt(x);
                                x++;
                            }
                            temp = temp.substring(x);
                            tokens.add(currentToken);
                            currentToken = "";
                        }
                        else
                            currentToken += temp.charAt(count);
                        count++;
                    }
                    currentToken = currentToken.trim();
                    if (!currentToken.isEmpty())
                    {
                        if (!keyWords.contains(currentToken))
                        {
                            tokens.add("_" + currentToken);
                        }
                        else
                            tokens.add(currentToken);
                    }
                }
                //tokens.add(currentToken);
            }
            int count = 0;
            while(count < tokens.size() && !tokens.isEmpty())
            {
                System.out.println(tokens.get(count));
                count++;
            }
        } 
        catch (FileNotFoundException ex) {
            Logger.getLogger(JackTokenizer.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    public void advance()
    {
        if (hasMoreTokens()) 
        {
            currentToken = tokens.get(index);
            index++;
            currentType = tokenType();
        }
    }
    public boolean hasMoreTokens() 
    {
        return  index < tokens.size();
    }
    
    public String tokenType() 
    {
        if (keyWords.contains(currentToken))
        {
            cKeyWord = keyWords.get(keyWords.indexOf(currentToken));
            return "KEYWORD";
        }
        else if (symbols.contains(currentToken))
        {
            cSymbol = currentToken.charAt(0);
            return "SYMBOL";
        }
        else if (Character.isDigit(currentToken.charAt(0)))
        {
            cIntVal = currentToken;
            return "INT_CONST";
        }
        else if (currentToken.charAt(0) == '*')
        {
            cStringVal = currentToken.substring(1);
            return "STRING_CONST";
        }
        else if (currentToken.charAt(0) == '_')
        {
            cIdentifier = currentToken.substring(1);
            return "IDENTIFIER";
        }
        else
            return null;
    }

    public String keyWord() 
    {
        if (currentType.equals("KEYWORD"))
        {
            return cKeyWord;
        }
        else 
        {
            throw new IllegalStateException("Current token is not a keyword!");
        }
    }
    
    public char symbol() 
    {
        if (currentType.equals("SYMBOL"))
        {
            return cSymbol;
        }
        else 
        {
            throw new IllegalStateException("Current token is not a symbol!");
        }
    }
    
    public String identifier() 
    {
        if (currentType.equals("IDENTIFIER"))
        {
            return cIdentifier;
        }
        else 
        {
            throw new IllegalStateException("Current token is not an identifier!");
        }
    }
    
    public String intVal() 
    {
        if (currentType.equals("INT_CONST"))
        {
            return cIntVal;
        }
        else 
        {
            throw new IllegalStateException("Current token is not an integer!");
        }
    }

    public String stringVal() 
    {
        if (currentType.equals("STRING_CONST"))
        {
            return cStringVal;
        }
        else 
        {
            throw new IllegalStateException("Current token is not a String!");
        }
    }
}
