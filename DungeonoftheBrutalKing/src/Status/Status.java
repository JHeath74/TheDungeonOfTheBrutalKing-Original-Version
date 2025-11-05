
package Status;

import DungeonoftheBrutalKing.Charecter;

/**
 * Represents a status effect that can be applied to a character.
 * Each status has a name, duration, and an effect that modifies the character's attributes or behavior.
 */
public abstract class Status {
    protected String name; // Name of the status (e.g., "Poison", "Hunger")
    protected int duration; // Duration of the status in time units

    public Status(String name, int duration) {
        this.name = name;
        this.duration = duration;
    }

    public final String getName() {
        return name;
    }

    public final int getDuration() {
        return duration;
    }

    public void reduceDuration(int amount) {
        duration -= amount;
    }

    public boolean isExpired() {
        return duration <= 0;
    }

    public abstract void applyEffect(Charecter character);

    @Override
    public String toString() {
        return name + " (" + duration + ")";
    }


 // src/Status/Status.java
 public void expireEffect(Charecter character) {
     character.resetHitChance();
     // Reset other attributes if they were modified by this status
     // e.g., character.resetDefense(), character.resetAttack(), etc.
 }

 public boolean canAct() {
	// TODO Auto-generated method stub
	return false;
 }

 public boolean canCastSpells() {
	// TODO Auto-generated method stub
	return false;
 }

 public boolean canAttack() {
	// TODO Auto-generated method stub
	return false;
 }

 public void remove(Charecter character) {
	// TODO Auto-generated method stub
	
 }

 public void apply(Charecter character) {
	// TODO Auto-generated method stub
	
 }

}
