
package DungeonoftheBrutalKing;

import java.util.*;
import DungeonoftheBrutalKing.Quests.Quest;
import DungeonoftheBrutalKing.SharedData.Guild;
import DungeonoftheBrutalKing.SharedData.GuildMembershipStatus;
import DungeonoftheBrutalKing.SharedData.GuildType;
import DungeonoftheBrutalKing.Status.HasHitPoints;
import DungeonoftheBrutalKing.Status.Status;
import DungeonoftheBrutalKing.Status.StatusManager;
import DungeonoftheBrutalKing.Status.StatusType;
import DungeonoftheBrutalKing.Status.PurityWardStatus;
import DungeonoftheBrutalKing.Status.AstralWardStatus;

public class Charecter implements HasHitPoints {

    private static Charecter instance;

    private ArrayList<String> charInfo = new ArrayList<>(Collections.nCopies(34, "0"));
    private Set<String> spellsLearned = new HashSet<>();
    private Set<String> charInventory = new HashSet<>();
    private Set<String> guildSpells = new HashSet<>();
    private List<String> guildStorage = new ArrayList<>();
    private List<Quest> activeQuests = new ArrayList<>();
    private Set<String> protectedEffects = new HashSet<>();
    private StatusManager statusManager = new StatusManager();
    private int actionPoints;
    private int baseStrength, baseIntelligence, baseWisdom, baseAgility;
    private boolean stunned = false;
    private boolean silenced = false;
    private boolean hidden = false;
    private GuildType currentGuild;
    private GuildMembershipStatus currentGuildStatus;
    private List<Status> statuses = new ArrayList<>();
    private double hasteModifier = 0.0;
    private int spellResistanceBonus = 0;
    private double critChance = 0;
    private double hitChance = 1.0;
    private Guild guild;
    private int manaRegenBonus = 0;
    private int damageBonus = 0;
    private Set<String> resistances = new HashSet<>();
    private Map<GuildType, GuildMembershipStatus> guildStatusMap = new HashMap<>();

    private static final int IDX_NAME = 0;
    private static final int IDX_CLASS = 2;
    private static final int IDX_LEVEL = 3;
    private static final int IDX_EXPERIENCE = 4;
    private static final int IDX_HITPOINTS = 5;
    private static final int IDX_MAGICPOINTS = 6;
    private static final int IDX_STAMINA = 7;
    private static final int IDX_CHARISMA = 8;
    private static final int IDX_STRENGTH = 9;
    private static final int IDX_INTELLIGENCE = 10;
    private static final int IDX_WISDOM = 11;
    private static final int IDX_AGILITY = 12;
    private static final int IDX_GOLD = 13;
    private static final int IDX_FOOD = 14;
    private static final int IDX_WATER = 15;
    private static final int IDX_TORCHES = 16;
    private static final int IDX_GEMS = 17;
    private static final int IDX_WEAPON = 18;
    private static final int IDX_ARMOUR = 19;
    private static final int IDX_SHIELD = 20;
    private static final int IDX_ALIGNMENT = 21;
    private static final int IDX_POS_X = 22;
    private static final int IDX_POS_Y = 23;
    private static final int IDX_POS_Z = 24;
    private static final int IDX_DIRECTION = 25;
    private static final int IDX_DEFENSE = 26;
    private static final int IDX_ATTACK = 27;
    private static final int IDX_FINAL_HP = 28;
    private static final int IDX_BASE_STR = 29;
    private static final int IDX_BASE_INT = 30;
    private static final int IDX_BASE_WIS = 31;
    private static final int IDX_BASE_AGI = 32;
    private static final int IDX_VITALITY = 33;

    // Hunger/thirst: 100 = full, 0 = starving/parched
    private int hungerLevel = 100;
    private int thirstLevel = 100;

    public static Charecter getInstance() {
        if (instance == null) instance = new Charecter();
        return instance;
    }

    public Charecter() {
        this.baseStrength = 8 + new Random().nextInt(7);
        this.baseIntelligence = 8 + new Random().nextInt(7);
        this.baseWisdom = 8 + new Random().nextInt(7);
        this.baseAgility = 8 + new Random().nextInt(7);
        setInt(IDX_BASE_STR, baseStrength);
        setInt(IDX_BASE_INT, baseIntelligence);
        setInt(IDX_BASE_WIS, baseWisdom);
        setInt(IDX_BASE_AGI, baseAgility);
        if (getStr(IDX_VITALITY) == null || getStr(IDX_VITALITY).isBlank()) setInt(IDX_VITALITY, 0);
    }

    private int getInt(int index, int def) {
        try {
            if (index < 0 || index >= charInfo.size()) return def;
            String v = charInfo.get(index);
            if (v == null || v.isBlank()) return def;
            return Integer.parseInt(v.trim());
        } catch (Exception ignored) { return def; }
    }

    private void setInt(int index, int value) {
        ensureSize(index + 1);
        charInfo.set(index, String.valueOf(value));
    }

    private String getStr(int index) {
        if (index < 0 || index >= charInfo.size()) return null;
        return charInfo.get(index);
    }

    private void setStr(int index, String value) {
        ensureSize(index + 1);
        charInfo.set(index, value == null ? "0" : value);
    }
    
public double getCritChance() {
    return critChance;
}

public void setCritChance(double critChance) {
    // clamp between 0.0 and 1.0 if you want it as a percentage chance
    this.critChance = Math.max(0.0, Math.min(1.0, critChance));
}


    private void ensureSize(int size) {
        if (charInfo == null) charInfo = new ArrayList<>();
        while (charInfo.size() < size) charInfo.add("0");
    }

    public ArrayList<String> getCharInfo() { return charInfo; }
    public void setCharInfo(ArrayList<String> charInfo) {
        this.charInfo = (charInfo == null) ? new ArrayList<>() : charInfo;
        ensureSize(34);
        if (getStr(IDX_VITALITY) == null || getStr(IDX_VITALITY).isBlank()) setInt(IDX_VITALITY, 0);
    }

    public String getName() { return getStr(IDX_NAME); }
    public void setName(String name) { setStr(IDX_NAME, name); }
    public String getToonClass() { return getStr(IDX_CLASS); }
    public void setToonClass(String clazz) { setStr(IDX_CLASS, clazz); }
    public int getLevel() { return getInt(IDX_LEVEL, 1); }
    public void setLevel(int level) { setInt(IDX_LEVEL, Math.max(1, level)); }
    public int getExperience() { return getInt(IDX_EXPERIENCE, 0); }
    public void setExperience(int xp) { setInt(IDX_EXPERIENCE, Math.max(0, xp)); }
    @Override
    public int getHitPoints() { return getInt(IDX_HITPOINTS, 0); }
    @Override
    public void setHitPoints(int hp) {
        setInt(IDX_HITPOINTS, Math.max(0, hp));
        setInt(IDX_FINAL_HP, Math.max(0, hp));
    }
    public int getMagicPoints() { return getInt(IDX_MAGICPOINTS, 0); }
    public void setMagicPoints(int mp) { setInt(IDX_MAGICPOINTS, Math.max(0, mp)); }
    public int getStamina() { return getInt(IDX_STAMINA, 0); }
    public void setStamina(int v) { setInt(IDX_STAMINA, Math.max(0, v)); }
    public int getCharisma() { return getInt(IDX_CHARISMA, 0); }
    public void setCharisma(int v) { setInt(IDX_CHARISMA, Math.max(0, v)); }
    public int getStrength() { return getInt(IDX_STRENGTH, 0); }
    public void setStrength(int v) { setInt(IDX_STRENGTH, Math.max(0, v)); }
    public int getIntelligence() { return getInt(IDX_INTELLIGENCE, 0); }
    public void setIntelligence(int v) { setInt(IDX_INTELLIGENCE, Math.max(0, v)); }
    public int getWisdom() { return getInt(IDX_WISDOM, 0); }
    public void setWisdom(int v) { setInt(IDX_WISDOM, Math.max(0, v)); }
    public int getAgility() { return getInt(IDX_AGILITY, 0); }
    public void setAgility(int v) { setInt(IDX_AGILITY, Math.max(0, v)); }
    public int getVitality() { return getInt(IDX_VITALITY, 0); }
    public void setVitality(int vitality) {
        setInt(IDX_VITALITY, Math.max(0, vitality));
        int newMaxHp = calculateMaxHitPointsFromVitality();
        setHitPoints(newMaxHp);
    }
    private int calculateMaxHitPointsFromVitality() {
        String clazz = getToonClass();
        int base = ("Paladin".equals(clazz) || "Warrior".equals(clazz)) ? 2 : 1;
        return base * (getVitality() * 10);
    }
    public int getGold() { return getInt(IDX_GOLD, 0); }
    public void setGold(int gold) { setInt(IDX_GOLD, Math.max(0, gold)); }
    public int getFood() { return getInt(IDX_FOOD, 0); }
    public void setFood(int food) { setInt(IDX_FOOD, Math.max(0, food)); }
    public int getWater() { return getInt(IDX_WATER, 0); }
    public void setWater(int water) { setInt(IDX_WATER, Math.max(0, water)); }
    public int getTorches() { return getInt(IDX_TORCHES, 0); }
    public void setTorches(int torches) { setInt(IDX_TORCHES, Math.max(0, torches)); }
    public int getGems() { return getInt(IDX_GEMS, 0); }
    public void setGems(int gems) { setInt(IDX_GEMS, Math.max(0, gems)); }
    public String getEquippedWeapon() { return getStr(IDX_WEAPON); }
    public void setEquippedWeapon(String weapon) { setStr(IDX_WEAPON, weapon); }
    public String getEquippedArmour() { return getStr(IDX_ARMOUR); }
    public void setEquippedArmour(String armour) { setStr(IDX_ARMOUR, armour); }
    public String getEquippedShield() { return getStr(IDX_SHIELD); }
    public void setEquippedShield(String shield) { setStr(IDX_SHIELD, shield); }
    public int getAlignment() { return getInt(IDX_ALIGNMENT, 0); }
    public void setAlignment(int alignment) { setInt(IDX_ALIGNMENT, alignment); }
    public void setPosition(int x, int y, int z) { setInt(IDX_POS_X, x); setInt(IDX_POS_Y, y); setInt(IDX_POS_Z, z); }
    public void getPosition(int[] pos) {
        if (pos == null || pos.length < 3) return;
        pos[0] = getInt(IDX_POS_X, 0);
        pos[1] = getInt(IDX_POS_Y, 0);
        pos[2] = getInt(IDX_POS_Z, 0);
    }
    public int getX() { return getInt(IDX_POS_X, 0); }
    public int getY() { return getInt(IDX_POS_Y, 0); }

public int getDungeonLevel() { return getInt(IDX_POS_Z, 0); }

    public int getDirection() { return getInt(IDX_DIRECTION, 0); }
    public void setDirection(int degrees) { setInt(IDX_DIRECTION, degrees); }
    public int getDefense() { return getInt(IDX_DEFENSE, 0); }
    public void setDefense(int d) { setInt(IDX_DEFENSE, Math.max(0, d)); }
    public int getAttack() { return getInt(IDX_ATTACK, 0); }
    public void setAttack(int attack) { setInt(IDX_ATTACK, Math.max(0, attack)); }
    public int getActionPoints() { return actionPoints; }
    public void setActionPoints(int points) { actionPoints = Math.max(0, points); }
    public void calculateAndSetAttack() { }
    public void calculateAndSetDefense() { }
    public boolean isStunned() { return stunned; }
    public void setStunned(boolean b) { stunned = b; }
    public boolean isSilenced() { return silenced; }
    public void setSilenced(boolean b) { silenced = b; }
    public boolean isHidden() { return hidden; }
    public void setHidden(boolean b) { hidden = b; }
    public Set<String> getCharInventory() { return charInventory; }
    public void setCharInventory(Set<String> inventory) { this.charInventory = (inventory == null) ? new HashSet<>() : inventory; }
    public void addToInventory(String item) { if (item != null) charInventory.add(item); }
    public boolean removeFromInventory(String item) { return item != null && charInventory.remove(item); }
    public Set<String> getSpellsLearned() { return spellsLearned; }
    public void setSpellsLearned(Set<String> spells) { spellsLearned = (spells == null) ? new HashSet<>() : spells; }
    public Set<String> getGuildSpells() { return guildSpells; }
    public void setGuildSpells(Set<String> spells) { guildSpells = (spells == null) ? new HashSet<>() : spells; }
    public List<Quest> getActiveQuests() { return activeQuests; }
    public void setActiveQuests(List<Quest> activeQuests) { this.activeQuests = (activeQuests == null) ? new ArrayList<>() : activeQuests; }
    public void addActiveQuest(Quest quest) { if (quest != null && !activeQuests.contains(quest)) activeQuests.add(quest); }
    public boolean removeActiveQuest(Quest quest) { return quest != null && activeQuests.remove(quest); }
    public GuildType getCurrentGuild() { return currentGuild; }
    public void setCurrentGuild(GuildType guild) { this.currentGuild = guild; }
    public GuildMembershipStatus getCurrentGuildStatus() { return currentGuildStatus; }
    public void setCurrentGuildStatus(GuildMembershipStatus status) { this.currentGuildStatus = status; }
    public Guild getGuild() { return guild; }
    public void setGuild(Guild guild) { this.guild = guild; }
    public Map<GuildType, GuildMembershipStatus> getGuildStatusMap() { return guildStatusMap; }
    public void setGuildStatusMap(Map<GuildType, GuildMembershipStatus> map) { this.guildStatusMap = (map == null) ? new HashMap<>() : map; }
    public StatusManager getStatusManager() { return statusManager; }
    public void setStatusManager(StatusManager statusManager) { this.statusManager = (statusManager == null) ? new StatusManager() : statusManager; }
    public List<Status> getStatuses() { return statuses; }
    public void setStatuses(List<Status> statuses) { this.statuses = (statuses == null) ? new ArrayList<>() : statuses; }
    public void applyStatusEffect(StatusType type, int duration, int value, Charecter caster) { }
    public void removeStatusEffect(StatusType type) {
        if (type == null) return;
        if (statuses != null) statuses.removeIf(s -> s != null && type.equals(s.getType()));
        if (statusManager == null) return;
        try {
            statusManager.getClass().getMethod("removeStatusEffect", StatusType.class).invoke(statusManager, type);
            return;
        } catch (Exception ignored) { }
        try {
            statusManager.getClass().getMethod("removeStatus", StatusType.class).invoke(statusManager, type);
            return;
        } catch (Exception ignored) { }
        try {
            statusManager.getClass().getMethod("removeStatus", String.class).invoke(statusManager, type.name());
            return;
        } catch (Exception ignored) { }
        try {
            Object result = statusManager.getClass().getMethod("getStatuses").invoke(statusManager);
            if (result instanceof List<?> list) {
                list.removeIf(o -> (o instanceof Status st) && type.equals(st.getType()));
            }
        } catch (Exception ignored) { }
    }
    public List<String> getGuildStorage() {
        return guildStorage;
    }

    public void setGuildStorage(List<String> storage) {
        this.guildStorage = (storage == null) ? new ArrayList<>() : storage;
    }
    public void takeDamage(int amount) { setHitPoints(Math.max(0, getHitPoints() - Math.max(0, amount))); }
    public void restoreHitPoints(int amount) { setHitPoints(getHitPoints() + Math.max(0, amount)); }
    public void takeDamageWithStatuses(int damage) {
        double mult = 1.0;
        if (statuses != null) {
            for (Status s : statuses) {
                try { mult *= s.damageTakenMultiplier(); } catch (Exception ignored) { }
            }
        }
        int finalDamage = (int) Math.round(damage * mult);
        takeDamage(finalDamage);
    }
    public boolean hasStatus(String statusName) {
        if (statusName == null || statuses == null) return false;
        for (Status s : statuses) {
            try { if (statusName.equalsIgnoreCase(s.getName())) return true; } catch (Exception ignored) { }
        }
        return false;
    }
    public Status getStatusByName(String statusName) {
        if (statusName == null || statuses == null) return null;
        for (Status s : statuses) {
            try { if (statusName.equalsIgnoreCase(s.getName())) return s; } catch (Exception ignored) { }
        }
        return null;
    }
    @Override
    public int getMaxHitPoints() {
        final int vitality = Math.max(0, getVitality());
        if (vitality == 0) return Math.max(0, getHitPoints());
        final String clazz = getToonClass();
        final int base = ("Paladin".equals(clazz) || "Warrior".equals(clazz)) ? 2 : 1;
        return Math.max(0, base * (vitality * 10));
    }
    @Override
    public void addStatus(Status effectStatus) {
        if (effectStatus == null) return;
        try {
            if (effectStatus.isNegative()) {
                boolean hasWard = false;
                if (statuses != null) {
                    for (Status s : statuses) {
                        if (s instanceof PurityWardStatus || s instanceof AstralWardStatus) { hasWard = true; break; }
                        if (s instanceof Status) {
                            try {
                                if (s.getClass().getSimpleName().equals("PurityWardStatus")) { hasWard = true; break; }
                            } catch (Exception ignored) { }
                        }
                    }
                }
                if (!hasWard && statusManager != null) {
                    try {
                        for (Status s : statusManager.getActiveStatuses()) {
                            if (s instanceof PurityWardStatus) { hasWard = true; break; }
                        }
                    } catch (Exception ignored) { }
                }
                if (hasWard) {
                    try {
                        System.out.println(getName() + " is protected by a Purity Ward; negative status '" + effectStatus.getName() + "' resisted.");
                    } catch (Exception ignored) { }
                    return;
                }
            }
        } catch (Exception ignored) { }
        if (statuses == null) statuses = new ArrayList<>();
        if (!statuses.contains(effectStatus)) statuses.add(effectStatus);
        if (statusManager != null) {
            try {
                statusManager.getClass().getMethod("addStatus", Status.class).invoke(statusManager, effectStatus);
            } catch (Exception ignored) {
                try {
                    statusManager.getClass().getMethod("applyStatus", Status.class).invoke(statusManager, effectStatus);
                } catch (Exception ignoredToo) { }
            }
        }
    }
    public void clearCurses() { }
    public void clearNegativeEffects() {
        if (statuses != null) statuses.removeIf(s -> s != null && s.isNegative());
        if (statusManager == null) return;
        try {
            Object result = statusManager.getClass().getMethod("getStatuses").invoke(statusManager);
            if (result instanceof List<?> list) {
                list.removeIf(o -> (o instanceof Status st) && st.isNegative());
            }
            return;
        } catch (Exception ignored) { }
        try {
            statusManager.getClass().getMethod("removeNegativeEffects").invoke(statusManager);
            return;
        } catch (Exception ignored) { }
        try {
            statusManager.getClass().getMethod("clearNegativeEffects").invoke(statusManager);
        } catch (Exception ignored) { }
    }
    public double getHitChance() { return hitChance; }
    public void setHitChance(double hitChance) { this.hitChance = Math.max(0.0, Math.min(1.0, hitChance)); }
    public int getMaxMagicPoints() {
        final int wisdom = Math.max(0, getWisdom());
        if (wisdom == 0) return Math.max(0, getMagicPoints());
        return Math.max(0, wisdom * 10);
    }
    @Override
    public String getClassName() {
        String clazz = getToonClass();
        return (clazz == null || clazz.isBlank()) ? "Unknown" : clazz;
    }
    public int getAttackDamage() { return getAttack(); }
    public void gainExperience(int exp) {
        if (exp <= 0) return;
        setExperience(getExperience() + exp);
    }
    public void removeRandomNegativeStatus() {
        if (statuses == null || statuses.isEmpty()) return;
        List<Status> negatives = new ArrayList<>();
        for (Status s : statuses) {
            if (s != null && s.isNegative()) negatives.add(s);
        }
        if (negatives.isEmpty()) return;
        Status chosen = negatives.get(new Random().nextInt(negatives.size()));
        StatusType type = chosen.getType();
        if (type == null) return;
        removeStatusEffect(type);
    }
    public void setAccuracy(int accuracy) {
        int clamped = Math.max(0, Math.min(100, accuracy));
        setHitChance(clamped / 100.0);
    }
    public int getAccuracy() {
        double clamped = Math.max(0.0, Math.min(1.0, getHitChance()));
        return (int) Math.round(clamped * 100.0);
    }
    public int getEvasion() {
        int base = Math.max(0, getAgility() / 2);
        int bonus = 0;
        if (statuses != null) {
            for (Status s : statuses) {
                if (s == null) continue;
                if (s.getType() != StatusType.EVASION_STATUS) continue;
                try {
                    Object v = s.getClass().getMethod("getValue").invoke(s);
                    if (v instanceof Number n) bonus += n.intValue();
                } catch (Exception ignored) { bonus += 3; }
            }
        }
        return Math.max(0, Math.min(100, base + bonus));
    }
    public void setEvasion(int evasion) {
        int desired = Math.max(0, Math.min(100, evasion));
        int base = Math.max(0, getAgility() / 2);
        int bonusNeeded = desired - base;
        removeStatusEffect(StatusType.EVASION_STATUS);
        if (bonusNeeded <= 0) return;
        applyStatusEffect(StatusType.EVASION_STATUS, Integer.MAX_VALUE, bonusNeeded, this);
    }
    public void addDamageBonus(int i) { }
    public void addHasteModifier(double hasteBonus) { }
    public void removeHasteModifier(double hasteBonus) { }
    public double getEvadeChance() { return 0; }
    public void setEvadeChance(double d) { }
    public void increaseResilience(int value) { }
    public int getMaxActionPoints() { return 0; }
    public int getMaxHealth() { return 0; }
    public int getPerception() { return 0; }
    public void setPerception(int i) { }
    public void addTemporaryPerceptionBuff(String string, int insightDuration) { }

    // --- Hunger/Thirst system ---
    public int getHungerLevel() { return hungerLevel; }
    public void setHungerLevel(int hungerLevel) { this.hungerLevel = Math.max(0, Math.min(100, hungerLevel)); }
    public int getThirstLevel() { return thirstLevel; }
    public void setThirstLevel(int thirstLevel) { this.thirstLevel = Math.max(0, Math.min(100, thirstLevel)); }
    public void eatFood(int amount) {
        setHungerLevel(getHungerLevel() + amount);
        if (getHungerLevel() > 100) setHungerLevel(100);
    }
    public void drinkWater(int amount) {
        setThirstLevel(getThirstLevel() + amount);
        if (getThirstLevel() > 100) setThirstLevel(100);
    }
    public void decreaseHunger(int amount) {
        setHungerLevel(getHungerLevel() - amount);
    }
    public void decreaseThirst(int amount) {
        setThirstLevel(getThirstLevel() - amount);
    }
    

 // In Charecter.java

 // 1. Max carry weight calculation
 public int getMaxCarryWeight() {
     // Example: base 30 + 5 per strength point
     return 30 + (getStrength() * 5);
 }

 // 2. Calculate current carry weight
 public int getCurrentCarryWeight(Map<String, Integer> itemWeights) {
     int total = 0;
     for (String item : charInventory) {
         total += itemWeights.getOrDefault(item, 1); // default weight 1 if not found
     }
     return total;
 }

 // 3. Add to inventory with weight check
 public boolean addToInventory(String item, Map<String, Integer> itemWeights) {
     int itemWeight = itemWeights.getOrDefault(item, 1);
     if (getCurrentCarryWeight(itemWeights) + itemWeight > getMaxCarryWeight()) {
         return false; // Too heavy
     }
     charInventory.add(item);
     return true;
 }

 public void removeResistance(String elementType) {
	// TODO Auto-generated method stub
	
 }

 public void addResistance(String elementType) {
	// TODO Auto-generated method stub
	
 }
 
 public int getSpellPower() {
	    // Example formula: INT * 2, plus small level scaling
	    int intelligence = getIntelligence();
	    int level = getLevel(); // assuming you have this; otherwise remove

	    int base = intelligence * 2;
	    int levelBonus = level; // or (int)(level * 1.5), etc.

	    return base + levelBonus;
	}

 public int getSpellResistanceBonus() {
	// TODO Auto-generated method stub
	return 0;
 }

 public void setSpellResistanceBonus(int i) {
	// TODO Auto-generated method stub
	
 }

 public void setEffectProtection(String string, boolean b) {
	// TODO Auto-generated method stub
	
 }

 public boolean hasEffectProtection(String string) {
	// TODO Auto-generated method stub
	return false;
 }

 public Object getEffectProtection() {
	// TODO Auto-generated method stub
	return null;
 }

}
