package com.jimo.tutorial.inter;

import com.jimo.tutorial.lexer.Token;

/**
 * Or
 *
 * @author jimo
 * @date 2020/11/10 8:53
 * @since 1.0.0
 */
public class Or extends Logical {

    public Or(Token op, Expr e1, Expr e2) {
        super(op, e1, e2);
    }

    @Override
    public void jumping(int to, int from) {
        int label = to != 0 ? to : newLabel();
        e1.jumping(label, 0);
        e2.jumping(to, from);
        if (to == 0) {
            emitLabel(label);
        }
    }
}
