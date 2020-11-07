package com.jimo.tutorial.lexer;

/**
 * number
 *
 * @author jimo
 * @date 2020/11/7 15:22
 * @since 1.0.0
 */
public class Num extends Token {

    public final int value;

    public Num(int v) {
        super(Tag.NUM);
        this.value = v;
    }

    @Override
    public String toString() {
        return "" + value;
    }
}
