package com.jimo.general;

import com.fasterxml.jackson.annotation.JsonFormat;

import java.util.Date;

public class Bean20 {
    public String name;

    @JsonFormat(
            shape = JsonFormat.Shape.STRING,
            pattern = "yyyy-MM-dd hh:mm:ss"
    )
    public Date date;

    public Bean20(String name, Date date) {
        this.name = name;
        this.date = date;
    }

    public Bean20() {
    }
}
