package com.jimo.serialize;

import com.fasterxml.jackson.annotation.JsonGetter;

public class Bean02 {
    public int id;
    private String name;

    public Bean02(int id, String name) {
        this.id = id;
        this.name = name;
    }

    @JsonGetter("name")
    public String getTheName() {
        return name;
    }
}
