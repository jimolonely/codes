package com.jimo.general;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.Test;

import java.util.Date;

import static org.junit.Assert.assertEquals;

public class JacksonGeneralAnnotationTest {

    @Test
    public void testBean19() throws JsonProcessingException {
        final Bean19 b = new Bean19(1, "jimo");

        final String s = new ObjectMapper().writeValueAsString(b);
        System.out.println(s);
        // {"id":1,"name":"jimo"}

        final Bean19 bb = new ObjectMapper().readValue(s, Bean19.class);
        assertEquals("jimo", bb.getTheName());
    }

    @Test
    public void testBean20() throws JsonProcessingException {
        final Bean20 b1 = new Bean20("jimo", new Date());

        final String s = new ObjectMapper().writeValueAsString(b1);
        System.out.println(s);
        // {"name":"jimo","date":"2020-09-08 01:16:55"}

        final Bean20 b2 = new ObjectMapper().readValue(s, Bean20.class);
        System.out.println(b2.date); // Tue Sep 08 09:16:55 CST 2020
    }

    @Test
    public void testBean21() throws JsonProcessingException {
        final Bean21.Name name = new Bean21.Name("jimo", "hehe");
        final Bean21 b = new Bean21(1, name);

        final String s = new ObjectMapper().writeValueAsString(b);
        System.out.println(s);
    }

    @Test
    public void testBean22() throws JsonProcessingException {
        final Bean22 b = new Bean22(1, "jimo", "hehe");

        final String s = new ObjectMapper()
                .writerWithView(Bean22.Public.class)
                .writeValueAsString(b);
        System.out.println(s); // {"id":1,"firstName":"jimo"}
    }
}
