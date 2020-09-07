package com.jimo.deserialize;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

/**
 * @author jimo
 * @version 1.0.0
 * @date 2020/9/7 8:11
 */
public class JacksonDeserializeAnnotationTest {

    @Test
    public void testBean08() throws JsonProcessingException {
        String json = "{\"id\":1,\"theName\":\"jimo\"}";

        final Bean08 b = new ObjectMapper().readValue(json, Bean08.class);
        assertEquals("jimo", b.name);
    }
}
