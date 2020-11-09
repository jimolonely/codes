package com.jimo.tutorial.inter;

import com.jimo.tutorial.lexer.Word;
import com.jimo.tutorial.symbols.Type;

/**
 * Id标识符
 *
 * @author jimo
 * @date 2020/11/7 18:04
 * @since 1.0.0
 */
public class Id extends Expr {
    // 相对地址
    public int offset;

    public Id(Word id, Type type, int b) {
        super(id, type);
        offset = b;
    }
}
