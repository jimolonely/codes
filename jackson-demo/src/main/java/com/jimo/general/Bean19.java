package com.jimo.general;

import com.fasterxml.jackson.annotation.JsonProperty;

public class Bean19 {
    public int id;
    public String name;

    public Bean19() {
    }

    public Bean19(int id, String name) {
        this.id = id;
        this.name = name;
    }

    @JsonProperty("name")
    public void setTheName(String name) {
        this.name = name;
    }

    @JsonProperty("name")
    public String getTheName() {
        return name;
    }
}
