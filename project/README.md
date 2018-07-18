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
## 7.Optional
```java
Optional<String> optional = Optional.of("jimo");
assertEquals(true, optional.isPresent());
assertEquals("jimo", optional.get());
assertEquals("jimo", optional.orElse("haha"));

Optional<String> optional1 = Optional.empty();
assertEquals("jimo", optional.orElse("haha")); //haha
System.out.println(optional1.get());//java.util.NoSuchElementException: No value present
```
## 8.Streams
before
```java
List<String> data = new ArrayList<>();
data.add("ddd2");
data.add("aaa2");
data.add("bbb1");
data.add("aaa1");
data.add("bbb3");
data.add("ccc");
data.add("bbb2");
data.add("ddd1");
```
### 8.1 Filter
```java
data.stream().filter((s) -> s.startsWith("a")).forEach(System.out::println);
//aa2 , aaa1
```
### 8.2 Sorted
```java
data.stream().sorted().filter((s) -> s.startsWith("a")).forEach(System.out::println);
// aa1,aa2
System.out.println(data);//[ddd2, aaa2, bbb1, aaa1, bbb3, ccc, bbb2, ddd1]
```
### 8.3 Map
```java
data
                .stream()
                .map(String::toUpperCase)
                .sorted((a, b) -> b.compareTo(a))
                .forEach(System.out::println);
/**
DDD2
DDD1
CCC
BBB3
BBB2
BBB1
AAA2
AAA1
*/
```
### 8.4 Match
```java
assertEquals(false, data.stream().allMatch((s) -> s.startsWith("a")));
assertEquals(true, data.stream().anyMatch((s) -> s.startsWith("a")));
assertEquals(true, data.stream().noneMatch((s) -> s.startsWith("z")));
```
### 8.4 Count
```java
assertEquals(3, data.stream().filter((s) -> s.startsWith("b")).count());
```
### 8.5 Reduce
```java
final Optional<String> reduce = data.stream().reduce((a, b) -> a + "_" + b);
        reduce.ifPresent(System.out::println);
//ddd2_aaa2_bbb1_aaa1_bbb3_ccc_bbb2_ddd1
```
## 9.Parallel Streams
```java
public class ParallelStreamsTest {

    private List<String> data;

    @Before
    public void init() {
        int max = 1000000;
        data = new ArrayList<>();
        for (int i = 0; i < max; i++) {
            data.add(UUID.randomUUID().toString());
        }
    }

    @Test
    public void sequentialTest() {
        long t0 = System.nanoTime();
        final long count = data.stream().sorted().count();
        System.out.println(count);
        long t1 = System.nanoTime();
        final long millis = TimeUnit.NANOSECONDS.toMillis(t1 - t0);
        System.out.println(String.format("Sequential Sort spend: %d ms.", millis));
    }

    @Test
    public void parallelTest() {
        long t0 = System.nanoTime();
        final long count = data.parallelStream().sorted().count();
        System.out.println(count);
        long t1 = System.nanoTime();
        final long millis = TimeUnit.NANOSECONDS.toMillis(t1 - t0);
        System.out.println(String.format("Parallel Sort spend: %d ms.", millis));
    }
}
/**
1000000
Sequential Sort spend: 774 ms.
1000000
Parallel Sort spend: 380 ms.
*/
```
## 10.Maps
preppare
```java
private Map<Integer, String> map;

@Before
public void init() {
    map = new HashMap<>();
    for (int i = 0; i < 10; i++) {
        map.putIfAbsent(i, "val" + i);
    }
    map.forEach((id, val) -> System.out.println(val));
}
```
compute
```java
@Test
public void computeTest() {
    map.compute(3, (k, v) -> v + k);
    Assert.assertEquals("val33", map.get(3));

    map.computeIfPresent(9, (k, v) -> null);
    Assert.assertFalse(map.containsKey(9));

    map.computeIfAbsent(20, k -> "val" + k);
    Assert.assertTrue(map.containsKey(20));

    map.computeIfAbsent(3, k -> "haha");
    Assert.assertEquals("val33", map.get(3));
}
```
remove or get
```java
@Test
public void removeTest() {
    map.remove(3, "valxx");
    Assert.assertEquals("val3", map.get(3));

    map.remove(3, "val3");
    Assert.assertNull(map.get(3));
}

@Test
public void defaultTest() {
    Assert.assertEquals("not found", map.getOrDefault(30, "not found"));
}
```

Merge either put the key/value into the map if no entry for the key exists, or the merging function will be called to change the existing value
```java
@Test
public void mergeTest() {
    map.computeIfPresent(9, (k, v) -> null);
    //map.get(9)==null
    map.merge(9, "val9", (value, newValue) -> value.concat(newValue));
    Assert.assertEquals("val9", map.get(9));

    map.merge(9, "haha", String::concat);
    Assert.assertEquals("val9haha", map.get(9));
}
```

## 11.Date API
java.time
### 11.1 Clock
```java
final Clock clock = Clock.systemDefaultZone();
System.out.println(clock.millis());
final Instant instant = clock.instant();
final Date date = Date.from(instant);
final Date date1 = new Date();
System.out.println(date);
System.out.println(date1);
```
### 11.2 TimeZone

```java
System.out.println(ZoneId.getAvailableZoneIds());

final ZoneId zone1 = ZoneId.of("Asia/Aden");
final ZoneId zone2 = ZoneId.of("Asia/Kashgar");
System.out.println(zone1.getRules());
System.out.println(zone2.getRules());
/**
[Asia/Aden, America/Cuiaba, Etc/GMT+9, Etc/GMT+8, Africa/Nairobi, America/Marigot, Asia/Aqtau,...]
ZoneRules[currentStandardOffset=+03:00]
ZoneRules[currentStandardOffset=+06:00]
*/
```
### 11.3 LocalTime
```java
final ZoneId zone1 = ZoneId.of("Asia/Aden");
final ZoneId zone2 = ZoneId.of("Asia/Kashgar");
final LocalTime now1 = LocalTime.now(zone1);
final LocalTime now2 = LocalTime.now(zone2);

System.out.println(now1.isBefore(now2));

final long hoursBetween = ChronoUnit.HOURS.between(now1, now2);
final long minutesBetween = ChronoUnit.MINUTES.between(now1, now2);

System.out.println(hoursBetween);
System.out.println(minutesBetween);
/**
true
3
180
*/
```
```java
final LocalTime time = LocalTime.of(23, 59, 59);
System.out.println(time);

final DateTimeFormatter formatter = DateTimeFormatter
        .ofLocalizedTime(FormatStyle.SHORT).withLocale(Locale.CHINA);
final LocalTime localTime = LocalTime.parse("3:30", formatter); //bug will be fixed in JDK9
System.out.println(localTime);
```



