package com.jimo.inclusion;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.Test;

import static org.hamcrest.CoreMatchers.containsString;
import static org.hamcrest.CoreMatchers.not;
import static org.junit.Assert.assertThat;

public class JacksonInclusionAnnotationTest {

    @Test
    public void testBean14() throws JsonProcessingException {
        final Bean14 b = new Bean14(1, "jimo");

        final String s = new ObjectMapper().writeValueAsString(b);

        assertThat(s, containsString("jimo"));
        assertThat(s, not(containsString("id")));
    }

}
