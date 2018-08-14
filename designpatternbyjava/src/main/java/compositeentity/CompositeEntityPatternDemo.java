package compositeentity;

public class CompositeEntityPatternDemo {
    public static void main(String[] args) {
        Client client = new Client();
        client.setData("jimo", "hehe");
        client.printData();

        client.setData("haha", "good");
        client.printData();
    }
}
