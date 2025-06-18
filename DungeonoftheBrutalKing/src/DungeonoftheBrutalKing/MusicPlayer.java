
package DungeonoftheBrutalKing;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;

import javax.sound.sampled.AudioFormat;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.DataLine;
import javax.sound.sampled.LineEvent;
import javax.sound.sampled.LineListener;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.SourceDataLine;
import javax.sound.sampled.UnsupportedAudioFileException;

public class MusicPlayer {

    // Path to the directory containing sound files
    static String SoundFilePath;

    // Clip object to play audio
    private static Clip clip;

    // Type of sound being played
    static String soundType;

    // Constructor to initialize default values
    public MusicPlayer() {
        MusicPlayer.SoundFilePath = "src\\DungeonoftheBrutalKing\\SoundEffects\\";
        MusicPlayer.clip = null;
        MusicPlayer.soundType = null;
    }

    // Method to play MIDI files
    public void midiPlayer(String soundType) throws UnsupportedAudioFileException, IOException, LineUnavailableException {
        new Thread(() -> {
            try {
                // Create AudioInputStream object for the specified sound file
                AudioInputStream audioInputStream = AudioSystem.getAudioInputStream(new File(SoundFilePath + soundType).getAbsoluteFile());

                // Create a Clip object to play the audio
                clip = AudioSystem.getClip();

                // Open the audio stream in the clip
                clip.open(audioInputStream);

                // Loop the audio continuously
                clip.loop(Clip.LOOP_CONTINUOUSLY);
            } catch (Exception e) {
                // Print stack trace if an exception occurs
                e.printStackTrace();
            }
        }).start(); // Start the thread
    }

    // Method to stop playing MIDI files
    public static void stopMidi() {
        // Stop the clip if it is running
        if (clip != null && clip.isRunning()) {
            clip.stop();
        }
    }

    // Method to play MP3 files
    public void mp3Player(String soundType) throws UnsupportedAudioFileException, IOException, LineUnavailableException {
        new Thread(() -> {
            try {
                // Print debug information for the file path and sound type
                System.out.println("SoundFilePath: " + SoundFilePath);
                System.out.println("soundType: " + soundType);

                // Load the sound file as an InputStream
                InputStream inputStream = getClass().getClassLoader().getResourceAsStream(SoundFilePath + soundType);

                // Print debug information for the InputStream
                System.out.println("Working: " + inputStream);

                // Create an AudioInputStream from the InputStream
                AudioInputStream audioStream = AudioSystem.getAudioInputStream(inputStream);

                // Get the audio format of the stream
                AudioFormat audioFormat = audioStream.getFormat();

                // Create a DataLine.Info object for the audio format
                DataLine.Info info = new DataLine.Info(SourceDataLine.class, audioFormat);

                // Create a Clip object to play the audio
                Clip audioClip = (Clip) AudioSystem.getLine(info);

                // Add a LineListener to the clip
                audioClip.addLineListener((LineListener) this);

                // Open the audio stream in the clip
                audioClip.open(audioStream);

                // Start playing the audio
                audioClip.start();

                // Close the clip and audio stream after playback
                audioClip.close();
                audioStream.close();
            } catch (Exception e) {
                // Print stack trace if an exception occurs
                e.printStackTrace();
            }
        }).start(); // Start the thread
    }

    // Method to stop playing MP3 files
    public static void stopMP3() {
        // Stop the clip if it is running
        if (clip != null && clip.isRunning()) {
            clip.stop();
        }
    }

    // Method to play WAV files
    public static void wavePlayer(String soundFileName) {
        new Thread(() -> {
            try {
                // Create AudioInputStream object for the specified sound file
                AudioInputStream audioInputStream = AudioSystem.getAudioInputStream(new File(SoundFilePath + soundFileName).getAbsoluteFile());

                // Create a Clip object to play the audio
                Clip clip = AudioSystem.getClip();

                // Open the audio stream in the clip
                clip.open(audioInputStream);

                // Start playing the audio
                clip.start();

                // Add a LineListener to close the clip after playback
                clip.addLineListener(event -> {
                    if (event.getType() == LineEvent.Type.STOP) {
                        clip.close();
                    }
                });

            } catch (UnsupportedAudioFileException | IOException | LineUnavailableException e) {
                // Print stack trace if an exception occurs
                e.printStackTrace();
            }
        }).start(); // Start the thread
    }

    // Method to stop playing WAV files
    public static void stopWave() {
        // Stop and close the clip if it is running
        if (clip != null && clip.isRunning()) {
            clip.stop();
            clip.close();
        }
    }
}
