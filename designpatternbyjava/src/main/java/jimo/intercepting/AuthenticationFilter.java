package jimo.intercepting;

public class AuthenticationFilter implements Filter {
    @Override
    public void execute(String request) {
        System.out.println("Authentication Filter:" + request);
    }
}
