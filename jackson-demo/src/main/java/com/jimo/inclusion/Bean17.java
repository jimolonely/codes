package com.jimo.inclusion;

import com.fasterxml.jackson.annotation.JsonInclude;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class Bean17 {
    public int id;
    public String name;

    public Bean17(int id, String name) {
        this.id = id;
        this.name = name;
    }
}
