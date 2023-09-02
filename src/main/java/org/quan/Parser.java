package org.quan;
import java.util.ArrayList;
public class Parser {
    public Parser(ArrayList<Token> tokens) {
        for (Token token : tokens) {
            System.out.println(token.type+": "+token.word);
        }
        //
    }
}