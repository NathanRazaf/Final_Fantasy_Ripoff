package FinalFantasy.Loot.Equipment.Weapons;

import FinalFantasy.Character.CharacterClass;
import FinalFantasy.StatusEffects;

public class Staff extends Weapon {
    public Staff(String name, int value, String description, int atkBonus, int defBonus, int crtBonus) {
        super(name, value, description, WeaponTypes.STAFF, new CharacterClass[]{CharacterClass.MAGIC}, atkBonus, defBonus, crtBonus, 1);
    }

}
