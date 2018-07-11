package java8;

import org.junit.Test;

import static org.junit.Assert.*;

/**
 * Created by jimo on 18-7-11.
 */
public class ConverterTest {
    @Test
    public void convert() throws Exception {
        Converter<Integer, String> converter = (from) -> Integer.parseInt(from);
        final int re = converter.convert("1234");
        assertEquals(1234, re);
    }

    @Test
    public void test() throws Exception {
        Converter<Integer, String> converter = Integer::parseInt;
        final int re = converter.convert("1234");
        assertEquals(1234, re);
    }

    @Test
    public void test2() throws Exception {
        final Something something = new Something();
        Converter<String, String> converter = something::startsWith;
        assertEquals("J", converter.convert("Jimo"));
    }

}