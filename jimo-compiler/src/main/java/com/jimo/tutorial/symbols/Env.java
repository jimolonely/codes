package com.jimo.tutorial.symbols;

import com.jimo.tutorial.inter.Id;
import com.jimo.tutorial.lexer.Token;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * 把词法单元映射为类ID
 *
 * @author jimo
 * @date 2020/11/7 17:28
 * @since 1.0.0
 */
public class Env {

    private Map<Token, Id> table = new ConcurrentHashMap<>();

    protected Env prev;

    public Env(Env n) {
        prev = n;
    }

    public void put(Token w, Id i) {
        table.put(w, i);
    }

    public Id get(Token w) {
        for (Env e = this; e != null; e = e.prev) {
            final Id id = e.table.get(w);
            if (id != null) {
                return id;
            }
        }
        return null;
    }
}
