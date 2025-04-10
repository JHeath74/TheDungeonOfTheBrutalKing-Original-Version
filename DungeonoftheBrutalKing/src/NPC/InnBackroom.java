
package NPC;

import javax.swing.*;

public class InnBackroom {
    private JTextArea displayArea;

    public void innBackroom() {
       
    }

    public void loadBackroom() {
        String[] items = {"Armor", "Shield", "Weapon"};
        String selectedItem = (String) JOptionPane.showInputDialog(
            null,
            "What would you like to buy?",
            "Backroom",
            JOptionPane.QUESTION_MESSAGE,
            null,
            items,
            items[0]
        );

        if (selectedItem != null) {
            switch (selectedItem) {
                case "Armor":
                    displayArea.append("You bought an Armor.\n");
                    break;
                case "Shield":
                    displayArea.append("You bought a Shield.\n");
                    break;
                case "Weapon":
                    displayArea.append("You bought a Weapon.\n");
                    break;
                default:
                    displayArea.append("Invalid selection.\n");
            }
        } else {
            displayArea.append("You left the backroom without buying anything.\n");
        }
    }
}
