package com.jimo;

import com.fasterxml.jackson.annotation.JsonPropertyOrder;

/**
 * @author jimo
 * @version 1.0.0
 * @date 2020/9/6 17:13
 */
@JsonPropertyOrder(value = {"name", "id"}, alphabetic = true)
public class Bean03 {

    public int id;
    public String name;

    public Bean03(int id, String name) {
        this.id = id;
        this.name = name;
    }
}
