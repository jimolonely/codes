package com.jimo.deserialize;

import com.fasterxml.jackson.annotation.JsonIgnoreType;

public class Bean16 {
    public int id;
    public Name name;

    public Bean16(int id, Name name) {
        this.id = id;
        this.name = name;
    }

    @JsonIgnoreType
    public static class Name {
        public String firstName;
        public String lastName;

        public Name(String firstName, String lastName) {
            this.firstName = firstName;
            this.lastName = lastName;
        }
    }
}
