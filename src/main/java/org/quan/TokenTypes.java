package org.quan;
public enum TokenTypes {
// Tokenizer types
    // basic
        word,
        number,
        floatNumber,
        singleChar,
        endline,
    // quote
        singleQuote,
        doubleQuote,
    // operator
        singleMathOperator,
        doubleMathOperator,
        singleLogicalOperator,
        doubleLogicalOperator,
// Parser types
    // func & proc & codeBlock | block & parameter
        procedureStart,
        procedureName,
        localProcedure,
        localProcedureStart,
        procedure,
        functionStart,
        functionName,
        localFunction,
        localFunctionStart,
        function,
        codeBlockStart,
        codeBlockEnd,
        blockStart,
        blockEnd,
        parameter
}
