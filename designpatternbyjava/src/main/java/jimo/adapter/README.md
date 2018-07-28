# Adapter Pattern
![adapter_pattern_uml_diagram](./adapter_pattern_uml_diagram.jpg?raw=true)

## 1
有一个高级播放器可以播放mp4和vlc格式的。
```java
public interface AdvancedMediaPlayer {
    void playVlc(String fileName);

    void playMp4(String fileName);
}
```
```java
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
```
```java
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
```
## 2
有一个遗留系统只能播放mp3。现在需要结合上面那个播放多种格式。
```java
public interface MediaPlayer {
    void play(String audioType, String fileName);
}
```
```java
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
```
## 3
于是有了这个中间适配器。
```java
public class MediaAdapter implements MediaPlayer {

    private AdvancedMediaPlayer advancedMediaPlayer;

    public MediaAdapter(String audioType) {
        if ("vlc".equalsIgnoreCase(audioType)) {
            advancedMediaPlayer = new VlcPlayer();
        } else if ("mp4".equalsIgnoreCase(audioType)) {
            advancedMediaPlayer = new Mp4Player();
        } else {
            throw new UnsupportedOperationException("Invalid Format Type: " + audioType);
        }
    }

    @Override
    public void play(String audioType, String fileName) {
        if ("vlc".equalsIgnoreCase(audioType)) {
            advancedMediaPlayer.playVlc(fileName);
        } else if ("mp4".equalsIgnoreCase(audioType)) {
            advancedMediaPlayer.playMp4(fileName);
        }
    }
}
```
## 4.
```java
public class AdapterPatternDemo {
    public static void main(String[] args) {
        AudioPlayer audioPlayer = new AudioPlayer();

        audioPlayer.play("mp3", "beyond the horizon.mp3");
        audioPlayer.play("mp4", "alone.mp4");
        audioPlayer.play("vlc", "far far away.vlc");
        audioPlayer.play("avi", "mind me.avi");
    }
}
```
```java
Play MP3 file: beyond the horizon.mp3
Exception in thread "main" java.lang.UnsupportedOperationException: Invalid Format Type: avi
	at jimo.adapter.MediaAdapter.<init>(MediaAdapter.java:13)
	at jimo.adapter.AudioPlayer.play(AudioPlayer.java:10)
	at jimo.adapter.AdapterPatternDemo.main(AdapterPatternDemo.java:10)
Play Mp4 File: alone.mp4
Play VLC File: far far away.vlc
```