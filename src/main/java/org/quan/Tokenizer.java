package org.quan;
import java.util.ArrayList;
public class Tokenizer {
    ArrayList<Token> tokens = new ArrayList<>();
    int counter = 0;
    String input;
    int inputLength;
    public Tokenizer(String input) {
        System.out.println("quan: Tokenizer: Input: ["+input+"]");
        // read tokens
        this.input = input;
        this.inputLength = input.length();
        while (counter < inputLength) {
            if ( !deleteComments() )
            if ( !addToken(getSingleQuotes(), TokenTypes.singleQuote) )
            if ( !addToken(getDoubleQuotes(), TokenTypes.doubleQuote) )
            if ( !addToken(getFloatNumber(), TokenTypes.floatNumber) )
            if ( !addToken(getNumber(), TokenTypes.number) )
            if ( !addToken(getWord(), TokenTypes.word) )
            if ( !addToken(getLogicalOperator(), TokenTypes.logicalOperator) )
            if ( !addToken(getMathOperator(), TokenTypes.mathOperator) ) {
                char c = input.charAt(counter);
                // + save needed spaces
                if (c == '\u001F' && tokens.isEmpty()) counter++; else
                {
                    if (c == '\u001F') addToken(c+"", TokenTypes.endline); else
                    if ((c == ' ' || c == '\t') && counter > 1 && counter+1 < inputLength) {
                        if ( Character.isLetter(input.charAt(counter-1)) && Character.isLetter(input.charAt(counter+1)) )
                            addToken(c + "", TokenTypes.space);
                    }
                    else addToken(c+"", TokenTypes.singleChar);
                    counter++;
                }
            }
            //
        }
        System.out.println("quan: Tokenizer: Tokens size: "+tokens.size());
    }
    private boolean addToken(String token, TokenTypes type) {
        if (!token.isEmpty() /*&& !type.isEmpty()*/) {
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
                if (counter+2 >= inputLength) new Error("quan: Tokenizer: Double comment was not closed at the end");
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
        char currentChar = input.charAt(counter);
        if (currentChar == '\'') {
            if (counter+1 >= inputLength) new Error("quan: Tokenizer: Single quote was not closed at the end");

            StringBuilder result = new StringBuilder();
            boolean openSingleComment = false;

            while (counter < inputLength) {
                currentChar = input.charAt(counter);
                result.append(currentChar);
                if (currentChar == '\'') {
                    if (openSingleComment) {
                        counter++;
                        return result.toString();
                    } else openSingleComment = true;
                }
                counter++;
            }
            if (openSingleComment) new Error("quan: Tokenizer: Single quote was not closed at the end");
        }
        return "";
    }
    private String getDoubleQuotes() { // delete comments
        char currentChar = input.charAt(counter);
        if (currentChar == '\"') {
            if (counter+1 >= inputLength) new Error("quan: Tokenizer: Double quote was not closed at the end");

            StringBuilder result = new StringBuilder();
            boolean openDoubleComment = false;

            while (counter < inputLength) {
                currentChar = input.charAt(counter);
                result.append(currentChar);
                if (currentChar == '\"') {
                    if (openDoubleComment) {
                        counter++;
                        return result.toString();
                    } else openDoubleComment = true;
                }
                counter++;
            }
            if (openDoubleComment) new Error("quan: Tokenizer: Double quote was not closed at the end");
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
            char nextChar = (counter+1 < inputLength) ? input.charAt(counter+1) : '\0';

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

        if (!result.isEmpty()) counter+=2;
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

        if (!result.isEmpty()) counter+=2;
        return result;
    }
}