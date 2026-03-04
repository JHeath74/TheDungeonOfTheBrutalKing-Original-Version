
// `src/DungeonoftheBrutalKing/Charecter.java`
package DungeonoftheBrutalKing;

import java.util.*;
import Quests.Quest;
import SharedData.Guild;
import SharedData.GuildMembershipStatus;
import SharedData.GuildType;
import Status.HasHitPoints;
import Status.Status;
import Status.StatusManager;
import Status.StatusType;

public class Charecter implements HasHitPoints {

    private static Charecter instance;

    // NOTE: This class stores most persisted fields in `charInfo` by index.
    // Expanded to 34 to include VITALITY at index 33.
    private ArrayList<String> charInfo = new ArrayList<>(Collections.nCopies(34, "0"));

    private Set<String> spellsLearned = new HashSet<>();
    private Set<String> charInventory = new HashSet<>();
    private Set<String> guildSpells = new HashSet<>();
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

    // --- Persisted charInfo indices (existing layout + new VITALITY) ---
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

    private static final int IDX_VITALITY = 33; // NEW

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

        // Ensure vitality exists for older saves; default 0 means "not rolled yet".
        if (getStr(IDX_VITALITY) == null || getStr(IDX_VITALITY).isBlank()) {
            setInt(IDX_VITALITY, 0);
        }
    }

    // --- Internal helpers ---
    private int getInt(int index, int def) {
        try {
            if (index < 0 || index >= charInfo.size()) return def;
            String v = charInfo.get(index);
            if (v == null || v.isBlank()) return def;
            return Integer.parseInt(v.trim());
        } catch (Exception ignored) {
            return def;
        }
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

    private void ensureSize(int size) {
        if (charInfo == null) charInfo = new ArrayList<>();
        while (charInfo.size() < size) charInfo.add("0");
    }

    // --- Core persisted fields ---
    public ArrayList<String> getCharInfo() { return charInfo; }

    public void setCharInfo(ArrayList<String> charInfo) {
        this.charInfo = (charInfo == null) ? new ArrayList<>() : charInfo;
        // Backward compatibility: old saves may have fewer entries.
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
        // Keep "final hp" slot in sync if you use it elsewhere.
        setInt(IDX_FINAL_HP, Math.max(0, hp));
    }

    public int getMagicPoints() { return getInt(IDX_MAGICPOINTS, 0); }
    public void setMagicPoints(int mp) { setInt(IDX_MAGICPOINTS, Math.max(0, mp)); }

    // --- Base stats ---
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

    // --- NEW: Vitality ---
    
    
    public int getVitality() {
        return getInt(IDX_VITALITY, 0);
    }

    public void setVitality(int vitality) {
        setInt(IDX_VITALITY, Math.max(0, vitality));
        // Make HP determined by Vitality:
        // Update max/start HP based on current class and vitality.
        int newMaxHp = calculateMaxHitPointsFromVitality();
        setHitPoints(newMaxHp);
    }

    private int calculateMaxHitPointsFromVitality() {
        String clazz = getToonClass();
        int base = ("Paladin".equals(clazz) || "Warrior".equals(clazz)) ? 2 : 1;
        return base * (getVitality() * 10);
    }

    // --- Economy / items ---
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

    public String getWeapon() { return getStr(IDX_WEAPON); }
    public void setWeapon(String weapon) { setStr(IDX_WEAPON, weapon); }

    public String getArmour() { return getStr(IDX_ARMOUR); }
    public void setArmour(String armour) { setStr(IDX_ARMOUR, armour); }

    public String getShield() { return getStr(IDX_SHIELD); }
    public void setShield(String shield) { setStr(IDX_SHIELD, shield); }

    public int getAlignment() { return getInt(IDX_ALIGNMENT, 0); }
    public void setAlignment(int alignment) { setInt(IDX_ALIGNMENT, alignment); }

    // --- Position / facing ---
    public void setPosition(int x, int y, int z) { setInt(IDX_POS_X, x); setInt(IDX_POS_Y, y); setInt(IDX_POS_Z, z); }

    public void getPosition(int[] pos) {
        if (pos == null || pos.length < 3) return;
        pos[0] = getInt(IDX_POS_X, 0);
        pos[1] = getInt(IDX_POS_Y, 0);
        pos[2] = getInt(IDX_POS_Z, 0);
    }
    
    public int getX() {
        return getInt(IDX_POS_X, 0);
    }

    public int getY() {
        return getInt(IDX_POS_Y, 0);
    }

    public int getDirection() { return getInt(IDX_DIRECTION, 0); }
    public void setDirection(int degrees) { setInt(IDX_DIRECTION, degrees); }

    // --- Combat core ---
    public int getDefense() { return getInt(IDX_DEFENSE, 0); }
    public void setDefense(int d) { setInt(IDX_DEFENSE, Math.max(0, d)); }

    public int getAttack() { return getInt(IDX_ATTACK, 0); }
    public void setAttack(int attack) { setInt(IDX_ATTACK, Math.max(0, attack)); }

    public int getActionPoints() { return actionPoints; }
    public void setActionPoints(int points) { actionPoints = Math.max(0, points); }

    // Placeholder calculations (keeps existing call sites compiling).
    public void calculateAndSetAttack() { /* existing logic elsewhere in file/project */ }
    public void calculateAndSetDefense() { /* existing logic elsewhere in file/project */ }

    // --- Flags ---
    public boolean isStunned() { return stunned; }
    public void setStunned(boolean b) { stunned = b; }

    public boolean isSilenced() { return silenced; }
    public void setSilenced(boolean b) { silenced = b; }

    public boolean isHidden() { return hidden; }
    public void setHidden(boolean b) { hidden = b; }

    // --- Inventory / spells / quests ---
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

    // --- Guild ---
    public GuildType getCurrentGuild() { return currentGuild; }
    public void setCurrentGuild(GuildType guild) { this.currentGuild = guild; }

    public GuildMembershipStatus getCurrentGuildStatus() { return currentGuildStatus; }
    public void setCurrentGuildStatus(GuildMembershipStatus status) { this.currentGuildStatus = status; }

    public Guild getGuild() { return guild; }
    public void setGuild(Guild guild) { this.guild = guild; }

    public Map<GuildType, GuildMembershipStatus> getGuildStatusMap() { return guildStatusMap; }
    public void setGuildStatusMap(Map<GuildType, GuildMembershipStatus> map) {
        this.guildStatusMap = (map == null) ? new HashMap<>() : map;
    }

    // --- Status system minimal accessors (keeps integration compiling) ---
    public StatusManager getStatusManager() { return statusManager; }
    public void setStatusManager(StatusManager statusManager) { this.statusManager = (statusManager == null) ? new StatusManager() : statusManager; }

    public List<Status> getStatuses() { return statuses; }
    public void setStatuses(List<Status> statuses) { this.statuses = (statuses == null) ? new ArrayList<>() : statuses; }

    public void applyStatusEffect(StatusType type, int duration, int value, Charecter caster) { /* existing logic elsewhere */ }
    public void removeStatusEffect(StatusType type) { /* existing logic elsewhere */ }

    // --- Common helpers used elsewhere ---
    public void takeDamage(int amount) { setHitPoints(Math.max(0, getHitPoints() - Math.max(0, amount))); }
    public void restoreHitPoints(int amount) { setHitPoints(getHitPoints() + Math.max(0, amount)); }

    @Override
    public int getMaxHitPoints() {
        // Vitality drives HP (same rule as CharacterCreation\#ToonHP)
        final int vitality = Math.max(0, getVitality());
        if (vitality == 0) {
            // Backward compatibility for older saves where vitality wasn't stored
            return Math.max(0, getHitPoints());
        }

        final String clazz = getToonClass();
        final int base = ("Paladin".equals(clazz) || "Warrior".equals(clazz)) ? 2 : 1;

        return Math.max(0, base * (vitality * 10));
    }

    @Override
    public void addStatus(Status effectStatus) {
        if (effectStatus == null) return;

        // Keep local list in sync (avoid duplicates).
        if (statuses == null) statuses = new ArrayList<>();
        if (!statuses.contains(effectStatus)) {
            statuses.add(effectStatus);
        }

        // If a StatusManager exists, forward the add (supports either method name).
        if (statusManager != null) {
            try {
                // Prefer: statusManager.addStatus(Status)
                statusManager.getClass().getMethod("addStatus", Status.class).invoke(statusManager, effectStatus);
            } catch (Exception ignored) {
                try {
                    // Fallback: statusManager.applyStatus(Status)
                    statusManager.getClass().getMethod("applyStatus", Status.class).invoke(statusManager, effectStatus);
                } catch (Exception ignoredToo) {
                    // No compatible method; local list still tracks statuses.
                }
            }
        }
    }

	public void clearCurses() {
		// TODO Auto-generated method stub
		
	}

	public void clearNegativeEffects() {
	    // Remove negative statuses from local list
	    if (statuses != null) {
	        statuses.removeIf(s -> s != null && s.isNegative());
	    }

	    // Best-effort: also clear them from StatusManager if it tracks independently
	    if (statusManager == null) return;

	    try {
	        // If StatusManager exposes getStatuses(): List<Status>
	        Object result = statusManager.getClass().getMethod("getStatuses").invoke(statusManager);
	        if (result instanceof List<?> list) {
	            list.removeIf(o -> (o instanceof Status st) && st.isNegative());
	        }
	        return;
	    } catch (Exception ignored) {
	        // fall through
	    }

	    try {
	        // If StatusManager exposes removeNegativeEffects()
	        statusManager.getClass().getMethod("removeNegativeEffects").invoke(statusManager);
	        return;
	    } catch (Exception ignored) {
	        // fall through
	    }

	    try {
	        // If StatusManager exposes clearNegativeEffects()
	        statusManager.getClass().getMethod("clearNegativeEffects").invoke(statusManager);
	    } catch (Exception ignored) {
	        // No compatible API; local list was still cleaned.
	    }
	}

	public double getHitChance() {
	    return hitChance;
	}

	public void setHitChance(double hitChance) {
	    // Clamp to \[0.0, 1.0\] to avoid negative or >100% hit rates.
	    this.hitChance = Math.max(0.0, Math.min(1.0, hitChance));
	}
	
	 public int getMaxMagicPoints() {
	        // Use Wisdom as the MP driver (fallback to current MP for older saves/default 0).
	        final int wisdom = Math.max(0, getWisdom());
	        if (wisdom == 0) {
	            return Math.max(0, getMagicPoints());
	        }

	        // Simple scaling rule: 10 MP per Wisdom (adjust if you have a different balance target).
	        return Math.max(0, wisdom * 10);
	    }

	 @Override
	 public String getClassName() {
	     String clazz = getToonClass();
	     return (clazz == null || clazz.isBlank()) ? "Unknown" : clazz;
	 }

	 public int getAttackDamage() {
		    return getAttack();
		}

	 public void gainExperience(int exp) {
		    if (exp <= 0) return;
		    setExperience(getExperience() + exp);
		}
}
