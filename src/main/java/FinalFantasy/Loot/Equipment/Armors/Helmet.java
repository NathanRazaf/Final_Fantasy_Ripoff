package FinalFantasy.Loot.Equipment.Armors;

import FinalFantasy.Enums.ArmorTypes;
import FinalFantasy.Enums.CharacterClass;
import FinalFantasy.Enums.StatusEffects;

public class Helmet extends Armor implements java.io.Serializable {
    public Helmet(String name, int value, String description, int hpBonus, int mpBonus, int atkBonus, int defBonus, int crtBonus, int spdBonus, CharacterClass characterClass, StatusEffects[] statusEffects) {
        super(name, value, description, hpBonus, mpBonus, atkBonus, defBonus, crtBonus, spdBonus, characterClass, ArmorTypes.HELMET, statusEffects);
    }


}
