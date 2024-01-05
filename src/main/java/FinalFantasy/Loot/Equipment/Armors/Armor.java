package FinalFantasy.Loot.Equipment.Armors;

import FinalFantasy.Enums.ArmorTypes;
import FinalFantasy.Enums.CharacterClass;
import FinalFantasy.Loot.Loot;
import FinalFantasy.Enums.StatusEffects;

public abstract class Armor extends Loot implements java.io.Serializable {
    protected final int hpBonus, mpBonus, atkBonus, defBonus, crtBonus, spdBonus;
    protected final CharacterClass characterClass;
    protected final ArmorTypes armorType;
    protected final StatusEffects[] statusEffects;

    public Armor(String name, int value, String description, int hpBonus, int mpBonus, int atkBonus, int defBonus, int crtBonus, int spdBonus, CharacterClass characterClass, ArmorTypes armorType, StatusEffects[] statusEffects) {
        super(name, value, description);
        this.hpBonus = hpBonus;
        this.mpBonus = mpBonus;
        this.atkBonus = atkBonus;
        this.defBonus = defBonus;
        this.crtBonus = crtBonus;
        this.spdBonus = spdBonus;
        this.characterClass = characterClass;
        this.armorType = armorType;
        this.statusEffects = statusEffects;
    }
    public CharacterClass getCharacterClass() {
        return this.characterClass;
    }
    public StatusEffects[] getStatusEffects() {
        return this.statusEffects;
    }
    public int getHpBonus() {
        return this.hpBonus;
    }
    public int getMpBonus() {
        return this.mpBonus;
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
    public int getSpdBonus() {
        return this.spdBonus;
    }
    public ArmorTypes getArmorType() {
        return this.armorType;
    }
    private String getStatusEffectsString() {
        if (this.statusEffects == null) {
            return "None";
        }
        StringBuilder statusEffectsString = new StringBuilder();
        for (StatusEffects statusEffect : this.statusEffects) {
            statusEffectsString.append(statusEffect).append("; ");
        }
        return statusEffectsString.toString();
    }
    public String toString() {
        return this.getName() + " - " + this.getDescription() + "\n" +
                "HP bonus: " + this.getHpBonus() + "\n" +
                "MP bonus: " + this.getMpBonus() + "\n" +
                "ATK bonus: " + this.getAtkBonus() + "\n" +
                "DEF bonus: " + this.getDefBonus() + "\n" +
                "CRT bonus: " + this.getCrtBonus() + "\n" +
                "SPD bonus: " + this.getSpdBonus() + "\n" +
                "Class: " + this.getCharacterClass() + "\n" +
                "Type: " + this.getArmorType() + "\n" +
                "Status Effects: " + this.getStatusEffectsString();
    }
}
