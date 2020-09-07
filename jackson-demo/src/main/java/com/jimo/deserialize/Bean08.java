package com.jimo.deserialize;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

public class Bean08 {
    public int id;
    public String name;

    @JsonCreator
    public Bean08(@JsonProperty("id") int id,
                  @JsonProperty("theName") String name) {
        this.id = id;
        this.name = name;
    }
}
