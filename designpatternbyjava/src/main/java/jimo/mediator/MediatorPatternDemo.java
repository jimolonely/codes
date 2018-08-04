package jimo.mediator;

public class MediatorPatternDemo {
    public static void main(String[] args) {
        User jimo = new User("jimo");
        User hehe = new User("hehe");

        jimo.sendMessage("hello every one!");
        hehe.sendMessage("hello,jimo!");
    }
}
