package FinalFantasy.Actions;

import FinalFantasy.StatusEffects;

public class MagicalAttack extends Action {
    public MagicalAttack(String name, int mpCost, String description, int value, int hitChance, StatusEffects[] statusEffect, StatusEffects[] drawbacks, int turnDuration, boolean isAsPercentage) {
        super(name, mpCost, description, value, ActionTypes.MAGICAL_ATTACK, hitChance, statusEffect, drawbacks, turnDuration, isAsPercentage);
    }

    public String toString() {
        return "Magical attack : " + super.toString();
    }
}
