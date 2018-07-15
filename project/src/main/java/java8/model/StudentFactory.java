package java8.model;

/**
 * Created by jimo on 18-7-11.
 */
public interface StudentFactory<S extends Student> {
    S create(String id, String name);
}
