package jimo.builder;

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
