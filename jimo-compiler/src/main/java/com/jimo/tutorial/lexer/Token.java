package com.jimo.tutorial.lexer;

/**
 * token
 *
 * @author jimo
 * @date 2020/11/7 15:20
 * @since 1.0.0
 */
public class Token {

    public final int tag;

    public Token(int tag) {
        this.tag = tag;
    }

    @Override
    public String toString() {
        return "" + (char) tag;
    }
}
