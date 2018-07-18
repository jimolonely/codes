package java8.test;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.util.HashMap;
import java.util.Map;

public class MapTest {

    private Map<Integer, String> map;

    @Before
    public void init() {
        map = new HashMap<>();
        for (int i = 0; i < 10; i++) {
            map.putIfAbsent(i, "val" + i);
        }
        map.forEach((id, val) -> System.out.println(val));
    }

    @Test
    public void computeTest() {
        map.compute(3, (k, v) -> v + k);
        Assert.assertEquals("val33", map.get(3));

        map.computeIfPresent(9, (k, v) -> null);
        Assert.assertFalse(map.containsKey(9));

        map.computeIfAbsent(20, k -> "val" + k);
        Assert.assertTrue(map.containsKey(20));

        map.computeIfAbsent(3, k -> "haha");
        Assert.assertEquals("val33", map.get(3));
    }

    @Test
    public void removeTest() {
        map.remove(3, "valxx");
        Assert.assertEquals("val3", map.get(3));

        map.remove(3, "val3");
        Assert.assertNull(map.get(3));
    }

    @Test
    public void defaultTest() {
        Assert.assertEquals("not found", map.getOrDefault(30, "not found"));
    }

    @Test
    public void mergeTest() {
        map.computeIfPresent(9, (k, v) -> null);
        //map.get(9)==null
        map.merge(9, "val9", (value, newValue) -> value.concat(newValue));
        Assert.assertEquals("val9", map.get(9));

        map.merge(9, "haha", String::concat);
        Assert.assertEquals("val9haha", map.get(9));
    }
}
