package FinalFantasy.Character;

import FinalFantasy.Actions.Action;
import FinalFantasy.StatusEffects;
import java.util.ArrayList;
import java.util.HashMap;

public abstract class Character {
    protected int maxHp, maxMp;
    protected int hp, mp, atk, def, crt, spd;
    protected final CharacterClass characterClass;
    protected final ArrayList<Action> actions = new ArrayList<>();
    protected HashMap<StatusEffects, Integer> statusEffects = new HashMap<>();

    //constructor
    public Character(int hp, int mp, int atk, int def, int crt, int spd, CharacterClass characterClass) {
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

    protected int randomIntRange(int min, int max) {
        return (int) (Math.random() * (max - min + 1) + min);
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

}
