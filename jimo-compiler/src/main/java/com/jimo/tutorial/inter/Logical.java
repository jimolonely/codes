package com.jimo.tutorial.inter;

import com.jimo.tutorial.lexer.Token;
import com.jimo.tutorial.symbols.Type;

/**
 * 逻辑运算符
 *
 * @author jimo
 * @date 2020/11/10 8:45
 * @since 1.0.0
 */
public class Logical extends Expr {

    public Expr e1, e2;

    Logical(Token op, Expr e1, Expr e2) {
        super(op, null);
        type = check(e1.type, e2.type);
        if (type == null) {
            throw new RuntimeException("type error");
        }
    }

    private Type check(Type p1, Type p2) {
        if (p1 == Type.Bool && p2 == Type.Bool) {
            return Type.Bool;
        } else {
            return null;
        }
    }

    @Override
    public Expr gen() {
        final int f = newLabel();
        final int a = newLabel();
        final Temp temp = new Temp(type);
        this.jumping(0, f);
        emit(temp.toString() + " = true");
        emit("goto L" + a);
        emitLabel(f);
        emit(temp.toString() + " = false");
        emitLabel(a);
        return temp;
    }

    @Override
    public String toString() {
        return e1.toString() + " " + op.toString() + " " + e2.toString();
    }
}
