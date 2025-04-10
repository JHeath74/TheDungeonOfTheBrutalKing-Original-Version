package DungeonoftheBrutalKing;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import javax.sound.sampled.*;

public class MusicPlayer {

    static String SoundFilePath;
    private static Clip clip;
    static String soundType;

    public MusicPlayer() {
        MusicPlayer.SoundFilePath = "src\\DungeonoftheBrutalKing\\SoundEffects\\";
        MusicPlayer.clip = null;
        MusicPlayer.soundType = null;
    }

    public void midiPlayer(String soundType) throws UnsupportedAudioFileException, IOException, LineUnavailableException {
        new Thread(() -> {
            try {
                // create AudioInputStream object
                AudioInputStream audioInputStream = AudioSystem.getAudioInputStream(new File(SoundFilePath + soundType).getAbsoluteFile());

                // create clip reference
                clip = AudioSystem.getClip();

                // open audioInputStream to the clip
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
                System.out.println("SoundFilePath: " + SoundFilePath);
                System.out.println("soundType: " + soundType);

                InputStream inputStream = getClass().getClassLoader().getResourceAsStream(SoundFilePath + soundType);

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
            // Create AudioInputStream object
            AudioInputStream audioInputStream = AudioSystem.getAudioInputStream(new File(SoundFilePath + soundFileName).getAbsoluteFile());

            // Create clip reference
            Clip clip = AudioSystem.getClip();

            // Open audioInputStream to the clip
            clip.open(audioInputStream);

            // Start playing the sound
            clip.start();

            // Wait for the clip to finish playing
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

}