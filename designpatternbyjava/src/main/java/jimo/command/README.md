# Command Pattern
将请求封装成一个Command传递给调用者，调用者寻找合适的处理这个Command的对象
去处理这个请求。

下面：Broker是调用者，Order是Command，Stock是request.

![command_pattern_uml_diagram](./command_pattern_uml_diagram.jpg?raw=true)

## 1.Request
```java
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
```
## 2.Command
```java
public interface Order {
    void execute();
}
```
```java
public class SellStockOrder implements Order {
    private Stock stock;

    public SellStockOrder(Stock stock) {
        this.stock = stock;
    }

    @Override
    public void execute() {
        stock.sell();
    }
}
```
```java
public class BuyStockOrder implements Order {
    private Stock stock;

    public BuyStockOrder(Stock stock) {
        this.stock = stock;
    }

    @Override
    public void execute() {
        stock.buy();
    }
}
```
## 3.Broker
```java
import java.util.ArrayList;
import java.util.List;

public class Broker {
    private List<Order> orders = new ArrayList<>();

    public void takeOrder(Order order) {
        orders.add(order);
    }

    public void placeOrders() {
        for (Order o : orders) {
            o.execute();
        }
        orders.clear();
    }
}
```
## 4.Test
```java
public class CommandPatternDemo {
    public static void main(String[] args) {
        Stock stock = new Stock();
        BuyStockOrder buyStockOrder = new BuyStockOrder(stock);
        SellStockOrder sellStockOrder = new SellStockOrder(stock);

        Broker broker = new Broker();
        broker.takeOrder(buyStockOrder);
        broker.takeOrder(sellStockOrder);
        broker.placeOrders();
    }
}
/**
* Bought[name:ABC,quantility:10]
  Sold[name:ABC,quantility:10]
*/
/

```
