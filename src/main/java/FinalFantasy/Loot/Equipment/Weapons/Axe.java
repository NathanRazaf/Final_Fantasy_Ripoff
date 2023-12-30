package FinalFantasy.Loot.Equipment.Weapons;

import FinalFantasy.Character.CharacterClass;

public class Axe extends Weapon {
    public Axe(String name, int value, String description, int atkBonus, int crtBonus) {
        super(name, value, description, WeaponTypes.AXE, new CharacterClass[]{CharacterClass.MELEE}, atkBonus, 0, crtBonus,2);
    }
}
