
package DungeonoftheBrutalKing.SharedData;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import javax.sound.sampled.*;
import javazoom.jl.player.Player;

public class MusicPlayer {

    private static Clip midiClip;
    private static Clip waveClip;
    private static Thread mp3Thread;

    public MusicPlayer() {
        midiClip = null;
        waveClip = null;
        mp3Thread = null;
    }

    public void midiPlayer(String soundFileName) {
        new Thread(() -> {
            try {
                String path = GameSettings.getSoundEffectsPath().trim() + soundFileName.trim();
                AudioInputStream audioInputStream = AudioSystem.getAudioInputStream(new File(path).getAbsoluteFile());
                midiClip = AudioSystem.getClip();
                midiClip.open(audioInputStream);
                midiClip.loop(Clip.LOOP_CONTINUOUSLY);
            } catch (Exception e) {
                // Log or handle exception
            }
        }).start();
    }

    public static void stopMidi() {
        if (midiClip != null && midiClip.isRunning()) {
            midiClip.stop();
            midiClip.close();
        }
    }

    public static void mp3Player(String soundFileName) {
        stopMP3();
        mp3Thread = new Thread(() -> {
            try {
                String path = GameSettings.getSoundEffectsPath().trim() + soundFileName.trim();
                try (FileInputStream fis = new FileInputStream(path)) {
                    Player player = new Player(fis);
                    player.play();
                }
            } catch (Exception e) {
                // Log or handle exception
            }
        });
        mp3Thread.start();
    }

    public static void stopMP3() {
        if (mp3Thread != null && mp3Thread.isAlive()) {
            mp3Thread.interrupt();
            mp3Thread = null;
        }
    }

    public static void wavePlayer(String soundFileName) {
        new Thread(() -> {
            try {
                String path = GameSettings.getSoundEffectsPath().trim() + soundFileName.trim();
                AudioInputStream audioInputStream = AudioSystem.getAudioInputStream(new File(path).getAbsoluteFile());
                waveClip = AudioSystem.getClip();
                waveClip.open(audioInputStream);
                waveClip.start();
                waveClip.addLineListener(event -> {
                    if (event.getType() == LineEvent.Type.STOP) {
                        waveClip.close();
                    }
                });
            } catch (Exception e) {
                // Log or handle exception
            }
        }).start();
    }

    public static void stopWave() {
        if (waveClip != null && waveClip.isRunning()) {
            waveClip.stop();
            waveClip.close();
        }
    }
}
