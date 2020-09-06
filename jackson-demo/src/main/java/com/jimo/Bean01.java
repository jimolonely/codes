package com.jimo;

import com.fasterxml.jackson.annotation.JsonAnyGetter;

import java.util.Map;

/**
 * @author jimo
 * @version 1.0.0
 * @date 2020/9/6 16:50
 */
public class Bean01 {
    public String name;
    private Map<String, String> properties;

    public Bean01(String name, Map<String, String> properties) {
        this.name = name;
        this.properties = properties;
    }

    @JsonAnyGetter(enabled = true)
    public Map<String, String> getProperties() {
        return properties;
    }

    @Override
    public String toString() {
        return "Bean01{" +
                "name='" + name + '\'' +
                ", properties=" + properties +
                '}';
    }
}
