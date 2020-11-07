package com.jimo.tutorial.lexer;

/**
 * 浮点数词法单元
 *
 * @author jimo
 * @date 2020/11/7 15:29
 * @since 1.0.0
 */
public class Real extends Token {
    public final float value;

    public Real(float v) {
        super(Tag.REAL);
        value = v;
    }

    @Override
    public String toString() {
        return "" + value;
    }
}
