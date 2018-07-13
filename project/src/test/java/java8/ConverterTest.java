package java8;

import org.junit.Test;

import java.util.Comparator;
import java.util.Objects;
import java.util.Optional;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.function.Supplier;

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

    @Test
    public void test5() throws Exception {
        Function<String, Integer> strToInteger = Integer::parseInt;
        Function<String, String> intToStr = strToInteger.andThen(String::valueOf);
        assertEquals("123", intToStr.apply("123"));
    }

    @Test
    public void test6() throws Exception {
        Supplier<Student> studentSupplier = Student::new;
        final Student student = studentSupplier.get();//new Student
    }

    @Test
    public void test7() throws Exception {
        Consumer<Student> consumer = (s) -> System.out.println("Hello, " + s.name);
        consumer.accept(new Student("001", "jimo"));
        consumer.accept(new Student("002", "hehe"));
    }

    @Test
    public void test8() throws Exception {
        Comparator<Student> comparator = Comparator.comparing(s -> (s.name));
//        Comparator<Student> comparator = (s1, s2) -> (s1.name).compareTo(s2.name);
        final Student jimo = new Student("001", "jimo");
        final Student hehe = new Student("001", "hehe");
        final int result = comparator.compare(jimo, hehe);
        assertTrue(result > 0);
        assertTrue(comparator.reversed().compare(jimo, hehe) < 0);
    }

    @Test
    public void test9() throws Exception {
        Optional<String> optional = Optional.of("jimo");
        assertEquals(true, optional.isPresent());
        assertEquals("jimo", optional.get());
        assertEquals("jimo", optional.orElse("haha"));

        Optional<String> optional1 = Optional.empty();
        assertEquals("jimo", optional.orElse("haha")); //haha
        System.out.println(optional1.get());//java.util.NoSuchElementException: No value present
    }
}