package com.jimo.custom;

import java.util.Date;

@CustomOrderAnnotation
public class Bean24 {
    public int id;
    public String name;
    public Date date;

    public Bean24(int id, String name, Date date) {
        this.id = id;
        this.name = name;
        this.date = date;
    }
}
