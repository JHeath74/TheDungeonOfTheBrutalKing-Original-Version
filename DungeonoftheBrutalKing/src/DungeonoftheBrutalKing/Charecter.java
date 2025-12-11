
// src/DungeonoftheBrutalKing/Character.java
package DungeonoftheBrutalKing;

import java.util.*;
import Quests.Quest;
import SharedData.GuildMembershipStatus;
import SharedData.GuildType;
import Status.StatusManager;

public class Charecter {

    private static Charecter instance;
    private ArrayList<String> charInfo = new ArrayList<>(Collections.nCopies(33, "0"));
    private Set<String> spellsLearned = new HashSet<>();
    private Set<String> charInventory = new HashSet<>();
    private Set<String> guildSpells = new HashSet<>();
    private List<Quest> activeQuests = new ArrayList<>();
    private StatusManager statusManager = new StatusManager();
    private int actionPoints;
    private int baseStrength, baseIntelligence, baseWisdom, baseAgility;
    private boolean stunned = false;
    private GuildType currentGuild;
    private GuildMembershipStatus currentGuildStatus;

    private Map<GuildType, GuildMembershipStatus> guildStatusMap = new HashMap<>();

    public static Charecter getInstance() {
        if (instance == null) instance = new Charecter();
        return instance;
    }

    public Charecter() {
        this.baseStrength = 8 + new Random().nextInt(7);
        this.baseIntelligence = 8 + new Random().nextInt(7);
        this.baseWisdom = 8 + new Random().nextInt(7);
        this.baseAgility = 8 + new Random().nextInt(7);
        setInt(29, baseStrength);
        setInt(30, baseIntelligence);
        setInt(31, baseWisdom);
        setInt(32, baseAgility);
        setStrength(baseStrength);
        setIntelligence(baseIntelligence);
        setWisdom(baseWisdom);
        setAgility(baseAgility);
    }

    // --- Stat Getters/Setters ---
    private int getInt(int idx, int def) { try { return Integer.parseInt(charInfo.get(idx)); } catch (Exception e) { return def; } }
    private void setInt(int idx, int val) { if (idx >= 0 && idx < charInfo.size()) charInfo.set(idx, String.valueOf(val)); }
    private String getStr(int idx) { if (charInfo == null || idx < 0 || idx >= charInfo.size()) return ""; return charInfo.get(idx); }
    public void setStr(int idx, String value) { while (charInfo.size() <= idx) charInfo.add(""); charInfo.set(idx, value); }
    public String getName() { return getStr(0); }
    public void setName(String name) { setStr(0, name); }
    public String getRace() { return getStr(2); }
    public void setRace(String race) { setStr(2, race); }
    public int getLevel() { return getInt(3, 1); }
    public void setLevel(int level) { setInt(3, Math.min(level, 50)); }
    public int getExperience() { return getInt(4, 0); }
    public void setExperience(int exp) { setInt(4, exp); }
    public int getHitPoints() { return getInt(5, 0); }
    public void setHitPoints(int hp) { setInt(5, hp); }
    public int getMaxHitPoints() { return getInt(28, 0); }
    public void setMaxHitPoints(int maxHitPoints) { setInt(28, maxHitPoints); }
    public int getMagicPoints() { return getInt(6, 0); }
    public void setMagicPoints(int mp) { setInt(6, mp); }
    public int getMaxMagicPoints() { return getInt(33, 0); }
    public void setMaxMagicPoints(int maxMP) { setInt(33, maxMP); }

    public int getMaxActionPoints() { return getInt(34, 0); }
    public void setMaxActionPoints(int maxAP) { setInt(34, maxAP); }
    public int getStamina() { return getInt(7, 0); }
    public void setStamina(int stamina) { setInt(7, stamina); }
    public int getCharisma() { return getInt(8, 0); }
    public void setCharisma(int charisma) { setInt(8, charisma); }
    public int getStrength() { return getInt(9, 0); }
    public void setStrength(int strength) { setInt(9, strength); }
    public int getIntelligence() { return getInt(10, 0); }
    public void setIntelligence(int intelligence) { setInt(10, intelligence); }
    public int getWisdom() { return getInt(11, 0); }
    public void setWisdom(int wisdom) { setInt(11, wisdom); }
    public int getAgility() { return getInt(12, 0); }
    public void setAgility(int agility) { setInt(12, agility); }
    public int getGold() { return getInt(13, 0); }
    public void setGold(int gold) { setInt(13, gold); }
    public int getFood() { return getInt(14, 0); }
    public void setFood(int food) { setInt(14, food); }
    public int getWater() { return getInt(15, 0); }
    public void setWater(int water) { setInt(15, water); }
    public int getTorches() { return getInt(16, 0); }
    public void setTorches(int torches) { setInt(16, torches); }
    public int getGems() { return getInt(17, 0); }
    public void setGems(int gems) { setInt(17, gems); }
    public String getWeapon() { return getStr(18); }
    public void setWeapon(String weapon) { setStr(18, weapon); }
    public String getArmour() { return getStr(19); }
    public void setArmour(String armour) { setStr(19, armour); }
    public String getShield() { return getStr(20); }
    public void setShield(String shield) { setStr(20, shield); }
    public int getAlignment() { return getInt(21, 0); }
    public void setAlignment(int alignment) { setInt(21, alignment); }
    public int getDefense() { return getInt(26, 0); }
    public void setDefense(int defense) { setInt(26, defense); }
    public void setAttack(int attack) { setInt(27, attack); }
    public int getAttack() { return getInt(27, 0); }
    public int getActionPoints() { return actionPoints; }
    public void setActionPoints(int points) { actionPoints = points; }
    public Set<String> getCharInventory() { return charInventory; }
    public void setCharInventory(Set<String> inventory) { this.charInventory = inventory; }
    public void addToInventory(String item) { charInventory.add(item); }
    public boolean removeFromInventory(String item) { return charInventory.remove(item); }
    public Set<String> getSpellsLearned() { return spellsLearned; }
    public void setSpellsLearned(Set<String> spells) { spellsLearned = spells; }
    public Set<String> getGuildSpells() { return guildSpells; }
    public void setGuildSpells(Set<String> spells) { guildSpells = spells; }
    public List<Quest> getActiveQuests() { return activeQuests; }
    public void addActiveQuest(Quest quest) { if (!activeQuests.contains(quest)) activeQuests.add(quest); }
    public boolean removeActiveQuest(Quest quest) { return activeQuests.remove(quest); }
    public void setActiveQuests(List<Quest> activeQuests) { this.activeQuests = activeQuests; }
    public void setPosition(int x, int y, int z) { setInt(22, x); setInt(23, y); setInt(24, z); }
    public void getPosition(int[] pos) { pos[0] = getInt(22, 0); pos[1] = getInt(23, 0); pos[2] = getInt(24, 0); }
    public GuildType getCurrentGuild() { return currentGuild; }
    public void setCurrentGuild(GuildType guild) { this.currentGuild = guild; }
    public GuildMembershipStatus getCurrentGuildStatus() { return currentGuildStatus; }
    public void setCurrentGuildStatus(GuildMembershipStatus status) { this.currentGuildStatus = status; }
    
    public boolean consumeSkillPoints(int cost) {
        if (actionPoints >= cost) { actionPoints -= cost; return true; }
        else if (getMagicPoints() >= cost) { setMagicPoints(getMagicPoints() - cost); return true; }
        return false;
    }
    
    public int getDirection() {
        return getInt(25, 0);
    }

    public void setDirection(int degrees) {
        setInt(25, degrees);
    }
    
    public ArrayList<String> getCharInfo() {
        return charInfo;
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
        setInt(27, attack);
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
    public boolean removeFood(int amount) { if (getFood() >= amount) { setFood(getFood() - amount); return true; } return false; }
    public boolean removeGold(int amount) { if (getGold() >= amount) { setGold(getGold() - amount); return true; } return false; }
    public void rewardExperience(int xp) { setExperience(getExperience() + xp); checkLevelUp(); }
    private void checkLevelUp() {
        int lvl = getLevel();
        if (lvl < 50 && getExperience() >= getExperienceRequiredForLevel(lvl + 1)) {
            setLevel(lvl + 1);
            setMaxHitPoints(getMaxHitPoints() + 10);
            setHitPoints(getMaxHitPoints());
        }
    }
    public int getExperienceRequiredForLevel(int level) { return (int)(1000 * Math.pow(1.5, level - 1)); }
    public void restoreHitPoints(int amount) { setHitPoints(Math.min(getHitPoints() + amount, getMaxHitPoints())); }
    public void setStunned(boolean b) { this.stunned = b; }
    public boolean isStunned() { return stunned; }
    public void clearCurses() {}
    public void clearNegativeEffects() {}

    // --- Guild Membership Methods ---
    public void setGuildStatus(GuildType guildType, GuildMembershipStatus status) {
        if (guildType == null || status == null) return;
        guildStatusMap.put(guildType, status);
    }

    public GuildMembershipStatus getGuildStatus(GuildType guildType) {
        return guildStatusMap.getOrDefault(guildType, GuildMembershipStatus.NOT_MEMBER);
    }
    
    public Map<GuildType, GuildMembershipStatus> getGuildStatusMap() {
        return guildStatusMap;
    }


}
