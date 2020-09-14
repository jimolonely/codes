
[https://github.com/google/guava/wiki/NewCollectionTypesExplained](https://github.com/google/guava/wiki/NewCollectionTypesExplained)

# 不可变的好处

* 使用不信任的库时安全
* 线程安全
* 更高效：不可变比可变消耗更少，不需要支持改变

# 简介

```java
@Test
void testCol() {
    final ImmutableSet<String> color = ImmutableSet.of("red", "orange", "yellow", "red");
    assertEquals(3, color.size());

    // 深度复制
    final ImmutableSet<String> newColor = ImmutableSet.copyOf(color);
    assertEquals(3, newColor.size());
}
```

# Multiset

轻松实现map统计次数的功能
```java
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
```

