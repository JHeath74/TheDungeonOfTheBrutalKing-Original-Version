//Related Clases
//DerRathskellerBarAndGrille.java
//Inn.java
//Innkeeper.java
//InformationProvider.java
package NPC;

import java.util.Arrays;
import java.util.List;

public class Innkeeper {
    private List<String> itemsForSale;

    public Innkeeper() {
        itemsForSale = Arrays.asList("Food", "Drink", "Information");
    }

    public List<String> getItemsForSale() {
        return itemsForSale;
    }

    public String sellItem(String item) {
        if (itemsForSale.contains(item)) {
            return "You bought " + item;
        } else {
            return "Item not available";
        }
    }
}
