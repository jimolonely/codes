package jimo.compositeentity;

public class Client {
    private CompositeEntity entity = new CompositeEntity();

    public void setData(String data1, String data2) {
        entity.setData(data1, data2);
    }

    public void printData() {
        for (String s : entity.getData()) {
            System.out.println("Data: " + s);
        }
    }
}
