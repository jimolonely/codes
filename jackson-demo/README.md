
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




