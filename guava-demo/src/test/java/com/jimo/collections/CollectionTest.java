package com.jimo.collections;

import com.google.common.collect.*;
import org.junit.jupiter.api.Test;

import java.util.Map;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;

public class CollectionTest {

    @Test
    void testCol() {
        final ImmutableSet<String> color = ImmutableSet.of("red", "orange", "yellow", "red");
        assertEquals(3, color.size());

        // 深度复制
        final ImmutableSet<String> newColor = ImmutableSet.copyOf(color);
        assertEquals(3, newColor.size());
    }

    @Test
    void testMultiSet() {
        final ImmutableMultiset<String> multiset = ImmutableMultiset.of("hehe", "hello", "jimo", "hehe", "jimo");
        assertEquals(5, multiset.size());
        for (Multiset.Entry<String> e : multiset.entrySet()) {
            System.out.println(String.format("key=%s,count=%s", e.getElement(), e.getCount()));
        }
        /*
         * key=hehe,count=2
         * key=hello,count=1
         * key=jimo,count=2
         */
    }

    @Test
    void testMultimap() {
        final ListMultimap<String, Integer> lmt = MultimapBuilder.hashKeys().arrayListValues().build();

        lmt.put("a", 1);
        lmt.put("a", 2);
        lmt.put("a", 3);
        lmt.put("b", 4);
        lmt.put("b", 5);
        lmt.put("c", 6);

        assertEquals(3, lmt.get("a").size());
        assertEquals(2, lmt.get("b").size());
        assertEquals(1, lmt.get("c").size());
    }

    @Test
    void testBiMap() {
        final HashBiMap<String, Integer> userId = HashBiMap.create();
        userId.put("jimo", 1);
        userId.put("hehe", 2);

        assertEquals("jimo", userId.inverse().get(1));
        assertEquals("hehe", userId.inverse().get(2));
    }

    @Test
    void testTable() {
        Table<String, String, Integer> weightGraph = HashBasedTable.create();
        weightGraph.put("v1", "v2", 4);
        weightGraph.put("v1", "v3", 20);
        weightGraph.put("v2", "v3", 5);

        final Map<String, Integer> v1 = weightGraph.row("v1");
        assertEquals(4, v1.get("v2"));

        final Map<String, Integer> v3 = weightGraph.column("v3");
        assertEquals(5, v3.get("v2"));

        final Set<Table.Cell<String, String, Integer>> cells = weightGraph.cellSet();
        final Map<String, Map<String, Integer>> rowMap = weightGraph.rowMap();
    }

    @Test
    void testClassToInstanceMap() {
        final MutableClassToInstanceMap<Number> numbers = MutableClassToInstanceMap.create();
        numbers.putInstance(Integer.class, 1);
        numbers.putInstance(Double.class, 1.1d);

        assertEquals(1, numbers.getInstance(Integer.class));
        assertEquals(1.1d, numbers.getInstance(Double.class));
    }

    @Test
    void testRangeSet() {
        final RangeSet<Integer> rangeSet = TreeRangeSet.create();
        rangeSet.add(Range.closed(1, 10)); // [1,10]
        assertEquals(1, rangeSet.asRanges().size());
        assertTrue(rangeSet.encloses(Range.closed(2, 8)));

        rangeSet.add(Range.closed(11, 15));// [1,10],[11,15]
        assertEquals(2, rangeSet.asRanges().size());
        assertFalse(rangeSet.encloses(Range.closed(15, 16)));

        rangeSet.add(Range.closed(15, 20));// [1,10],[11,20] // 合并
        rangeSet.add(Range.closed(0, 0));// [1,10],[11,20] // 空的会忽略
        rangeSet.remove(Range.open(5, 10));// [1,5],[10,10],[11,20]
    }
}
