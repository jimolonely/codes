# Template Pattern
抽象类定义了一些方法（模板），子类根据需要重写这些方法，但调用方式一致。

![template_pattern_uml_diagram](./template_pattern_uml_diagram.jpg?raw=true)

## 1.抽象类
```java
public abstract class Game {
    abstract void initialize();

    abstract void startPlay();

    abstract void endPlay();

    //template 方法,不可覆盖
    public final void play() {
        initialize();
        startPlay();
        endPlay();
    }
}
```
## 2.实现类
```java
//板球游戏
public class Cricket extends Game {
    @Override
    void initialize() {
        System.out.println("板球游戏初始化，开始把");
    }

    @Override
    void startPlay() {
        System.out.println("开始玩板球游戏");
    }

    @Override
    void endPlay() {
        System.out.println("板球游戏结束，谢谢");
    }
}
```
```java
public class Football extends Game {
    @Override
    void initialize() {
        System.out.println("足球游戏初始化，开始把");
    }

    @Override
    void startPlay() {
        System.out.println("开始玩足球游戏");
    }

    @Override
    void endPlay() {
        System.out.println("足球游戏结束，谢谢");
    }
}
```
## 3.Test
```java
public class TemplatePatternDemo {
    public static void main(String[] args) {
        Game game = new Cricket();
        game.play();
        System.out.println();
        game = new Football();
        game.play();
    }
}
/*
板球游戏初始化，开始把
开始玩板球游戏
板球游戏结束，谢谢

足球游戏初始化，开始把
开始玩足球游戏
足球游戏结束，谢谢
*/
```