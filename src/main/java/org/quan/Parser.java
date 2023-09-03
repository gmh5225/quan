package org.quan;
import java.util.ArrayList;
public class Parser {
    ArrayList<Token> tokens;
    public Parser(ArrayList<Token> tokens) {
        this.tokens = tokens;

        parseProcedureAndFunctionStart();      // proc & func
        parseCodeBlockStartAndEnd();           // : & end
        parseLocalProcedureAndFunctionStart(); // local proc & func (look at :)
        parseProcedureAndFunctionName();       // proc & func names

        // 3: proc, func, codeBlockStart (next proc or func)



        for (Token token : tokens) {
            System.out.println(token.type.toString()+": "+token.word);
        }
        //
    }
    public void parseProcedureAndFunctionStart() {
        for (Token token : tokens) {
            if (token.type == TokenTypes.word) {
                if (token.word.equals("proc"))
                    token.type = TokenTypes.procedureStart;
                else
                if (token.word.equals("func"))
                    token.type = TokenTypes.functionStart;
            }
        }
        //
    }
    public void parseCodeBlockStartAndEnd() {
        for (Token token : tokens) {
            if (token.type == TokenTypes.singleChar && token.word.equals(":"))
                token.type = TokenTypes.codeBlockStart;
            else
            if (token.type == TokenTypes.word && token.word.equals("end"))
                token.type = TokenTypes.codeBlockEnd;
        }
        //
    }
    public void parseLocalProcedureAndFunctionStart() {
        for (int i = 0; i+1 < tokens.size(); i++) {
            Token currentToken = tokens.get(i);
            if (tokens.get(i+1).type == TokenTypes.codeBlockStart) {
                if (currentToken.type == TokenTypes.procedureStart)
                    currentToken.type = TokenTypes.localProcedureStart;
                else
                if (currentToken.type == TokenTypes.functionStart)
                    currentToken.type = TokenTypes.localProcedureStart;
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
                if (currentTokenType == TokenTypes.procedureStart)
                    nextToken.type = TokenTypes.procedureName;
                else
                if (currentTokenType == TokenTypes.functionStart)
                    nextToken.type = TokenTypes.functionName;
            }
        }
        //
    }
}