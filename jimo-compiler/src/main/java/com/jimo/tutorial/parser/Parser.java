package com.jimo.tutorial.parser;


import com.jimo.tutorial.lexer.Lexer;
import com.jimo.tutorial.lexer.Token;
import com.jimo.tutorial.symbols.Env;

import java.io.IOException;

/**
 * 语法解析器
 *
 * @author jimo
 * @date 2020/11/11 9:10
 * @since 1.0.0
 */
public class Parser {
    private Lexer lex;
    private Token look;
    Env top = null;
    int used = 0;

    public Parser(Lexer l) throws IOException {
        lex = l;
        move();
    }

    void move() throws IOException {
        look = lex.scan();
    }

    void error(String s) {
        throw new Error("near line " + Lexer.line + ": " + s);
    }

    void match(int t) throws IOException {
        if (look.tag == t) {
            move();
        } else {
            error("syntax error");
        }
    }
}
