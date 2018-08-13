package jimo.businessdelegate;

public class JMSService implements BusinessService {
    @Override
    public void doProcessing() {
        System.out.println("processing task by JMS Service");
    }
}
