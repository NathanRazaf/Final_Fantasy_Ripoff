package FinalFantasy.Loot.Equipment.Armors;

import FinalFantasy.Character.Character;
import FinalFantasy.Character.CharacterClass;
import FinalFantasy.Loot.Loot;
import FinalFantasy.StatusEffects;

public abstract class Armor extends Loot {
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

}
