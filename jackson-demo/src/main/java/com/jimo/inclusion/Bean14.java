package com.jimo.inclusion;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties({"id"})
public class Bean14 {
    public int id;
    public String name;

    public Bean14(int id, String name) {
        this.id = id;
        this.name = name;
    }
}
