package com.jimo.general;

import com.fasterxml.jackson.annotation.JsonUnwrapped;

public class Bean21 {
    public int id;

    @JsonUnwrapped
    public Name name;

    public Bean21(int id, Name name) {
        this.id = id;
        this.name = name;
    }

    public static class Name {
        public String firstName;
        public String lastName;

        public Name(String firstName, String lastName) {
            this.firstName = firstName;
            this.lastName = lastName;
        }

        public Name() {
        }
    }
}
