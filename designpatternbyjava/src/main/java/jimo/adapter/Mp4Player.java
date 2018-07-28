package jimo.adapter;

public class Mp4Player implements AdvancedMediaPlayer {
    @Override
    public void playVlc(String fileName) {
        throw new UnsupportedOperationException();
    }

    @Override
    public void playMp4(String fileName) {
        System.out.println("Play Mp4 File: " + fileName);
    }
}
