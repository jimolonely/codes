package jimo.order;

public class Sub extends Base {
    int a;
    int b = Init.init("sub variable b", 9);

    public Sub() {
        a = Init.init("sub variable a", 10);
    }

    static int c = Init.init("sub static variable c", 4);
}
