package com.jimo.serialize;

import com.fasterxml.jackson.annotation.JsonRawValue;

public class Bean04 {
    public String name;

    @JsonRawValue
    public String json;

    public Bean04(String name, String json) {
        this.name = name;
        this.json = json;
    }
}
