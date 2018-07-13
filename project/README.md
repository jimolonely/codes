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
## 4.Method and constructor reference ::
 ```java
//way1
@Test
public void test() throws Exception {
    Converter<Integer, String> converter = Integer::parseInt;
    final int re = converter.convert("1234");
    assertEquals(1234, re);
}

//way2
public class Something {
    String startsWith(String s) {
        return String.valueOf(s.charAt(0));
    }
}

final Something something = new Something();
Converter<String, String> converter = something::startsWith;
assertEquals("J", converter.convert("Jimo"));

//way3
public class Student {
    String id;
    String name;

    public Student() {
    }

    public Student(String id, String name) {
        this.id = id;
        this.name = name;
    }
}

public interface StudentFactory<S extends Student> {
    S create(String id, String name);
}

StudentFactory<Student> factory = Student::new;
final Student jimo = factory.create("001", "jimo");
```
## 5.Lambda Scopes
### 5.1 final local variables
```java
int num = 2;
Converter<String,Integer> converter = (from)->String.valueOf(from+num); 
//num = 3;//error,num is final
```
### 5.2 field and static variables
instance fields and static variables can read and write.
```java
public class LambdaScope {
    int num;
    static int staticNum;

    void test() {
        Converter<String, Integer> converter = (from) -> {
            num = 10;
            return String.valueOf(from + num);
        };

        Converter<String, Integer> converter1 = (from) -> {
            staticNum = 20;
            return String.valueOf(from + staticNum);
        };
    }
}
```
### 5.3 access default interface method 
the code does not compile when accessing the default interface method 
```java
final Calculator calculator = (a, b) -> a - b
```
## 6.Built-in Functional Interfaces
### 6.1 Predicates
```java
Predicate<String> strLenBig = (s) -> s.length() > 0;
assertEquals(true, strLenBig.test("jimo"));
assertEquals(false, strLenBig.negate().test("jimo"));

Predicate<Boolean> nonNull = Objects::nonNull;
assertEquals(false, nonNull.test(null));

Predicate<String> isEmpty = String::isEmpty;
Predicate<String> isNotEmpty = isEmpty.negate();
```
### 6.2 Function<T,F>
```java
Function<String, Integer> strToInteger = Integer::parseInt;
Function<String, String> intToStr = strToInteger.andThen(String::valueOf);
assertEquals("123", intToStr.apply("123"));
```
### 6.3 Supplier<T>
don't accept arguments
```java
Supplier<Student> studentSupplier = Student::new;
final Student student = studentSupplier.get();//new Student
```
### 6.4 Consumer<T>
```java
Consumer<Student> consumer = (s) -> System.out.println("Hello, " + s.name);
consumer.accept(new Student("001", "jimo"));
consumer.accept(new Student("002", "hehe"));
```
### 6.5 Comparator
```java
//Comparator<Student> comparator = Comparator.comparing(s -> (s.name));
Comparator<Student> comparator = (s1, s2) -> (s1.name).compareTo(s2.name);
final Student jimo = new Student("001", "jimo");
final Student hehe = new Student("001", "hehe");
final int result = comparator.compare(jimo, hehe);
assertTrue(result > 0);
assertTrue(comparator.reversed().compare(jimo, hehe) < 0);
```
### 6.6 Optional
```java
Optional<String> optional = Optional.of("jimo");
assertEquals(true, optional.isPresent());
assertEquals("jimo", optional.get());
assertEquals("jimo", optional.orElse("haha"));

Optional<String> optional1 = Optional.empty();
assertEquals("jimo", optional.orElse("haha")); //haha
System.out.println(optional1.get());//java.util.NoSuchElementException: No value present
```
