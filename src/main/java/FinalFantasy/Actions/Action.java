package FinalFantasy.Actions;

import FinalFantasy.Enums.ActionTypes;
import FinalFantasy.Enums.StatusEffects;

import static Utilities.ConsoleColors.*;

public abstract class Action implements java.io.Serializable {
    protected String name;
    protected int mpCost;
    protected String description;
    protected int value;
    protected ActionTypes actionType;
    protected int hitChance;
    protected StatusEffects[] statusEffects;
    protected StatusEffects[] drawbacks;
    protected int turnDuration;
    protected boolean isAsPercentage;

    public Action(String name, int mpCost, String description, int value, ActionTypes actionType, int hitChance, StatusEffects[] statusEffect, StatusEffects[] drawbacks, int turnDuration, boolean isAsPercentage) {
        this.name = name;
        this.mpCost = mpCost;
        this.description = description;
        this.value = value;
        this.actionType = actionType;
        this.hitChance = hitChance;
        this.statusEffects = statusEffect;
        this.drawbacks = drawbacks;
        this.turnDuration = turnDuration;
        this.isAsPercentage = isAsPercentage;
    }

    public String getName() {
        return this.name;
    }
    public int getMpCost() {
        return this.mpCost;
    }
    public String getDescription() {
        return this.description;
    }
    public int getValue() {
        return this.value;
    }
    public ActionTypes getActionType() {
        return this.actionType;
    }
    public int getHitChance() {
        return this.hitChance;
    }
    public StatusEffects[] getStatusEffects() {
        return this.statusEffects;
    }
    public StatusEffects[] getDrawbacks() {
        return this.drawbacks;
    }
    public int getTurnDuration() {
        return this.turnDuration;
    }
    public boolean getIsAsPercentage() {
        return this.isAsPercentage;
    }

    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(RESET + " ").append(this.name).append(" (").append(this.mpCost).append(" MP)").append("\n");
        sb.append(this.description).append("\n");
        sb.append("Has a ").append(this.hitChance).append("% chance to ");
        if (value > 0) {
            if (this.isAsPercentage) {
                sb.append("deal ").append(this.value).append("% of the target(s)' HP as damage");
            } else {
                sb.append("deal ").append(this.value).append(" HP damage to the target(s)");
            }
        }
        if (this.statusEffects != null) {
            sb.append(" and to apply ");
            for (int i = 0; i < this.statusEffects.length; i++) {
                sb.append(this.statusEffects[i].toString());
                if (i != this.statusEffects.length - 1) {
                    sb.append(", ");
                }
            }
            sb.append(" to the target(s) for ").append(this.turnDuration).append(" turn(s)");
        }
        sb.append("\n");
        if (this.drawbacks != null) {
            sb.append("Applies ");
            for (int i = 0; i < this.drawbacks.length; i++) {
                sb.append(this.drawbacks[i].toString());
                if (i != this.drawbacks.length - 1) {
                    sb.append(", ");
                }
            }
            sb.append(" to the user in return for ").append(this.turnDuration).append(" turn(s)");
        }

        return sb.toString();
    }

    public String toStringNoColor() {
            StringBuilder sb = new StringBuilder();
            sb.append(this.name).append(" (").append(this.mpCost).append(" MP)").append("\n");
            sb.append(this.description).append("\n");
            sb.append("Has a ").append(this.hitChance).append("% chance to ");
            if (value > 0) {
                if (this.isAsPercentage) {
                    sb.append("deal ").append(this.value).append("% of the target(s)' HP as damage");
                } else {
                    sb.append("deal ").append(this.value).append(" HP damage to the target(s)");
                }
            }
            if (this.statusEffects != null) {
                sb.append(" and to apply ");
                for (int i = 0; i < this.statusEffects.length; i++) {
                    sb.append(this.statusEffects[i].toString());
                    if (i != this.statusEffects.length - 1) {
                        sb.append(", ");
                    }
                }
                sb.append(" to the target(s) for ").append(this.turnDuration).append(" turn(s)");
            }
            sb.append("\n");
            if (this.drawbacks != null) {
                sb.append("Applies ");
                for (int i = 0; i < this.drawbacks.length; i++) {
                    sb.append(this.drawbacks[i].toString());
                    if (i != this.drawbacks.length - 1) {
                        sb.append(", ");
                    }
                }
                sb.append(" to the user in return for ").append(this.turnDuration).append(" turn(s)");
            }

            return sb.toString();

    }

    public String smallToString() {
        return this.name + " (" + this.mpCost + " MP)";
    }
}
