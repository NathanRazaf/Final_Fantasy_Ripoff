package FinalFantasy.Character;

import FinalFantasy.Actions.Action;
import FinalFantasy.StatusEffects;
import java.util.ArrayList;

public abstract class Character {
    protected int maxHp, maxMp, currAtk, currDef, currCrt, currSpd;
    protected int hp, mp, atk, def, crt, spd;
    protected final CharacterClass characterClass;
    protected final ArrayList<Action> actions = new ArrayList<>();
    protected ArrayList<StatusEffects> statusEffects;

    //constructor
    public Character(int hp, int mp, int atk, int def, int crt, int spd, CharacterClass characterClass) {
        this.maxHp = hp;
        this.maxMp = mp;
        this.currAtk = atk;
        this.currDef = def;
        this.currCrt = crt;
        this.currSpd = spd;
        this.hp = hp;
        this.mp = mp;
        this.atk = atk;
        this.def = def;
        this.crt = crt;
        this.spd = spd;
        this.characterClass = characterClass;
        this.statusEffects = new ArrayList<>();
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
    public CharacterClass getCharacterClass() {
        return characterClass;
    }
    public ArrayList<StatusEffects> getStatusEffects() {
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

    protected int randomIntRange(int min, int max) {
        return (int) (Math.random() * (max - min + 1) + min);
    }

    public void addStatusEffect(StatusEffects statusEffect) {
        statusEffects.add(statusEffect);
    }
    public abstract void attack(Character target);
}
