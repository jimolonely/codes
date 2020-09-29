
# 新的集合类型

[https://github.com/google/guava/wiki/NewCollectionTypesExplained](https://github.com/google/guava/wiki/NewCollectionTypesExplained)

## 不可变的好处

* 使用不信任的库时安全
* 线程安全
* 更高效：不可变比可变消耗更少，不需要支持改变

## 简介

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

## Multiset

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

## multimap

multimap不是个map

```java
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
```

## BiMap

允许反向一个map的k-v
```java
@Test
void testBiMap() {
    final HashBiMap<String, Integer> userId = HashBiMap.create();
    userId.put("jimo", 1);
    userId.put("hehe", 2);

    assertEquals("jimo", userId.inverse().get(1));
    assertEquals("hehe", userId.inverse().get(2));
}
```

## Table

由行、列和值构成，能单独由行和列获取map

```java
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
```

## classToInstanceMap

```java
@Test
void testClassToInstanceMap() {
    final MutableClassToInstanceMap<Number> numbers = MutableClassToInstanceMap.create();
    numbers.putInstance(Integer.class, 1);
    numbers.putInstance(Double.class, 1.1d);

    assertEquals(1, numbers.getInstance(Integer.class));
    assertEquals(1.1d, numbers.getInstance(Double.class));
}
```

## RangeSet

保存一些范围集合，自动合并范围，可以判断范围的交和包含。
```java
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
```

## RangeMap

范围为key，可以按范围操作map
```java
@Test
void testRangeMap() {
    final TreeRangeMap<Integer, String> rangeMap = TreeRangeMap.create();
    rangeMap.put(Range.closed(1, 10), "foo"); // {[1, 10] => "foo"}
    rangeMap.put(Range.open(3, 6), "bar"); // {[1, 3] => "foo", (3, 6) => "bar", [6, 10] => "foo"}
    rangeMap.put(Range.open(10, 20), "foo"); // {[1, 3] => "foo", (3, 6) => "bar", [6, 10] => "foo", (10, 20) =>
    // "foo"}
    rangeMap.remove(Range.closed(5, 11)); // {[1, 3] => "foo", (3, 5) => "bar", (11, 20) => "foo"}
}
```

# 有用的集合工具类

[https://github.com/google/guava/wiki/CollectionUtilitiesExplained](https://github.com/google/guava/wiki/CollectionUtilitiesExplained)

## 静态构造器

```java
@Test
void testStaticConstructor() {
    final List<String> list = Lists.newArrayList("A", "b", "C");
    final ArrayList<String> list1 = Lists.newArrayListWithCapacity(10);

    Map<String, String> map = Maps.newLinkedHashMap();

    final HashSet<Object> set = Sets.newHashSet();

    final HashMultiset<Object> multiset = HashMultiset.create();
}
```

## 迭代器

也就是 Iterables类对迭代器接口的实现封装的一些静态方法。

```java
@Test
void testIterables() {
    final Iterable<Integer> concat = Iterables.concat(Ints.asList(1, 2, 3), Ints.asList(4, 5, 6));

    final Integer last = Iterables.getLast(concat);
    assertEquals(6, last);

    final Iterable<Integer> limit = Iterables.limit(concat, 2);
    assertTrue(Iterables.elementsEqual(limit, Ints.asList(1, 2)));
}
```

## 比较器

Comparators类

```java
@Test
void testComparators() {
    assertEquals(4, Collections.max(Longs.asList(1, 2, 3, 4)));
}
```

## Sets

包含一些集合操作：交并差

```java
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
```


## Maps

同样集合的操作也适用于map作用于key。


# 集合帮助类

## PeekingIterator

允许在迭代时调用peek方法查看当前元素而不移动游标。

```java
@Test
void testPeekingIterator() {
    // 去重
    final ArrayList<Integer> result = Lists.newArrayList();
    final ArrayList<Integer> integers = Lists.newArrayList(1, 2, 2, 3, 4, 5, 5);
    final PeekingIterator<Integer> it = Iterators.peekingIterator(integers.iterator());
    while (it.hasNext()) {
        final Integer cur = it.next();
        while (it.hasNext() && it.peek().equals(cur)) {
            it.next();
        }
        result.add(cur);
    }
    assertEquals(5, result.size());
}
```


