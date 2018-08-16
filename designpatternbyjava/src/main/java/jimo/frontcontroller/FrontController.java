package jimo.frontcontroller;

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
