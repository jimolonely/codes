package com.jimo.general;

import com.fasterxml.jackson.annotation.JsonFilter;

@JsonFilter("myFilter")
public class Bean23 {
    public int id;
    public String name;

    public Bean23(int id, String name) {
        this.id = id;
        this.name = name;
    }
}
