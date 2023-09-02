package org.quan;
class Token {
    public String word;
    public TokenTypes type;
    public Token(String word, TokenTypes type) {
        this.word = word;
        this.type = type;
    }
}