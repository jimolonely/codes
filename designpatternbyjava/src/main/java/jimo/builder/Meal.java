package jimo.builder;

import java.util.ArrayList;
import java.util.List;

public class Meal {
    private List<Item> items = new ArrayList<>();

    public void addItem(Item item) {
        items.add(item);
    }

    public float getCost() {
        float total = 0;
        for (Item i : items) {
            total
         += i.price();
        }
        return total
        ;
    }

    public void showItems() {
        for (Item item : items) {
            System.out.println("Name: " + item.name() + ", Packing: " + item.packing().pack() + ", Price: " + item.price());
        }
    }
}
