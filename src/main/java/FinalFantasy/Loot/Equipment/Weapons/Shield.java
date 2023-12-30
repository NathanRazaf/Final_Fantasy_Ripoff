package FinalFantasy.Loot.Equipment.Weapons;

import FinalFantasy.Character.CharacterClass;

public class Shield extends Weapon {
    public Shield(String name, int value, String description, int defBonus) {
        super(name, value, description, WeaponTypes.SHIELD, new CharacterClass[]{CharacterClass.MELEE, CharacterClass.MAGIC}, 0, defBonus, 0, 1);
    }
}
