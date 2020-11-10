package com.jimo.tutorial.inter;

import com.jimo.tutorial.lexer.Token;

/**
 * And
 *
 * @author jimo
 * @date 2020/11/10 9:00
 * @since 1.0.0
 */
public class And extends Logical {
    public And(Token op, Expr e1, Expr e2) {
        super(op, e1, e2);
    }

    @Override
    public void jumping(int to, int from) {
        int label = from != 0 ? from : newLabel();
        e1.jumping(0, label);
        e2.jumping(to, from);
        if (from != 0) {
            emitLabel(label);
        }
    }
}
