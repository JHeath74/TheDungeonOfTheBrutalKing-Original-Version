package DungeonoftheBrutalKing.DevTools;

import DungeonoftheBrutalKing.Character;
import DungeonoftheBrutalKing.SharedData.Guild;
import DungeonoftheBrutalKing.Guild.SilverwardSentinels.Spells.BlessingofRestoration;

public class TestBlessingofRestoration {
    public static void main(String[] args) {
        System.out.println("== Blessing of Restoration Tests ==");

        // Scenario 1: Proper Silverward caster with sufficient MP
        Character paladin = new Character();
        paladin.setName("PaladinBob");
        paladin.setGuild(Guild.SILVERWARD_SENTINELS);
        paladin.setVitality(5); // gives a reasonable max HP
        paladin.setHitPoints(10); // low HP
        paladin.setMagicPoints(20);

        System.out.println("-- Scenario 1: Proper caster --");
        printStatus(paladin);
        new BlessingofRestoration().cast(paladin);
        printStatus(paladin);

        // Scenario 2: Wrong guild
        Character rogue = new Character();
        rogue.setName("RogueRick");
        rogue.setGuild(Guild.AURORA_ARCANUM); // wrong guild
        rogue.setVitality(5);
        rogue.setHitPoints(10);
        rogue.setMagicPoints(20);

        System.out.println("-- Scenario 2: Wrong guild --");
        printStatus(rogue);
        new BlessingofRestoration().cast(rogue);
        printStatus(rogue);

        // Scenario 3: Insufficient MP
        Character paladinPoor = new Character();
        paladinPoor.setName("PaladinPoor");
        paladinPoor.setGuild(Guild.SILVERWARD_SENTINELS);
        paladinPoor.setVitality(5);
        paladinPoor.setHitPoints(10);
        paladinPoor.setMagicPoints(2); // less than REQUIRED_MP (5)

        System.out.println("-- Scenario 3: Insufficient MP --");
        printStatus(paladinPoor);
        new BlessingofRestoration().cast(paladinPoor);
        printStatus(paladinPoor);

        System.out.println("== Tests Complete ==");
    }

    private static void printStatus(Character c) {
        try {
            System.out.println(c.getName() + " - HP: " + c.getHitPoints() + "/" + c.getMaxHitPoints() + ", MP: " + c.getMagicPoints() + ", Guild: " + c.getGuild());
        } catch (Exception e) {
            System.out.println("(unable to print character status)");
        }
    }
}
