package com.jimo.tutorial.inter;

import com.jimo.tutorial.lexer.Word;
import com.jimo.tutorial.symbols.Type;

/**
 * 临时节点
 *
 * @author jimo
 * @date 2020/11/7 19:03
 * @since 1.0.0
 */
public class Temp extends Expr {
    static int count = 0;
    int number = 0;

    Temp(Type type) {
        super(Word.temp, type);
        number = ++count;
    }

    @Override
    public String toString() {
        return "t" + number;
    }
}
