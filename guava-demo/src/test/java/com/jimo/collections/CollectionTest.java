package com.jimo.collections;

import com.google.common.collect.ImmutableMultiset;
import com.google.common.collect.ImmutableSet;
import com.google.common.collect.Multiset;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

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
}
