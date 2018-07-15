package java8.test;

import java8.model.Converter;
import org.junit.Test;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

/**
 * Created by jimo on 18-7-12.
 */
public class LambdaTest {
    int num;
    static int staticNum;

    @Test
    void testVariable() {
        Converter<String, Integer> converter = (from) -> {
            num = 10;
            return String.valueOf(from + num);
        };

        Converter<String, Integer> converter1 = (from) -> {
            staticNum = 20;
            return String.valueOf(from + staticNum);
        };
    }

    @Test
    public void testLambda() {
        List<String> data = Arrays.asList("jimo", "hehe", "lizi");

        //way 1
        Collections.sort(data, (o1, o2) -> o1.compareTo(o2));

        //way 2
        data.sort((a, b) -> a.compareTo(b));
    }

    @Test
    public void testFinal() throws Exception {
        int num = 2;
        Converter<String, Integer> converter = (from) -> String.valueOf(from + num);
//        num = 3;//error,num is final

    }
}
