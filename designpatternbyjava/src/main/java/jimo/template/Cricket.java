package jimo.template;

//板球游戏
public class Cricket extends Game {
    @Override
    void initialize() {
        System.out.println("板球游戏初始化，开始把");
    }

    @Override
    void startPlay() {
        System.out.println("开始玩板球游戏");
    }

    @Override
    void endPlay() {
        System.out.println("板球游戏结束，谢谢");
    }
}
