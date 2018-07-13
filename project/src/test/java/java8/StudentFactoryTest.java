package java8;

import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.*;

/**
 * Created by jimo on 18-7-11.
 */
public class StudentFactoryTest {
    @Test
    public void create() throws Exception {
        StudentFactory<Student> factory = Student::new;
        final Student jimo = factory.create("001", "jimo");
    }

    @Test
    public void streams() throws Exception {
        List<String> data = new ArrayList<>();
        data.add("ddd2");
        data.add("aaa2");
        data.add("bbb1");
        data.add("aaa1");
        data.add("bbb3");
        data.add("ccc");
        data.add("bbb2");
        data.add("ddd1");

        //filter
//        data.stream().filter((s) -> s.startsWith("a")).forEach(System.out::println);

        //sorted
//        data.stream().sorted().filter((s) -> s.startsWith("a")).forEach(System.out::println);
//        System.out.println(data);

        //map
//        data
//                .stream()
//                .map(String::toUpperCase)
//                .sorted((a, b) -> b.compareTo(a))
//                .forEach(System.out::println);

        //Match
        assertEquals(false, data.stream().allMatch((s) -> s.startsWith("a")));
        assertEquals(true, data.stream().anyMatch((s) -> s.startsWith("a")));
        assertEquals(true, data.stream().noneMatch((s) -> s.startsWith("z")));
    }
}