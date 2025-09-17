
package NPC.DerRathskellerBarAndGrille;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Map;
import java.util.Random;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextArea;
import javax.swing.SwingConstants;

import DungeonoftheBrutalKing.Charecter;
import DungeonoftheBrutalKing.MainGameScreen;
import SharedData.GameSettings;
import Status.StatusManager;

public class Innkeeper {
    private Map<String, Integer> foodItems;
    private Map<String, Integer> drinkItems;
    private JPanel mainPanel;


    StatusManager statusManager = new StatusManager();
    Charecter myChar = new Charecter();
    MainGameScreen myMainGameScreen;
    GameSettings myGameSettings = new GameSettings();

    public Innkeeper(JPanel mainPanel, JTextArea displayArea) {
        this.mainPanel = mainPanel;


        // Initialize food items with prices
        foodItems = Map.of(
            "Bread", 5,
            "Meat", 10,
            "Cheese", 7,
            "Soup", 8,
            "Fruit", 4,
            "Vegetables", 6
        );

        // Initialize drink items with prices
        drinkItems = Map.of(
            "Water", 3,
            "Ale", 5,
            "Wine", 12,
            "Juice", 6,
            "Milk", 4,
            "Tea", 5
        );

        setupUI();
    }


void setupUI() {
    mainPanel.removeAll();
    mainPanel.setLayout(new BorderLayout());

    // Resize and add image at the top center
    ImageIcon originalIcon = new ImageIcon(GameSettings.NPCImagePath + "Innkeeper - innkeeper.jpeg");
    Image scaledImage = originalIcon.getImage().getScaledInstance(400, 300, Image.SCALE_SMOOTH); // Adjust size as needed
    ImageIcon scaledIcon = new ImageIcon(scaledImage);
    JLabel imageLabel = new JLabel(scaledIcon);
    imageLabel.setHorizontalAlignment(SwingConstants.CENTER);
    mainPanel.add(imageLabel, BorderLayout.NORTH);

    // Add buttons side by side below the image
    JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 10, 10));

    JButton foodButton = new JButton("Buy Food");
    JButton drinkButton = new JButton("Buy Drink");
    JButton exitButton = new JButton("Exit");

    // Add action listeners for buttons
    foodButton.addActionListener(new ActionListener() {
        @Override
        public void actionPerformed(ActionEvent e) {
            handlePurchase(foodItems, "Food");
        }
    });

    drinkButton.addActionListener(new ActionListener() {
        @Override
        public void actionPerformed(ActionEvent e) {
            handlePurchase(drinkItems, "Drink");
        }
    });

    exitButton.addActionListener(new ActionListener() {
        @Override
        public void actionPerformed(ActionEvent e) {
            new DerRathskellerBarAndGrille(mainPanel);
        }
    });

    buttonPanel.add(foodButton);
    buttonPanel.add(drinkButton);
    buttonPanel.add(exitButton);

    mainPanel.add(buttonPanel, BorderLayout.SOUTH);
    mainPanel.revalidate();
    mainPanel.repaint();
}


private void handlePurchase(Map<String, Integer> items, String type) {
    if (items == null || items.isEmpty()) {
       myMainGameScreen.setMessageTextPane("No " + type + " items are available for purchase.\n");
        return;
    }

    Object[] itemArray = items.keySet().toArray();
    if (itemArray.length == 0) {
    	myMainGameScreen.setMessageTextPane("No " + type + " items are available for purchase.\n");
        return;
    }

    String selectedItem = (String) JOptionPane.showInputDialog(
        null,
        "Select a " + type + " to buy:",
        "Buy " + type,
        JOptionPane.QUESTION_MESSAGE,
        null,
        itemArray,
        itemArray[0] // Safe because itemArray is not empty
    );

    if (selectedItem != null) {
        int cost = items.get(selectedItem);
        if (myChar.removeGold(cost)) {
        	myMainGameScreen.setMessageTextPane("You bought " + selectedItem + " for " + cost + " silver.\n");
            if (type.equals("Food")) {
                statusManager.removeStatusByName("Hunger");
            }
            if (new Random().nextBoolean()) { // 50% chance to add to inventory
                myChar.addToInventory(selectedItem);
                myMainGameScreen.setMessageTextPane(selectedItem + " was added to your inventory.\n");
            }
        } else {
        	myMainGameScreen.setMessageTextPane("You don't have enough silver to buy " + selectedItem + ".\n");
        }
    }
}


}
