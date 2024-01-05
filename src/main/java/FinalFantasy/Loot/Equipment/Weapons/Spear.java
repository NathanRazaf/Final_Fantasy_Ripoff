package FinalFantasy.Loot.Equipment.Weapons;

import FinalFantasy.Enums.CharacterClass;
import FinalFantasy.Enums.WeaponTypes;

public class Spear extends Weapon implements java.io.Serializable {
    public Spear(String name, int value, String description, int atkBonus, int crtBonus) {
        super(name, value, description, WeaponTypes.SPEAR, new CharacterClass[]{CharacterClass.MELEE}, atkBonus, 0, crtBonus,1);
    }
}
