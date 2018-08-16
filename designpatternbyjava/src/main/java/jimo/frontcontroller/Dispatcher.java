package jimo.frontcontroller;

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
