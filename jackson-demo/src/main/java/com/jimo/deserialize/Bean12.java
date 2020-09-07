package com.jimo.deserialize;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;

import java.time.LocalDate;

public class Bean12 {
    public String name;

    @JsonDeserialize(using = MyLocalDateDeserializer.class)
    public LocalDate date;
}
