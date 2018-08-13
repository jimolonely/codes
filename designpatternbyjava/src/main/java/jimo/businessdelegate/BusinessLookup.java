package jimo.businessdelegate;

public class BusinessLookup {
    public BusinessService getBussinessService(String type) {
        if ("EJB".equalsIgnoreCase(type)) {
            return new EJBService();
        } else {
            return new JMSService();
        }
    }
}
