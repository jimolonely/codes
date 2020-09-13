
# Optional

避免直接使用null。

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

# Preconditions

先决条件，该类包含了n个静态方法，检查变量是否满足条件；可以使代码更加整洁。

```java
@Test
public void testPreconditions() {
    int a = 10;
    int b = 20;
    List<Integer> list = new ArrayList<>();
    assertThrows(IllegalArgumentException.class, () -> Preconditions.checkArgument(a > b, "%s must > %s", a, b));
    assertThrows(NullPointerException.class, () -> Preconditions.checkNotNull(null));
    assertThrows(IllegalStateException.class, () -> Preconditions.checkState(list.size() > 0));
    assertThrows(IndexOutOfBoundsException.class, () -> Preconditions.checkElementIndex(10, list.size()));
}
```

# Ordering

TODO

# Objects

```java
@Test
void testObjects() {
    // JDK7提供了同样的功能
    // equals
    assertTrue(Objects.equals("a", "a"));
    assertFalse(Objects.equals("a", null));
    assertTrue(Objects.equals(null, null));

    // hashcode
    assertEquals(2530, Objects.hash("1", "2"));

    // toString
    System.out.println(MoreObjects.toStringHelper(this).add("x", 1).toString());
    // BasicTest{x=1}
    System.out.println(MoreObjects.toStringHelper("MyObject").add("x", 1).toString());
    // MyObject{x=1}

    // compare/compareTo
    final Foo foo1 = new Foo("jimo", 18, Foo.SEX.FM);
    final Foo foo2 = new Foo("jimo", 20, Foo.SEX.M);
    assertTrue(foo1.compareTo(foo2) < 0);
}

static class Foo {
    private String name;
    private int age;
    private SEX sex;

    public Foo(String name, int age, SEX sex) {
        this.name = name;
        this.age = age;
        this.sex = sex;
    }

    enum SEX {
        M, FM
    }

    public int compareTo(Foo that) {
        return ComparisonChain.start()
                .compare(this.name, that.name)
                .compare(this.age, that.age)
                .compare(this.sex, that.sex, Ordering.natural().nullsFirst())
                .result();
    }
}
```

