package FinalFantasy.Actions;

import FinalFantasy.StatusEffects;

public class PhysicalAttack extends Action {
    public PhysicalAttack(String name, int mpCost, String description, int value, int hitChance, StatusEffects[] statusEffect, StatusEffects[] drawbacks, int turnDuration, boolean isAsPercentage) {
        super(name, mpCost, description, value, ActionTypes.PHYSICAL_ATTACK, hitChance, statusEffect, drawbacks, turnDuration, isAsPercentage);
    }

    public String toString() {
        return "Physical attack : " + super.toString();
    }
}
