package com.jimo.tutorial.symbols;

import com.jimo.tutorial.lexer.Tag;

/**
 * 数组
 *
 * @author jimo
 * @date 2020/11/7 17:40
 * @since 1.0.0
 */
public class Array extends Type {
    /**
     * 数组元素类型
     */
    public Type of;
    /**
     * 元素个数
     */
    public int size;

    public Array(int size, Type p) {
        super("[]", Tag.INDEX, size * p.width);
        this.size = size;
        of = p;
    }

    @Override
    public String toString() {
        return "[" + size + "]" + of.toString();
    }
}
