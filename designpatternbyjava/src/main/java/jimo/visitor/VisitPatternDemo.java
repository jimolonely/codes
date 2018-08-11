package jimo.visitor;

public class VisitPatternDemo {
    public static void main(String[] args) {
        Computer computer = new Computer();
        computer.accept(new ComputerPartDispslayVisitor());
    }
}
