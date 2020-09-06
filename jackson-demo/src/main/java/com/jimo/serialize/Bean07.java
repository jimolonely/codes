package com.jimo.serialize;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;

import java.time.LocalDate;

/**
 * @author jimo
 * @version 1.0.0
 * @date 2020/9/6 18:56
 */
public class Bean07 {

    public String name;

    @JsonSerialize(using = MyLocalDateSerializer.class)
    public LocalDate date;

    public Bean07(String name, LocalDate date) {
        this.name = name;
        this.date = date;
    }
}
