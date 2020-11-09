package com.jimo.tutorial.inter;

import com.jimo.tutorial.lexer.Token;
import com.jimo.tutorial.symbols.Type;

/**
 * 一元表达式
 *
 * @author jimo
 * @date 2020/11/9 8:41
 * @since 1.0.0
 */
public class Unary extends Expr {
    public Expr e;

    Unary(Token op, Expr e) {
        super(op, null);
        type = Type.max(Type.Int, e.type);
        if (type == null) {
            throw new RuntimeException("type error");
        }
    }

    @Override
    public Expr gen() {
        return new Unary(op, e.reduce());
    }

    @Override
    public String toString() {
        return op.toString() + " " + e.toString();
    }
}
