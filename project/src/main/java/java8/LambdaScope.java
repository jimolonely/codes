package java8;

/**
 * Created by jimo on 18-7-12.
 */
public class LambdaScope {
    int num;
    static int staticNum;

    void test() {
        Converter<String, Integer> converter = (from) -> {
            num = 10;
            return String.valueOf(from + num);
        };

        Converter<String, Integer> converter1 = (from) -> {
            staticNum = 20;
            return String.valueOf(from + staticNum);
        };
    }
}
