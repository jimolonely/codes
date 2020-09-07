package com.jimo.deserialize;

import com.fasterxml.jackson.annotation.JsonAnySetter;

import java.util.HashMap;
import java.util.Map;

public class Bean10 {
    public String name;
    private Map<String, String> properties;

    public Bean10() {
        this.properties = new HashMap<>();
    }

    @JsonAnySetter
    public void add(String key, String value) {
        properties.put(key, value);
    }

    public Map<String, String> getProperties() {
        return properties;
    }
}
