package FinalFantasy.Loot.Equipment.Weapons;

import FinalFantasy.Enums.CharacterClass;
import FinalFantasy.Enums.WeaponTypes;

public class Crossbow extends Weapon implements java.io.Serializable {
    public Crossbow(String name, int value, String description, int atkBonus, int defBonus, int crtBonus) {
        super(name, value, description, WeaponTypes.CROSSBOW, new CharacterClass[]{CharacterClass.RANGED}, atkBonus, defBonus, crtBonus, 2);
    }
}
