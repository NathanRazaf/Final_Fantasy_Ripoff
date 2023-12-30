package FinalFantasy.Loot.Equipment.Weapons;

import FinalFantasy.Character.CharacterClass;

public class Crossbow extends Weapon {
    public Crossbow(String name, int value, String description, int atkBonus, int defBonus, int crtBonus) {
        super(name, value, description, WeaponTypes.CROSSBOW, new CharacterClass[]{CharacterClass.RANGED}, atkBonus, defBonus, crtBonus, 2);
    }
}
