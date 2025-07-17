
package SharedData;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.HashMap;
import java.util.LinkedHashSet;
import java.util.Map;
import java.util.Set;

public class SettingsAndPreferences extends JFrame {

    private static final long serialVersionUID = 1L;

    private int soundVolume;
    private String resolution;
    private Map<String, String> keyMappings = new HashMap<>();
    private boolean isFullScreen = false;
    private GraphicsDevice graphicsDevice;

    public SettingsAndPreferences() {
        setTitle("Settings");
        setSize(400, 400);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLayout(new GridBagLayout());

        graphicsDevice = GraphicsEnvironment.getLocalGraphicsEnvironment().getDefaultScreenDevice();

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10);
        gbc.fill = GridBagConstraints.HORIZONTAL;

        // Sound Volume Slider
        JLabel volumeLabel = new JLabel("Sound Volume:");
        gbc.gridx = 0;
        gbc.gridy = 0;
        add(volumeLabel, gbc);

        JSlider volumeSlider = new JSlider(0, 100, getSoundVolume());
        volumeSlider.setMajorTickSpacing(20);
        volumeSlider.setMinorTickSpacing(5);
        volumeSlider.setPaintTicks(true);
        volumeSlider.setPaintLabels(true);
        gbc.gridx = 1;
        gbc.gridy = 0;
        add(volumeSlider, gbc);

        // Resolution Dropdown
        JLabel resolutionLabel = new JLabel("Resolution:");
        gbc.gridx = 0;
        gbc.gridy = 1;
        add(resolutionLabel, gbc);

        String[] resolutions = getSupportedResolutions();
        JComboBox<String> resolutionDropdown = new JComboBox<>(resolutions);
        resolutionDropdown.setSelectedItem(getResolution());
        gbc.gridx = 1;
        gbc.gridy = 1;
        add(resolutionDropdown, gbc);

        // Save Button
        JButton saveButton = new JButton("Save");
        gbc.gridx = 0;
        gbc.gridy = 2;
        gbc.gridwidth = 2;
        add(saveButton, gbc);

        saveButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    setSoundVolume(volumeSlider.getValue());
                    setResolution((String) resolutionDropdown.getSelectedItem());
                    autoSaveSettings();
                    JOptionPane.showMessageDialog(SettingsAndPreferences.this, "Settings saved successfully!");
                } catch (IllegalArgumentException ex) {
                    JOptionPane.showMessageDialog(SettingsAndPreferences.this, ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
                }
            }
        });

        // Control Mapping Button
        JButton controlMappingButton = new JButton("Control Mapping");
        gbc.gridx = 0;
        gbc.gridy = 3;
        gbc.gridwidth = 2;
        add(controlMappingButton, gbc);

        controlMappingButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                openControlMappingDialog();
            }
        });

        // Full-Screen Toggle Button
        JButton fullScreenButton = new JButton("Toggle Full-Screen");
        gbc.gridx = 0;
        gbc.gridy = 4;
        gbc.gridwidth = 2;
        add(fullScreenButton, gbc);

        fullScreenButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                toggleFullScreen();
            }
        });

        setLocationRelativeTo(null);
        setVisible(true);
    }

    private void toggleFullScreen() {
        if (isFullScreen) {
            graphicsDevice.setFullScreenWindow(null); // Exit full-screen mode
            setSize(400, 400); // Restore window size
            setLocationRelativeTo(null); // Center the window
        } else {
            graphicsDevice.setFullScreenWindow(this); // Enter full-screen mode
        }
        isFullScreen = !isFullScreen;
    }

    private void openControlMappingDialog() {
        JDialog controlMappingDialog = new JDialog(this, "Control Mapping", true);
        controlMappingDialog.setSize(400, 300);
        controlMappingDialog.setLayout(new GridLayout(0, 2));

        String[] actions = {"Move Forward", "Move Backward", "Move Left", "Move Right"};
        Map<String, JTextField> actionFields = new HashMap<>();

        for (String action : actions) {
            controlMappingDialog.add(new JLabel(action + ":"));
            JTextField keyField = new JTextField(keyMappings.getOrDefault(action, ""));
            keyField.setEditable(false);
            keyField.addKeyListener(new KeyListener() {
                @Override
                public void keyTyped(KeyEvent e) {}

                @Override
                public void keyPressed(KeyEvent e) {
                    keyField.setText(KeyEvent.getKeyText(e.getKeyCode()));
                }

                @Override
                public void keyReleased(KeyEvent e) {}
            });
            actionFields.put(action, keyField);
            controlMappingDialog.add(keyField);
        }

        JButton saveMappingButton = new JButton("Save Mappings");
        controlMappingDialog.add(saveMappingButton);
        saveMappingButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                for (Map.Entry<String, JTextField> entry : actionFields.entrySet()) {
                    keyMappings.put(entry.getKey(), entry.getValue().getText());
                }
                saveKeyMappings();
                controlMappingDialog.dispose();
            }
        });

        controlMappingDialog.setLocationRelativeTo(this);
        controlMappingDialog.setVisible(true);
    }

    private void saveKeyMappings() {
        String filePath = "src/DungeonoftheBrutalKing/TextFiles/keyMappings.txt";
        try (FileWriter writer = new FileWriter(filePath)) {
            for (Map.Entry<String, String> entry : keyMappings.entrySet()) {
                writer.write(entry.getKey() + "=" + entry.getValue() + "\n");
            }
            System.out.println("Key mappings saved successfully.");
        } catch (IOException e) {
            System.err.println("Failed to save key mappings: " + e.getMessage());
        }
    }

    private String[] getSupportedResolutions() {
        GraphicsEnvironment ge = GraphicsEnvironment.getLocalGraphicsEnvironment();
        GraphicsDevice gd = ge.getDefaultScreenDevice();
        DisplayMode[] displayModes = gd.getDisplayModes();

        Set<String> resolutionsSet = new LinkedHashSet<>();
        for (DisplayMode mode : displayModes) {
            int width = mode.getWidth();
            int height = mode.getHeight();
            resolutionsSet.add(width + "x" + height);
        }

        return resolutionsSet.toArray(new String[0]);
    }

    protected void setResolution(String selectedItem) {
        if (selectedItem != null && !selectedItem.isEmpty()) {
            this.resolution = selectedItem;
        } else {
            throw new IllegalArgumentException("Resolution cannot be empty.");
        }
    }

    protected void setSoundVolume(int value) {
        if (value >= 0 && value <= 100) {
            this.soundVolume = value;
        } else {
            throw new IllegalArgumentException("Volume must be between 0 and 100.");
        }
    }

    private String getResolution() {
        return resolution != null ? resolution : "1920x1080";
    }

    private int getSoundVolume() {
        return soundVolume;
    }

    protected void autoSaveSettings() {
        String filePath = "src/DungeonoftheBrutalKing/TextFiles/settings.txt";
        try (FileWriter writer = new FileWriter(filePath)) {
            writer.write("SoundVolume=" + soundVolume + "\n");
            writer.write("Resolution=" + resolution + "\n");
            System.out.println("Settings auto-saved to file: Volume = " + soundVolume + ", Resolution = " + resolution);
        } catch (IOException e) {
            System.err.println("Failed to save settings: " + e.getMessage());
        }
    }

    protected void loadSettings() throws IOException {
        String filePath = "src/DungeonoftheBrutalKing/TextFiles/settings.txt";
        try (BufferedReader reader = new BufferedReader(new FileReader(filePath))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split("=");
                if (parts.length == 2) {
                    switch (parts[0]) {
                        case "SoundVolume":
                            setSoundVolume(Integer.parseInt(parts[1]));
                            break;
                        case "Resolution":
                            setResolution(parts[1]);
                            break;
                    }
                }
            }
        }
    }

    public static void main(String[] args) {
        new SettingsAndPreferences();
    }
}
