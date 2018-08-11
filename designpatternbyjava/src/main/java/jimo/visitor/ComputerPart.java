package jimo.visitor;

public interface ComputerPart {
    void accept(ComputerPartVisitor visitor);
}
