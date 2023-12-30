package FinalFantasy.Loot.Equipment.Weapons;

import FinalFantasy.Character.Character;
import FinalFantasy.Character.CharacterClass;
import FinalFantasy.Loot.Loot;
import FinalFantasy.StatusEffects;

public abstract class Weapon extends Loot {
    protected final WeaponTypes weaponType;
    protected final CharacterClass[] classType;
    protected final int atkBonus;
    protected final int defBonus;
    protected final int crtBonus;
    protected final int numHands;

    public Weapon(String name, int value, String description, WeaponTypes weaponType, CharacterClass[] classType, int atkBonus, int defBonus, int crtBonus, int numHands) {
        super(name, value, description);
        this.weaponType = weaponType;
        this.classType = classType;
        this.atkBonus = atkBonus;
        this.defBonus = defBonus;
        this.crtBonus = crtBonus;
        this.numHands = numHands;
    }

    public WeaponTypes getWeaponType() {
        return this.weaponType;
    }
    public CharacterClass[] getClassType() {
        return this.classType;
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

}
