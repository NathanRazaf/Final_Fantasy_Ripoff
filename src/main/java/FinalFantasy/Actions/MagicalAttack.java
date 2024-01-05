package FinalFantasy.Actions;

import FinalFantasy.Enums.ActionTypes;
import FinalFantasy.Enums.StatusEffects;

import static Utilities.ConsoleColors.*;

public class MagicalAttack extends Action implements java.io.Serializable {
    public MagicalAttack(String name, int mpCost, String description, int value, int hitChance, StatusEffects[] statusEffect, StatusEffects[] drawbacks, int turnDuration, boolean isAsPercentage) {
        super(name, mpCost, description, value, ActionTypes.MAGICAL_ATTACK, hitChance, statusEffect, drawbacks, turnDuration, isAsPercentage);
    }

    public String toString() {
        return PURPLE_UNDERLINED + PURPLE_BOLD + "Magical attack:" + RESET + super.toString();
    }
}
