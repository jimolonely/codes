package com.jimo.tutorial.lexer;

/**
 * 管理保留字、标识符，比如&&这样的复合词
 *
 * @author jimo
 * @date 2020/11/7 15:25
 * @since 1.0.0
 */
public class Word extends Token {

    public String lexeme = "";

    public Word(String s, int tag) {
        super(tag);
        this.lexeme = s;
    }

    @Override
    public String toString() {
        return lexeme;
    }

    public static final Word
            and = new Word("&&", Tag.AND), or = new Word("||", Tag.OR),
            eq = new Word("==", Tag.EQ), ne = new Word("!=", Tag.NE),
            le = new Word("<=", Tag.LE), ge = new Word(">=", Tag.GE),
            minus = new Word("minus", Tag.MINUS), True = new Word("true", Tag.TRUE),
            False = new Word("false", Tag.FALSE), temp = new Word("t", Tag.TEMP);
}
