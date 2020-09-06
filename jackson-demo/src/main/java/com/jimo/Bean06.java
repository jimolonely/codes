package com.jimo;

import com.fasterxml.jackson.annotation.JsonRootName;

@JsonRootName(value = "user")
public class Bean06 {
    public int id;
    public String name;

    public Bean06(int id, String name) {
        this.id = id;
        this.name = name;
    }
}
