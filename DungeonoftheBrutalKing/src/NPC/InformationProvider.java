//Related Clases
//DerRathskellerBarAndGrille.java
//Inn.java
//Innkeeper.java
//InformationProvider.java
package NPC;

import java.util.Arrays;
import java.util.List;
import java.util.Random;

public class InformationProvider {
    private List<String> informationList;
    private Random random;

    public InformationProvider() {
        informationList = Arrays.asList(
            "Information 1",
            "Information 2",
            "Information 3",
            "Information 4",
            "Information 5",
            "Information 6",
            "Information 7",
            "Information 8",
            "Information 9",
            "Information 10"
        );
        random = new Random();
    }

    public String provideInformation() {
        int index = random.nextInt(informationList.size());
        return informationList.get(index);
    }
}
