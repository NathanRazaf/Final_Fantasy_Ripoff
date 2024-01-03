package FinalFantasy.Character.Enemy;

import FinalFantasy.Character.Character;
import FinalFantasy.Character.CharacterClass;
import FinalFantasy.Loot.Loot;
import FinalFantasy.Loot.LootTable;

import java.util.ArrayList;
import java.util.Map;
import java.util.Random;

public abstract class Enemy extends Character {
    protected final int level;
    protected final boolean isBoss;
    protected int expGiven;
    protected final ArrayList<Loot> lootGiven = new ArrayList<>();

    public Enemy(String name, int level, boolean isBoss, int hp, int mp, int atk, int def, int crt, int spd, CharacterClass characterClass, int expGiven) {
        super(name, hp, mp, atk, def, crt, spd, characterClass);
        this.level = level;
        this.isBoss = isBoss;
        this.expGiven = expGiven;

        this.hp = randomIntRange((int) (this.hp*Math.pow(1.1, this.level)), (int) (this.hp*Math.pow(1.2, this.level)));
        this.mp = randomIntRange((int) (this.mp*Math.pow(1.05, this.level)), (int) (this.mp*Math.pow(1.1, this.level)));
        this.atk = randomIntRange((int) (this.atk*Math.pow(1.07, this.level)), (int) (this.atk*Math.pow(1.15, this.level)));
        this.def = randomIntRange((int) (this.def*Math.pow(1.06, this.level)), (int) (this.def*Math.pow(1.13, this.level)));
        this.crt = randomIntRange((int) (this.crt*Math.pow(1.08, this.level)), (int) (this.crt*Math.pow(1.14, this.level)));
        this.spd = randomIntRange((int) (this.spd*Math.pow(1.05, this.level)), (int) (this.spd*Math.pow(1.1, this.level)));
        this.expGiven = randomIntRange((int) (this.expGiven*Math.pow(1.1, this.level)), (int) (this.expGiven*Math.pow(1.2, this.level)));

        if (isBoss) {
            this.hp *= 2;
            this.mp *= 2;
            this.atk *= 2;
            this.def *= 2;
            this.crt *= 2;
            this.spd *= 2;
            this.expGiven *= 2;
        }

        this.generateLoot();
    }
    private void generateLoot() {
        Map<Loot, Integer> lootMap = LootTable.INSTANCE.getLootForMonsterAndLevel(this.getClass(), this.level);
        Random rand = new Random();

        for (Map.Entry<Loot, Integer> entry : lootMap.entrySet()) {
            int randToReach = entry.getValue();
            if (isBoss) randToReach *= 3;
            if (rand.nextInt(100) < randToReach) {
                this.lootGiven.add(entry.getKey());
            }
        }
    }
    public int getLevel() {
        return this.level;
    }
    public boolean getIsBoss() {
        return this.isBoss;
    }
    public ArrayList<Loot> getLootGiven() {
        return this.lootGiven;
    }
    public int getExpGiven() {
        return this.expGiven;
    }

    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(this.name).append("\t\t").append("Type: ").append(this.characterClass).append("\n");
        sb.append("HP: ").append(this.hp).append("\n");
        sb.append("Status effects: ").append(this.effectsToString()).append("\n");
        if (isBoss) sb.append("BOSS").append("\n");
        return sb.toString();
    }
}
