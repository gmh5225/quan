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
            if ( !addToken(getQuotes(), "quote") )
            if ( !addToken(getNumber(), "number") )
            if ( !addToken(getWord(), "word") )
            if ( !addToken(getDoubleChar(), "doubleChar") ) {
                char c = input.charAt(counter);
                // + save needed spaces
                if (c == '\u001F' && tokens.isEmpty()) counter++; else
                {
                    if (c == '\u001F') addToken(c+"", "endline"); else
                    if ((c == ' ' || c == '\t') && counter > 1 && counter+1 < inputLength) {
                        if ( Character.isLetter(input.charAt(counter-1)) && Character.isLetter(input.charAt(counter+1)) )
                            addToken(c + "", "space");
                    }
                    else addToken(c+"", "char");
                    counter++;
                }
            }
            //
        }
        System.out.println("quan: Tokenizer: Tokens size: "+tokens.size());
    }
    private boolean addToken(String token, String type) {
        if (!token.isEmpty() && !type.isEmpty()) {
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
    private String getQuotes() { // delete comments
        char currentChar = input.charAt(counter);
        if (currentChar == '\"' || currentChar == '\'') {
            if (counter+1 >= inputLength) {
                if (currentChar == '\"') new Error("quan: Tokenizer: Double quote was not closed at the end");
                if (currentChar == '\'') new Error("quan: Tokenizer: Single quote was not closed at the end");
            }

            StringBuilder result = new StringBuilder();
            int openSingleComment = -1;
            int openDoubleComment = -1;

            while (counter < inputLength)
            {
                char token = input.charAt(counter);
                if (token == '\"') {
                    if (openDoubleComment != -1) {
                        result.append(token);
                        counter++;
                        return result.toString();
                    } else if(openSingleComment == -1) openDoubleComment = 1;
                } else
                if (token == '\'') {
                    if (openSingleComment != -1) {
                        result.append(token);
                        counter++;
                        return result.toString();
                    } else if (openDoubleComment == -1) openSingleComment = 1;
                }
                result.append(token);
                counter++;
            }
            if (openSingleComment != -1)  new Error("quan: Tokenizer: Single quote was not closed at the end");
            else
            if (openDoubleComment != -1)  new Error("quan: Tokenizer: Double quote was not closed at the end");
        }
        return "";
    }
    private String getNumber() {
        StringBuilder result = new StringBuilder();
        boolean dot = false;

        while (counter < inputLength) {
            char currentChar = input.charAt(counter);
            char nextChar = (counter+1 < inputLength) ? input.charAt(counter+1) : '\0';

            if (Character.isDigit(currentChar)) {
                result.append(currentChar);
                counter++;
            } else if ((currentChar == '.' || currentChar == ',') && !dot && Character.isDigit(nextChar)) {
                dot = true;
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
    private String getDoubleChar() {
        if (counter+1 >= inputLength)
            return "";

        String result;
        char nextChar = input.charAt(counter+1);
        switch (input.charAt(counter)) {
            case '!' -> result = nextChar == '=' ? "!=" : "";
            case '=' -> result = nextChar == '=' ? "==" : "";
            case '+' -> result = nextChar == '=' ? "+=" : (nextChar == '+' ? "++" : "");
            case '-' -> result = nextChar == '=' ? "-=" : (nextChar == '-' ? "--" : "");
            case '*' -> result = nextChar == '=' ? "*=" : (nextChar == '*' ? "**" : "");
            case '/' -> result = nextChar == '=' ? "/=" : (nextChar == '/' ? "//" : "");
            case '&' -> result = nextChar == '&' ? "&&" : "";
            case '|' -> result = nextChar == '|' ? "||" : "";
            case '\\' -> result = nextChar == '\\' ? "\\\\" : "";
            default -> {
                return "";
            }
        }

        if (!result.isEmpty())
            counter+=2;
        return result;
    }
}