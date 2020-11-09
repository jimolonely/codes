package com.jimo.tutorial.inter;

import com.jimo.tutorial.lexer.Token;
import com.jimo.tutorial.symbols.Type;

/**
 * 表达式构造的父类, op 和 type标识运算符和类型
 *
 * @author jimo
 * @date 2020/11/7 17:50
 * @since 1.0.0
 */
public class Expr extends Node {
    public Token op;
    public Type type;

    Expr(Token op, Type type) {
        this.op = op;
        this.type = type;
    }

    /**
     * 返回一个项，可以成为一个三地址指令的右部，子类通常会重写
     */
    public Expr gen() {
        return this;
    }

    public Expr reduce() {
        return this;
    }

    public void emitJumps(String test, int to, int from) {
        if (to != 0 && from != 0) {
            emit("if " + test + " goto L" + to);
            emit("goto L" + from);
        } else if (to != 0) {
            emit("if " + test + " goto L" + to);
        } else if (from != 0) {
            emit("if false " + test + " goto L" + from);
        }
        // 否则没有指令
    }

    public void jumping(int to, int from) {
        emitJumps(toString(), to, from);
    }

    @Override
    public String toString() {
        return op.toString();
    }
}
