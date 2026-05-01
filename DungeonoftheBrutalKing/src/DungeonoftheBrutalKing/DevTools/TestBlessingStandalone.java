package DungeonoftheBrutalKing.DevTools;

/**
 * Standalone test harness for the Blessing of Restoration spell.
 * Provides minimal mock classes and a local implementation of the spell,
 * allowing validation of HP/MP changes and failure messages without requiring the full project.
 */
public class TestBlessingStandalone {
    // Minimal mock Guild enum
    enum Guild { SILVERWARD_SENTINELS, AURORA_ARCANUM, NON_GUILD }

    // Minimal mock Character with only the fields/methods used by the spell
    static class MockCharacter {
        private String name;
        private Guild guild = Guild.NON_GUILD;
        private int hitPoints;
        private int maxHitPoints;
        private int magicPoints;
        private int wisdom;

        MockCharacter(String name, Guild guild, int maxHp, int hp, int mp, int wisdom) {
            this.name = name; this.guild = guild; this.maxHitPoints = maxHp; this.hitPoints = hp; this.magicPoints = mp; this.wisdom = wisdom;
        }
        String getName() { return name; }
        Guild getGuild() { return guild; }
        void setGuild(Guild g) { guild = g; }
        int getHitPoints() { return hitPoints; }
        void setHitPoints(int hp) { hitPoints = Math.max(0, Math.min(maxHitPoints, hp)); }
        int getMaxHitPoints() { return maxHitPoints; }
        int getMagicPoints() { return magicPoints; }
        void setMagicPoints(int mp) { magicPoints = Math.max(0, mp); }
        int getWisdom() { return wisdom; }
    }

    // Local copy of BlessingofRestoration behavior (solo spell)
    static class BlessingofRestoration {
        private static final int REQUIRED_MP = 5;
        private static final double BASE_HEAL_PERCENT = 0.18;
        private static final double WISDOM_SCALING_PER_10 = 0.03;
        private static final int MIN_FLAT_HEAL = 6;

        void cast(MockCharacter caster) {
            if (caster == null) {
                System.out.println("No caster provided for Blessing of Restoration. Spell requires a caster.");
                return;
            }
            if (caster.getGuild() != Guild.SILVERWARD_SENTINELS) {
                System.out.println(caster.getName() + " is not a member of SILVERWARD_SENTINELS and cannot cast Blessing of Restoration.");
                return;
            }
            if (caster.getMagicPoints() < REQUIRED_MP) {
                System.out.println(caster.getName() + " lacks the magic points to cast Blessing of Restoration.");
                return;
            }
            caster.setMagicPoints(caster.getMagicPoints() - REQUIRED_MP);

            int maxHp = Math.max(0, caster.getMaxHitPoints());
            int curHp = Math.max(0, caster.getHitPoints());
            if (maxHp <= 0 || curHp >= maxHp) {
                System.out.println(caster.getName() + " is already at full health or cannot be healed by Blessing of Restoration.");
                return;
            }
            int wisdom = Math.max(0, caster.getWisdom());
            double wisdomSteps = wisdom / 10.0;
            double bonusPercent = wisdomSteps * WISDOM_SCALING_PER_10;
            double totalPercent = BASE_HEAL_PERCENT + bonusPercent;
            int amountFromPercent = (int) Math.round(maxHp * totalPercent);
            int rawAmount = Math.max(MIN_FLAT_HEAL, amountFromPercent);
            int heal = Math.min(rawAmount, maxHp - curHp);
            if (heal <= 0) {
                System.out.println(caster.getName() + " receives no effective heal from Blessing of Restoration.");
                return;
            }
            caster.setHitPoints(curHp + heal);
            System.out.println(caster.getName() + " is healed for " + heal + " HP by Blessing of Restoration.");
        }
    }

    public static void main(String[] args) {
        System.out.println("== Standalone Blessing of Restoration Demo ==\n");

        // Scenario 1: Proper Silverward caster with sufficient MP
        MockCharacter paladin = new MockCharacter("PaladinBob", Guild.SILVERWARD_SENTINELS, 100, 10, 20, 25);
        printStatus(paladin);
        new BlessingofRestoration().cast(paladin);
        printStatus(paladin);

        // Scenario 2: Wrong guild
        MockCharacter rogue = new MockCharacter("RogueRick", Guild.AURORA_ARCANUM, 100, 10, 20, 25);
        printStatus(rogue);
        new BlessingofRestoration().cast(rogue);
        printStatus(rogue);

        // Scenario 3: Insufficient MP
        MockCharacter paladinPoor = new MockCharacter("PaladinPoor", Guild.SILVERWARD_SENTINELS, 100, 10, 2, 25);
        printStatus(paladinPoor);
        new BlessingofRestoration().cast(paladinPoor);
        printStatus(paladinPoor);

        System.out.println("\n== Demo Complete ==");
    }

    private static void printStatus(MockCharacter c) {
        System.out.println(c.getName() + " - HP: " + c.getHitPoints() + "/" + c.getMaxHitPoints() + ", MP: " + c.getMagicPoints() + ", Guild: " + c.getGuild());
    }
}
