package jimo.intercepting;

public class Client {
    private FilterManager manager;

    public void setFilterManager(FilterManager filterManager) {
        this.manager = filterManager;
    }

    public void sendRequest(String request) {
        manager.filterRequest(request);
    }
}
