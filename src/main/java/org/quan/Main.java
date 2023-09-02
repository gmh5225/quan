package org.quan;
public class Main {
    public static void main(String[] args) {
        Tokenizer tokenizer = new Tokenizer(File.getFileString("release/main.quan"));
        StringBuilder result = new StringBuilder("quan: Tokenizer: Output: [");
        for (Token token : tokenizer.tokens) {
            result.append(token.word);
        }
        System.out.println(result.append("]").toString());
        //
        Parser parser = new Parser(tokenizer.tokens);
    }
}