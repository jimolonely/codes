# Business Delegate Pattern
Business Delegate Pattern用于解耦表示层和业务层。 它基本上用于减少表示层代码中的业务层代码的通信或远程查找功能。 在业务层，我们有以下实体。

1. Client - 表示层代码可以是JSP，servlet或UI java代码。

2. Business Delegate - 客户实体提供对业务服务方法的访问的单一入口点类。

3. LookUp服务 - 查找服务对象负责获取相关业务实现并提供业务对象访问Business Delegate对象。

4. Business服务 - 商业服务界面。 具体类实现此业务服务以提供实际的业务实现逻辑。

![business_delegate_pattern_uml_diagram](./business_delegate_pattern_uml_diagram.jpg?raw=true)

## 1.Service
```java
public interface BusinessService {
    void doProcessing();
}
```
```java
public class EJBService implements BusinessService {
    @Override
    public void doProcessing() {
        System.out.println("processing task by EJB Service");
    }
}
```
```java
public class JMSService implements BusinessService {
    @Override
    public void doProcessing() {
        System.out.println("processing task by JMS Service");
    }
}
```
## 2.BusinessDelegate
```java
public class BusinessDelegate {
    private BusinessService businessService;
    private BusinessLookup lookup = new BusinessLookup();
    private String type;

    public void setType(String type) {
        this.type = type;
    }

    public void doTask() {
        businessService = lookup.getBussinessService(type);
        businessService.doProcessing();
    }
}
```
## 3.BusinessLookup
```java
public class BusinessLookup {
    public BusinessService getBussinessService(String type) {
        if ("EJB".equalsIgnoreCase(type)) {
            return new EJBService();
        } else {
            return new JMSService();
        }
    }
}
```
## 4.Client
```java
public class Client {
    private BusinessDelegate delegate;

    public Client(BusinessDelegate delegate) {
        this.delegate = delegate;
    }

    public void doTask() {
        delegate.doTask();
    }
}
```
## 5. Test
```java
public class BusinessDelegatePatternDemo {
    public static void main(String[] args) {
        BusinessDelegate businessDelegate = new BusinessDelegate();
        businessDelegate.setType("EJB");

        Client client = new Client(businessDelegate);
        client.doTask();

        businessDelegate.setType("JMS");
        client.doTask();
    }
}
/*
processing task by EJB Service
processing task by JMS Service
*/
```

