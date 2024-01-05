package FinalFantasy.Loot.Equipment.Weapons;

import FinalFantasy.Enums.CharacterClass;
import FinalFantasy.Enums.WeaponTypes;

public class Shield extends Weapon implements java.io.Serializable {
    public Shield(String name, int value, String description, int defBonus) {
        super(name, value, description, WeaponTypes.SHIELD, new CharacterClass[]{CharacterClass.MELEE, CharacterClass.MAGIC}, 0, defBonus, 0, 1);
    }
}
