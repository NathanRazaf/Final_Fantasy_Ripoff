package FinalFantasy.Loot.Equipment.Weapons;

import FinalFantasy.Enums.CharacterClass;
import FinalFantasy.Enums.WeaponTypes;
import FinalFantasy.Loot.Loot;

public abstract class Weapon extends Loot implements java.io.Serializable {
    protected final WeaponTypes weaponType;
    protected final CharacterClass[] characterClasses;
    protected final int atkBonus;
    protected final int defBonus;
    protected final int crtBonus;
    protected final int numHands;

    public Weapon(String name, int value, String description, WeaponTypes weaponType, CharacterClass[] characterClasses, int atkBonus, int defBonus, int crtBonus, int numHands) {
        super(name, value, description);
        this.weaponType = weaponType;
        this.characterClasses = characterClasses;
        this.atkBonus = atkBonus;
        this.defBonus = defBonus;
        this.crtBonus = crtBonus;
        this.numHands = numHands;
    }

    public WeaponTypes getWeaponType() {
        return this.weaponType;
    }
    public CharacterClass[] getCharacterClasses() {
        return this.characterClasses;
    }
    public int getAtkBonus() {
        return this.atkBonus;
    }
    public int getDefBonus() {
        return this.defBonus;
    }
    public int getCrtBonus() {
        return this.crtBonus;
    }
    public int getNumHands() {
        return this.numHands;
    }
    private String getCharacterClassesString() {
        StringBuilder characterClassesString = new StringBuilder();
        for (int i = 0; i < this.characterClasses.length; i++) {
            characterClassesString.append(this.characterClasses[i].toString());
            if (i < this.characterClasses.length - 1) {
                characterClassesString.append(", ");
            }
        }
        return characterClassesString.toString();
    }

    public String toString() {
        return this.getName() + " - " + this.getDescription() + "\n" +
                "Weapon Type: " + this.getWeaponType() + "\n" +
                "Character Class: " + this.getCharacterClassesString() + "\n" +
                "Attack Bonus: " + this.getAtkBonus() + "\n" +
                "Defense Bonus: " + this.getDefBonus() + "\n" +
                "Critical Chance Bonus: " + this.getCrtBonus() + "\n" +
                "Number of Hands Required: " + this.getNumHands();
    }

}
