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

