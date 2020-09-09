package com.jimo.general;

import com.fasterxml.jackson.annotation.JsonBackReference;

import java.util.ArrayList;
import java.util.List;

public class B {

    public String name;

    @JsonBackReference
    public List<A> items;

    public B(String name) {
        this.name = name;
        items = new ArrayList<>();
    }
}
