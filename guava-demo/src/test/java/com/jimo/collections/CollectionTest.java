package com.jimo.collections;

import com.google.common.collect.ImmutableSet;
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
}
