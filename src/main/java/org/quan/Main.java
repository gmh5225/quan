package org.quan;

import org.quan.log.Log;
import org.quan.log.LogType;

public class Main {
    public static void main(String[] args) {
        if (args.length == 0)
            new Log(LogType.warning,"[Main]: Launch parameters weren't passed", true);

        String inputFileString = File.getFileString(args[0]);
        if (inputFileString.trim().isEmpty())
            new Log(LogType.warning,"[Main]: The uploaded file was empty", true);
        //
        Tokenizer tokenizer = new Tokenizer(inputFileString);
        StringBuilder result = new StringBuilder("quan: Tokenizer: Output: [");
        for (Token token : tokenizer.tokens) {
            result.append(token.word);
        }
        System.out.println(result.append("]").toString());
        //
        Parser parser = new Parser(tokenizer.tokens);
    }
}