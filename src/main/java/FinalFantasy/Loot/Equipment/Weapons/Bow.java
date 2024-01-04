package FinalFantasy.Loot.Equipment.Weapons;

import FinalFantasy.Character.CharacterClass;

public class Bow extends Weapon {
    public Bow(String name, int value, String description, int atkBonus, int crtBonus) {
        super(name, value, description, WeaponTypes.BOW, new CharacterClass[]{CharacterClass.RANGED}, atkBonus,0, crtBonus, 2);
    }
}
