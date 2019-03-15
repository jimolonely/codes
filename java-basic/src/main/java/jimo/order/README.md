# 类和变量的初始化顺序
1. 祖先的静态域或静态块
2. 当前类的静态域或静态块
3. 祖先的实例字段和初始化块
4. 初始化祖先实例字段后的祖先构造函数
5. 当前类的实例字段和初始化块
6. 当前类的构造函数


```java
public class Init {
    public static <T> T init(String name, T obj) {
        System.out.println("Initializing " + name + " " + obj);
        return obj;
    }
}
```
```java
public class Base {

    private static int i = Init.init("base static variable i ", 1);
    private static int i1 = Init.init("base static variable i1 ", 2);
    private int j = Init.init("base instance variable j", 5);
    private int j1 = Init.init("base instance variable j1", 6);
    private int k;

    static {
        Init.init("base static block", 3);
    }

    {
        Init.init("base initialization block", 7);
    }

    public Base() {
        Init.init("base contructor k ", 8);
    }

    public void hello() {
        System.out.println("base method");
    }
}
```
```java
public class Sub extends Base {
    int a;
    int b = Init.init("sub variable b", 9);

    public Sub() {
        a = Init.init("sub variable a", 10);
    }

    static int c = Init.init("sub static variable c", 4);
}
```
```java
public class Test {
    public static void main(String[] args) {
        System.out.println("First new Sub():");
        Sub sub = new Sub();
        sub.hello();
        System.out.println("\nSecond new Sub():");
        new Sub();
        System.out.println("\nFirst new Base():");
        new Base();
        System.out.println("\nSecond new Base():");
        new Base();
    }
}
/**
* First new Sub():
  Initializing base static variable i  1
  Initializing base static variable i1  2
  Initializing base static block 3
  Initializing sub static variable c 4
  Initializing base instance variable j 5
  Initializing base instance variable j1 6
  Initializing base initialization block 7
  Initializing base contructor k  8
  Initializing sub variable b 9
  Initializing sub variable a 10
  base method
  
  Second new Sub():
  Initializing base instance variable j 5
  Initializing base instance variable j1 6
  Initializing base initialization block 7
  Initializing base contructor k  8
  Initializing sub variable b 9
  Initializing sub variable a 10
  
  First new Base():
  Initializing base instance variable j 5
  Initializing base instance variable j1 6
  Initializing base initialization block 7
  Initializing base contructor k  8
  
  Second new Base():
  Initializing base instance variable j 5
  Initializing base instance variable j1 6
  Initializing base initialization block 7
  Initializing base contructor k  8
*/
```
