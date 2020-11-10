package com.jimo.tutorial.inter;

import com.jimo.tutorial.lexer.Token;

/**
 * Not
 *
 * @author jimo
 * @date 2020/11/10 9:03
 * @since 1.0.0
 */
public class Not extends Logical {
    public Not(Token op, Expr e) {
        super(op, e, e);
    }

    @Override
    public void jumping(int to, int from) {
        e1.jumping(from, to);
    }

    @Override
    public String toString() {
        return op.toString() + " " + e1.toString();
    }
}
