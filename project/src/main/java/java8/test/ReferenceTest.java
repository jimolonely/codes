package java8.test;

import java8.model.Converter;
import java8.model.Something;
import java8.model.Student;
import java8.model.StudentFactory;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

/**
 * Created by jimo on 18-7-11.
 */
public class ReferenceTest {
    @Test
    public void convert() throws Exception {
        Converter<Integer, String> converter = (from) -> Integer.parseInt(from);
        final int re = converter.convert("1234");
        assertEquals(1234, re);
    }

    @Test
    public void create() throws Exception {
        StudentFactory<Student> factory = Student::new;
        final Student jimo = factory.create("001", "jimo");
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