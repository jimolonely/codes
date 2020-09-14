
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

