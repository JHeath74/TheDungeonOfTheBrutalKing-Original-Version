
package DungeonoftheBrutalKing;

import java.util.*;
import Quests.Quest;
import Status.StatusManager;

public class Character {

    // --- Stat Index Constants ---
    private static final int NAME_IDX = 0;
    private static final int CLASS_IDX = 1;
    private static final int RACE_IDX = 2;
    private static final int LEVEL_IDX = 3;
    private static final int EXP_IDX = 4;
    private static final int HP_IDX = 5;
    private static final int MP_IDX = 6;
    private static final int STAMINA_IDX = 7;
    private static final int CHARISMA_IDX = 8;
    private static final int STRENGTH_IDX = 9;
    private static final int INTELLIGENCE_IDX = 10;
    private static final int WISDOM_IDX = 11;
    private static final int AGILITY_IDX = 12;
    private static final int GOLD_IDX = 13;
    private static final int FOOD_IDX = 14;
    private static final int WATER_IDX = 15;
    private static final int TORCHES_IDX = 16;
    private static final int GEMS_IDX = 17;
    private static final int WEAPON_IDX = 18;
    private static final int ARMOR_IDX = 19;
    private static final int SHIELD_IDX = 20;
    private static final int ALIGNMENT_IDX = 21;
    private static final int X_IDX = 22;
    private static final int Y_IDX = 23;
    private static final int Z_IDX = 24;
    private static final int DIRECTION_IDX = 25;
    private static final int DEFENSE_IDX = 26;
    private static final int ATTACK_IDX = 27;
    private static final int MAX_HP_IDX = 28;

    // --- Singleton Instance ---
    private static Character instance;

    // --- Character Data ---
    private ArrayList<String> charInfo = new ArrayList<>(Collections.nCopies(29, "0"));
    private Set<String> spellsLearned = new HashSet<>();
    private Set<String> charInventory = new HashSet<>();
    private Set<String> guildSpells = new HashSet<>();
    private List<Quest> activeQuests = new ArrayList<>();
    private int actionPoints = 100;
    private double hitChance = 1.0;
    private double originalHitChance = 1.0;
    private StatusManager statusManager = new StatusManager();

    // --- Singleton Access ---
    public static Character getInstance() {
        if (instance == null) {
            instance = new Character();
        }
        return instance;
    }

    // --- Constructors ---
    public Character() {}

    // --- Stat Getters/Setters ---
    private int getInt(int idx, int def) {
        try { return Integer.parseInt(charInfo.get(idx)); } catch (Exception e) { return def; }
    }
    private void setInt(int idx, int val) {
        if (idx >= 0 && idx < charInfo.size()) {
            charInfo.set(idx, String.valueOf(val));
        }
    }
    private String getStr(int idx) {
        if (charInfo == null || idx < 0 || idx >= charInfo.size()) {
            return "";
        }
        return charInfo.get(idx);
    }
    public void setStr(int idx, String value) {
        // Ensure the list is large enough
        while (charInfo.size() <= idx) {
            charInfo.add("");
        }
        charInfo.set(idx, value);
    }

    public String getName() { return getStr(NAME_IDX); }
    public void setName(String name) { setStr(NAME_IDX, name); }
    public String getRace() { return getStr(RACE_IDX); }
    public void setRace(String race) { setStr(RACE_IDX, race); }
    public int getLevel() { return getInt(LEVEL_IDX, 1); }
    public void setLevel(int level) { setInt(LEVEL_IDX, level); }
    public int getExperience() { return getInt(EXP_IDX, 0); }
    public void setExperience(int exp) { setInt(EXP_IDX, exp); }
    public int getHitPoints() { return getInt(HP_IDX, 0); }
    public void setHitPoints(int hp) { setInt(HP_IDX, hp); }
    public int getMaxHitPoints() { return getInt(MAX_HP_IDX, 0); }
    public void setMaxHitPoints(int maxHitPoints) { setInt(MAX_HP_IDX, maxHitPoints); }
    public int getMagicPoints() { return getInt(MP_IDX, 0); }
    public void setMagicPoints(int mp) { setInt(MP_IDX, mp); }
    public int getStamina() { return getInt(STAMINA_IDX, 0); }
    public void setStamina(int stamina) { setInt(STAMINA_IDX, stamina); }
    public int getCharisma() { return getInt(CHARISMA_IDX, 0); }
    public void setCharisma(int charisma) { setInt(CHARISMA_IDX, charisma); }
    public int getStrength() { return getInt(STRENGTH_IDX, 0); }
    public void setStrength(int strength) { setInt(STRENGTH_IDX, strength); }
    public int getIntelligence() { return getInt(INTELLIGENCE_IDX, 0); }
    public void setIntelligence(int intelligence) { setInt(INTELLIGENCE_IDX, intelligence); }
    public int getWisdom() { return getInt(WISDOM_IDX, 0); }
    public void setWisdom(int wisdom) { setInt(WISDOM_IDX, wisdom); }
    public int getAgility() { return getInt(AGILITY_IDX, 0); }
    public void setAgility(int agility) { setInt(AGILITY_IDX, agility); }
    public int getGold() { return getInt(GOLD_IDX, 0); }
    public void setGold(int gold) { setInt(GOLD_IDX, gold); }
    public int getFood() { return getInt(FOOD_IDX, 0); }
    public void setFood(int food) { setInt(FOOD_IDX, food); }
    public int getWater() { return getInt(WATER_IDX, 0); }
    public void setWater(int water) { setInt(WATER_IDX, water); }
    public int getTorches() { return getInt(TORCHES_IDX, 0); }
    public void setTorches(int torches) { setInt(TORCHES_IDX, torches); }
    public int getGems() { return getInt(GEMS_IDX, 0); }
    public void setGems(int gems) { setInt(GEMS_IDX, gems); }
    public String getWeapon() { return getStr(WEAPON_IDX); }
    public void setWeapon(String weapon) { setStr(WEAPON_IDX, weapon); }
    public String getArmour() { return getStr(ARMOR_IDX); }
    public void setArmour(String armour) { setStr(ARMOR_IDX, armour); }
    public String getShield() { return getStr(SHIELD_IDX); }
    public void setShield(String shield) { setStr(SHIELD_IDX, shield); }
    public int getAlignment() { return getInt(ALIGNMENT_IDX, 0); }
    public void setAlignment(int alignment) { setInt(ALIGNMENT_IDX, alignment); }

    // --- Defensive Double Parsing for Position ---
    public double getX() { return parseDoubleSafe(getStr(X_IDX)); }
    public double getY() { return parseDoubleSafe(getStr(Y_IDX)); }
    public double getZ() { return parseDoubleSafe(getStr(Z_IDX)); }
    public double getDirection() { return parseDoubleSafe(getStr(DIRECTION_IDX)); }
    private double parseDoubleSafe(String s) {
        if (s == null || s.isEmpty()) return 0.0;
        try { return Double.parseDouble(s); } catch (NumberFormatException e) { return 0.0; }
    }

    public int getDefense() { return getInt(DEFENSE_IDX, 0); }
    public void setDefense(int defense) { setInt(DEFENSE_IDX, defense); }
    public void setAttack(int attack) { setInt(ATTACK_IDX, attack); }
    public int getAttack() { return getInt(ATTACK_IDX, 0); }
    public int getActionPoints() { return actionPoints; }
    public void setActionPoints(int points) { actionPoints = points; }

    // --- Inventory and Spells ---
    public Set<String> getCharInventory() { return charInventory; }
    public void setCharInventory(Set<String> inventory) { this.charInventory = inventory; }
    public void addToInventory(String item) { charInventory.add(item); }
    public boolean removeFromInventory(String item) { return charInventory.remove(item); }

    public Set<String> getSpellsLearned() { return spellsLearned; }
    public void setSpellsLearned(Set<String> spells) { spellsLearned = spells; }
    public Set<String> getGuildSpells() { return guildSpells; }
    public void setGuildSpells(Set<String> spells) { guildSpells = spells; }

    // --- Quests ---
    public List<Quest> getActiveQuests() { return activeQuests; }
    public void addActiveQuest(Quest quest) { if (!activeQuests.contains(quest)) activeQuests.add(quest); }
    public boolean removeActiveQuest(Quest quest) { return activeQuests.remove(quest); }
    public void setActiveQuests(List<Quest> activeQuests) { this.activeQuests = activeQuests; }

    // --- Position ---
    public void setPosition(int x, int y, int z) {
        setInt(X_IDX, x); setInt(Y_IDX, y); setInt(Z_IDX, z);
    }
    public void getPosition(int[] pos) {
        pos[0] = getInt(X_IDX, 0); pos[1] = getInt(Y_IDX, 0); pos[2] = getInt(Z_IDX, 0);
    }

    // --- Combat and Stats ---
    public boolean consumeSkillPoints(int cost) {
        if (actionPoints >= cost) {
            actionPoints -= cost;
            return true;
        } else if (getMagicPoints() >= cost) {
            setMagicPoints(getMagicPoints() - cost);
            return true;
        }
        return false;
    }

    public int getAttackDamage() {
        int strength = getStrength();
        int weaponDamage = 0;
        try { weaponDamage = Integer.parseInt(getWeapon()); } catch (Exception e) { weaponDamage = 0; }
        return weaponDamage + (int)(strength * 1.2) + new Random().nextInt(5) + 1;
    }

    public void calculateAndSetAttack() {
        int baseAttack = 5;
        int strMod = (int) ((getStrength() - 10) / 2.0);
        int weaponBonus = 0;
        try { weaponBonus = Integer.parseInt(getWeapon()); } catch (Exception e) {}
        int attack = baseAttack + strMod + weaponBonus;
        setInt(ATTACK_IDX, attack);
    }

    public void calculateAndSetDefense() {
        int baseDefense = 10;
        int dexMod = (getAgility() - 10) / 2;
        int armorBonus = 0, shieldBonus = 0;
        try { armorBonus = Integer.parseInt(getArmour()); } catch (Exception e) {}
        try { shieldBonus = Integer.parseInt(getShield()); } catch (Exception e) {}
        setDefense(baseDefense + dexMod + armorBonus + shieldBonus);
    }

    public void reduceHitPoints(int amount) { setHitPoints(Math.max(0, getHitPoints() - amount)); }
    public void reduceDefense(int amount) { setDefense(Math.max(0, getDefense() - amount)); }
    public boolean removeFood(int amount) {
        if (getFood() >= amount) { setFood(getFood() - amount); return true; }
        return false;
    }
    public boolean removeGold(int amount) {
        if (getGold() >= amount) { setGold(getGold() - amount); return true; }
        return false;
    }
    public void rewardExperience(int xp) {
        setExperience(getExperience() + xp);
        checkLevelUp();
    }
    private void checkLevelUp() {
        int lvl = getLevel();
        if (getExperience() >= getExperienceRequiredForLevel(lvl + 1)) {
            setLevel(lvl + 1);
            setMaxHitPoints(getMaxHitPoints() + 10);
            setHitPoints(getMaxHitPoints());
        }
    }
    public int getExperienceRequiredForLevel(int level) {
        return (int)(1000 * Math.pow(1.5, level - 1));
    }

    // --- Hit Chance ---
    public double getHitChance() { return hitChance; }
    public void setHitChance(double hitChance) { this.hitChance = Math.max(0.0, Math.min(1.0, hitChance)); }
    public double calculateHitChance(boolean isMagicUser) {
        if (isMagicUser) {
            double wis = getWisdom();
            double intel = getIntelligence();
            return Math.max(0.1, Math.min(1.0, (wis + intel) / 40.0));
        } else {
            double agi = getAgility();
            double str = getStrength();
            return Math.max(0.1, Math.min(1.0, (agi + str) / 40.0));
        }
    }
    public void updateOriginalHitChance(boolean isMagicUser) {
        originalHitChance = calculateHitChance(isMagicUser);
        setHitChance(originalHitChance);
    }
    public void applyStatusToHitChance(double modifier) {
        setHitChance(originalHitChance * modifier);
    }
    public void resetHitChance() {
        setHitChance(originalHitChance);
    }

    // --- Status Manager ---
    public void setStatusManager(StatusManager statusManager) {
        this.statusManager = statusManager;
    }
    public StatusManager getStatusManager() {
        return statusManager;
    }

    // --- Miscellaneous ---
    public void updateCharInfo(int index, String value) {
        if (charInfo == null) return;
        if (index >= 0 && index < charInfo.size()) charInfo.set(index, value);
        else if (index == charInfo.size()) charInfo.add(value);
    }
    public void setCharInfo(ArrayList<String> charInfo) {
        if (charInfo == null || charInfo.size() < 29) {
            this.charInfo = new ArrayList<>(Collections.nCopies(29, "0"));
        } else {
            this.charInfo = charInfo;
        }
    }
    public ArrayList<String> getCharInfo() { return charInfo; }
    public String getClassName() { return getStr(CLASS_IDX); }
    public String getEquippedWeapon() { return getStr(WEAPON_IDX); }
    public String getEquippedArmor() { return getStr(ARMOR_IDX); }
    public String getEquippedShield() { return getStr(SHIELD_IDX); }
    public void setCanAct(boolean b) {}
    public void addStatus(Object status) {}
    public void takeDamage(int amount) { reduceHitPoints(amount); }
}
