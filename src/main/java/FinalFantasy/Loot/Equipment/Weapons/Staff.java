package FinalFantasy.Loot.Equipment.Weapons;

import FinalFantasy.Enums.CharacterClass;
import FinalFantasy.Enums.WeaponTypes;

public class Staff extends Weapon implements java.io.Serializable {
    public Staff(String name, int value, String description, int atkBonus, int defBonus, int crtBonus) {
        super(name, value, description, WeaponTypes.STAFF, new CharacterClass[]{CharacterClass.MAGIC}, atkBonus, defBonus, crtBonus, 1);
    }

}
