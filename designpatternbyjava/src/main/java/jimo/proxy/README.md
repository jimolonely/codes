# Proxy Pattern
创建一个类包含有原有类的对象，以此来向外提供原有类功能的接口。

可以延迟对象的创建，只在需要调用的时候才初始化对象。

![proxy_pattern_uml_diagram](./proxy_pattern_uml_diagram.jpg?raw=true)

## 1.Real Display Image
```java
public interface Image {
    void display();
}
```
```java
public class RealImage implements Image {
    private String fileName;

    public RealImage(String fileName) {
        this.fileName = fileName;
        loadImageFromDisk(fileName);
    }

    private void loadImageFromDisk(String fileName) {
        System.out.println("Loading from disk: " + fileName);
    }

    @Override
    public void display() {
        System.out.println("Display Image: " + fileName);
    }
}
```
## 2.Proxy Image Display
```java
public class ProxyImage implements Image {
    private RealImage realImage;
    private String fileName;

    public ProxyImage(String fileName) {
        this.fileName = fileName;
    }

    @Override
    public void display() {
        if (realImage == null) {
            realImage = new RealImage(fileName);
        }
        realImage.display();
    }
}
```
## 3.Test
```java
public class ProxyPatternDemo {
    public static void main(String[] args) {
        ProxyImage proxyImage = new ProxyImage("test.jpg");
        proxyImage.display();
        System.out.println();
        proxyImage.display();
    }
}
/*
* Loading from disk: test.jpg
  Display Image: test.jpg
  
  Display Image: test.jpg
* */
```