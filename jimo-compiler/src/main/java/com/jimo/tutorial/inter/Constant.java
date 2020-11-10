package com.jimo.tutorial.inter;

import com.jimo.tutorial.lexer.Num;
import com.jimo.tutorial.lexer.Token;
import com.jimo.tutorial.lexer.Word;
import com.jimo.tutorial.symbols.Type;

/**
 * 布尔表达式跳转
 *
 * @author jimo
 * @date 2020/11/10 8:41
 * @since 1.0.0
 */
public class Constant extends Expr {

    Constant(Token op, Type type) {
        super(op, type);
    }

    public Constant(int i) {
        super(new Num(i), Type.Int);
    }

    public static final Constant
            True = new Constant(Word.True, Type.Bool),
            False = new Constant(Word.False, Type.Bool);

    @Override
    public void jumping(int to, int from) {
        if (this == True && to != 0) {
            emit("goto L" + to);
        } else if (this == False && from != 0) {
            emit("goto L" + from);
        }
    }
}
