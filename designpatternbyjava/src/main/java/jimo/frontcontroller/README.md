# Front Controller Pattern
前端控制器设计模式用于提供集中的请求处理机制，以便所有请求将由单个处理程序处理。 此处理程序可以执行身份验证/授权/日志记录或跟踪请求，然后将请求传递给相应的处理程序。 以下是此类设计模式的实体。

1. 前端控制器 - 用于发送到应用程序的所有类型请求的单一处理程序（基于Web或基于桌面）。

2. Dispatcher - Front Controller可以使用调度程序对象，该对象可以将请求分派给相应的特定处理程序。

3. View - View是发出请求的对象。

![frontcontroller_pattern_uml_diagram](./frontcontroller_pattern_uml_diagram.jpg?raw=true)

## 1.View
```java
public class HomeView {
    public void show() {
        System.out.println("Home View Show");
    }
}
```
```java
public class StudentView {
    public void show() {
        System.out.println("Student View  Show");
    }
}
```
## Dispatcher
```java
public class Dispatcher {
    private HomeView homeView;
    private StudentView studentView;

    public Dispatcher() {
        homeView = new HomeView();
        studentView = new StudentView();
    }

    public void dispatcher(String request) {
        if ("Student".equalsIgnoreCase(request)) {
            studentView.show();
        } else {
            homeView.show();
        }
    }
}
```
## 3.FrontController
```java
public class FrontController {
    private Dispatcher dispatcher;

    public FrontController() {
        dispatcher = new Dispatcher();
    }

    private boolean isAuthenticationUser() {
        System.out.println("user is authenticated successfully");
        return true;
    }

    private void trackRequest(String request) {
        System.out.println("page requested: " + request);
    }

    public void dispatchRequest(String request) {
        trackRequest(request);
        if (isAuthenticationUser()) {
            dispatcher.dispatcher(request);
        }
    }
}
```
## 4.Test
```java
public class FrontControllerPatternDemo {
    public static void main(String[] args) {
        FrontController frontController = new FrontController();
        frontController.dispatchRequest("Student");
        frontController.dispatchRequest("home");
    }
}
/*
page requested: Student
user is authenticated successfully
Student View  Show
page requested: home
user is authenticated successfully
Home View Show
*/
```
