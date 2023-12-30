package FinalFantasy.Loot.Equipment.Armors;

import FinalFantasy.Character.Character;
import FinalFantasy.Character.CharacterClass;
import FinalFantasy.StatusEffects;

public class ChestPlate extends Armor {
    public ChestPlate(String name, int value, String description, int hpBonus, int mpBonus, int atkBonus, int defBonus, int crtBonus, int spdBonus, CharacterClass characterClass, StatusEffects[] statusEffects) {
        super(name, value, description, hpBonus, mpBonus, atkBonus, defBonus, crtBonus, spdBonus, characterClass, ArmorTypes.CHEST_PLATE, statusEffects);
    }


}
