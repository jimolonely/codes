package jimo.template;

public class Football extends Game {
    @Override
    void initialize() {
        System.out.println("足球游戏初始化，开始把");
    }

    @Override
    void startPlay() {
        System.out.println("开始玩足球游戏");
    }

    @Override
    void endPlay() {
        System.out.println("足球游戏结束，谢谢");
    }
}
