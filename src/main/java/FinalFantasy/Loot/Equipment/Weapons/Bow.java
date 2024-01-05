package FinalFantasy.Loot.Equipment.Weapons;

import FinalFantasy.Enums.CharacterClass;
import FinalFantasy.Enums.WeaponTypes;

public class Bow extends Weapon implements java.io.Serializable {
    public Bow(String name, int value, String description, int atkBonus, int crtBonus) {
        super(name, value, description, WeaponTypes.BOW, new CharacterClass[]{CharacterClass.RANGED}, atkBonus,0, crtBonus, 2);
    }
}
