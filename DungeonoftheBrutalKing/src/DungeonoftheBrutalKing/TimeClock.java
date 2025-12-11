
package DungeonoftheBrutalKing;

import java.io.IOException;
import java.text.ParseException;
import java.time.LocalTime;
import javax.swing.JTextPane;
import javax.swing.Timer;

public class TimeClock {

    public enum Month {
        REBIRTH, AWAKENING, WINDS, RAINS, SOWINGS, FIRST_FRUITS,
        HARVEST, FINAL_REAPING, THE_FALL, DARKNESS, COLD_WINDS, LIGHTS
    }

    private static final TimeClock timeClock = new TimeClock(Month.REBIRTH, null, null);

    private Month currentMonth;
    private int currentDay;
    private LocalTime currentTime;
    private Timer timer;
    private MainGameScreen myMainGameScreen;

    int startTime;
    private long startMillis = 0;

    public TimeClock(Month startMonth, JTextPane messageTextPane, MainGameScreen mainGameScreen) {
        this.currentMonth = startMonth;
        this.currentDay = 1;
        this.currentTime = LocalTime.of(0, 0);
        this.startTime = currentTime.getHour();
        this.myMainGameScreen = mainGameScreen;
    }

    TimeClock(Month startMonth, JTextPane messageTextPane) {
        this(startMonth, messageTextPane, null);
    }

    public synchronized void startSimulation() {
        if (timer != null && timer.isRunning()) {
            timer.stop();
        }
        startMillis = System.currentTimeMillis();
        timer = new Timer(300000, e -> advanceTime()); // 5 minutes
        timer.setRepeats(true);
        timer.start();
    }

    public void startClock() {
        startSimulation();
    }

    private synchronized void advanceTime() {
        currentTime = currentTime.plusHours(1);

        if (currentTime.getHour() == 0) {
            currentDay++;
        }

        if (currentDay > 30) {
            currentDay = 1;
            currentMonth = getNextMonth(currentMonth);
        }

        updateOutputField();
    }

    private Month getNextMonth(Month month) {
        int index = (month.ordinal() + 1) % Month.values().length;
        return Month.values()[index];
    }

    private void updateOutputField() {
        try {
            if (myMainGameScreen == null) {
                myMainGameScreen = MainGameScreen.getInstance();
            }
            myMainGameScreen.updateCombatMessageArea(getCurrentTimeString());
        } catch (IOException | InterruptedException | ParseException e) {
            e.printStackTrace();
        }
    }

    public synchronized void stopClock() {
        if (timer != null) {
            timer.stop();
        }
    }

    public int getCurrentDay() {
        return currentDay;
    }

    public Month getCurrentMonth() {
        return currentMonth;
    }

    public LocalTime getCurrentTime() {
        return currentTime;
    }

    public int getElapsedTime() {
        int elapsedDaysInHours = (currentDay - 1) * 24;
        int elapsedHours = currentTime.getHour();
        return elapsedDaysInHours + elapsedHours;
    }

    public int getCurrentHour() {
        return currentTime.getHour();
    }

    public static TimeClock Singleton() {
        return timeClock;
    }

    public String getCurrentTimeString() {
        return String.format("Time: %s\nDay: %d\nMonth: %s", currentTime, currentDay, currentMonth);
    }

    public int getElapsedSeconds() {
        if (startMillis == 0) return 0;
        long now = System.currentTimeMillis();
        return (int)((now - startMillis) / 1000);
    }
}
