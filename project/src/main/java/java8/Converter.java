package java8;

/**
 * Created by jimo on 18-7-11.
 */
@FunctionalInterface
public interface Converter<T, F> {
    T convert(F from);
}
