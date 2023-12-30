package FinalFantasy.Loot.Equipment.Weapons;

import FinalFantasy.Character.CharacterClass;

public class Spear extends Weapon {
    public Spear(String name, int value, String description, int atkBonus, int crtBonus) {
        super(name, value, description, WeaponTypes.SPEAR, new CharacterClass[]{CharacterClass.MELEE}, atkBonus, 0, crtBonus,1);
    }
}
