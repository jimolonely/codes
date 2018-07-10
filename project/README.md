# java8－tutorial

## 1.Default Methods for Interfaces
```java
public interface Calculator {

    int minus(int a, int b);

    default int add(int a, int b) {
        return a + b;
    }
}

@Test
public void test() throws Exception {
    //lambda:  final Calculator calculator = (a, b) -> a - b
    final Calculator calculator = new Calculator() {

        @Override
        public int minus(int a, int b) {
            return a - b;
        }
    };

    assertEquals(3, calculator.add(2, 1));
    assertEquals(1, calculator.minus(2, 1));
}
```
## 2.Lambda expression
```java
List<String> data = Arrays.asList("jimo", "hehe", "lizi");

//way 1
Collections.sort(data, new Comparator<String>() {
    @Override
    public int compare(String o1, String o2) {
        return o1.compareTo(o2);
    }
});

//way 1
Collections.sort(data, (o1, o2) -> o1.compareTo(o2));

//way 2
data.sort((a, b) -> a.compareTo(b));
```
## 3.Functional Interfaces
define a lambda by youself.
```java
@FunctionalInterface
public interface Converter<T, F> {
    T convert(F from);
}

@Test
public void convert() throws Exception {
    Converter<Integer, String> converter = (from) -> Integer.parseInt(from);
    final int re = converter.convert("1234");
    assertEquals(1234, re);
}
```
