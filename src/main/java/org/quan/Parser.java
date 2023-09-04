package org.quan;
import org.quan.log.Log;
import org.quan.log.LogType;

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

        parseVarriableName();
        parseParameterVarriableName();
        parseCodeBlock();

        // output
        System.out.println("###");
        for (int i = 0; i < tokens.size(); i++) {
            Token token = tokens.get(i);
            System.out.println(i+":"+token.type.toString()+": "+token.word);
        }
        //
    }
    public void parseProcedureAndFunctionBegin() {
        for (Token token : tokens) {
            if (token.type == TokenType.word) {
                if (token.word.equals("proc"))
                    token.type = TokenType.procedureBegin;
                else
                if (token.word.equals("func"))
                    token.type = TokenType.functionBegin;
            }
        }
        //
    }
    public void parseLocalProcedureAndFunctionBegin() {
        for (int i = 0; i+1 < tokens.size(); i++) {
            Token currentToken = tokens.get(i);
            if (tokens.get(i+1).type == TokenType.codeBlockBegin) {
                if (currentToken.type == TokenType.procedureBegin)
                    currentToken.type = TokenType.localProcedureBegin;
                else
                if (currentToken.type == TokenType.functionBegin)
                    currentToken.type = TokenType.localFunctionBegin;
            }
        }
        //
    }
    public void parseProcedureAndFunctionName() {
        // +1= check name | +2= check parameters or :
        for (int i = 0; i+2 < tokens.size(); i++) {
            TokenType currentTokenType = tokens.get(i).type;
            Token nextToken = tokens.get(i+1);
            if (nextToken.type == TokenType.word) {
                if (currentTokenType == TokenType.procedureBegin)
                    nextToken.type = TokenType.procedureName;
                else
                if (currentTokenType == TokenType.functionBegin)
                    nextToken.type = TokenType.functionName;
            }
        }
        //
    }
    public void parseCodeBlockName() {
        for (int i = 0; i+1 < tokens.size(); i++) {
            Token currentToken = tokens.get(i);
            if (tokens.get(i+1).type == TokenType.codeBlockBegin && currentToken.type == TokenType.word)
                currentToken.type = TokenType.codeBlockName;
        }
        //
    }
    public void parseCodeBlockCall() {
        for (int i = 0; i+1 < tokens.size(); i++) {
            Token currentToken = tokens.get(i);
            if (tokens.get(i+1).type == TokenType.parameterBlockBegin && currentToken.type == TokenType.word)
                currentToken.type = TokenType.codeBlockCall;
        }
        //
    }
    public void parseVarriableName() {
        for (int i = 0; i+1 < tokens.size(); i++) {
            Token currentToken = tokens.get(i);
            if (currentToken.type == TokenType.word)
                currentToken.type = TokenType.varriableName;
        }
        //
    }
    public void parseParameterVarriableName() {
        // 0= ) | 2= 1 parameter | 3= ( | 4= name
        boolean globalParameterOpen = false;
        for (int i = tokens.size()-1; i > 1; i--) {
            Token currentToken = tokens.get(i);
            if (currentToken.type == TokenType.parameterBlockEnd) {
                TokenType backTokenTyoe = tokens.get(i-1).type;
                if (backTokenTyoe == TokenType.procedureName ||
                    backTokenTyoe == TokenType.functionName ||
                    backTokenTyoe == TokenType.codeBlockName)
                    new Log(LogType.error,"[Parser]: The left parameter bracket wasn't closed");
                else
                if (backTokenTyoe == TokenType.varriableName) { // read proc/func/blockCode parameters
                    boolean parameterOpen = false, codeBlockCall = false;
                    for (int j = i-1; j > 1; j--) {
                        if (tokens.get(j).type == TokenType.procedureName ||
                            tokens.get(j).type == TokenType.functionName ||
                            tokens.get(j).type == TokenType.codeBlockName)
                            break;
                        else
                        if (tokens.get(j).type == TokenType.parameterBlockBegin &&
                            tokens.get(j-1).type == TokenType.codeBlockCall) {
                            codeBlockCall = true;
                            break;
                        } else
                        if (tokens.get(j).type == TokenType.parameterBlockBegin &&
                            tokens.get(j-1).type != TokenType.codeBlockCall) {
                            parameterOpen = true;
                            break;
                        }
                    }
                    if (!parameterOpen && !codeBlockCall)
                        new Log(LogType.error,"[Parser]: The left parameter bracket wasn't closed");
                    else if (parameterOpen) globalParameterOpen = true;
                }
                //
            }
            else
            if (currentToken.type == TokenType.parameterBlockBegin && globalParameterOpen)
                globalParameterOpen = false;
            else
            if (globalParameterOpen)
                currentToken.type = TokenType.parameterVarriableName;
        }
        //
    }
    public void parseCodeBlock() {
        for (int i = 0; i+1 < tokens.size(); i++) {
            Token currentToken = tokens.get(i);
            //if (tokens.get(i+1).type == TokenTypes.blockBegin && currentToken.type == TokenTypes.word)
            //    currentToken.type = TokenTypes.codeBlockCall;
        }
        //
    }
}