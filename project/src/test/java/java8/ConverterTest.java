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

}