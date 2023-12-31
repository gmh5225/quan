package org.quan;
import org.quan.log.Log;
import org.quan.log.LogType;

import java.util.ArrayList;
public class Tokenizer {
    ArrayList<Token> tokens = new ArrayList<>();
    int counter = 0;
    String input;
    int inputLength;
    public Tokenizer(String input) {
        new Log(LogType.info,"[Tokenizer]: Input: ["+input+"]");
        // read tokens
        this.input = input;
        this.inputLength = input.length();
        while (counter < inputLength) {
            if ( !deleteComments() )
            if ( !addToken(getSingleQuotes(), TokenType.singleQuote) )
            if ( !addToken(getDoubleQuotes(), TokenType.doubleQuote) )
            if ( !addToken(getFloatNumber(), TokenType.floatNumber) )
            if ( !addToken(getNumber(), TokenType.number) )
            if ( !addToken(getWord(), TokenType.word) )
            if ( !addToken(getLogicalOperator(), TokenType.doubleLogicalOperator) )
            if ( !addToken(getMathOperator(), TokenType.doubleMathOperator) ) {
                char c = input.charAt(counter);
                //
                if (c == '\u001F' && tokens.isEmpty()) counter++; else
                {
                    if (c == '\u001F' && tokens.get(tokens.size()-1).type != TokenType.endline)
                        addToken(String.valueOf(c), TokenType.endline);
                    else
                    if (c == '+' || c == '-' || c == '*' || c == '/' || c == '=' || c == '%')
                        addToken(String.valueOf(c), TokenType.singleMathOperator);
                    else
                    if (c == '?' || c == '!')
                        addToken(String.valueOf(c), TokenType.singleLogicalOperator);
                    else
                    if (c == '(')
                        addToken(String.valueOf(c), TokenType.parameterBlockBegin);
                    else
                    if (c == ')')
                        addToken(String.valueOf(c), TokenType.parameterBlockEnd);
                    else
                    if (c == '['|| c == '{') // TO:DO:
                        addToken(String.valueOf(c), TokenType.blockBegin);
                    else
                    if (c == ']'|| c == '}') // TO:DO:
                        addToken(String.valueOf(c), TokenType.blockEnd);
                    else
                    if (c == ':')
                        addToken(String.valueOf(c), TokenType.codeBlockBegin);
                    counter++;
                }
            }
            //
        }
        new Log(LogType.info,"[Tokenizer]: Tokens size: "+tokens.size());
    }
    private boolean addToken(String token, TokenType type) {
        if (!token.isEmpty()) {
            //
            if (type == TokenType.word && token.equals("end"))
                type = TokenType.codeBlockEnd;
            if (type == TokenType.word && token.equals("return"))
                type = TokenType.returnValue;
            //
            tokens.add(new Token(token, type));
            return true;
        }
        return false;
    }
    private boolean deleteComments() {
        if (input.charAt(counter) == '\\') {
            if (input.charAt(counter+1) == '\\') { // single comment
                counter++;
                while (counter < inputLength) {
                    counter++;
                    if (input.charAt(counter) == '\u001F') {
                        return true;
                    }
                }
            } else { // double comment
                if (counter+2 >= inputLength) new Log(LogType.error,"[Tokenizer]: Double comment was not closed at the end");
                counter++;
                while (counter < inputLength) {
                    counter++;
                    if (input.charAt(counter) == '\\') {
                        counter+=2;
                        return true;
                    }
                }
                //
            }
        }
        return false;
    }
    private String getSingleQuotes() { // delete comments
        if (input.charAt(counter) == '\'') {
            if (counter+1 >= inputLength) new Log(LogType.error,"[Tokenizer]: Single quote was not closed at the end");

            StringBuilder result = new StringBuilder();
            boolean openSingleComment = false;

            while (counter < inputLength) {
                char currentChar = input.charAt(counter);
                result.append(currentChar);
                if (currentChar == '\'') {
                    if (openSingleComment) {
                        counter++;
                        return result.toString();
                    } else openSingleComment = true;
                }
                counter++;
            }
            if (openSingleComment) new Log(LogType.error,"[Tokenizer]: Single quote was not closed at the end");
        }
        return "";
    }
    private String getDoubleQuotes() { // delete comments
        if (input.charAt(counter) == '\"') {
            if (counter+1 >= inputLength) new Log(LogType.error,"[Tokenizer]: Double quote was not closed at the end");

            StringBuilder result = new StringBuilder();
            boolean openDoubleComment = false;

            while (counter < inputLength) {
                char currentChar = input.charAt(counter);
                result.append(currentChar);
                if (currentChar == '\"') {
                    if (openDoubleComment) {
                        counter++;
                        return result.toString();
                    } else openDoubleComment = true;
                }
                counter++;
            }
            if (openDoubleComment) new Log(LogType.error,"[Tokenizer]: Double quote was not closed at the end");
        }
        return "";
    }
    private String getFloatNumber() {
        StringBuilder result = new StringBuilder();
        boolean dot = false;

        int counterBuffer = counter;
        while (counterBuffer < inputLength) {
            char currentChar = input.charAt(counterBuffer);
            char nextChar = (counterBuffer+1 < inputLength) ? input.charAt(counterBuffer+1) : '\0';

            if (Character.isDigit(currentChar)) {
                result.append(currentChar);
                counterBuffer++;
            } else if ((currentChar == '.' || currentChar == ',') && !dot && Character.isDigit(nextChar)) {
                dot = true;
                result.append(currentChar);
                counterBuffer++;
            } else break;
        }

        if (dot) {
            counter = counterBuffer;
            return result.toString();
        }
        return "";
    }
    private String getNumber() {
        StringBuilder result = new StringBuilder();

        while (counter < inputLength) {
            char currentChar = input.charAt(counter);
            if (Character.isDigit(currentChar)) {
                result.append(currentChar);
                counter++;
            } else break;
        }

        return result.toString();
    }
    private String getWord() {
        StringBuilder result = new StringBuilder();

        while (counter < inputLength) {
            char currentChar = input.charAt(counter);
            char nextChar = (counter+1 < inputLength) ? input.charAt(counter+1) : '\0';

            if (Character.isLetterOrDigit(currentChar) ||
                    (currentChar == '_' && !result.isEmpty() && Character.isLetterOrDigit(nextChar))) {
                result.append(currentChar);
                counter++;
            } else break;
        }

        return result.toString();
    }
    private String getMathOperator() {
        if (counter+1 >= inputLength)
            return "";

        String result;
        char nextChar = input.charAt(counter+1);
        switch (input.charAt(counter)) {
            case '+' -> result = nextChar == '=' ? "+=" : (nextChar == '+' ? "++" : "");
            case '-' -> result = nextChar == '=' ? "-=" : (nextChar == '-' ? "--" : "");
            case '*' -> result = nextChar == '=' ? "*=" : (nextChar == '*' ? "**" : "");
            case '/' -> result = nextChar == '=' ? "/=" : (nextChar == '/' ? "//" : "");
            default -> {
                return "";
            }
        }

        if (!result.isEmpty())
            counter+=2;
        return result;
    }
    private String getLogicalOperator() {
        if (counter+1 >= inputLength)
            return "";

        String result;
        char nextChar = input.charAt(counter+1);
        switch (input.charAt(counter)) {
            case '!' -> result = nextChar == '=' ? "!=" : "";
            case '=' -> result = nextChar == '=' ? "==" : "";
            case '&' -> result = nextChar == '&' ? "&&" : "";
            case '|' -> result = nextChar == '|' ? "||" : "";
            default -> {
                return "";
            }
        }

        if (!result.isEmpty())
            counter+=2;
        return result;
    }
}