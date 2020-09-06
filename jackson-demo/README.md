
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
```java
{
  "name": "jimo",
  "k1": "v1",
  "k2": "v2"
}
```

可以看到，map被拉平了。

# 