# Service Locator Pattern
当我们想要使用JNDI查找定位各种服务时，使用服务定位器设计模式。 考虑到为服务查找JNDI的高成本，Service Locator模式使用缓存技术。 第一次需要服务时，Service Locator在JNDI中查找并缓存服务对象。 通过Service Locator进行进一步查找或相同的服务在其缓存中完成，这在很大程度上提高了应用程序的性能。 以下是此类设计模式的实体。

1. 服务 - 将处理请求的实际服务。 在JNDI服务器中查看此类服务的引用。

2. 上下文/初始上下文 - JNDI上下文携带用于查找目的的服务的引用。

3. 服务定位器 - 服务定位器是通过JNDI查找缓存服务来获取服务的单一联系点。

4. 缓存 - 用于存储服务引用的缓存以重用它们

5. 客户端 - 客户端是通过ServiceLocator调用服务的对象。

![servicelocator_pattern_uml_diagram](./servicelocator_pattern_uml_diagram.jpg?raw=true)

## 1.Service
```java
public interface Service {
    String getName();

    void execute();
}
```
```java
public class Service1 implements Service {
    @Override
    public String getName() {
        return "Service1";
    }

    @Override
    public void execute() {
        System.out.println("execute service 1");
    }
}
```
```java
public class Service2 implements Service {
    @Override
    public String getName() {
        return "Service2";
    }

    @Override
    public void execute() {
        System.out.println("execute service 2");
    }
}
```
## 2.InitialContext
```java
public class InitialContext {
    public Object lookup(String jndiName) {
        if ("service1".equalsIgnoreCase(jndiName)) {
            System.out.println("looking up and create s new service1");
            return new Service1();
        } else if ("service2".equalsIgnoreCase(jndiName)) {
            System.out.println("looking up and create s new service2");
            return new Service2();
        }
        return null;
    }
}
```
## 3.Cache
```java
public class Cache {
    private List<Service> services;

    public Cache() {
        services = new ArrayList<>();
    }

    public void addService(Service service) {
        boolean existed = false;
        for (Service s : services) {
            if (s.getName().equalsIgnoreCase(service.getName())) {
                existed = true;
                break;
            }
        }
        if (!existed) {
            services.add(service);
        }
    }

    public Service getService(String serviceName) {
        for (Service service : services) {
            if (service.getName().equalsIgnoreCase(serviceName)) {
                System.out.println("return cached service: " + serviceName);
                return service;
            }
        }
        return null;
    }
}
```
## 4.ServiceLocator
```java
public class ServiceLocator {
    private static Cache cache;

    static {
        cache = new Cache();
    }

    public static Service getService(String jndiName) {
        Service service = cache.getService(jndiName);
        if (service != null) {
            return service;
        }
        InitialContext initialContext = new InitialContext();
        Service newService = (Service) initialContext.lookup(jndiName);
        cache.addService(newService);
        return newService;
    }
}
```
## 5.Use
```java
public class ServiceLocatorPattern {
    public static void main(String[] args) {
        Service service = ServiceLocator.getService("service1");
        service.execute();
        service = ServiceLocator.getService("service2");
        service.execute();
        service = ServiceLocator.getService("service1");
        service.execute();
        service = ServiceLocator.getService("service2");
        service.execute();
    }
}
/*
looking up and create s new service1
execute service 1
looking up and create s new service2
execute service 2
return cached service: service1
execute service 1
return cached service: service2
execute service 2
*/
```
