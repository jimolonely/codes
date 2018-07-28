# Builder Pattern
通过计算饭钱，一顿Meal由Items组成，Item由Burger,Drink，Burger和Drink
又有不同的Packing方式，就像修建筑一样，一层层调用。
素食和非素食的组合。

![builder_pattern_uml_diagram](./builder_pattern_uml_diagram.jpg?raw=true)
## 1.
```java
public interface Packing {
    String pack();
}
```
```java
public class Wrapper implements Packing {
    public String pack() {
        return "Wrapper";
    }
}
```
```java
public class Bottle implements Packing {
    public String pack() {
        return "Bottle";
    }
}
```
## 2
```java
public interface Item {
    float price();

    String name();

    Packing packing();
}
```
```java
public abstract class Burger implements Item {
    public Packing packing() {
        return new Wrapper();
    }
}
```
```java
public abstract class Burger implements Item {
    public Packing packing() {
        return new Wrapper();
    }
}
```
## 3.
```java
public class VegBurger extends Burger {
    public float price() {
        return 100.5f;
    }

    public String name() {
        return "Veg Burger";
    }
}
```
```java
public class NonVegBurger extends Burger {
    public float price() {
        return 50.5f;
    }

    public String name() {
        return "Non Veg Burger";
    }
}
```
```java
public class Pepsi extends ColdDrink {
    public float price() {
        return 10;
    }

    public String name() {
        return "Pepsi";
    }
}
```
```java
public class Coco extends ColdDrink {
    public float price() {
        return 12;
    }

    public String name() {
        return "Coco";
    }
}
```
## 4
```java
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
```
## 5
```java
public class MealBuilder {
    public Meal prepareVegMeal() {
        Meal meal = new Meal();
        meal.addItem(new VegBurger());
        meal.addItem(new Pepsi());
        return meal;
    }

    public Meal prepareNonVegMeal() {
        Meal meal = new Meal();
        meal.addItem(new NonVegBurger());
        meal.addItem(new Coco());
        return meal;
    }
}
```
## 6.
```java
public class BuilderPatternDemo {
    public static void main(String[] args) {
        MealBuilder builder = new MealBuilder();
        Meal vegMeal = builder.prepareVegMeal();
        vegMeal.showItems();
        System.out.println("Veg Meal Totle Cost: " + vegMeal.getCost());

        Meal nonVegMeal = builder.prepareNonVegMeal();
        nonVegMeal.showItems();
        System.out.println("Non Veg Meal Total Cost: " + nonVegMeal.getCost());
    }
}
```
```java
Name: Veg Burger, Packing: Wrapper, Price: 100.5
Name: Pepsi, Packing: Bottle, Price: 10.0
Veg Meal Totle Cost: 110.5
Name: Non Veg Burger, Packing: Wrapper, Price: 50.5
Name: Coco, Packing: Bottle, Price: 12.0
Non Veg Meal Total Cost: 62.5
```
