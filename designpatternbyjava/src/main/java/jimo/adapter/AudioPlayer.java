package jimo.adapter;

public class AudioPlayer implements MediaPlayer {

    @Override
    public void play(String audioType, String fileName) {
        if ("mp3".equalsIgnoreCase(audioType)) {
            System.out.println("Play MP3 file: " + fileName);
        } else {
            MediaAdapter adapter = new MediaAdapter(audioType);
            adapter.play(audioType, fileName);
        }
    }
}
