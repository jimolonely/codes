package com.jimo.tutorial.inter;

import com.jimo.tutorial.lexer.Token;
import com.jimo.tutorial.symbols.Type;

/**
 * 算数二目运算符
 *
 * @author jimo
 * @date 2020/11/9 8:38
 * @since 1.0.0
 */
public class Arith extends Expr {
    public Expr e1, e2;

    Arith(Token op, Expr e1, Expr e2) {
        super(op, null);
        this.e1 = e1;
        this.e2 = e2;
        type = Type.max(e1.type, e2.type);
        if (type == null) {
            throw new RuntimeException("type error");
        }
    }

    @Override
    public Expr gen() {
        return new Arith(op, e1.reduce(), e2.reduce());
    }

    @Override
    public String toString() {
        return e1.toString() + " " + op.toString() + " " + e2.toString();
    }
}
