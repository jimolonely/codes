package java8;

import org.junit.Test;

import java.util.Objects;
import java.util.function.Predicate;

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

    @Test
    public void test3() throws Exception {
        int num = 2;
        Converter<String, Integer> converter = (from) -> String.valueOf(from + num);
//        num = 3;//error,num is final

    }

    @Test
    public void test4() throws Exception {
        Predicate<String> strLenBig = (s) -> s.length() > 0;
        assertEquals(true, strLenBig.test("jimo"));
        assertEquals(false, strLenBig.negate().test("jimo"));

        Predicate<Boolean> nonNull = Objects::nonNull;
        assertEquals(false, nonNull.test(null));

        Predicate<String> isEmpty = String::isEmpty;
        Predicate<String> isNotEmpty = isEmpty.negate();
    }
}