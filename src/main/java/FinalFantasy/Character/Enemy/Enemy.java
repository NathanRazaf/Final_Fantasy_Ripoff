package FinalFantasy.Character.Enemy;

import FinalFantasy.Character.Character;
import FinalFantasy.Enums.CharacterClass;
import FinalFantasy.Loot.Equipment.Armors.*;
import FinalFantasy.Loot.Equipment.Weapons.*;
import FinalFantasy.Loot.Loot;
import FinalFantasy.Loot.LootTable;
import FinalFantasy.Enums.LootTiers;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Map;
import java.util.Random;

import static FinalFantasy.Enums.ArmorTypes.*;
import static Utilities.Utility.randomIntRange;

public abstract class Enemy extends Character implements Serializable {
    protected final boolean isBoss;
    protected int expGiven;
    protected int goldGiven = 0;
    protected final ArrayList<Loot> lootGiven = new ArrayList<>();

    public Enemy(String name, int level, boolean isBoss, int hp, int mp, int atk, int def, int crt, int spd, CharacterClass characterClass, int expGiven) {
        super(name, level, hp, mp, atk, def, crt, spd, characterClass);
        this.isBoss = isBoss;
        this.expGiven = expGiven;

        this.hp = randomIntRange((int) (this.hp*Math.pow(1.158, this.level)), (int) (this.hp*Math.pow(1.165, this.level)));
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
            this.expGiven *= 3;
        }

        this.maxHp = this.hp;
        this.maxMp = this.mp;

        this.generateLoot();
    }
    private void generateLoot() {
        Map<Loot, LootTiers> lootMap = LootTable.INSTANCE.getMonsterLoot(this.getClass());
        Random rand = new Random();

        for (Map.Entry<Loot, LootTiers> entry : lootMap.entrySet()) {
            int chance = this.isBoss ? this.chanceBasedOnTier(entry.getValue())*2 : this.chanceBasedOnTier(entry.getValue());
            if (rand.nextInt(100) < chance) {
                Loot originalLoot = entry.getKey();
                // Create a copy of the Loot object
                Loot copyOfLoot = null;
                if (originalLoot instanceof Armor) {
                    switch (((Armor) originalLoot).getArmorType()) {
                        case HELMET ->
                                copyOfLoot = new Helmet(originalLoot.getName() + " lvl."+this.level, originalLoot.getValue()*(int) Math.pow(1.12,level), originalLoot.getDescription(), ((Helmet) originalLoot).getHpBonus()*(int) Math.pow(1.12,level), ((Helmet) originalLoot).getMpBonus()*(int) Math.pow(1.12,level), ((Helmet) originalLoot).getAtkBonus()*(int) Math.pow(1.12,level), ((Helmet) originalLoot).getDefBonus()*(int) Math.pow(1.12,level), ((Helmet) originalLoot).getCrtBonus()*(int) Math.pow(1.12,level), ((Helmet) originalLoot).getSpdBonus()*(int) Math.pow(1.12,level), ((Helmet) originalLoot).getCharacterClass(), ((Helmet) originalLoot).getStatusEffects());
                        case CHEST_PLATE ->
                                copyOfLoot = new ChestPlate(originalLoot.getName() + " lvl."+this.level, originalLoot.getValue()*(int) Math.pow(1.12,level), originalLoot.getDescription(), ((ChestPlate) originalLoot).getHpBonus()*(int) Math.pow(1.12,level), ((ChestPlate) originalLoot).getMpBonus()*(int) Math.pow(1.12,level), ((ChestPlate) originalLoot).getAtkBonus()*(int) Math.pow(1.12,level), ((ChestPlate) originalLoot).getDefBonus()*(int) Math.pow(1.12,level), ((ChestPlate) originalLoot).getCrtBonus()*(int) Math.pow(1.12,level), ((ChestPlate) originalLoot).getSpdBonus()*(int) Math.pow(1.12,level), ((ChestPlate) originalLoot).getCharacterClass(), ((ChestPlate) originalLoot).getStatusEffects());
                        case BOOTS ->
                                copyOfLoot = new Boots(originalLoot.getName() + " lvl."+this.level, originalLoot.getValue()*(int) Math.pow(1.12,level), originalLoot.getDescription(), ((Boots) originalLoot).getHpBonus()*(int) Math.pow(1.12,level), ((Boots) originalLoot).getMpBonus()*(int) Math.pow(1.12,level), ((Boots) originalLoot).getAtkBonus()*(int) Math.pow(1.12,level), ((Boots) originalLoot).getDefBonus()*(int) Math.pow(1.12,level), ((Boots) originalLoot).getCrtBonus()*(int) Math.pow(1.12,level), ((Boots) originalLoot).getSpdBonus()*(int) Math.pow(1.12,level), ((Boots) originalLoot).getCharacterClass(), ((Boots) originalLoot).getStatusEffects());
                        case LEGGINGS ->
                                copyOfLoot = new Leggings(originalLoot.getName() + " lvl."+this.level, originalLoot.getValue()*(int) Math.pow(1.12,level), originalLoot.getDescription(), ((Leggings) originalLoot).getHpBonus()*(int) Math.pow(1.12,level), ((Leggings) originalLoot).getMpBonus()*(int) Math.pow(1.12,level), ((Leggings) originalLoot).getAtkBonus()*(int) Math.pow(1.12,level), ((Leggings) originalLoot).getDefBonus()*(int) Math.pow(1.12,level), ((Leggings) originalLoot).getCrtBonus()*(int) Math.pow(1.12,level), ((Leggings) originalLoot).getSpdBonus()*(int) Math.pow(1.12,level), ((Leggings) originalLoot).getCharacterClass(), ((Leggings) originalLoot).getStatusEffects());
                    }
                } else if (originalLoot instanceof Weapon) {
                    switch (((Weapon) originalLoot).getWeaponType()) {
                        case SWORD ->
                                copyOfLoot = new Sword(originalLoot.getName() + " lvl."+this.level, originalLoot.getValue() *(int) Math.pow(1.12,level), originalLoot.getDescription(), ((Sword) originalLoot).getAtkBonus() *(int) Math.pow(1.12,level), ((Sword) originalLoot).getCrtBonus() *(int) Math.pow(1.12,level));
                        case AXE ->
                                copyOfLoot = new Axe(originalLoot.getName() + " lvl."+this.level, originalLoot.getValue() *(int) Math.pow(1.12,level), originalLoot.getDescription(), ((Axe) originalLoot).getAtkBonus() *(int) Math.pow(1.12,level), ((Axe) originalLoot).getCrtBonus() *(int) Math.pow(1.12,level));
                        case BOW ->
                                copyOfLoot = new Bow(originalLoot.getName() + " lvl."+this.level, originalLoot.getValue() *(int) Math.pow(1.12,level), originalLoot.getDescription(), ((Bow) originalLoot).getAtkBonus() *(int) Math.pow(1.12,level), ((Bow) originalLoot).getCrtBonus() *(int) Math.pow(1.12,level));
                        case STAFF ->
                                copyOfLoot = new Staff(originalLoot.getName() + " lvl."+this.level, originalLoot.getValue() *(int) Math.pow(1.12,level), originalLoot.getDescription(), ((Staff) originalLoot).getAtkBonus() *(int) Math.pow(1.12,level), ((Staff) originalLoot).getDefBonus() *(int) Math.pow(1.12,level), ((Staff) originalLoot).getCrtBonus() *(int) Math.pow(1.12,level));
                        case SPEAR ->
                                copyOfLoot = new Spear(originalLoot.getName() + " lvl."+this.level, originalLoot.getValue() *(int) Math.pow(1.12,level), originalLoot.getDescription(), ((Spear) originalLoot).getAtkBonus() *(int) Math.pow(1.12,level), ((Spear) originalLoot).getCrtBonus() *(int) Math.pow(1.12,level));
                        case SHIELD ->
                                copyOfLoot = new Shield(originalLoot.getName() + " lvl."+this.level, originalLoot.getValue() *(int) Math.pow(1.12,level), originalLoot.getDescription(), ((Shield) originalLoot).getDefBonus() *(int) Math.pow(1.12,level));
                        case CROSSBOW ->
                                copyOfLoot = new Crossbow(originalLoot.getName() + " lvl."+this.level, originalLoot.getValue() *(int) Math.pow(1.12,level), originalLoot.getDescription(), ((Crossbow) originalLoot).getAtkBonus() *(int) Math.pow(1.12,level), ((Crossbow) originalLoot).getDefBonus() *(int) Math.pow(1.12,level), ((Crossbow) originalLoot).getCrtBonus() *(int) Math.pow(1.12,level));
                    }
                } else {
                    copyOfLoot = new Loot(originalLoot.getName() + " lvl."+this.level, originalLoot.getValue() *(int) Math.pow(1.12,level), originalLoot.getDescription());
                }
                // Add the copied Loot to the enemy's loot
                this.lootGiven.add(copyOfLoot);
            }
        }
    }

    private int chanceBasedOnTier(LootTiers lootTier) {
        return switch (lootTier) {
            case COMMON -> Math.min(50 + this.level / 2, 100);
            case UNCOMMON -> Math.min(30 + this.level / 2, 80);
            case RARE -> Math.min(20 + this.level / 2, 55);
            case EPIC -> Math.min(10 + this.level / 2, 30);
            case LEGENDARY -> Math.min(5 + this.level / 3, 15);
            case MYTHIC -> Math.min(2 + this.level / 3, 10);
        };
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
    public int getGoldGiven() {
        return this.goldGiven;
    }

    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(this.name).append("\t\t").append("Type: ").append(this.characterClass).append("\n");
        sb.append("HP: ").append(this.hp).append("/").append(this.maxHp).append("\n");
        sb.append("Status effects: ").append(this.effectsToString()).append("\n");
        if (isBoss) sb.append("BOSS").append("\n");
        return sb.toString();
    }

    public String lootToString() {
        StringBuilder sb = new StringBuilder();
        for (Loot loot : this.lootGiven) {
            sb.append(loot.toString()).append("\n");
        }
        return sb.toString();
    }
}
