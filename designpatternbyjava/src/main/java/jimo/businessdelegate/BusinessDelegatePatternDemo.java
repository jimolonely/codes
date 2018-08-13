package jimo.businessdelegate;

public class BusinessDelegatePatternDemo {
    public static void main(String[] args) {
        BusinessDelegate businessDelegate = new BusinessDelegate();
        businessDelegate.setType("EJB");

        Client client = new Client(businessDelegate);
        client.doTask();

        businessDelegate.setType("JMS");
        client.doTask();
    }
}
