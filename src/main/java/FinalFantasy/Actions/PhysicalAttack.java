package FinalFantasy.Actions;

import FinalFantasy.StatusEffects;

import static FinalFantasy.ConsoleColors.RED_BOLD;
import static FinalFantasy.ConsoleColors.RED_UNDERLINED;

public class PhysicalAttack extends Action {
    public PhysicalAttack(String name, int mpCost, String description, int value, int hitChance, StatusEffects[] statusEffect, StatusEffects[] drawbacks, int turnDuration, boolean isAsPercentage) {
        super(name, mpCost, description, value, ActionTypes.PHYSICAL_ATTACK, hitChance, statusEffect, drawbacks, turnDuration, isAsPercentage);
    }

    public String toString() {
        return RED_UNDERLINED+RED_BOLD+ "Physical attack:" + super.toString();
    }
}
