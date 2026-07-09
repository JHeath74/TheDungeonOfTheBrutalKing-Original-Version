package DungeonoftheBrutalKing.Locations.TheRustyTankard;

import DungeonoftheBrutalKing.Character;
import DungeonoftheBrutalKing.MainGameScreen;
import DungeonoftheBrutalKing.SharedData.GameSettings;
import DungeonoftheBrutalKing.SharedData.RandomFactory;
import DungeonoftheBrutalKing.Status.StatusManager;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.SwingConstants;
import javax.swing.SwingUtilities;
import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.Image;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
import java.io.IOException;
import java.text.ParseException;
import java.util.Map;

public class Innkeeper {
    private static final int BOTTOM_BAR_HEIGHT = 80;

    private final Map<String, Integer> foodItems;
    private final Map<String, Integer> drinkItems;

    private final JPanel mainPanel;
    private final MainGameScreen mainGameScreen;

    private final StatusManager statusManager = new StatusManager();

    private final ImageIcon baseIcon;
    private final JLabel imageLabel;

    public Innkeeper(JPanel mainPanel, MainGameScreen mainGameScreen) {
        this.mainPanel = mainPanel;
        this.mainGameScreen = mainGameScreen;

        this.foodItems = Map.of(
                "Bread", 5,
                "Meat", 10,
                "Cheese", 7,
                "Soup", 8,
                "Fruit", 4,
                "Vegetables", 6
        );

        this.drinkItems = Map.of(
                "Water", 3,
                "Ale", 5,
                "Wine", 12,
                "Juice", 6,
                "Milk", 4,
                "Tea", 5
        );

        // Use the static getter for NPC image path
        this.baseIcon = new ImageIcon(GameSettings.getNPCImagePath() + "TheRustyTankard/Innkeeper - innkeeper.jpeg");
        this.imageLabel = new JLabel();
        this.imageLabel.setHorizontalAlignment(SwingConstants.CENTER);

        setupUI();
    }

    public void setupUI() {
        if (mainPanel == null) return;

        mainPanel.removeAll();
        mainPanel.setLayout(new BorderLayout());

        mainPanel.add(imageLabel, BorderLayout.CENTER);

        Runnable refreshImage = this::refreshScaledImage;
        mainPanel.addComponentListener(new ComponentAdapter() {
            @Override
            public void componentResized(ComponentEvent e) {
                refreshImage.run();
            }
        });
        SwingUtilities.invokeLater(refreshImage);

        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 10, 10));
        JButton foodButton = new JButton("Buy Food");
        JButton drinkButton = new JButton("Buy Drink");
        JButton exitButton = new JButton("Exit");

        foodButton.addActionListener(_ -> handlePurchase(foodItems, ItemType.FOOD));
        drinkButton.addActionListener(_ -> handlePurchase(drinkItems, ItemType.DRINK));
        exitButton.addActionListener(_ -> exitToRustyTankard());

        buttonPanel.add(foodButton);
        buttonPanel.add(drinkButton);
        buttonPanel.add(exitButton);

        mainPanel.add(buttonPanel, BorderLayout.SOUTH);

        mainPanel.revalidate();
        mainPanel.repaint();
    }

    private void refreshScaledImage() {
        if (mainPanel == null) return;

        int w = Math.max(1, mainPanel.getWidth());
        int h = Math.max(1, mainPanel.getHeight());

        int targetW = w;
        int targetH = Math.max(1, h - BOTTOM_BAR_HEIGHT);

        Image scaled = baseIcon.getImage().getScaledInstance(targetW, targetH, Image.SCALE_SMOOTH);
        imageLabel.setIcon(new ImageIcon(scaled));

        mainPanel.revalidate();
        mainPanel.repaint();
    }

    private void handlePurchase(Map<String, Integer> items, ItemType type) {
        if (items == null || items.isEmpty()) {
            uiSafely(() -> mainGameScreen.setMessageTextPane("No " + type.label + " items are available for purchase.\n"));
            return;
        }

        Object[] itemArray = items.keySet().toArray();
        if (itemArray.length == 0) {
            uiSafely(() -> mainGameScreen.setMessageTextPane("No " + type.label + " items are available for purchase.\n"));
            return;
        }

        String selectedItem = (String) JOptionPane.showInputDialog(
                null,
                "Select a " + type.label + " to buy:",
                "Buy " + type.label,
                JOptionPane.QUESTION_MESSAGE,
                null,
                itemArray,
                itemArray[0]
        );

        if (selectedItem == null) return;

        Integer costObj = items.get(selectedItem);
        int cost = costObj != null ? costObj : 0;

        Character player;
        try {
            player = Character.getInstance();
        } catch (RuntimeException ex) {
            return;
        }
        if (player == null) return;

        if (player.removeGold(cost)) {
            uiSafely(() -> mainGameScreen.setMessageTextPane(
                    "You bought " + selectedItem + " for " + cost + " silver.\n"
            ));

            if (type == ItemType.FOOD) {
                statusManager.removeStatusByName("Hunger", player);
                player.setFood(player.getFood() + 1);
            } else if (type == ItemType.DRINK) {
                player.setWater(player.getWater() + 1);
            }

            boolean addToInventory = RandomFactory.gameplayInt(2) == 1;
            if (addToInventory) {
                player.addToInventory(selectedItem);
                uiSafely(() -> mainGameScreen.setMessageTextPane(
                        selectedItem + " was added to your inventory.\n"
                ));
            }
        } else {
            uiSafely(() -> mainGameScreen.setMessageTextPane(
                    "You don't have enough silver to buy " + selectedItem + ".\n"
            ));
        }
    }

    private void exitToRustyTankard() {
        if (mainGameScreen == null) return;

        JPanel fallback = mainPanel != null ? mainPanel : new JPanel();
        JPanel targetPanel;

        try {
            targetPanel = new TheRustyTankard(fallback, mainGameScreen).getMainPanel();
        } catch (RuntimeException ex) {
            targetPanel = fallback;
        }

        JPanel finalTarget = targetPanel != null ? targetPanel : fallback;
        // Call replaceWithAnyPanel statically as required
        uiSafely(() -> MainGameScreen.replaceWithAnyPanel(finalTarget));
    }

    private void uiSafely(UiAction action) {
        if (mainGameScreen == null || action == null) return;
        try {
            action.run();
        } catch (IOException | InterruptedException | ParseException | RuntimeException ignored) {
            // Keep UI flow running even if messaging/navigation fails.
        }
    }

    @FunctionalInterface
    private interface UiAction {
        void run() throws IOException, InterruptedException, ParseException;
    }

    private enum ItemType {
        FOOD("Food"),
        DRINK("Drink");

        final String label;

        ItemType(String label) {
            this.label = label;
        }
    }
}
