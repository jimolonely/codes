package java8;

/**
 * Created by jimo on 18-7-10.
 */
public interface Calculator {

    int minus(int a, int b);

    default int add(int a, int b) {
        return a + b;
    }
}
