package jimo.template;

public abstract class Game {
    abstract void initialize();

    abstract void startPlay();

    abstract void endPlay();

    //template 方法,不可覆盖
    public final void play() {
        initialize();
        startPlay();
        endPlay();
    }
}
