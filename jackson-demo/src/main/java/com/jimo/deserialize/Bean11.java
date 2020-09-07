package com.jimo.deserialize;

import com.fasterxml.jackson.annotation.JsonSetter;

public class Bean11 {
    public int id;
    private String name;

    @JsonSetter("name")
    public void setTheName(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }
}
