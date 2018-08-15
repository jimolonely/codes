# Composite Entity Pattern 
复合实体模式用于EJB持久性机制。 Composite实体是EJB实体bean，
表示对象图。 更新复合实体时，内部相关对象bean会自动更新为EJB实体bean管理。 
以下是Composite Entity Bean的参与者。

1. 复合实体 - 它是主要的实体bean。 它可以是粗粒度的，也可以包含粗粒度的对象以用于持久性目的。

2. 粗粒度对象 - 此对象包含依赖对象。 它有自己的生命周期，也管理依赖对象的生命周期。

3. 依赖对象 - 依赖对象是一个对象，它依赖于粗粒度对象的持久化生命周期。

4. 策略 - 策略表示如何实现复合实体。

![compositeentity_pattern_uml_diagram](./compositeentity_pattern_uml_diagram.jpg?raw=true)

## 1.依赖对象
```java
public class DependentObject1 {
    private String data;

    public String getData() {
        return data;
    }

    public void setData(String data) {
        this.data = data;
    }
}
```
```java
public class DependentObject2 {
    private String data;

    public String getData() {
        return data;
    }

    public void setData(String data) {
        this.data = data;
    }
}
```
## 2. 粗粒度对象
```java
public class CoarseGrainedObject {
    private DependentObject1 object1 = new DependentObject1();
    private DependentObject2 object2 = new DependentObject2();

    public void setData(String data1, String data2) {
        object1.setData(data1);
        object2.setData(data2);
    }

    public String[] getData() {
        return new String[]{object1.getData(), object2.getData()};
    }
}
```
## 3.复合实体
```java
public class CompositeEntity {
    private CoarseGrainedObject object = new CoarseGrainedObject();

    public void setData(String data1, String data2) {
        object.setData(data1, data2);
    }

    public String[] getData() {
        return object.getData();
    }
}
```
## 4.Client
```java
public class Client {
    private CompositeEntity entity = new CompositeEntity();

    public void setData(String data1, String data2) {
        entity.setData(data1, data2);
    }

    public void printData() {
        for (String s : entity.getData()) {
            System.out.println("Data: " + s);
        }
    }
}
```
## 5.Use
```java
public class CompositeEntityPatternDemo {
    public static void main(String[] args) {
        Client client = new Client();
        client.setData("jimo", "hehe");
        client.printData();

        client.setData("haha", "good");
        client.printData();
    }
}
/*
Data: jimo
Data: hehe
Data: haha
Data: good
*/
```