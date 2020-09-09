package com.jimo.general;

import com.fasterxml.jackson.annotation.JsonView;

public class Bean22 {
    @JsonView(Public.class)
    public int id;
    @JsonView(Public.class)
    public String firstName;
    @JsonView({Internal.class})
    public String lastName;

    public Bean22(int id, String firstName, String lastName) {
        this.id = id;
        this.firstName = firstName;
        this.lastName = lastName;
    }

    public class Public {
    }

    public class Internal extends Public {
    }
}
