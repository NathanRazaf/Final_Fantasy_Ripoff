package FinalFantasy.Actions;

import FinalFantasy.StatusEffects;

public class MagicalRecovery extends Action {
    public MagicalRecovery(String name, int mpCost, String description, int value, StatusEffects[] statusEffects, StatusEffects[] drawbacks, int turnDuration, boolean isAsPercentage) {
        super(name, mpCost, description, value, ActionTypes.MAGICAL_RECOVERY, 100, statusEffects, drawbacks, turnDuration, isAsPercentage);
    }

    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("Magical recovery: ").append(this.name).append(" (").append(this.mpCost).append(" MP)").append("\n");
        sb.append(this.description).append("\n");
        if (value > 0) {
            if (this.isAsPercentage) {
                sb.append("Heals ").append(this.value).append("% of the target(s)' HP");
            } else {
                sb.append("Heals back ").append(this.value).append(" HP of the target(s)");
            }
        }
        if (this.statusEffects != null) {
            sb.append(" and applies ");
            for (int i = 0; i < this.statusEffects.length; i++) {
                sb.append(this.statusEffects[i].toString());
                if (i != this.statusEffects.length - 1) {
                    sb.append(", ");
                }
            }
            sb.append(" to the target(s) for ").append(this.turnDuration).append(" turn(s)");
        }

        return sb.toString();
    }
}
