package com.jimo.deserialize;

import com.fasterxml.jackson.annotation.JsonAlias;

public class Bean13 {
    @JsonAlias({"fName", "f_name"})
    private String firstName;
    private String lastName;

    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }
}
