
# 引入依赖

```xml
    <dependency>
        <groupId>com.fasterxml.jackson.core</groupId>
        <artifactId>jackson-databind</artifactId>
        <version>2.11.2</version>
    </dependency>
```

# @JsonAnyGetter

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

# @JsonGetter

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

# @JsonPropertyOrder

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

# @JsonRawValue 

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

