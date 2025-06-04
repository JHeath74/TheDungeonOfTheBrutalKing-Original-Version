
package DungeonoftheBrutalKing;

import javax.swing.*;
import java.io.IOException;
import java.time.LocalTime;
import java.util.Timer;
import java.util.TimerTask;

public class TimeClock {

    public enum Month {
        REBIRTH, AWAKENING, WINDS, RAINS, SOWINGS, FIRST_FRUITS,
        HARVEST, FINAL_REAPING, THE_FALL, DARKNESS, COLD_WINDS, LIGHTS
    }

    private static TimeClock timeClock;
    
    private Month currentMonth;
    private int currentDay;
    private LocalTime currentTime;
    private final Timer timer;
    private MainGameScreen myMainGameScreen;
    int startTime = timeClock.getCurrentHour();

    static {
        timeClock = new TimeClock(Month.REBIRTH, null); // Initialize in a static block
    }
    TimeClock(Month startMonth, JTextPane messageTextPane) {
        this.currentMonth = startMonth;
        this.currentDay = 1;
        this.currentTime = LocalTime.of(0, 0);
        this.timer = new Timer();
    }

    public synchronized void startSimulation() {
        timer.scheduleAtFixedRate(new TimerTask() {
            @Override
            public void run() {
                advanceTime();
            }
        }, 0, 300000); // 300,000 ms = 5 real-time minutes
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

    private synchronized Month getNextMonth(Month month) {
        int index = (month.ordinal() + 1) % Month.values().length;
        return Month.values()[index];
    }

    private void updateOutputField() {
        SwingUtilities.invokeLater(() -> {
            if (myMainGameScreen != null && myMainGameScreen.MessageTextPane != null) {
                myMainGameScreen.MessageTextPane.setText(String.format("Day %d, %s | Time: %s",
                        currentDay, currentMonth, currentTime));
            } else {
                System.err.println("myMainGameScreen or MessageTextPane is null");
            }
            try {
                myMainGameScreen = new MainGameScreen();
            } catch (IOException e) {
                e.printStackTrace();
            }
        });
    }

    public synchronized void stopClock() {
        timer.cancel();
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
        int elapsedDaysInHours = (currentDay - 1) * 24; // Convert days to hours
        int elapsedHours = currentTime.getHour(); // Add current hour
        return elapsedDaysInHours + elapsedHours;
    }

   
        public int getCurrentHour() {
            return timeClock.getCurrentHour(); // Access the initialized object
        }

		public static TimeClock Singleton() {
			// TODO Auto-generated method stub
			return null;
		}
}
