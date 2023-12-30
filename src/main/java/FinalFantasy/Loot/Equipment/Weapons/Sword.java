package FinalFantasy.Loot.Equipment.Weapons;

import FinalFantasy.Character.CharacterClass;
import FinalFantasy.StatusEffects;

public class Sword extends Weapon {
    public Sword(String name, int value, String description, int atkBonus, int crtBonus) {
        super(name, value, description, WeaponTypes.SWORD, new CharacterClass[]{CharacterClass.MELEE}, atkBonus, 0, crtBonus,1);
    }
}
