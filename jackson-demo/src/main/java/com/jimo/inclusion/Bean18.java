package com.jimo.inclusion;

import com.fasterxml.jackson.annotation.JsonAutoDetect;

@JsonAutoDetect(fieldVisibility = JsonAutoDetect.Visibility.PROTECTED_AND_PUBLIC)
public class Bean18 {
    public int id;
    protected String name;
    private int age;

    public Bean18(int id, String name, int age) {
        this.id = id;
        this.name = name;
        this.age = age;
    }
}
