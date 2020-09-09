package com.jimo.custom;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({"name", "id"})
public class Bean26 {
    public int id;
    public String name;

    public Bean26(int id, String name) {
        this.id = id;
        this.name = name;
    }
}
