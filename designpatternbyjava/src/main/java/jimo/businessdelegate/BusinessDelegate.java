package jimo.businessdelegate;

public class BusinessDelegate {
    private BusinessService businessService;
    private BusinessLookup lookup = new BusinessLookup();
    private String type;

    public void setType(String type) {
        this.type = type;
    }

    public void doTask() {
        businessService = lookup.getBussinessService(type);
        businessService.doProcessing();
    }
}
