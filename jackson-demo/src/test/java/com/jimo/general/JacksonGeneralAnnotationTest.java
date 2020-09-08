package com.jimo.general;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.Test;

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

}
