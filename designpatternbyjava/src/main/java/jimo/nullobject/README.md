# Null Object Pattern
当对象为null时的默认实现。

![null_pattern_uml_diagram](./null_pattern_uml_diagram.jpg?raw=true)

## 1.
```java
public abstract class AbstractCustomer {
    protected String name;

    abstract boolean isNil();

    abstract String getName();
}
```
## 2.
```java
public class RealCustomer extends AbstractCustomer {
    public RealCustomer(String name) {
        this.name = name;
    }

    @Override
    boolean isNil() {
        return false;
    }

    @Override
    String getName() {
        return name;
    }
}
```
```java
public class NilCustomer extends AbstractCustomer {
    @Override
    boolean isNil() {
        return true;
    }

    @Override
    String getName() {
        return "No Available in Customer Database";
    }
}
```
## 3
```java
public class CustomerFactory {
    private static final String[] names = {"Rob", "Joe", "julia"};

    public static AbstractCustomer getCustomer(String name) {
        for (String n : names) {
            if (n.equalsIgnoreCase(name)) {
                return new RealCustomer(n);
            }
        }
        return new NilCustomer();
    }
}
```
## 4
```java
public class NullObjectPatternDemo {
    public static void main(String[] args) {
        AbstractCustomer jimo = CustomerFactory.getCustomer("jimo");
        AbstractCustomer rob = CustomerFactory.getCustomer("Rob");
        AbstractCustomer hehe = CustomerFactory.getCustomer("hehe");

        System.out.println(jimo.getName());
        System.out.println(rob.getName());
        System.out.println(hehe.getName());
    }
}
/**
No Available in Customer Database
Rob
No Available in Customer Database
/
```