package FinalFantasy.Character;

import FinalFantasy.Actions.Action;
import FinalFantasy.Enums.ActionTypes;
import FinalFantasy.Enums.CharacterClass;
import FinalFantasy.Enums.StatusEffects;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Set;

import static Utilities.Utility.randomIntRange;

public abstract class Character implements java.io.Serializable {
    protected final String name;
    private Character lastTarget;
    private int lastDamageDealt;
    private StatusEffects[] lastStatusEffectsApplied;
    private Action lastMoveUsed;
    protected int level;
    protected int maxHp, maxMp;
    protected int hp, mp, atk, def, crt, spd;
    protected final CharacterClass characterClass;
    protected final ArrayList<Action> actions = new ArrayList<>();
    protected HashMap<StatusEffects, Integer> statusEffects = new HashMap<>();
    protected ArrayList<StatusEffects> appliedEffects = new ArrayList<>();

    //constructor
    public Character(String name, int level, int hp, int mp, int atk, int def, int crt, int spd, CharacterClass characterClass) {
        this.name = name;
        this.level = level;
        this.maxHp = hp;
        this.maxMp = mp;
        this.hp = hp;
        this.mp = mp;
        this.atk = atk;
        this.def = def;
        this.crt = crt;
        this.spd = spd;
        this.characterClass = characterClass;
    }


    //getters and setters
    public String getName() {
        return name;
    }
    public int getHp() {
        return hp;
    }
    public int getMp() {
        return mp;
    }
    public int getAtk() {
        return atk;
    }
    public int getDef() {
        return def;
    }
    public int getCrt() {
        return crt;
    }
    public int getSpd() {
        return spd;
    }
    public int getMaxHp() {
        return maxHp;
    }
    public int getMaxMp() {
        return maxMp;
    }
    public ArrayList<Action> getActions() {
        return actions;
    }
    public CharacterClass getCharacterClass() {
        return characterClass;
    }
    public HashMap<StatusEffects, Integer> getStatusEffects() {
        return statusEffects;
    }

    public void setHp(int hp) {
        this.hp = hp;
    }
    public void setMp(int mp) {
        this.mp = mp;
    }
    public void setAtk(int atk) {
        this.atk = atk;
    }
    public void setDef(int def) {
        this.def = def;
    }
    public void setCrt(int crt) {
        this.crt = crt;
    }
    public void setSpd(int spd) {
        this.spd = spd;
    }

    public void setMaxHp(int maxHp) {
        this.maxHp = maxHp;
    }
    public void setMaxMp(int maxMp) {
        this.maxMp = maxMp;
    }

    public void addStatusEffect(StatusEffects statusEffect, int turnDuration) {
        if (statusEffects.containsKey(statusEffect)) {
            statusEffects.put(statusEffect, statusEffects.get(statusEffect) + turnDuration);
        } else {
            statusEffects.put(statusEffect, turnDuration);
        }
    }
    public void addStatusEffect(StatusEffects statusEffect) {
        statusEffects.put(statusEffect, Integer.MAX_VALUE/2);
    }
    public void removeStatusEffect(StatusEffects statusEffect) {
        statusEffects.remove(statusEffect);
    }
    public void addAction(Action action) {
        actions.add(action);
    }
    public void removeAction(Action action) {
        actions.remove(action);
    }
    public void applyEffect(StatusEffects effect) {
        switch (effect) {
            case POISONED:
                this.hp = (int) (this.hp * 0.95);
                System.out.println(this.getName() + " took 5% damage from poison!");
                break;
            case FOCUSED:
                if (!appliedEffects.contains(StatusEffects.FOCUSED)) {
                    this.crt = (int) (this.crt * 1.2);
                    appliedEffects.add(StatusEffects.FOCUSED);
                    System.out.println(this.getName() + " is focused!");
                }
                break;
            case CONFUSED:
                if (!appliedEffects.contains(StatusEffects.CONFUSED)) {
                    this.crt = (int) (this.crt * 0.8);
                    appliedEffects.add(StatusEffects.CONFUSED);
                    System.out.println(this.getName() + " is confused!");
                }
                break;
            case ACCELERATED:
                if (!appliedEffects.contains(StatusEffects.ACCELERATED)) {
                    this.spd = (int) (this.spd * 1.2);
                    appliedEffects.add(StatusEffects.ACCELERATED);
                    System.out.println(this.getName() + " is accelerated!");
                }
                break;
            case SLOWED:
                if (!appliedEffects.contains(StatusEffects.SLOWED)) {
                    this.spd = (int) (this.spd * 0.8);
                    appliedEffects.add(StatusEffects.SLOWED);
                    System.out.println(this.getName() + " is slowed!");
                }
                break;
            case WEAKENED:
                if (!appliedEffects.contains(StatusEffects.WEAKENED)) {
                    this.atk = (int) (this.atk * 0.8);
                    this.def = (int) (this.def * 0.8);
                    appliedEffects.add(StatusEffects.WEAKENED);
                    System.out.println(this.getName() + " is weakened!");
                }
                break;
            case STRENGTHENED:
                if (!appliedEffects.contains(StatusEffects.STRENGTHENED)) {
                    this.atk = (int) (this.atk * 1.2);
                    this.def = (int) (this.def * 1.2);
                    appliedEffects.add(StatusEffects.STRENGTHENED);
                    System.out.println(this.getName() + " is strengthened!");
                }
                break;
        }
        if (this.statusEffects.get(effect) == 0) {
            this.removeEffect(effect);
            return;
        }
        this.statusEffects.put(effect, this.statusEffects.get(effect) - 1);
    }
    public void removeEffect(StatusEffects effect) {
        switch (effect) {
            case POISONED:
                this.hp = (int) (this.hp / 0.95);
                break;
            case FOCUSED:
                this.crt = (int) (this.crt / 1.2);
                appliedEffects.remove(StatusEffects.FOCUSED);
                break;
            case CONFUSED:
                this.crt = (int) (this.crt / 0.8);
                appliedEffects.remove(StatusEffects.CONFUSED);
                break;
            case ACCELERATED:
                this.spd = (int) (this.spd / 1.2);
                appliedEffects.remove(StatusEffects.ACCELERATED);
                break;
            case SLOWED:
                this.spd = (int) (this.spd / 0.8);
                appliedEffects.remove(StatusEffects.SLOWED);
                break;
            case WEAKENED:
                this.atk = (int) (this.atk / 0.8);
                this.def = (int) (this.def / 0.8);
                appliedEffects.remove(StatusEffects.WEAKENED);
                break;
            case STRENGTHENED:
                this.atk = (int) (this.atk / 1.2);
                this.def = (int) (this.def / 1.2);
                appliedEffects.remove(StatusEffects.STRENGTHENED);
                break;
        }
        this.removeStatusEffect(effect);
    }
    public void applyEffects() {
        // Create a copy of the keySet to avoid ConcurrentModificationException
        Set<StatusEffects> effectsCopy = new HashSet<>(this.statusEffects.keySet());

        for (StatusEffects effect : effectsCopy) {
            this.applyEffect(effect);
        }
    }

    public void doAction(Action action, Character target) {
        this.lastTarget = target;
        switch (action.getActionType()) {
            case PHYSICAL_ATTACK, MAGICAL_ATTACK:
                this.attack(action, target);
                break;
            case MAGICAL_RECOVERY:
                this.heal(action, target);
                break;
        }
        this.lastMoveUsed = action;

        if (action.getDrawbacks() != null) {
            for (StatusEffects drawback : action.getDrawbacks()) {
                this.addStatusEffect(drawback, action.getTurnDuration());
            }
        }
        this.setMp(this.getMp() - action.getMpCost());
    }
    public void attack(Action attack, Character target) {
        if (attack.getHitChance() >= (int) (Math.random() * 100)) {
            int damage = this.calculateDamage(attack, target);
            target.setHp(Math.max(0, target.getHp() - damage));
            System.out.println(this.getName() + " dealt " + damage + " damage to " + target.getName() + " with " + attack.getName() + "!");
            if (attack.getStatusEffects() != null) {
                for (StatusEffects statusEffect : attack.getStatusEffects()) {
                    target.addStatusEffect(statusEffect, attack.getTurnDuration());
                }
            }
            this.lastStatusEffectsApplied = attack.getStatusEffects();
        } else {
            System.out.println(this.getName() + " missed his attack on " + target.getName() + "!");
            this.lastTarget = null;
        }
    }
    public void heal(Action heal, Character target) {
        if (heal.getHitChance() >= (int) (Math.random() * 100)) {
            int healAmount = this.calculateHeal(heal, target);
            target.setHp(Math.min(target.getMaxHp(), target.getHp() + healAmount));
            System.out.println(this.getName() + " healed " + target.getName() + " for " + healAmount + " HP with " + heal.getName() + "!");
        }
    }
    private int calculateHeal(Action heal, Character target) {
        int healAmount;

        if (heal.getIsAsPercentage()) {
            healAmount = (int) (target.getMaxHp() * (heal.getValue() / 100.0));
        } else {
            healAmount = heal.getValue();
        }

        return healAmount;
    }
    private int calculateDamage(Action attack, Character target) {
        int damage;

        if (attack.getIsAsPercentage()) {
            damage = (int) (target.getMaxHp() * (attack.getValue() / 100.0));
        } else {
            damage = attack.getValue()*(int) Math.pow(1.145, this.level) + this.getAtk() - target.getDef();
        }

        int crt = this.calculateCriticalHitChance(target.getDef(), target.getSpd());

        if (crt > (int) (Math.random() * 100)) {
            damage = (int) (damage * 1.5);
            System.out.println("Critical hit!");
        }

        // Character class advantage/disadvantage
        if (
            (this.getCharacterClass() == CharacterClass.MELEE && target.getCharacterClass() == CharacterClass.RANGED) ||
            (this.getCharacterClass() == CharacterClass.RANGED && target.getCharacterClass() == CharacterClass.MAGIC) ||
            (this.getCharacterClass() == CharacterClass.MAGIC && target.getCharacterClass() == CharacterClass.MELEE)
            )
            damage = (int) (damage * 1.1);

        if (
            (this.getCharacterClass() == CharacterClass.MELEE && target.getCharacterClass() == CharacterClass.MAGIC) ||
            (this.getCharacterClass() == CharacterClass.RANGED && target.getCharacterClass() == CharacterClass.MELEE) ||
            (this.getCharacterClass() == CharacterClass.MAGIC && target.getCharacterClass() == CharacterClass.RANGED)
            )
            damage = (int) (damage * 0.9);


        // Attack type advantage/disadvantage
        if (
            (attack.getActionType() == ActionTypes.PHYSICAL_ATTACK && target.getCharacterClass() == CharacterClass.MAGIC) ||
            (attack.getActionType() == ActionTypes.MAGICAL_ATTACK && target.getCharacterClass() == CharacterClass.RANGED) ||
            (attack.getActionType() == ActionTypes.MAGICAL_ATTACK && target.getCharacterClass() == CharacterClass.MELEE)
            )
            damage = (int) (damage * 1.1);

        if (
            (attack.getActionType() == ActionTypes.PHYSICAL_ATTACK && target.getCharacterClass() == CharacterClass.RANGED) ||
            (attack.getActionType() == ActionTypes.MAGICAL_ATTACK && target.getCharacterClass() == CharacterClass.MELEE) ||
            (attack.getActionType() == ActionTypes.MAGICAL_ATTACK && target.getCharacterClass() == CharacterClass.MAGIC)
            )
            damage = (int) (damage * 0.9);

        lastDamageDealt = Math.max(1, damage);
        return Math.max(1, damage);
    }
    protected int calculateCriticalHitChance(double enemyDef, double enemySpd) {
        double defFactor = 0.5; // Example value, adjust based on testing
        double spdFactor = 0.3; // Example value, adjust based on testing

        double criticalHitChance = this.crt*1.5 - (enemyDef * defFactor + enemySpd * spdFactor);
        criticalHitChance = Math.max(Math.min(criticalHitChance, 100), 0); // Ensuring it's within 0-100%

        return (int) criticalHitChance;
    }

    protected String effectsToString() {
        if (this.statusEffects.isEmpty()) return "none";
        StringBuilder s = new StringBuilder();
        for (StatusEffects statusEffect : this.statusEffects.keySet()) {
            s.append(statusEffect.toString()).append(" (").append(this.statusEffects.get(statusEffect)).append(" turns left); ");
        }
        return s.toString();
    }

    public Character getLastTarget() {
        return lastTarget;
    }
    public int getLastDamageDealt() {
        return lastDamageDealt;
    }
    public StatusEffects[] getLastStatusEffectsApplied() {
        return lastStatusEffectsApplied;
    }
    public Action getLastMoveUsed() {
        return lastMoveUsed;
    }
}
