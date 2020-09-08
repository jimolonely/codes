package com.jimo.type;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class JacksonTypeAnnotationTest {

    @Test
    public void testZooEmpty() throws JsonProcessingException {
        final ZooRaw.Dog dog = new ZooRaw.Dog("jimo");
        final ZooRaw zoo = new ZooRaw(dog);

        final String s = new ObjectMapper().writeValueAsString(zoo);
        System.out.println(s);
    }

    @Test
    public void testCatEmptyDeserialize() throws JsonProcessingException {
        String json = "{\"animal\":{\"name\":\"lily\"}}";

        final ZooRaw zoo = new ObjectMapper().readValue(json, ZooRaw.class);
        assertEquals("lily", zoo.animal.name);
        assertEquals(ZooRaw.Animal.class, zoo.animal.getClass());
    }

    @Test
    public void testZoo() throws JsonProcessingException {
        final Zoo.Dog dog = new Zoo.Dog("jimo");
        final Zoo zoo = new Zoo(dog);

        final String s = new ObjectMapper().writeValueAsString(zoo);
        System.out.println(s);
    }

    @Test
    public void testCatDeserialize() throws JsonProcessingException {
        String json = "{\"animal\":{\"name\":\"lily\",\"type\":\"cat\"}}";

        final Zoo zoo = new ObjectMapper().readValue(json, Zoo.class);
        assertEquals("lily", zoo.animal.name);
        assertEquals(Zoo.Cat.class, zoo.animal.getClass());
    }
}
