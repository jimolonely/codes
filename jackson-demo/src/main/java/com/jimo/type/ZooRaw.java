package com.jimo.type;

public class ZooRaw {

    public Animal animal;

    public ZooRaw() {
    }

    public ZooRaw(Animal animal) {
        this.animal = animal;
    }

    public static class Animal {
        public String name;

        public Animal(String name) {
            this.name = name;
        }

        public Animal() {
        }
    }

    public static class Dog extends Animal {
        public double barkVolume;

        public Dog(String name) {
            super(name);
        }
    }

    public static class Cat extends Animal {
        boolean likeCream;
        public int lives;

        public Cat(String name, int lives) {
            super(name);
            this.lives = lives;
        }

        public Cat() {
        }
    }
}
