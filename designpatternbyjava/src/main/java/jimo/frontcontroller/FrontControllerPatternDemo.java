package jimo.frontcontroller;

public class FrontControllerPatternDemo {
    public static void main(String[] args) {
        FrontController frontController = new FrontController();
        frontController.dispatchRequest("Student");
        frontController.dispatchRequest("home");
    }
}
