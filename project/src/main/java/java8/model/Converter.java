package java8.model;

/**
 * Created by jimo on 18-7-11.
 */
@FunctionalInterface
public interface Converter<T, F> {
    T convert(F from);
}
