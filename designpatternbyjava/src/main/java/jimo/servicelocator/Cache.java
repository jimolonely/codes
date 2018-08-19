package jimo.servicelocator;

import java.util.ArrayList;
import java.util.List;

public class Cache {
    private List<Service> services;

    public Cache() {
        services = new ArrayList<>();
    }

    public void addService(Service service) {
        boolean existed = false;
        for (Service s : services) {
            if (s.getName().equalsIgnoreCase(service.getName())) {
                existed = true;
                break;
            }
        }
        if (!existed) {
            services.add(service);
        }
    }

    public Service getService(String serviceName) {
        for (Service service : services) {
            if (service.getName().equalsIgnoreCase(serviceName)) {
                System.out.println("return cached service: " + serviceName);
                return service;
            }
        }
        return null;
    }
}
