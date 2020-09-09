package com.jimo.custom;

import com.fasterxml.jackson.annotation.JsonIgnoreType;

public class Bean25 {
    public int id;
    public String name;
    public A a;

    public Bean25(int id, String name, A a) {
        this.id = id;
        this.name = name;
        this.a = a;
    }

    public class A {
    }

    @JsonIgnoreType
    public class B {
    }
}
