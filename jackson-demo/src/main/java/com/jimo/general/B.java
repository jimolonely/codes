package com.jimo.general;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;

import java.util.ArrayList;
import java.util.List;

@JsonIdentityInfo(
        generator = ObjectIdGenerators.PropertyGenerator.class,
        property = "id"
)
public class B {
    public int id;
    public String name;

    //    @JsonBackReference
    public List<A> items;

    public B(int id, String name) {
        this.id = id;
        this.name = name;
        items = new ArrayList<>();
    }
}
