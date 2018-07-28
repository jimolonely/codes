package jimo.adapter;

public class VlcPlayer implements AdvancedMediaPlayer {
    @Override
    public void playVlc(String fileName) {
        System.out.println("Play VLC File: " + fileName);
    }

    @Override
    public void playMp4(String fileName) {
        throw new UnsupportedOperationException();
    }
}
