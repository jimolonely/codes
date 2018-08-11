package jimo.visitor;

public interface ComputerPartVisitor {
    void visit(Mouse mouse);

    void visit(Computer computer);

    void visit(KeyBoard keyBoard);

    void visit(Monitor monitor);
}
