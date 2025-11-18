
package SharedData;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import javax.sound.sampled.*;
import javazoom.jl.player.Player;


public class MusicPlayer {

    // Path to the directory containing sound files
    static String SoundEffectsPath;

    // Clip object to play audio
    private static Clip clip;

    // Type of sound being played
    static String soundType;

    // Constructor to initialize default values
    public MusicPlayer() {

        MusicPlayer.clip = null;
        MusicPlayer.soundType = null;
    }

    // Method to play MIDI files
    public void midiPlayer(String soundType) throws UnsupportedAudioFileException, IOException, LineUnavailableException {
        new Thread(() -> {
            try {
                AudioInputStream audioInputStream = AudioSystem.getAudioInputStream(new File(SoundEffectsPath + soundType).getAbsoluteFile());
                clip = AudioSystem.getClip();
                clip.open(audioInputStream);
                clip.loop(Clip.LOOP_CONTINUOUSLY);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }).start();
    }

    public static void stopMidi() {
        if (clip != null && clip.isRunning()) {
            clip.stop();
        }
    }

    public void mp3Player(String soundType) throws UnsupportedAudioFileException, IOException, LineUnavailableException {
        new Thread(() -> {
            try {
                System.out.println("SoundEffectsPath: " + SoundEffectsPath);
                System.out.println("soundType: " + soundType);
                InputStream inputStream = getClass().getClassLoader().getResourceAsStream(SoundEffectsPath + soundType);
                System.out.println("Working: " + inputStream);
                AudioInputStream audioStream = AudioSystem.getAudioInputStream(inputStream);
                AudioFormat audioFormat = audioStream.getFormat();
                DataLine.Info info = new DataLine.Info(SourceDataLine.class, audioFormat);
                Clip audioClip = (Clip) AudioSystem.getLine(info);
                audioClip.addLineListener((LineListener) this);
                audioClip.open(audioStream);
                audioClip.start();
                audioClip.close();
                audioStream.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }).start();
    }

    public static void stopMP3() {
        if (clip != null && clip.isRunning()) {
            clip.stop();
        }
    }

    public static void wavePlayer(String soundFileName) {
        new Thread(() -> {
            try {
                AudioInputStream audioInputStream = AudioSystem.getAudioInputStream(new File(SoundEffectsPath + soundFileName).getAbsoluteFile());
                Clip clip = AudioSystem.getClip();
                clip.open(audioInputStream);
                clip.start();
                clip.addLineListener(event -> {
                    if (event.getType() == LineEvent.Type.STOP) {
                        clip.close();
                    }
                });
            } catch (UnsupportedAudioFileException | IOException | LineUnavailableException e) {
                e.printStackTrace();
            }
        }).start();
    }

    public static void stopWave() {
        if (clip != null && clip.isRunning()) {
            clip.stop();
            clip.close();
        }
    }
    
    public static void playMP3(String fileName) {
        new Thread(() -> {
            try (FileInputStream fis = new FileInputStream(SoundEffectsPath + fileName)) {
                Player player = new Player(fis);
                player.play();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }).start();
    }
}
