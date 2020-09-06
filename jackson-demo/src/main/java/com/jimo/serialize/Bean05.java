package com.jimo.serialize;

import com.fasterxml.jackson.annotation.JsonValue;

public enum Bean05 {
    USER1(1, "JIMO"), USER2(2, "HEHE");

    private int id;
    private String name;

    Bean05(int id, String name) {
        this.id = id;
        this.name = name;
    }

    @JsonValue
    public String getName() {
        return name;
    }
}
