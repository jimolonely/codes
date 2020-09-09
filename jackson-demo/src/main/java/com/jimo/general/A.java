package com.jimo.general;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;

@JsonIdentityInfo(
        generator = ObjectIdGenerators.PropertyGenerator.class,
        property = "id"
)
public class A {

    public int id;
    //    @JsonManagedReference
    public B b;

    public A(int id, B b) {
        this.id = id;
        this.b = b;
    }
}
