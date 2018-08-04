# Mediator Pattern
中间人处理其他类之间的通信以达到解偶的目的。

下面使用聊天室来模拟中间人，所有的消息都由其处理。

![mediator_pattern_uml_diagram](./mediator_pattern_uml_diagram.jpg?raw=true)

## 1.
```java
import java.util.Date;

public class ChatRoom {
    public static void showMessage(User user, String msg) {
        System.out.println(new Date() + "[" + user.getName() + "]: " + msg);
    }
}
```
## 2.
```java
public class User {
    private String name;

    public User(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void sendMessage(String msg) {
        ChatRoom.showMessage(this, msg);
    }
}
```
## 3.
```java
public class MediatorPatternDemo {
    public static void main(String[] args) {
        User jimo = new User("jimo");
        User hehe = new User("hehe");

        jimo.sendMessage("hello every one!");
        hehe.sendMessage("hello,jimo!");
    }
}
/**
* Sat Aug 04 20:04:21 CST 2018[jimo]: hello every one!
  Sat Aug 04 20:04:21 CST 2018[hehe]: hello,jimo!
*/
```