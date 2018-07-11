package java8;

import org.junit.Test;

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

}