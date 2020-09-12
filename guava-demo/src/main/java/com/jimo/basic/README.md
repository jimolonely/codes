
# Optional

```java
    @Test
    public void testOptional() {
        // 使用of创建，不允许为null
        final Optional<Integer> i = Optional.of(3);
        if (i.isPresent()) {
            assertEquals(3, (int) i.get());
        } else {
            assertNull(i.get());
        }

        // 使用fromNullable创建
        final Optional<Integer> i2 = Optional.fromNullable(null);
        assertFalse(i2.isPresent());

        // 直接创建为null
        final Optional<Integer> i3 = Optional.absent();
        // i3.get(); // java.lang.IllegalStateException: Optional.get() cannot be called on an absent value
        assertFalse(i3.isPresent());
    }
```

