package org.quan;
import java.util.ArrayList;
public class Parser {
    ArrayList<Token> tokens;
    public Parser(ArrayList<Token> tokens) {
        this.tokens = tokens;

        parseProcedureAndFunctionBegin();      // proc & func
        parseLocalProcedureAndFunctionBegin(); // local proc & func (look at :)
        parseProcedureAndFunctionName();       // proc & func names
        parseCodeBlockName();                  // next ->> proc or func

        // 3: proc, func, codeBlockStart (next ->> proc or func)

        parseCodeBlockCall();

        for (Token token : tokens) {
            System.out.println(token.type.toString()+": "+token.word);
        }
        //
    }
    public void parseProcedureAndFunctionBegin() {
        for (Token token : tokens) {
            if (token.type == TokenTypes.word) {
                if (token.word.equals("proc"))
                    token.type = TokenTypes.procedureBegin;
                else
                if (token.word.equals("func"))
                    token.type = TokenTypes.functionBegin;
            }
        }
        //
    }
    public void parseLocalProcedureAndFunctionBegin() {
        for (int i = 0; i+1 < tokens.size(); i++) {
            Token currentToken = tokens.get(i);
            if (tokens.get(i+1).type == TokenTypes.codeBlockBegin) {
                if (currentToken.type == TokenTypes.procedureBegin)
                    currentToken.type = TokenTypes.localProcedureBegin;
                else
                if (currentToken.type == TokenTypes.functionBegin)
                    currentToken.type = TokenTypes.localFunctionBegin;
            }
        }
        //
    }
    public void parseProcedureAndFunctionName() {
        // +1 check name +2 check parameters or :
        for (int i = 0; i+2 < tokens.size(); i++) {
            TokenTypes currentTokenType = tokens.get(i).type;
            Token nextToken = tokens.get(i+1);
            if (nextToken.type == TokenTypes.word) {
                if (currentTokenType == TokenTypes.procedureBegin)
                    nextToken.type = TokenTypes.procedureName;
                else
                if (currentTokenType == TokenTypes.functionBegin)
                    nextToken.type = TokenTypes.functionName;
            }
        }
        //
    }
    public void parseCodeBlockName() {
        for (int i = 0; i+1 < tokens.size(); i++) {
            Token currentToken = tokens.get(i);
            if (tokens.get(i+1).type == TokenTypes.codeBlockBegin && currentToken.type == TokenTypes.word)
                currentToken.type = TokenTypes.codeBlockName;
        }
        //
    }
    public void parseCodeBlockCall() {
        for (int i = 0; i+1 < tokens.size(); i++) {
            Token currentToken = tokens.get(i);
            if (tokens.get(i+1).type == TokenTypes.blockBegin && currentToken.type == TokenTypes.word)
                currentToken.type = TokenTypes.codeBlockCall;
        }
        //
    }
}