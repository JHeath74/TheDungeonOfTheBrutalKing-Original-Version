package DungeonoftheBrutalKing.DevTools;

import DungeonoftheBrutalKing.Character;
import DungeonoftheBrutalKing.MainGameScreen;
import DungeonoftheBrutalKing.Maps.DungeonLevel;
import DungeonoftheBrutalKing.SharedData.LocationType;

import javax.swing.*;
import java.awt.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Map;

/**
 * Developer dialog that shows player position and known special location coordinates.
 */
public class LocationCoordinatesDialog extends JDialog {
    private static final long serialVersionUID = 1L;
    private static final DateTimeFormatter TIME_FORMAT = DateTimeFormatter.ofPattern("HH:mm:ss");

    private final MainGameScreen mainGameScreen;
    private final List<DungeonLevel> knownLevels;
    private final JTextArea textArea;
    private final JCheckBox stairsFilterCheckBox;
    private final JCheckBox innFilterCheckBox;
    private final JCheckBox doorFilterCheckBox;
    private final JCheckBox otherFilterCheckBox;
    private final JCheckBox currentLevelOnlyCheckBox;
    private final JLabel currentLevelLabel;
    private final JCheckBox autoRefreshCheckBox;
    private final JSpinner refreshIntervalSpinner;
    private final JLabel refreshStatusLabel;
    private final Timer autoRefreshTimer;

    private long refreshCount = 0;
    private long autoRefreshCount = 0;
    private LocalTime lastRefreshTime = null;

    public LocationCoordinatesDialog(JFrame parent) {
        super(parent, "Location Coordinates", true);
        setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);

        this.mainGameScreen = (parent instanceof MainGameScreen) ? (MainGameScreen) parent : null;
        this.knownLevels = loadKnownLevels();

        textArea = new JTextArea(24, 74);
        textArea.setEditable(false);
        textArea.setFont(new Font(Font.MONOSPACED, Font.PLAIN, 12));

        stairsFilterCheckBox = new JCheckBox("Stairs", true);
        innFilterCheckBox = new JCheckBox("Inn", true);
        doorFilterCheckBox = new JCheckBox("Doors", true);
        otherFilterCheckBox = new JCheckBox("Other", true);
        currentLevelOnlyCheckBox = new JCheckBox("Only current dungeon level", false);
        currentLevelLabel = new JLabel();

        autoRefreshCheckBox = new JCheckBox("Auto-refresh", true);
        refreshIntervalSpinner = new JSpinner(new SpinnerNumberModel(1000, 250, 30000, 250));
        refreshStatusLabel = new JLabel();

        autoRefreshTimer = new Timer(getRefreshIntervalMillis(), e -> refreshText(true));
        autoRefreshTimer.setRepeats(true);
        autoRefreshTimer.start();

        JButton refreshButton = new JButton("Refresh");
        refreshButton.addActionListener(e -> refreshText(false));

        JButton closeButton = new JButton("Close");
        closeButton.addActionListener(e -> dispose());

        JButton selectAllFiltersButton = new JButton("Select All");
        selectAllFiltersButton.addActionListener(e -> setAllCategoryFilters(true));

        JButton clearAllFiltersButton = new JButton("Clear All");
        clearAllFiltersButton.addActionListener(e -> setAllCategoryFilters(false));

        JPanel filtersPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        filtersPanel.add(new JLabel("Show:"));
        filtersPanel.add(stairsFilterCheckBox);
        filtersPanel.add(innFilterCheckBox);
        filtersPanel.add(doorFilterCheckBox);
        filtersPanel.add(otherFilterCheckBox);
        filtersPanel.add(selectAllFiltersButton);
        filtersPanel.add(clearAllFiltersButton);

        JPanel levelFilterPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        levelFilterPanel.add(currentLevelOnlyCheckBox);
        levelFilterPanel.add(currentLevelLabel);

        JPanel autoRefreshPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        autoRefreshPanel.add(autoRefreshCheckBox);
        autoRefreshPanel.add(new JLabel("every"));
        autoRefreshPanel.add(refreshIntervalSpinner);
        autoRefreshPanel.add(new JLabel("ms"));

        JPanel controlsPanel = new JPanel(new BorderLayout(0, 4));
        controlsPanel.add(filtersPanel, BorderLayout.NORTH);
        controlsPanel.add(levelFilterPanel, BorderLayout.CENTER);

        JPanel lowerControlsPanel = new JPanel(new BorderLayout(0, 4));
        lowerControlsPanel.add(autoRefreshPanel, BorderLayout.NORTH);
        lowerControlsPanel.add(refreshStatusLabel, BorderLayout.SOUTH);

        controlsPanel.add(lowerControlsPanel, BorderLayout.SOUTH);

        JPanel bottom = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        bottom.add(refreshButton);
        bottom.add(closeButton);

        setLayout(new BorderLayout(8, 8));
        add(controlsPanel, BorderLayout.NORTH);
        add(new JScrollPane(textArea), BorderLayout.CENTER);
        add(bottom, BorderLayout.SOUTH);

        stairsFilterCheckBox.addActionListener(e -> refreshText(false));
        innFilterCheckBox.addActionListener(e -> refreshText(false));
        doorFilterCheckBox.addActionListener(e -> refreshText(false));
        otherFilterCheckBox.addActionListener(e -> refreshText(false));
        currentLevelOnlyCheckBox.addActionListener(e -> refreshText(false));

        autoRefreshCheckBox.addActionListener(e -> updateAutoRefreshState());
        refreshIntervalSpinner.addChangeListener(e -> updateAutoRefreshState());

        addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                autoRefreshTimer.stop();
            }

            @Override
            public void windowClosed(WindowEvent e) {
                autoRefreshTimer.stop();
            }
        });

        getRootPane().setDefaultButton(refreshButton);
        registerEscapeToClose();
        refreshText(false);

        pack();
        setLocationRelativeTo(parent);
    }

    private void refreshText(boolean fromAutoRefresh) {
        refreshCount++;
        if (fromAutoRefresh) {
            autoRefreshCount++;
        }
        lastRefreshTime = LocalTime.now();
        updateCurrentLevelLabel();
        textArea.setText(buildReport());
        textArea.setCaretPosition(0);
        updateStatusLabel();
    }

    private void updateCurrentLevelLabel() {
        int level = getCurrentPlayerLevel();
        if (level < 0) {
            currentLevelLabel.setText("(Current level: n/a)");
            return;
        }
        currentLevelLabel.setText("(Current level: " + level + ")");
    }

    private void updateStatusLabel() {
        String state = autoRefreshCheckBox.isSelected() ? "ON" : "OFF";
        String last = (lastRefreshTime == null) ? "never" : lastRefreshTime.format(TIME_FORMAT);
        refreshStatusLabel.setText(String.format(
                "Auto-refresh: %s every %d ms | total refreshes: %d | auto refreshes: %d | last refresh: %s",
                state,
                getRefreshIntervalMillis(),
                refreshCount,
                autoRefreshCount,
                last
        ));
    }

    private int getRefreshIntervalMillis() {
        Object value = refreshIntervalSpinner.getValue();
        if (value instanceof Number) {
            return ((Number) value).intValue();
        }
        return 1000;
    }

    private void updateAutoRefreshState() {
        int intervalMs = getRefreshIntervalMillis();
        autoRefreshTimer.setDelay(intervalMs);
        autoRefreshTimer.setInitialDelay(intervalMs);

        if (autoRefreshCheckBox.isSelected()) {
            if (!autoRefreshTimer.isRunning()) {
                autoRefreshTimer.start();
            }
        } else {
            autoRefreshTimer.stop();
        }

        updateStatusLabel();
    }

    private String buildReport() {
        StringBuilder sb = new StringBuilder();

        appendPlayerSection(sb);
        sb.append("\n");
        appendSpecialLocationsSection(sb);

        return sb.toString();
    }

    private void appendPlayerSection(StringBuilder sb) {
        sb.append("Current Player Position\n");
        sb.append("-----------------------\n");

        if (mainGameScreen == null) {
            sb.append("No active MainGameScreen reference was provided.\n");
            return;
        }

        Character player = mainGameScreen.getPlayer();
        if (player == null) {
            sb.append("Player instance not available.\n");
            return;
        }

        int x = (int) Math.round(player.getX());
        int y = (int) Math.round(player.getY());
        int level = player.getDungeonLevel();

        DungeonLevel activeLevel = mainGameScreen.getCurrentDungeonLevelInstance();
        if (activeLevel == null) {
            activeLevel = findLevel(level);
        }

        LocationType locationType = LocationType.EMPTY;
        if (activeLevel != null) {
            locationType = activeLevel.getSpecialLocation(x, y);
        }

        sb.append(String.format("Dungeon Level : %d%n", level));
        sb.append(String.format("X, Y          : (%d, %d)%n", x, y));
        sb.append(String.format("Location Type : %s%n", locationType));
    }

    private void appendSpecialLocationsSection(StringBuilder sb) {
        sb.append("Special Locations\n");
        sb.append("-----------------\n");
        sb.append("Filters: ");
        sb.append(stairsFilterCheckBox.isSelected() ? "Stairs " : "");
        sb.append(innFilterCheckBox.isSelected() ? "Inn " : "");
        sb.append(doorFilterCheckBox.isSelected() ? "Doors " : "");
        sb.append(otherFilterCheckBox.isSelected() ? "Other " : "");
        if (currentLevelOnlyCheckBox.isSelected()) {
            sb.append("| Current level only ");
        }
        sb.append("\n\n");

        int currentLevel = getCurrentPlayerLevel();
        List<LocationRow> rows = new ArrayList<>();
        for (DungeonLevel level : knownLevels) {
            if (level == null) {
                continue;
            }
            if (currentLevelOnlyCheckBox.isSelected() && level.getDungeonLevelNumber() != currentLevel) {
                continue;
            }

            Map<java.awt.Point, LocationType> locations = level.getSpecialLocations();
            for (Map.Entry<java.awt.Point, LocationType> entry : locations.entrySet()) {
                if (entry.getKey() == null || entry.getValue() == null || entry.getValue() == LocationType.EMPTY) {
                    continue;
                }
                if (!isLocationTypeVisible(entry.getValue())) {
                    continue;
                }

                java.awt.Point p = entry.getKey();
                rows.add(new LocationRow(entry.getValue(), level.getDungeonLevelNumber(), p.x, p.y));
            }
        }

        rows.sort(Comparator
                .comparing((LocationRow r) -> r.locationType.name())
                .thenComparingInt(r -> r.level)
                .thenComparingInt(r -> r.x)
                .thenComparingInt(r -> r.y));

        if (rows.isEmpty()) {
            sb.append("No special locations found in loaded dungeon levels.\n");
            return;
        }

        sb.append(String.format("%-30s %-6s %-5s %-5s%n", "Location", "Level", "X", "Y"));
        sb.append(String.format("%-30s %-6s %-5s %-5s%n", "--------", "-----", "-", "-"));

        for (LocationRow row : rows) {
            sb.append(String.format("%-30s %-6d %-5d %-5d%n", row.locationType, row.level, row.x, row.y));
        }
    }

    private int getCurrentPlayerLevel() {
        if (mainGameScreen == null || mainGameScreen.getPlayer() == null) {
            return -1;
        }
        return mainGameScreen.getPlayer().getDungeonLevel();
    }

    private void setAllCategoryFilters(boolean selected) {
        stairsFilterCheckBox.setSelected(selected);
        innFilterCheckBox.setSelected(selected);
        doorFilterCheckBox.setSelected(selected);
        otherFilterCheckBox.setSelected(selected);
        refreshText(false);
    }

    private boolean isLocationTypeVisible(LocationType locationType) {
        if (locationType == null || locationType == LocationType.EMPTY) {
            return false;
        }

        return switch (locationType) {
            case STAIRS_UP, STAIRS_DOWN -> stairsFilterCheckBox.isSelected();
            case THE_RUSTY_TANKARD, WELCOME_MESSAGE_RUSTY_TANKARD -> innFilterCheckBox.isSelected();
            case DOOR -> doorFilterCheckBox.isSelected();
            default -> otherFilterCheckBox.isSelected();
        };
    }

    private DungeonLevel findLevel(int levelNumber) {
        for (DungeonLevel level : knownLevels) {
            if (level != null && level.getDungeonLevelNumber() == levelNumber) {
                return level;
            }
        }
        return null;
    }

    private List<DungeonLevel> loadKnownLevels() {
        List<DungeonLevel> levels = new ArrayList<>();

        for (int i = 1; i <= 10; i++) {
            String fqcn = "DungeonoftheBrutalKing.Maps.DungeonLevel" + i;
            try {
                Class<?> clazz = Class.forName(fqcn);
                Object instance = clazz.getDeclaredConstructor().newInstance();
                if (instance instanceof DungeonLevel) {
                    levels.add((DungeonLevel) instance);
                }
            } catch (Exception ignored) {
                // If a level cannot be created, skip it and keep showing available data.
            }
        }

        return levels;
    }

    private void registerEscapeToClose() {
        JRootPane root = getRootPane();
        InputMap im = root.getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW);
        ActionMap am = root.getActionMap();

        im.put(KeyStroke.getKeyStroke("ESCAPE"), "close");
        am.put("close", new AbstractAction() {
            @Override
            public void actionPerformed(java.awt.event.ActionEvent e) {
                dispose();
            }
        });
    }

    private static class LocationRow {
        private final LocationType locationType;
        private final int level;
        private final int x;
        private final int y;

        private LocationRow(LocationType locationType, int level, int x, int y) {
            this.locationType = locationType;
            this.level = level;
            this.x = x;
            this.y = y;
        }
    }
}
