package com.jimo.deserialize;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.InjectableValues;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.Test;

import java.io.IOException;

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

    @Test
    public void testBean09() throws IOException {
        String json = "{\"name\":\"jimo\"}";

        final InjectableValues.Std inject = new InjectableValues.Std().addValue(int.class, 1);
        final Bean09 b = new ObjectMapper().reader(inject).readValue(json, Bean09.class);

        assertEquals(1, b.id);
        assertEquals("jimo", b.name);
    }

    @Test
    public void testBean10() throws JsonProcessingException {
        String json = "{\"name\":\"jimo\",\"attr2\":\"val2\",\"attr1\":\"val1\"}";

        final Bean10 b = new ObjectMapper().readerFor(Bean10.class).readValue(json);

        assertEquals("jimo", b.name);
        assertEquals("val1", b.getProperties().get("attr1"));
        assertEquals("val2", b.getProperties().get("attr2"));
    }

    @Test
    public void testBean11() throws JsonProcessingException {
        String json = "{\"id\":1,\"name\":\"jimo\"}";

        final Bean11 b = new ObjectMapper().readValue(json, Bean11.class);

        assertEquals("jimo", b.getName());
    }

    @Test
    public void testBean12() throws JsonProcessingException {
        String json = "{\"name\":\"jimo\",\"date\":\"2020-09-06\"}";

        final Bean12 b = new ObjectMapper().readValue(json, Bean12.class);

        assertEquals("2020-09-06", b.date.format(MyLocalDateDeserializer.formatter));
    }
}
