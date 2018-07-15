package java8;


import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import java.util.concurrent.TimeUnit;

/**
 * Created by jimo on 18-7-15.
 */
public class ParallelStreamsTest {

    private List<String> data;

    @Before
    public void init() {
        int max = 1000000;
        data = new ArrayList<>();
        for (int i = 0; i < max; i++) {
            data.add(UUID.randomUUID().toString());
        }
    }

    @Test
    public void sequentialTest() {
        long t0 = System.nanoTime();
        final long count = data.stream().sorted().count();
        System.out.println(count);
        long t1 = System.nanoTime();
        final long millis = TimeUnit.NANOSECONDS.toMillis(t1 - t0);
        System.out.println(String.format("Sequential Sort spend: %d ms.", millis));
    }

    @Test
    public void parallelTest() {
        long t0 = System.nanoTime();
        final long count = data.parallelStream().sorted().count();
        System.out.println(count);
        long t1 = System.nanoTime();
        final long millis = TimeUnit.NANOSECONDS.toMillis(t1 - t0);
        System.out.println(String.format("Parallel Sort spend: %d ms.", millis));
    }
}
