package com.jimo.collections;

import com.google.common.collect.*;
import com.google.common.primitives.Ints;
import com.google.common.primitives.Longs;
import org.junit.jupiter.api.Test;

import java.util.*;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

/**
 * 有用的工具类
 *
 * @author jimo
 * @version 1.0.0
 * @date 2020/9/26 9:27
 */
public class UtilityClassTest {

    @Test
    void testStaticConstructor() {
        final List<String> list = Lists.newArrayList("A", "b", "C");
        final ArrayList<String> list1 = Lists.newArrayListWithCapacity(10);

        Map<String, String> map = Maps.newLinkedHashMap();

        final HashSet<Object> set = Sets.newHashSet();

        final HashMultiset<Object> multiset = HashMultiset.create();
    }

    @Test
    void testIterables() {
        final Iterable<Integer> concat = Iterables.concat(Ints.asList(1, 2, 3), Ints.asList(4, 5, 6));

        final Integer last = Iterables.getLast(concat);
        assertEquals(6, last);

        final Iterable<Integer> limit = Iterables.limit(concat, 2);
        assertTrue(Iterables.elementsEqual(limit, Ints.asList(1, 2)));
    }

    @Test
    void testComparators() {
        assertEquals(4, Collections.max(Longs.asList(1, 2, 3, 4)));
    }

    @Test
    void testSets() {
        final ImmutableSet<String> s1 = ImmutableSet.of("a", "b", "c");
        final ImmutableSet<String> s2 = ImmutableSet.of("a", "c", "d");

        final Sets.SetView<String> intersection = Sets.intersection(s1, s2);
        assertEquals(2, intersection.size());

        final Sets.SetView<String> union = Sets.union(s1, s2);
        assertEquals(4, union.size());

        final Sets.SetView<String> difference = Sets.difference(s1, s2);
        assertEquals(1, difference.size());
    }
}
