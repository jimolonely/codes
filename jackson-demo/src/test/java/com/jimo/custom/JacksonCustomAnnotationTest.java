package com.jimo.custom;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.MapperFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class JacksonCustomAnnotationTest {

    @Test
    public void testBean24() throws JsonProcessingException {
        final Bean24 b = new Bean24(1, "jimo", null);

        final String s = new ObjectMapper().writeValueAsString(b);
        assertEquals("{\"name\":\"jimo\",\"id\":1}", s);
    }

    @Test
    public void testBean25() throws JsonProcessingException {
        final Bean25 b1 = new Bean25(1, "jimo", null);

        final String s = new ObjectMapper().writeValueAsString(b1);
        assertEquals("{\"id\":1,\"name\":\"jimo\",\"a\":null}", s);

        final String s1 = new ObjectMapper().addMixIn(Bean25.A.class, Bean25.B.class).writeValueAsString(b1);
        assertEquals("{\"id\":1,\"name\":\"jimo\"}", s1);
    }

    @Test
    public void testBean26() throws JsonProcessingException {
        final Bean26 b = new Bean26(1, null);

        final String s = new ObjectMapper().disable(MapperFeature.USE_ANNOTATIONS).writeValueAsString(b);
        assertEquals("{\"id\":1,\"name\":null}", s);
    }
}
