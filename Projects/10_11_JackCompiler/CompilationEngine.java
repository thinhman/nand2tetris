package jackcompiler;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;

public class CompilationEngine 
{
    public PrintWriter outFile;
    public JackTokenizer tokenizer;
    public CompilationEngine (String fileName, File inFile) throws FileNotFoundException
    {
        //Integer index = fileName.indexOf(".");
        fileName = fileName + ".xml";
        outFile = new PrintWriter(fileName);
        try {

            tokenizer = new JackTokenizer(inFile);
            outFile = new PrintWriter(fileName);

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }
    public void compileClass()
    {
        tokenizer.advance();
        if ("KEYWORD".equals(tokenizer.tokenType()) && "class".equals(tokenizer.keyWord()))
        {
            //System.out.print("<class>\n");
            outFile.print("<class>\n");
            outFile.print("<keyword> " + tokenizer.keyWord()+ " </keyword>\n");
            tokenizer.advance();
            outFile.print("<identifier> " + tokenizer.identifier() + " </identifier>\n");
            tokenizer.advance();
            outFile.print("<symbol> " + tokenizer.symbol()+ " </symbol>\n");
            compileClassVarDec();
            compileSubroutine();
            tokenizer.advance();
            //System.out.println(tokenizer.currentToken);
            outFile.print("<symbol> " + tokenizer.symbol()+ " </symbol>\n");
            outFile.print("</class>\n");
        }
        outFile.close();
    }
    public void compileClassVarDec()
    {
        tokenizer.advance();
        if (tokenizer.currentType.equals("KEYWORD") && (tokenizer.keyWord().equals("static") || tokenizer.keyWord().equals("field")))
        {
            outFile.print("<classVarDec>\n");
            outFile.print("<keyword> " + tokenizer.keyWord()+ " </keyword>\n");
            tokenizer.advance();
            if (tokenizer.currentType.equals("KEYWORD"))
            {
                outFile.print("<keyword> " + tokenizer.keyWord()+ " </keyword>\n");
            }
            else
                outFile.print("<identifier> " + tokenizer.identifier()+ " </identifier>\n");
            tokenizer.advance();
            outFile.print("<identifier> " + tokenizer.identifier()+ " </identifier>\n");
            tokenizer.advance();
            if (tokenizer.currentType.equals("SYMBOL") && tokenizer.symbol() == ',')
            {
                while (tokenizer.symbol() == ',')
                {
                    outFile.print("<symbol> " + tokenizer.symbol() + " </symbol>\n");
                    tokenizer.advance();
                    outFile.print("<identifier> " + tokenizer.identifier()+ " </identifier>\n");
                    tokenizer.advance();
                }    
            }
            outFile.print("<symbol> " + tokenizer.symbol() + " </symbol>\n");
            outFile.print("</classVarDec>\n");
            compileClassVarDec();
        }
        else
            tokenizer.index = tokenizer.index - 1;
    }
    public void compileSubroutine()
    {
        tokenizer.advance();
        //System.out.println(tokenizer.currentToken);
        if (tokenizer.tokenType().equals("KEYWORD") && ( tokenizer.keyWord().equals("function") || 
            tokenizer.keyWord().equals("method") || tokenizer.keyWord().equals("constructor") ))
        {
            // KEYWORD KEYWORD->type IDENTIFIER->NAME (PARAMETERLIST) { varDec STATEMENTS }
            outFile.print("<subroutineDec>\n");
            outFile.print("<keyword> " + tokenizer.keyWord()+ " </keyword>\n");
            if (tokenizer.keyWord().equals("constructor"))
            { 
                tokenizer.advance();
                outFile.print("<identifier> " + tokenizer.identifier()+ " </identifier>\n");
            }
            else
            {
                tokenizer.advance();
                outFile.print("<keyword> " + tokenizer.keyWord()+ " </keyword>\n");  
            }
            tokenizer.advance();
            outFile.print("<identifier> " + tokenizer.identifier()+ " </identifier>\n");
            //(PARAMETERLIST)
            tokenizer.advance();
            outFile.print("<symbol> " + tokenizer.symbol()+ " </symbol>\n");
            compileParameterList();
            tokenizer.advance();
            outFile.print("<symbol> " + tokenizer.symbol()+ " </symbol>\n");
            outFile.print("<subroutineBody>\n");
            //{varDec STATEMENTS  RETURN}
            tokenizer.advance();
            outFile.print("<symbol> " + tokenizer.symbol()+ " </symbol>\n");
            compileVarDec();
            outFile.print("<statements>\n");
            compileStatements();
            outFile.print("</statements>\n");
            tokenizer.advance();
            outFile.print("<symbol> " + tokenizer.symbol()+ " </symbol>\n");
            outFile.print("</subroutineBody>\n");
            outFile.print("</subroutineDec>\n");
            compileSubroutine();
        }   
        else
            tokenizer.index--;
    }
    public void compileParameterList()
    {
        outFile.print("<parameterList>\n");
        tokenizer.advance();
        if (tokenizer.currentType.equals("KEYWORD"))
        {
            outFile.print("<keyword> " + tokenizer.keyWord()+ " </keyword>\n");
            tokenizer.advance();
            outFile.print("<identifier> " + tokenizer.identifier()+ " </identifier>\n");
            tokenizer.advance();
            if (tokenizer.currentType.equals("SYMBOL") && tokenizer.symbol() == ',')
            {
                while (tokenizer.symbol() == ',')
                {
                    outFile.print("<symbol> " + tokenizer.symbol() + " </symbol>\n");
                    tokenizer.advance();
                    outFile.print("<keyword> " + tokenizer.keyWord()+ " </keyword>\n");
                    tokenizer.advance();
                    outFile.print("<identifier> " + tokenizer.identifier()+ " </identifier>\n");
                    tokenizer.advance();
                }    
                tokenizer.index = tokenizer.index - 1;
            }
        }
        else
            tokenizer.index = tokenizer.index - 1;
        outFile.print("</parameterList>\n");
    }
    public void compileVarDec()
    {
        tokenizer.advance();
        if (tokenizer.currentType.equals("KEYWORD") && tokenizer.keyWord().equals("var"))
        {
            outFile.print("<varDec>\n");
            outFile.print("<keyword> " + tokenizer.keyWord()+ " </keyword>\n");
            //data type
            tokenizer.advance();
            if (tokenizer.currentType.equals("KEYWORD"))
            {
                outFile.print("<keyword> " + tokenizer.keyWord()+ " </keyword>\n");
            }
            else
                outFile.print("<identifier> " + tokenizer.identifier()+ " </identifier>\n");
            //varName
            tokenizer.advance();
            outFile.print("<identifier> " + tokenizer.identifier()+ " </identifier>\n");
            tokenizer.advance();
            while(tokenizer.symbol() == ',' )
            {
                outFile.print("<symbol> " + tokenizer.symbol() + " </symbol>\n");
                tokenizer.advance();
                //System.out.println(tokenizer.identifier());
                outFile.print("<identifier> " + tokenizer.identifier()+ " </identifier>\n");
                tokenizer.advance();
            }
            outFile.print("<symbol> " + tokenizer.symbol() + " </symbol>\n");
            outFile.print("</varDec>\n");
            compileVarDec();
        }
        else
            tokenizer.index = tokenizer.index - 1;
    }
    public void compileStatements()
    {
        tokenizer.advance();
        if (tokenizer.currentType.equals("KEYWORD"))
        {
            switch (tokenizer.keyWord()) 
            {
                case "let":
                    outFile.print("<letStatement>\n");
                    compileLet();
                    outFile.print("</letStatement>\n");
                    break;
                case "if":
                    outFile.print("<ifStatement>\n");
                    compileIf();
                    outFile.print("</ifStatement>\n");
                    break;
                case "while":
                    outFile.print("<whileStatement>\n");
                    compileWhile();
                    outFile.print("</whileStatement>\n");
                    break;
                case "do":
                    outFile.print("<doStatement>\n");
                    compileDo();
                    outFile.print("</doStatement>\n");
                    break;
                case "return":
                    outFile.print("<returnStatement>\n");
                    compileReturn();
                    outFile.print("</returnStatement>\n");
                    break;
                default:
                    break;
            }
            compileStatements();
        }
        else
            tokenizer.index = tokenizer.index - 1;
        
    }
    public void compileDo()
    {
        //tokenizer.advance();
         // do identifier . identifier ( ExpressionList ) ;
        outFile.print("<keyword> " + tokenizer.keyWord()+ " </keyword>\n");
        tokenizer.advance();
        outFile.print("<identifier> " + tokenizer.identifier()+ " </identifier>\n");
        tokenizer.advance();
        outFile.print("<symbol> " + tokenizer.symbol()+ " </symbol>\n");
        if (tokenizer.symbol() == '.')
        {
            tokenizer.advance();
            outFile.print("<identifier> " + tokenizer.identifier()+ " </identifier>\n");
            tokenizer.advance();
            outFile.print("<symbol> " + tokenizer.symbol()+ " </symbol>\n");
        }
        outFile.print("<expressionlist>\n");
        compileExpressionList();
        outFile.print("<expressionlist>\n");
        tokenizer.advance();
        outFile.print("<symbol> " + tokenizer.symbol()+ " </symbol>\n");
        tokenizer.advance();
        outFile.print("<symbol> " + tokenizer.symbol()+ " </symbol>\n");
    }
    public void compileLet()
    {
        //let varName [ ]?
        outFile.print("<keyword> " + tokenizer.keyWord()+ " </keyword>\n");
        tokenizer.advance();
        outFile.print("<identifier> " + tokenizer.identifier() + " </identifier>\n");
        tokenizer.advance();
        if ( tokenizer.tokenType().equals("SYMBOL") && tokenizer.symbol() == '[')
        {
            // [ expression ]
            outFile.print("<symbol> " + tokenizer.symbol() + " </symbol>\n");
            //tokenizer.advance();
            compileExpression();
            tokenizer.advance();
            outFile.print("<symbol> " + tokenizer.symbol() + " </symbol>\n");
            tokenizer.advance();
        }
        // = expression ;
        outFile.print("<symbol> " + tokenizer.symbol() + " </symbol>\n");
        compileExpression();
        tokenizer.advance();
//        if ( tokenizer.tokenType().equals("SYMBOL") && tokenizer.symbol() == ')')
//        {
//            System.out.print("<symbol> " + tokenizer.symbol() + " </symbol>\n");
//            tokenizer.advance();
//        }
        outFile.print("<symbol> " + tokenizer.symbol() + " </symbol>\n");

    }
    public void compileWhile()
    {
        // while ( expression )
        outFile.print("<keyword> " + tokenizer.keyWord()+ " </keyword>\n");
        tokenizer.advance();
        outFile.print("<symbol> " + tokenizer.symbol() + " </symbol>\n");
        compileExpression();
        tokenizer.advance();
        outFile.print("<symbol> " + tokenizer.symbol() + " </symbol>\n");
        // { statements }
        tokenizer.advance();
        outFile.print("<symbol> " + tokenizer.symbol() + " </symbol>\n");
        outFile.print("<statements>\n");
        compileStatements();
        outFile.print("</statements>\n");
        tokenizer.advance();
        outFile.print("<symbol> " + tokenizer.symbol() + " </symbol>\n");
    }
    public void compileReturn()
    {
        //return expression?
        //tokenizer.advance();
        outFile.print("<keyword> " + tokenizer.keyWord()+ " </keyword>\n");
        tokenizer.advance();
        if (!tokenizer.currentType.equals("SYMBOL"))
        {
            tokenizer.index--;
            compileExpression();
            tokenizer.advance();
        }
        // ;
        outFile.print("<symbol> " + tokenizer.symbol() + " </symbol>\n");
    }
    
    public void compileIf()
    {
        // if 
        outFile.print("<keyword> " + tokenizer.keyWord()+ " </keyword>\n");
        tokenizer.advance();
        // ( expression
        outFile.print("<symbol> " + tokenizer.symbol() + " </symbol>\n");
        compileExpression();
        tokenizer.advance();
        outFile.print("<symbol> " + tokenizer.symbol() + " </symbol>\n");
        tokenizer.advance();
        outFile.print("<symbol> " + tokenizer.symbol() + " </symbol>\n");
        // ) { statements }
        outFile.print("<statements>\n");
        compileStatements();
        outFile.print("</statements>\n");
        tokenizer.advance();
        outFile.print("<symbol> " + tokenizer.symbol() + " </symbol>\n");
        //System.out.println(tokenizer.currentToken);
        tokenizer.advance();
        if (tokenizer.currentType.equals("KEYWORD") && tokenizer.keyWord().equals("else"))
        {
            outFile.print("<keyword> " + tokenizer.keyWord()+ " </keyword>\n");
            tokenizer.advance();
            outFile.print("<symbol> " + tokenizer.symbol() + " </symbol>\n");
            outFile.print("<statements>\n");
            compileStatements();
            outFile.print("</statements>\n");
            tokenizer.advance();
            outFile.print("<symbol> " + tokenizer.symbol() + " </symbol>\n");
        }
        else
            tokenizer.index--;
        
    }
    public void compileExpression()
    {
        outFile.print("<expression>\n");
        compileTerm();
        outFile.print("</expression>\n");
    }
    public void compileTerm()
    {
        tokenizer.advance();
        if (tokenizer.currentType.equals("IDENTIFIER")) 
        {
            outFile.print("<term>\n");
            outFile.print("<identifier> " + tokenizer.identifier() + " </identifier>\n");
            tokenizer.advance();
            if (tokenizer.currentType.equals("SYMBOL") && (tokenizer.symbol() == '<' || tokenizer.symbol() == '>' || 
                    tokenizer.symbol() == '+' || tokenizer.symbol() == '-' || tokenizer.symbol() == '|' 
                    || tokenizer.symbol() == '*' ||  tokenizer.symbol() == '&'))
            {
                tokenizer.index--;
                outFile.print("</term>\n");
                compileTerm(); // keyword.readInt()
            }
            else if (tokenizer.currentType.equals("SYMBOL") && tokenizer.symbol() == ',')
            {
                tokenizer.index--;
                outFile.print("</term>\n");
            }
            else
            {
                tokenizer.index--;
                outFile.print("</term>\n");
                compileTerm(); // keyword.readInt()
                
            } 
        }
        else if (tokenizer.currentType.equals("SYMBOL"))
        {
            if (tokenizer.symbol() == '[')
            {
                //IDENTIFIER[ expression ]; => a[i]
                outFile.print("<symbol> " + tokenizer.symbol() + " </symbol>\n");
                compileExpression();
                tokenizer.advance();
                outFile.print("<symbol> " + tokenizer.symbol() + " </symbol>\n");
                //tokenizer.advance();
                //outFile.write("<symbol> " + tokenizer.symbol() + " </symbol>\n");
            }
            else if (tokenizer.symbol() == '.')
            {
                //IDENTIFIER.IDENTIFIER( ExpressionList ); => keyboard.keyPressed();
                outFile.print("<symbol> " + tokenizer.symbol() + " </symbol>\n");
                tokenizer.advance();
                outFile.print("<identifier> " + tokenizer.identifier() + " </identifier>\n");
                tokenizer.advance();
                outFile.print("<symbol> " + tokenizer.symbol() + " </symbol>\n");
                outFile.print("<expressionlist>\n");
                compileExpressionList();
                outFile.print("</expressionlist>\n");
                tokenizer.advance();
                outFile.print("<symbol> " + tokenizer.symbol() + " </symbol>\n");
            }
            else if(tokenizer.symbol() == '(')
            {
                //IDENTIFIER( Expression ); => moveSquare();
                outFile.print("<term>\n");
                outFile.print("<symbol> " + tokenizer.symbol() + " </symbol>\n");
                compileExpression();
                tokenizer.advance();
                outFile.print("<symbol> " + tokenizer.symbol() + " </symbol>\n");
                outFile.print("</term>\n");
                tokenizer.advance();
                if(tokenizer.symbol() != '{' || tokenizer.symbol() != ';')
                {
                    tokenizer.index--;
                    compileTerm();
                }
                else
                    tokenizer.index--;
            }
            else if (tokenizer.symbol() == '=' || tokenizer.symbol() == '/' || tokenizer.symbol() == '<' || tokenizer.symbol() == '>'
                    || tokenizer.symbol() == '+' || tokenizer.symbol() == '-' || tokenizer.symbol() == '|' 
                    || tokenizer.symbol() == '*' ||  tokenizer.symbol() == '&' ||  tokenizer.symbol() == '~') 
            {
                //IDENTIFIER = IDENTIFIER => key = 0
                outFile.print("<symbol> " + tokenizer.symbol() + " </symbol>\n");
                //tokenizer.advance();
                compileTerm();
                //outFile.write("<identifier> " + tokenizer.identifier() + " </identifier>\n");
            }
            else
                tokenizer.index = tokenizer.index - 1;
        }
        else if(tokenizer.currentType.equals("SYMBOL") && tokenizer.symbol() == '~')
        {
            //~ compileTerm() => ~(key = 0) || ~exit
            
            outFile.print("<symbol> " + tokenizer.symbol() + " </symbol>\n");
            
            compileTerm();
        }
        else if (tokenizer.currentType.equals("SYMBOL") && tokenizer.symbol() == '(')
        {
            // ( Expression ) => (key = 0)
            outFile.print("<symbol> " + tokenizer.symbol() + " </symbol>\n");
            compileExpression();
            tokenizer.advance();
            outFile.print("<symbol> " + tokenizer.symbol() + " </symbol>\n");
        }
        else if (tokenizer.currentType.equals("KEYWORD"))
        {
            outFile.print("<term>\n");
            outFile.print("<keyword> " + tokenizer.keyWord() + " </keyword>\n");
            outFile.print("</term>\n");
        }
        else if (tokenizer.currentType.equals("INT_CONST"))
        {
            outFile.print("<term>\n");
            outFile.print("<integerConstant> " + tokenizer.intVal() + " </integerConstant>\n");
            outFile.print("</term>\n");
        }
        else if (tokenizer.currentType.equals("STRING_CONST")) 
        {
            outFile.print("<term>\n");
            outFile.print("<stringConstant> " + tokenizer.stringVal()+ " </stringConstant>\n");
            outFile.print("</term>\n");
        }
        else 
            tokenizer.index--;
    }
   
    public void compileExpressionList()
    {
        
        tokenizer.advance();
        if (tokenizer.currentType.equals("IDENTIFIER"))
        {
            tokenizer.index = tokenizer.index - 1;
            compileExpression();
            tokenizer.advance();
            if (tokenizer.currentType.equals("SYMBOL") && tokenizer.symbol() == ',')
            {
                while (tokenizer.symbol() == ',')
                {
                    outFile.print("<symbol> " + tokenizer.symbol() + " </symbol>\n");
                    compileExpression();
                    tokenizer.advance();
                }
                tokenizer.index = tokenizer.index - 1;
            }
            else
                tokenizer.index = tokenizer.index - 1;
        }
        else if (tokenizer.currentType.equals("SYMBOL") && tokenizer.symbol() == '(')
        {
            outFile.print("<symbol> " + tokenizer.symbol() + " </symbol>\n");
            compileExpression();
            tokenizer.advance();
            outFile.print("<symbol> " + tokenizer.symbol() + " </symbol>\n");
            tokenizer.advance();
            if(tokenizer.currentType.equals("SYMBOL") && tokenizer.symbol() != ',')
            {
                tokenizer.index--;
                compileTerm();
                tokenizer.advance();
            }
            outFile.print("<symbol> " + tokenizer.symbol() + " </symbol>\n");
            compileExpressionList();
            
        }
        else if (tokenizer.currentType.equals("STRING_CONST") || tokenizer.currentType.equals("KEYWORD"))
        {
            tokenizer.index = tokenizer.index - 1;
            compileExpression();
        }
        else if (tokenizer.currentType.equals("INT_CONST"))
        {
            outFile.print("<term>\n");
            outFile.print("<integerConstant> " + tokenizer.intVal() + " </integerConstant>\n");
            outFile.print("</term>\n");
            tokenizer.advance();
            if (tokenizer.currentType.equals("SYMBOL") && tokenizer.symbol() != ',')
            {
                tokenizer.index--;
            }
            else
            {
                outFile.print("<symbol> " + tokenizer.symbol() + " </symbol>\n");
                compileExpressionList();
            }
        }
        else
            tokenizer.index = tokenizer.index - 1;
    }
    
    public void close()
    {
        outFile.close();
    }
}
