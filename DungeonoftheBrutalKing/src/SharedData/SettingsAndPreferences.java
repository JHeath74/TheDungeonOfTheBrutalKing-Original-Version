
package SharedData;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.GraphicsDevice;
import java.awt.GraphicsEnvironment;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.RenderingHints;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.FileWriter;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JSlider;
import javax.swing.WindowConstants;
import javax.swing.border.Border;

public class SettingsAndPreferences extends JFrame {
    private static final long serialVersionUID = 1L;

    private int soundVolume = 50; // Default value
    private String resolution = "1920x1080"; // Default value
    private Map<String, String> keyMappings = new HashMap<>();
    private boolean isFullScreen = false;

    public SettingsAndPreferences() {
        GameSettings myGameSettings = new GameSettings(); // Retrieve color scheme

        setTitle("Settings");
        setSize(400, 400);
        setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
        setLayout(new GridBagLayout());

        // Set background color
        getContentPane().setBackground(myGameSettings.getColorBlack());

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10);
        gbc.fill = GridBagConstraints.HORIZONTAL;

        // Sound Volume Slider
        JLabel volumeLabel = new JLabel("Sound Volume:");
        volumeLabel.setForeground(myGameSettings.getColorWhite());
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
        resolutionLabel.setForeground(myGameSettings.getColorWhite());
        gbc.gridx = 0;
        gbc.gridy = 1;
        add(resolutionLabel, gbc);

        String[] resolutions = getSupportedResolutions();
        JComboBox<String> resolutionDropdown = new JComboBox<>(resolutions);
        resolutionDropdown.setSelectedItem(getResolution());
        gbc.gridx = 1;
        gbc.gridy = 1;
        add(resolutionDropdown, gbc);

        // Screen Mode Dropdown
        JLabel screenModeLabel = new JLabel("Screen Mode:");
        screenModeLabel.setForeground(myGameSettings.getColorWhite());
        gbc.gridx = 0;
        gbc.gridy = 4;
        add(screenModeLabel, gbc);

        String[] screenModes = {"Windowed", "Full-Screen"};
        JComboBox<String> screenModeDropdown = new JComboBox<>(screenModes);
        gbc.gridx = 1;
        gbc.gridy = 4;
        add(screenModeDropdown, gbc);

        screenModeDropdown.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String selectedMode = (String) screenModeDropdown.getSelectedItem();
                toggleScreenMode(selectedMode);
            }
        });

        // Control Mapping Button
        JButton controlMappingButton = createButton("Control Mapping");
        gbc.gridx = 0;
        gbc.gridy = 5;
        gbc.gridwidth = 2;
        add(controlMappingButton, gbc);

        controlMappingButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                openControlMappingDialog();
            }
        });

        // Save Settings Button
        JButton saveButton = createButton("Save Settings");
        gbc.gridx = 0;
        gbc.gridy = 6;
        gbc.gridwidth = 1;
        add(saveButton, gbc);

        saveButton.setPreferredSize(new Dimension((int) (150 * 1.25), (int) (50 * 1.25))); // 25% larger size
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

        // Restore Default Button
        JButton restoreDefaultButton = createButton("Restore Default");
        gbc.gridx = 1;
        gbc.gridy = 6;
        gbc.gridwidth = 1;
        add(restoreDefaultButton, gbc);

        restoreDefaultButton.setPreferredSize(new Dimension((int) (150 * 1.25), (int) (50 * 1.25))); // 25% larger size
        restoreDefaultButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                setSoundVolume(50); // Default sound volume
                setResolution("1920x1080"); // Default resolution
                toggleScreenMode("Full-Screen"); // Default screen mode

                keyMappings.put("Move Forward", "Up");
                keyMappings.put("Move Backward", "Down");
                keyMappings.put("Move Left", "Left");
                keyMappings.put("Move Right", "Right");

                JOptionPane.showMessageDialog(SettingsAndPreferences.this, "Default settings restored!");
            }
        });

        // Exit Button
        JButton exitButton = createButton("Exit");
        gbc.gridx = 0;
        gbc.gridy = 7; // Move below Save and Restore Default buttons
        gbc.gridwidth = 2; // Center the Exit button
        add(exitButton, gbc);

        exitButton.setPreferredSize(new Dimension((int) (150 * 1.25), (int) (50 * 1.25))); // 25% larger size
        exitButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                dispose();
            }
        });

        setLocationRelativeTo(null);
        setVisible(true);
    }

    private JButton createButton(String text) {
        Font gameFont = new Font("Verdana", Font.PLAIN, 18); // Use default font

        JButton button = new JButton(text) {
            @Override
            protected void paintComponent(Graphics g) {
                Graphics2D g2 = (Graphics2D) g.create();
                g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                g2.setColor(getBackground());
                g2.fillRoundRect(0, 0, getWidth(), getHeight(), 20, 20);

                // Draw yellow border
                g2.setColor(Color.YELLOW);
                g2.setStroke(new BasicStroke(2));
                g2.drawRoundRect(1, 1, getWidth() - 2, getHeight() - 2, 20, 20);

                g2.dispose();

                // Call superclass method to render text
                super.paintComponent(g);
            }

            @Override
            public void setBorder(Border border) {
                // Prevent setting a border to avoid visual issues
            }
        };
        button.setFont(gameFont); // Apply default font
        button.setBackground(Color.GRAY);
        button.setForeground(Color.WHITE); // White text
        button.setContentAreaFilled(false);
        button.setFocusPainted(false);
        button.setPreferredSize(new Dimension((int) (150 * 1.2), (int) (50 * 1.2))); // 20% larger size
        return button;
    }

    private int getSoundVolume() {
        return soundVolume;
    }

    private String getResolution() {
        return resolution;
    }

    private String[] getSupportedResolutions() {
        return new String[]{"1920x1080", "1280x720", "800x600"};
    }

    private void toggleScreenMode(String mode) {
        GraphicsDevice graphicsDevice = GraphicsEnvironment.getLocalGraphicsEnvironment().getDefaultScreenDevice();
        isFullScreen = mode.equals("Full-Screen");

        if (isFullScreen) {
            dispose();
            setUndecorated(true);
            graphicsDevice.setFullScreenWindow(this);
        } else {
            graphicsDevice.setFullScreenWindow(null);
            setUndecorated(false);
            setVisible(true);
        }
    }

    private void setSoundVolume(int volume) {
        soundVolume = volume;
    }

    private void setResolution(String res) {
        resolution = res;
    }

    private void autoSaveSettings() {
        try (FileWriter writer = new FileWriter("settings.txt")) {
            writer.write("SoundVolume=" + soundVolume + "\n");
            writer.write("Resolution=" + resolution + "\n");
            writer.write("FullScreen=" + isFullScreen + "\n");
        } catch (IOException e) {
            JOptionPane.showMessageDialog(this, "Failed to save settings: " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void openControlMappingDialog() {
        JOptionPane.showMessageDialog(this, "Control mapping dialog opened.");
    }
}
