package org.quan;
public enum TokenTypes {
    // Tokenizer types
    word,
    number,
    floatNumber,
    singleQuote,
    doubleQuote,
    singleChar,
    space,
    endline,
    mathOperator,
    logicalOperator,
    // Parser types
    procedure,
    procedureName,
    function,
    functionName,
    codeBlock, // next func or proc
    parameter  // func or proc or codeBlock parameter
}
