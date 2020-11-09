package com.jimo.tutorial.inter;

import com.jimo.tutorial.lexer.Lexer;

/**
 * AST的节点
 *
 * @author jimo
 * @date 2020/11/7 17:46
 * @since 1.0.0
 */
public class Node {
    /**
     * 为了记录错误和行数
     */
    int lexLine = 0;

    Node() {
        lexLine = Lexer.line;
    }

    void error(String s) {
        throw new Error("near line " + lexLine);
    }

    static int labels = 0;

    public int newLabel() {
        return ++labels;
    }

    public void emitLabel(int i) {
        System.out.print("L" + i + ":");
    }

    public void emit(String s) {
        System.out.println("\t" + s);
    }
}
