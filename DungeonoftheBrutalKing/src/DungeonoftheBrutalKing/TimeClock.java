
package DungeonoftheBrutalKing;

import java.io.IOException;
import java.text.ParseException;
import java.time.LocalTime;
import javax.swing.JTextPane;
import javax.swing.Timer;


public class TimeClock {

	public TimeClock(Month startMonth, MainGameScreen mainGameScreen) {
	    this.currentMonth = startMonth;
	    this.currentDay = 1;
	    this.currentTime = LocalTime.of(0, 0);
	    this.startTime = currentTime.getHour();
	    this.myMainGameScreen = mainGameScreen;
	}
	
    public enum Month {
        REBIRTH, AWAKENING, WINDS, RAINS, SOWINGS, FIRST_FRUITS,
        HARVEST, FINAL_REAPING, THE_FALL, DARKNESS, COLD_WINDS, LIGHTS
    }

    private static TimeClock timeClock = new TimeClock(Month.REBIRTH, null, null);

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
        timer = new Timer(300000, __ -> advanceTime()); // 5 minutes
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
    
    public String getTimeOfDay() {
        int hour = currentTime.getHour();
        if (hour >= 5  && hour < 8)  return "Dawn";
        if (hour >= 8  && hour < 12) return "Morning";
        if (hour >= 12 && hour < 17) return "Afternoon";
        if (hour >= 17 && hour < 20) return "Evening";
        if (hour >= 20 && hour < 23) return "Night";
        return "Midnight";
    }
    
    public boolean isDaytime() {
        int hour = currentTime.getHour();
        return hour >= 6 && hour < 20;
    }

    public boolean isNighttime() {
        return !isDaytime();
    }

    public int getCurrentHour() {
        return currentTime.getHour();
    }

    public static TimeClock Singleton() {
        if (timeClock == null) {
            timeClock = new TimeClock(Month.REBIRTH, null, null);
        }
        return timeClock;
    }
    

public String getCurrentSeason() {
    return switch (currentMonth) {
        case REBIRTH, AWAKENING, WINDS -> "Spring";
        case RAINS, SOWINGS, FIRST_FRUITS -> "Summer";
        case HARVEST, FINAL_REAPING, THE_FALL -> "Autumn";
        case DARKNESS, COLD_WINDS, LIGHTS -> "Winter";
    };
}


    public String getCurrentTimeString() {
        return String.format("Time: %02d:00 (%s)\nDay: %d | %s\nMonth: %s | %s",
            currentTime.getHour(), getTimeOfDay(),
            currentDay, getCurrentSeason(),
            currentMonth, isDaytime() ? "Day" : "Night");
    }

    public int getElapsedSeconds() {
        if (startMillis == 0) return 0;
        long now = System.currentTimeMillis();
        return (int)((now - startMillis) / 1000);
    }
}
