package jimo.command;

public class Stock {
    private String name = "ABC";
    private int quanlity = 10;

    public void buy() {
        System.out.println("Bought[name:" + name + ",quantility:" + quanlity + "]");
    }

    public void sell() {
        System.out.println("Sold[name:" + name + ",quantility:" + quanlity + "]");
    }
}
