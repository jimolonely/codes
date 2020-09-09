package com.jimo.general;

import com.fasterxml.jackson.annotation.JsonManagedReference;

public class A {

    public int id;
    @JsonManagedReference
    public B b;

    public A(int id, B b) {
        this.id = id;
        this.b = b;
    }
}
