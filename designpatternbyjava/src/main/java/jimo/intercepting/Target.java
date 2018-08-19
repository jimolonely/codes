package jimo.intercepting;

public class Target {
    public void execute(String request) {
        System.out.println("Execute request: " + request);
    }
}
