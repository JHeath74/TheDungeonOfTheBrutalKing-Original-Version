package DungeonoftheBrutalKing;

import java.util.*;

import Quests.Quest;

public class Charecter {

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

    private double hitChance = 1.0;
    private double originalHitChance = 1.0;

    private static Charecter instance;
    private Object effectManager = null; // Replace with EffectManager when available
    private List<String> activeEffects = new ArrayList<>();
    private ArrayList<String> charInfo = new ArrayList<>(Collections.nCopies(27, "0"));
    private ArrayList<String> spellsLearned = new ArrayList<>();
    private ArrayList<String> charInventory = new ArrayList<>();
    private ArrayList<String> guildSpells = new ArrayList<>();
    private List<Quest> activeQuests = new ArrayList<>();
    private int actionPoints = 100;

    public Charecter() {}

    public static Charecter getInstance() {
        if (instance == null) {
            instance = new Charecter();
        }
        return instance;
    }

    private int getInt(int idx, int def) {
        try { return Integer.parseInt(charInfo.get(idx)); } catch (Exception e) { return def; }
    }
    private void setInt(int idx, int val) { charInfo.set(idx, String.valueOf(val)); }
    private String getStr(int idx) { return charInfo.get(idx); }
    private void setStr(int idx, String val) { charInfo.set(idx, val); }

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
    public double getX() { return Double.parseDouble(getStr(X_IDX)); }
    public double getY() { return Double.parseDouble(getStr(Y_IDX)); }
    public double getZ() { return Double.parseDouble(getStr(Z_IDX)); }
    public double getDirection() { return Double.parseDouble(getStr(DIRECTION_IDX)); }
    public int getDefense() { return getInt(DEFENSE_IDX, 0); }
    public void setDefense(int defense) { setInt(DEFENSE_IDX, defense); }
    public void setAttack(int attack) { setInt(STRENGTH_IDX, attack); }
    public int getAttack() { return getInt(ATTACK_IDX, 0); }
    public int getActionPoints() { return actionPoints; }
    public void setActionPoints(int points) { actionPoints = points; }
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

    public void setPosition(int x, int y, int z) {
        setInt(X_IDX, x); setInt(Y_IDX, y); setInt(Z_IDX, z);
    }
    public void getPosition(int[] pos) {
        pos[0] = getInt(X_IDX, 0); pos[1] = getInt(Y_IDX, 0); pos[2] = getInt(Z_IDX, 0);
    }

    public ArrayList<String> getCharInventory() {
        return charInventory;
    }

    public void setCharInventory(ArrayList<String> inventory) {
        this.charInventory = inventory;
    }

    public void addToInventory(String item) {
        if (!charInventory.contains(item)) charInventory.add(item);
    }
    public boolean removeFromInventory(String item) { return charInventory.remove(item); }
    public ArrayList<String> getSpellsLearned() { return spellsLearned; }
    public void setSpellsLearned(ArrayList<String> spells) { spellsLearned = spells; }
    public ArrayList<String> getGuildSpells() { return guildSpells; }
    public void setGuildSpells(ArrayList<String> spells) { guildSpells = spells; }
    public List<String> getActiveEffects() { return activeEffects; }
    public void setActiveEffects(List<String> activeEffects) { this.activeEffects = activeEffects; }
    public Object getEffectManager() { return effectManager; }
    public void setEffectManager(Object em) { effectManager = em; }

    public List<Quest> getActiveQuests() { return activeQuests; }
    public void addActiveQuest(Quest quest) {
        if (!activeQuests.contains(quest)) activeQuests.add(quest);
    }
    public boolean removeActiveQuest(Quest quest) {
        return activeQuests.remove(quest);
    }
    public void setActiveQuests(List<Quest> activeQuests) {
        this.activeQuests = activeQuests;
    }

    public int getAttackDamage() {
        int strength = getStrength();
        int weaponDamage = 0;
        try {
            weaponDamage = Integer.parseInt(getWeapon());
        } catch (Exception e) {
            weaponDamage = 0;
        }
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
        if (getExperience() >= getExperienceRequiredForLevel(lvl + 1)) setLevel(lvl + 1);
    }
    public int getExperienceRequiredForLevel(int level) {
        return (int)(1000 * Math.pow(1.5, level - 1));
    }
    public String[] getKnownSpells() { return spellsLearned.toArray(new String[0]); }
    public Object getSpellByName(String name) {
        for (String spell : spellsLearned)
            if (spell.equalsIgnoreCase(name)) return null; // Replace with SpellList.getSpells(spell) when available
        return null;
    }
    public void calculateAndSetDefense() {
        int baseDefense = 10;
        int dexMod = (getAgility() - 10) / 2;
        int armorBonus = 0, shieldBonus = 0;
        try { armorBonus = Integer.parseInt(getArmour()); } catch (Exception e) {}
        try { shieldBonus = Integer.parseInt(getShield()); } catch (Exception e) {}
        setDefense(baseDefense + dexMod + armorBonus + shieldBonus);
    }

    public void updateCharInfo(int index, String value) {
        if (charInfo == null) return;
        if (index >= 0 && index < charInfo.size()) charInfo.set(index, value);
        else if (index == charInfo.size()) charInfo.add(value);
    }

    public void setCharInfo(ArrayList<String> charInfo) {
        this.charInfo = charInfo;
    }

    public ArrayList<String> getCharInfo() {
        return charInfo;
    }

    public String getClassName() {
        return getStr(CLASS_IDX);
    }

    public String getEquippedWeapon() {
        return getStr(WEAPON_IDX);
    }

    public String getEquippedArmor() {
        return getStr(ARMOR_IDX);
    }

    public String getEquippedShield() {
        return getStr(SHIELD_IDX);
    }

    public void setCanAct(boolean b) {
        // TODO Auto-generated method stub
    }

    public void addStatus(Object status) {
        // TODO: implement when Status is available
    }

    public void takeDamage(int amount) {
        reduceHitPoints(amount);
    }

    public double getHitChance() {
        return hitChance;
    }

    public void setHitChance(double hitChance) {
        this.hitChance = Math.max(0.0, Math.min(1.0, hitChance));
    }

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
    
    public boolean removeEffect(String effect) {
        return activeEffects.remove(effect);
    }
}
