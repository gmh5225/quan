package org.quan;
public enum TokenType {
// Tokenizer types
    // basic
        word,
        number,
        floatNumber,
        endline,
    // quote
        singleQuote,
        doubleQuote,
    // parameter
        parameterBlockBegin,
        parameterBlockEnd,
    // operator
        singleMathOperator,
        doubleMathOperator,
        singleLogicalOperator,
        doubleLogicalOperator,
// Parser types
    // proc & func & codeBlock | block
        // procedure
            procedureBegin,
            procedureName,
            localProcedure,
            localProcedureBegin,
            procedure,
        // function
            functionBegin,
            functionName,
            localFunction,
            localFunctionBegin,
            function,
        // codeBlock
            codeBlockBegin,
            codeBlockEnd,
            codeBlockName,
            codeBlockCall,
        // block
            blockBegin,
            blockEnd,
    //
        varriableName,
        parameterVarriableName,
    //
        returnValue
    // assignment
    // return
}