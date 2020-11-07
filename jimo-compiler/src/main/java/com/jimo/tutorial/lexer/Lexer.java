package com.jimo.tutorial.lexer;

import com.jimo.tutorial.symbols.Type;

import java.io.IOException;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * 词法解析
 *
 * @author jimo
 * @date 2020/11/7 17:28
 * @since 1.0.0
 */
public class Lexer {
    public static int line = 1;

    char peek = ' ';
    Map<String, Word> words = new ConcurrentHashMap<>();

    void reserve(Word w) {
        words.put(w.lexeme, w);
    }

    public Lexer() {
        reserve(new Word("if", Tag.IF));
        reserve(new Word("else", Tag.IF));
        reserve(new Word("while", Tag.IF));
        reserve(new Word("do", Tag.IF));
        reserve(new Word("break", Tag.IF));
        reserve(Word.True);
        reserve(Word.False);
        reserve(Type.Int);
        reserve(Type.Float);
        reserve(Type.Char);
        reserve(Type.Bool);
    }

    void readChar() throws IOException {
        peek = (char) System.in.read();
    }

    boolean readChar(char c) throws IOException {
        readChar();
        if (peek != c) {
            return false;
        }
        peek = ' ';
        return true;
    }

    public Token scan() throws IOException {
        for (; ; readChar()) {
            if (peek != ' ' && peek != '\t') {
                if (peek == '\n') {
                    line = line + 1;
                } else {
                    break;
                }
            }
        }
        switch (peek) {
            case '&':
                if (readChar('&')) return Word.and;
                else return new Token('&');
            case '|':
                if (readChar('|')) return Word.or;
                else return new Token('|');
            case '=':
                if (readChar('=')) return Word.eq;
                else return new Token('=');
            case '!':
                if (readChar('=')) return Word.ne;
                else return new Token('!');
            case '<':
                if (readChar('=')) return Word.le;
                else return new Token('<');
            case '>':
                if (readChar('=')) return Word.ge;
                else return new Token('>');
        }

        if (Character.isDigit(peek)) {
            int v = 0;
            do {
                v = 10 * v + Character.digit(peek, 10);
                readChar();
            } while (Character.isDigit(peek));
            if (peek != '.') {
                return new Num(v);
            }
            float x = v;
            float d = 10;
            for (; ; ) {
                readChar();
                if (!Character.isDigit(peek)) {
                    break;
                }
                x = x + Character.digit(peek, 10) / d;
                d = d * 10;
            }
            return new Real(x);
        }

        if (Character.isLetter(peek)) {
            final StringBuilder b = new StringBuilder();
            do {
                b.append(peek);
                readChar();
            } while (Character.isLetterOrDigit(peek));
            String s = b.toString();
            Word w = words.get(s);
            if (w != null) {
                return w;
            }
            w = new Word(s, Tag.ID);
            words.put(s, w);
            return w;
        }
        final Token token = new Token(peek);
        peek = ' ';
        return token;
    }
}
