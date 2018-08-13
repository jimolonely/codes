package jimo.businessdelegate;

public class EJBService implements BusinessService {
    @Override
    public void doProcessing() {
        System.out.println("processing task by EJB Service");
    }
}
