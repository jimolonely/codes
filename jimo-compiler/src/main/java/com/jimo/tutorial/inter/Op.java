package com.jimo.tutorial.inter;

import com.jimo.tutorial.lexer.Token;
import com.jimo.tutorial.symbols.Type;

/**
 * 操作符
 *
 * @author jimo
 * @date 2020/11/7 19:01
 * @since 1.0.0
 */
public class Op extends Expr {
    public Op(Token op, Type type) {
        super(op, type);
    }

    @Override
    public Expr reduce() {
        final Expr x = gen();
        final Temp t = new Temp(type);
        emit(t.toString() + " = " + x.toString());
        return t;
    }
}
