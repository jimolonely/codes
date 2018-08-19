# Intercepting filter Pattern
当我们想要对应用程序的请求或响应进行一些预处理/后处理时，使用拦截过滤器设计模式。 在将请求传递给实际目标应用程序之前，会在请求上定义和应用过滤器。 过滤器可以执行身份验证/授权/记录或跟踪请求，然后将请求传递给相应的处理程序。 以下是此类设计模式的实体。

1. 过滤器 - 在请求处理程序执行请求之前或之后执行特定任务的过滤器。

2. 过滤链 - 过滤链带有多个过滤器，有助于在目标上按照定义的顺序执行它们。

3. 目标 - 目标对象是请求处理程序

4. 过滤器管理器 - 过滤器管理器管理过滤器和过滤器链。

5. 客户端 - 客户端是向Target对象发送请求的对象。

![interceptingfilter_pattern_uml_diagram](./interceptingfilter_pattern_uml_diagram.jpg?raw=true)

## 1.Filter
```java
public interface Filter {
    void execute(String request);
}
```
```java
public class AuthenticationFilter implements Filter {
    @Override
    public void execute(String request) {
        System.out.println("Authentication Filter:" + request);
    }
}
```
```java
public class DebugFilter implements Filter {
    @Override
    public void execute(String request) {
        System.out.println("Debug Filter: " + request);
    }
}
```
## 2.Target
```java
public class Target {
    public void execute(String request) {
        System.out.println("Execute request: " + request);
    }
}
```
## 3.FilterChain
```java
public class FilterChain {
    private List<Filter> filters = new ArrayList<>();
    private Target target;

    public void addFilter(Filter filter) {
        filters.add(filter);
    }

    public void setTarget(Target target) {
        this.target = target;
    }

    public void execute(String request) {
        for (Filter filter : filters) {
            filter.execute(request);
        }
        target.execute(request);
    }
}
```
## 4.FilterManager
```java
public class FilterManager {
    private FilterChain chain;

    public FilterManager(Target target) {
        chain = new FilterChain();
        chain.setTarget(target);
    }

    public void setFilter(Filter filter) {
        chain.addFilter(filter);
    }

    public void filterRequest(String request) {
        chain.execute(request);
    }
}
```
## 5.Client
```java
public class Client {
    private FilterManager manager;

    public void setFilterManager(FilterManager filterManager) {
        this.manager = filterManager;
    }

    public void sendRequest(String request) {
        manager.filterRequest(request);
    }
}
```
## 6.Test
```java
public class FilterPatternDemo {
    public static void main(String[] args) {
        FilterManager filterManager = new FilterManager(new Target());
        filterManager.setFilter(new AuthenticationFilter());
        filterManager.setFilter(new DebugFilter());

        Client client = new Client();
        client.setFilterManager(filterManager);
        client.sendRequest("/HOME");
    }
}
```
