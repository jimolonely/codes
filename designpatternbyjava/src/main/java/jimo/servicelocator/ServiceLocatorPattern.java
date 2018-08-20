package jimo.servicelocator;

public class ServiceLocatorPattern {
    public static void main(String[] args) {
        Service service = ServiceLocator.getService("service1");
        service.execute();
        service = ServiceLocator.getService("service2");
        service.execute();
        service = ServiceLocator.getService("service1");
        service.execute();
        service = ServiceLocator.getService("service2");
        service.execute();
    }
}
