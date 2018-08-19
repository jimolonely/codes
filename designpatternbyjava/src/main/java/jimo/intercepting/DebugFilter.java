package jimo.intercepting;

public class DebugFilter implements Filter {
    @Override
    public void execute(String request) {
        System.out.println("Debug Filter: " + request);
    }
}
