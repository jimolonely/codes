
# 引入依赖

```xml
    <dependency>
        <groupId>com.fasterxml.jackson.core</groupId>
        <artifactId>jackson-databind</artifactId>
        <version>2.11.2</version>
    </dependency>
```

# 序列化注解

1-7

## @JsonAnyGetter

允许将map的字段变成标准字段.

看一个bean

```java
public class Bean01 {
    public String name;
    private Map<String, String> properties;

    public Bean01(String name, Map<String, String> properties) {
        this.name = name;
        this.properties = properties;
    }

    @JsonAnyGetter(enabled = false)
    public Map<String, String> getProperties() {
        return properties;
    }
}
```
再看看测试：
```java
@Test
public void testBean01() throws JsonProcessingException {
    final Bean01 bean01 = new Bean01("jimo", new HashMap<>());
    bean01.getProperties().put("k1", "v1");
    bean01.getProperties().put("k2", "v2");

    final String result1 = new ObjectMapper().writeValueAsString(bean01);
    System.out.println(result1);
    assertThat(result1, containsString("k1"));
    assertThat(result1, containsString("v1"));
}
```
当enabled=false时，结果如下：
```json
{
  "name": "jimo",
  "properties": {
    "k1": "v1",
    "k2": "v2"
  }
}
```

当enabled=true时，结果如下：
```json
{
  "name": "jimo",
  "k1": "v1",
  "k2": "v2"
}
```

可以看到，map被拉平了。

## @JsonGetter

```java
public class Bean02 {
    public int id;
    private String name;

    public Bean02(int id, String name) {
        this.id = id;
        this.name = name;
    }

    // @JsonGetter("name")
    public String getTheName() {
        return name;
    }
}
```
看测试
```java
@Test
public void testBean02() throws JsonProcessingException {
    final Bean02 b = new Bean02(1, "jimo");

    final String result = new ObjectMapper().writeValueAsString(b);
    System.out.println(result);
    assertThat(result, containsString("jimo"));
    assertThat(result, containsString("name"));
    assertThat(result, containsString("1"));
}
```
如果没有 @JsonGetter 的注解，得到的json是：
```json
{
  "id": 1,
  "theName": "jimo"
}
```
加上后才是name：
```json
{
  "id": 1,
  "name": "jimo"
}
```

## @JsonPropertyOrder

就是指定生成的json字符串中属性的顺序，默认是按照声明顺序。

```java
@JsonPropertyOrder(value = {"name", "id"})
public class Bean03 {

    public int id;
    public String name;

    public Bean03(int id, String name) {
        this.id = id;
        this.name = name;
    }
}
```
测试：
```java
@Test
public void testBean03() throws JsonProcessingException {
    final Bean03 b = new Bean03(1, "jimo");

    final String s = new ObjectMapper().writeValueAsString(b);
    assertEquals("{\"name\":\"jimo\",\"id\":1}", s);
}
```
也支持按字母序排列： `@JsonPropertyOrder(value = {"name", "id"}, alphabetic = true)`

## @JsonRawValue 

直接将字符串里的json写成json格式.

```java
public class Bean04 {
    public String name;

    @JsonRawValue
    public String json;

    public Bean04(String name, String json) {
        this.name = name;
        this.json = json;
    }
}
```

测试：
```java
@Test
public void testBean04() throws JsonProcessingException {
    final Bean04 b = new Bean04("jimo", "{\"attr\":false}");

    final String s = new ObjectMapper().writeValueAsString(b);
    assertEquals("{\"name\":\"jimo\",\"json\":{\"attr\":false}}", s);
}
```

结果：
```json
{
  "name": "jimo",
  "json": {
    "attr": false
  }
}
```

## JsonValue

```java
public enum Bean05 {
    USER1(1, "JIMO"), USER2(2, "HEHE");

    private int id;
    private String name;

    Bean05(int id, String name) {
        this.id = id;
        this.name = name;
    }

    // @JsonValue
    public String getName() {
        return name;
    }
}
```
看上面的枚举类，我们序列化时，其结果： "USER1"
```java
@Test
public void testBean05() throws JsonProcessingException {
    final String s = new ObjectMapper().writeValueAsString(Bean05.USER1);
    // s== "USER1"
}
```
但是我们想只用name这个字段来代表枚举类，于是加上 `@JsonValue`.

得到： `assertEquals("\"JIMO\"", s);`


## @JsonRootName

```java
@JsonRootName(value = "user")
public class Bean06 {
    public int id;
    public String name;

    public Bean06(int id, String name) {
        this.id = id;
        this.name = name;
    }
}
```
测试：需要启用 `WRAP_ROOT_VALUE`
```java
@Test
public void testBean06() throws JsonProcessingException {
    final Bean06 b = new Bean06(1, "jimo");

    final String s = new ObjectMapper().enable(SerializationFeature.WRAP_ROOT_VALUE).writeValueAsString(b);
    assertEquals("{\"user\":{\"id\":1,\"name\":\"jimo\"}}", s);
}
```
可以看到user作为了根节点：
```json
{"user":{"id":1,"name":"jimo"}}
```

## @JsonSerialize

自定义序列化。

```java
public class Bean07 {

    public String name;

    @JsonSerialize(using = MyLocalDateSerializer.class)
    public LocalDate date;

    public Bean07(String name, LocalDate date) {
        this.name = name;
        this.date = date;
    }
}
```
```java
public class MyLocalDateSerializer extends StdSerializer<LocalDate> {

    private static DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");

    public MyLocalDateSerializer() {
        this(null);
    }

    protected MyLocalDateSerializer(Class<LocalDate> t) {
        super(t);
    }

    @Override
    public void serialize(LocalDate localDate, JsonGenerator jsonGenerator, SerializerProvider serializerProvider) throws IOException {
        jsonGenerator.writeString(formatter.format(localDate));
    }
}
```
测试：
```java
@Test
public void testBean07() throws JsonProcessingException {
    final Bean07 b = new Bean07("jimo", LocalDate.now());
    final String s = new ObjectMapper().writeValueAsString(b);
    System.out.println(s);
}
```

在没有自定义序列化时：
```json
{"name":"jimo","date":{"year":2020,"month":"SEPTEMBER","dayOfMonth":6,"monthValue":9,"chronology":{"id":"ISO","calendarType":"iso8601"},"dayOfWeek":"SUNDAY","dayOfYear":250,"era":"CE","leapYear":true}}
```
定义了之后：
```json
{"name":"jimo","date":"2020-09-06"}
```

# 反序列化注解

## @JsonCreator

用在构造方法或工厂方法，调节反序列化的过程。

比如，下面将 `theName`属性映射到 `name` 属性上：
```java
public class Bean08 {
    public int id;
    public String name;

    @JsonCreator
    public Bean08(@JsonProperty("id") int id,
                  @JsonProperty("theName") String name) {
        this.id = id;
        this.name = name;
    }
}
```
测试：
```java
@Test
public void testBean08() throws JsonProcessingException {
    String json = "{\"id\":1,\"theName\":\"jimo\"}";

    final Bean08 b = new ObjectMapper().readValue(json, Bean08.class);
    assertEquals("jimo", b.name);
}
```

## JacksonInject

表示属性的值不从json里读，而是由注解指定。

```java
public class Bean09 {
    @JacksonInject
    public int id;
    public String name;
}
```
测试：
```java
@Test
public void testBean09() throws IOException {
    String json = "{\"name\":\"jimo\"}";

    final InjectableValues.Std inject = new InjectableValues.Std().addValue(int.class, 1);
    final Bean09 b = new ObjectMapper().reader(inject).readValue(json, Bean09.class);

    assertEquals(1, b.id);
    assertEquals("jimo", b.name);
}
```

## JsonAnySetter

和JsonAnyGetter相对应，将json里的k-v映射回map：

```java
public class Bean10 {
    public String name;
    private Map<String, String> properties;

    public Bean10() {
        this.properties = new HashMap<>();
    }

    @JsonAnySetter
    public void add(String key, String value) {
        properties.put(key, value);
    }

    public Map<String, String> getProperties() {
        return properties;
    }
}
```

测试：
```java
@Test
public void testBean10() throws JsonProcessingException {
    String json = "{\"name\":\"jimo\",\"attr2\":\"val2\",\"attr1\":\"val1\"}";

    final Bean10 b = new ObjectMapper().readerFor(Bean10.class).readValue(json);

    assertEquals("jimo", b.name);
    assertEquals("val1", b.getProperties().get("attr1"));
    assertEquals("val2", b.getProperties().get("attr2"));
}
```

## JsonSetter

将任意方法变成一个setter方法

```java
public class Bean11 {
    public int id;
    private String name;

    @JsonSetter("name")
    public void setTheName(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }
}
```

测试：
```java
@Test
public void testBean11() throws JsonProcessingException {
    String json = "{\"id\":1,\"name\":\"jimo\"}";

    final Bean11 b = new ObjectMapper().readValue(json, Bean11.class);

    assertEquals("jimo", b.getName());
}
```

## JsonDeserialize

自定义反序列化

```java
public class Bean12 {
    public String name;

    @JsonDeserialize(using = MyLocalDateDeserializer.class)
    public LocalDate date;
}
```

```java
public class MyLocalDateDeserializer extends StdDeserializer<LocalDate> {

    public static DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");

    public MyLocalDateDeserializer() {
        this(null);
    }

    protected MyLocalDateDeserializer(Class<?> vc) {
        super(vc);
    }

    @Override
    public LocalDate deserialize(JsonParser p, DeserializationContext ctx) throws IOException,
            JsonProcessingException {
        return LocalDate.parse(p.getText(), formatter);
    }
}
```
测试：
```java
@Test
public void testBean12() throws JsonProcessingException {
    String json = "{\"name\":\"jimo\",\"date\":\"2020-09-06\"}";

    final Bean12 b = new ObjectMapper().readValue(json, Bean12.class);

    assertEquals("2020-09-06", b.date.format(MyLocalDateDeserializer.formatter));
}
```

## JsonAlias

别名

```java
public class Bean13 {
    @JsonAlias({"fName", "f_name"})
    private String firstName;
    private String lastName;

    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }
}
```
测试：
```java

@Test
public void testBean13() throws JsonProcessingException {
    String json = "{\"fName\":\"jimo\",\"lastName\":\"hehe\"}";

    final Bean13 b = new ObjectMapper().readValue(json, Bean13.class);
    assertEquals("jimo", b.getFirstName());
}
```

# 属性相关注解

## @JsonIgnoreProperties

忽略某些注解, 用在类级别

```java
@JsonIgnoreProperties({"id"})
public class Bean14 {
    public int id;
    public String name;

    public Bean14(int id, String name) {
        this.id = id;
        this.name = name;
    }
}
```
测试：
```java
@Test
public void testBean14() throws JsonProcessingException {
    final Bean14 b = new Bean14(1, "jimo");

    final String s = new ObjectMapper().writeValueAsString(b);

    assertThat(s, containsString("jimo"));
    assertThat(s, not(containsString("id")));
}
```

## JsonIgnore

也是忽略属性字段，用在字段级别

```java
public class Bean15 {
    @JsonIgnore
    public int id;
    public String name;
}
```

## JsonIgnoreType

忽略某种类型的值。

```java
public class Bean16 {
    public int id;
    public Name name;

    public Bean16(int id, Name name) {
        this.id = id;
        this.name = name;
    }

    @JsonIgnoreType
    public static class Name {
        public String firstName;
        public String lastName;

        public Name(String firstName, String lastName) {
            this.firstName = firstName;
            this.lastName = lastName;
        }
    }
}
```
测试：
```java
@Test
public void testBean16() throws JsonProcessingException {
    final Bean16.Name name = new Bean16.Name("jimo", "hehe");
    final Bean16 b = new Bean16(1, name);

    final String s = new ObjectMapper().writeValueAsString(b);
    System.out.println(s); // {"id":1}
}
```

## JsonInclude

过滤某些字段，比如不为null的：
```java
@JsonInclude(JsonInclude.Include.NON_NULL)
public class Bean17 {
    public int id;
    public String name;

    public Bean17(int id, String name) {
        this.id = id;
        this.name = name;
    }
}
```
测试：
```java
@Test
public void testBean17() throws JsonProcessingException {
    final Bean17 b = new Bean17(1, null);

    final String s = new ObjectMapper().writeValueAsString(b);
    System.out.println(s); // {"id":1}
}
```

## JsonAutoDetect

自动检测，也可以加一些规则

```java
@JsonAutoDetect(fieldVisibility = JsonAutoDetect.Visibility.PROTECTED_AND_PUBLIC)
public class Bean18 {
    public int id;
    protected String name;
    private int age;

    public Bean18(int id, String name, int age) {
        this.id = id;
        this.name = name;
        this.age = age;
    }
}
```
测试：
```java
@Test
public void testBean18() throws JsonProcessingException {
    final Bean18 b = new Bean18(1, "jimo", 18);

    final String s = new ObjectMapper().writeValueAsString(b);
    System.out.println(s); // {"id":1,"name":"jimo"}
}
```

# 类型处理注解

* @JsonTypeInfo: 声明序列化时要包含的类型信息
* @JsonSubTypes: 声明注解的字类型
* @JsonTypeName: 给被注解的类定义一个逻辑类型名称

看例子：

一个动物园里有动物，动物的子类有狗和猫：
```java
public class ZooRaw {

    public Animal animal;

    public ZooRaw() {
    }

    public ZooRaw(Animal animal) {
        this.animal = animal;
    }

    public static class Animal {
        public String name;

        public Animal(String name) {
            this.name = name;
        }

        public Animal() {
        }
    }

    public static class Dog extends Animal {
        public double barkVolume;

        public Dog(String name) {
            super(name);
        }
    }

    public static class Cat extends Animal {
        boolean likeCream;
        public int lives;

        public Cat(String name, int lives) {
            super(name);
            this.lives = lives;
        }

        public Cat() {
        }
    }
}
```

现在我们序列化动物园：
```java
@Test
public void testZooEmpty() throws JsonProcessingException {
    final ZooRaw.Dog dog = new ZooRaw.Dog("jimo");
    final ZooRaw zoo = new ZooRaw(dog);

    final String s = new ObjectMapper().writeValueAsString(zoo);
    System.out.println(s); // {"animal":{"name":"jimo","barkVolume":0.0}}
}
```
没问题，但是当我们反序列化时，得到的就不是dog的实例了：
```java
@Test
public void testCatEmptyDeserialize() throws JsonProcessingException {
    String json = "{\"animal\":{\"name\":\"lily\"}}";

    final ZooRaw zoo = new ObjectMapper().readValue(json, ZooRaw.class);
    assertEquals("lily", zoo.animal.name);
    assertEquals(ZooRaw.Animal.class, zoo.animal.getClass());
}
```

如何才能得到呢？使用上面的注解：

```java
public class Zoo {

    public Animal animal;

    public Zoo() {
    }

    public Zoo(Animal animal) {
        this.animal = animal;
    }

    @JsonTypeInfo(
            use = JsonTypeInfo.Id.NAME,
            include = JsonTypeInfo.As.PROPERTY,
            property = "type"
    )
    @JsonSubTypes({
            @JsonSubTypes.Type(value = Dog.class, name = "dog"),
            @JsonSubTypes.Type(value = Cat.class, name = "cat")
    })
    public static class Animal {
        public String name;

        public Animal(String name) {
            this.name = name;
        }

        public Animal() {
        }
    }

    @JsonTypeName("dog")
    public static class Dog extends Animal {
        public double barkVolume;

        public Dog(String name) {
            super(name);
        }
    }

    @JsonTypeName("cat")
    public static class Cat extends Animal {
        boolean likeCream;
        public int lives;

        public Cat(String name, int lives) {
            super(name);
            this.lives = lives;
        }

        public Cat() {
        }
    }
}
```

使用type字段区分类型，name区分同类型的不同类：

```java
@Test
public void testZoo() throws JsonProcessingException {
    final Zoo.Dog dog = new Zoo.Dog("jimo");
    final Zoo zoo = new Zoo(dog);

    final String s = new ObjectMapper().writeValueAsString(zoo);
    System.out.println(s);
    // {"animal":{"type":"dog","name":"jimo","barkVolume":0.0}}
}
```

可以看到多了 `type` 字段。

反序列化也ok：
```java
@Test
public void testCatDeserialize() throws JsonProcessingException {
    String json = "{\"animal\":{\"name\":\"lily\",\"type\":\"cat\"}}";

    final Zoo zoo = new ObjectMapper().readValue(json, Zoo.class);
    assertEquals("lily", zoo.animal.name);
    assertEquals(Zoo.Cat.class, zoo.animal.getClass());
}
```

# 通用注解

## @JsonProperty

标注属性的

```java
public class Bean19 {
    public int id;
    public String name;

    public Bean19() {
    }

    public Bean19(int id, String name) {
        this.id = id;
        this.name = name;
    }

    @JsonProperty("name")
    public void setTheName(String name) {
        this.name = name;
    }

    @JsonProperty("name")
    public String getTheName() {
        return name;
    }
}
```
测试：
```java
@Test
public void testBean19() throws JsonProcessingException {
    final Bean19 b = new Bean19(1, "jimo");

    final String s = new ObjectMapper().writeValueAsString(b);
    System.out.println(s);
    // {"id":1,"name":"jimo"}

    final Bean19 bb = new ObjectMapper().readValue(s, Bean19.class);
    assertEquals("jimo", bb.getTheName());
}
```

## @JsonFormat

```java
public class Bean20 {
    public String name;

    @JsonFormat(
            shape = JsonFormat.Shape.STRING,
            pattern = "yyyy-MM-dd hh:mm:ss"
    )
    public Date date;

    public Bean20(String name, Date date) {
        this.name = name;
        this.date = date;
    }

    public Bean20() {
    }
}
```
测试：
```java
@Test
public void testBean20() throws JsonProcessingException {
    final Bean20 b1 = new Bean20("jimo", new Date());

    final String s = new ObjectMapper().writeValueAsString(b1);
    System.out.println(s);
    // {"name":"jimo","date":"2020-09-08 01:16:55"}

    final Bean20 b2 = new ObjectMapper().readValue(s, Bean20.class);
    System.out.println(b2.date); // Tue Sep 08 09:16:55 CST 2020
}
```

## @JsonUnwrapped

解构

```java
public class Bean21 {
    public int id;

    @JsonUnwrapped
    public Name name;

    public Bean21(int id, Name name) {
        this.id = id;
        this.name = name;
    }

    public static class Name {
        public String firstName;
        public String lastName;

        public Name(String firstName, String lastName) {
            this.firstName = firstName;
            this.lastName = lastName;
        }

        public Name() {
        }
    }
}
```
测试：
```java
@Test
public void testBean21() throws JsonProcessingException {
    final Bean21.Name name = new Bean21.Name("jimo", "hehe");
    final Bean21 b = new Bean21(1, name);

    final String s = new ObjectMapper().writeValueAsString(b);
    System.out.println(s);
    // {"id":1,"firstName":"jimo","lastName":"hehe"}
}
```

## @JsonView

就像数据库的视图一样，序列化和反序列化时指定哪些被view标记的序列化：

```java
public class Bean22 {
    @JsonView(Public.class)
    public int id;
    @JsonView(Public.class)
    public String firstName;
    @JsonView({Internal.class})
    public String lastName;

    public Bean22(int id, String firstName, String lastName) {
        this.id = id;
        this.firstName = firstName;
        this.lastName = lastName;
    }

    public class Public {
    }

    public class Internal extends Public {
    }
}
```

测试：只序列化 `Public` 的：
```java
@Test
public void testBean22() throws JsonProcessingException {
    final Bean22 b = new Bean22(1, "jimo", "hehe");

    final String s = new ObjectMapper()
            .writerWithView(Bean22.Public.class)
            .writeValueAsString(b);
    System.out.println(s); // {"id":1,"firstName":"jimo"}
}
```

## @JsonManagedReference,@JsonBackReference

处理父子关系循环，特别是递归情况下的序列化。

有一个类A：引用了B
```java
public class A {

    public int id;
    public B b;

    public A(int id, B b) {
        this.id = id;
        this.b = b;
    }
}
```
类B：引用了A
```java
public class B {
    public int id;
    public String name;

    public List<A> items;

    public B(int id, String name) {
        this.id = id;
        this.name = name;
        items = new ArrayList<>();
    }
}
```

我们来测试：
```java
@Test
public void testRef() throws JsonProcessingException {
    final B b = new B(2, "jimo");
    final A a = new A(1, b);
    b.items.add(a);

    final String s = new ObjectMapper().writeValueAsString(a);
    System.out.println(s);
}
```

得到一个栈溢出错误：
```java
Caused by: java.lang.StackOverflowError
```

现在修改：

A.java
```java
public class A {

    public int id;
    @JsonManagedReference
    public B b;

    public A(int id, B b) {
        this.id = id;
        this.b = b;
    }
}
```
B.java
```java
public class B {
    public int id;
    public String name;

    @JsonBackReference
    public List<A> items;

    public B(int id, String name) {
        this.id = id;
        this.name = name;
        items = new ArrayList<>();
    }
}
```
结果为：
```json
{"id":1,"b":{"id":2,"name":"jimo"}}
```

看起来结果里缺失了 items 字段，怎么解决看下面：

## @JsonIdentityInfo

使用ID来标识对象。

A
```java
@JsonIdentityInfo(
        generator = ObjectIdGenerators.PropertyGenerator.class,
        property = "id"
)
public class A {

    public int id;
    //    @JsonManagedReference
    public B b;

    public A(int id, B b) {
        this.id = id;
        this.b = b;
    }
}
```
B
```java
@JsonIdentityInfo(
        generator = ObjectIdGenerators.PropertyGenerator.class,
        property = "id"
)
public class B {
    public int id;
    public String name;

    //    @JsonBackReference
    public List<A> items;

    public B(int id, String name) {
        this.id = id;
        this.name = name;
        items = new ArrayList<>();
    }
}
```

结果：
```json
{"id":1,"b":{"id":2,"name":"jimo","items":[1]}}
```

## @JsonFilter

在序列化时指定过滤器

```java
@JsonFilter("myFilter")
public class Bean23 {
    public int id;
    public String name;

    public Bean23(int id, String name) {
        this.id = id;
        this.name = name;
    }
}
```
测试
```java
@Test
public void testBean23() throws JsonProcessingException {
    final Bean23 b = new Bean23(1, "jimo");

    final SimpleFilterProvider filters = new SimpleFilterProvider().addFilter("myFilter",
            SimpleBeanPropertyFilter.filterOutAllExcept("name"));

    final String s = new ObjectMapper().writer(filters).writeValueAsString(b);
    assertEquals("{\"name\":\"jimo\"}", s);
}
```

# 自定义注解

## @JacksonAnnotationInside

注解：排列列的顺序，忽略为null的
```java
@Retention(RetentionPolicy.RUNTIME)
@JacksonAnnotationsInside
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({"name", "id", "date"})
public @interface CustomOrderAnnotation {
}
```

定义bean
```java
@CustomOrderAnnotation
public class Bean24 {
    public int id;
    public String name;
    public Date date;

    public Bean24(int id, String name, Date date) {
        this.id = id;
        this.name = name;
        this.date = date;
    }
}
```
测试：
```java
@Test
public void testBean24() throws JsonProcessingException {
    final Bean24 b = new Bean24(1, "jimo", null);

    final String s = new ObjectMapper().writeValueAsString(b);
    assertEquals("{\"name\":\"jimo\",\"id\":1}", s);
}
```

