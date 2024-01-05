package FinalFantasy.Character.Player;

import FinalFantasy.Actions.Action;
import FinalFantasy.Character.Character;
import FinalFantasy.Character.CharacterClass;
import FinalFantasy.Loot.Equipment.Armors.*;
import FinalFantasy.Loot.Equipment.Weapons.Weapon;
import FinalFantasy.Loot.Loot;
import FinalFantasy.StatusEffects;

import static FinalFantasy.ConsoleColors.*;

public abstract class Player extends Character {
    private int exp = 0;
    private int level = 1;
    private Helmet helmet = null;
    private ChestPlate chestPlate = null;
    private Leggings leggings = null;
    private Boots boots = null;
    private Weapon[] weapons = new Weapon[2];
    public Player(String name, int hp, int mp, int atk, int def, int crt, int spd, CharacterClass characterClass) {
        super(name, hp, mp, atk, def, crt, spd, characterClass);
    }
    public int getExp() {
        return this.exp;
    }
    public int getLevel() {
        return this.level;
    }
    public Helmet getHelmet() {
        return this.helmet;
    }
    public ChestPlate getChestPlate() {
        return this.chestPlate;
    }
    public Leggings getLeggings() {
        return this.leggings;
    }
    public Boots getBoots() {
        return this.boots;
    }
    public Weapon[] getWeapons() {
        return this.weapons;
    }

    public void setHelmet(Helmet helmet) {
        this.helmet = helmet;
    }
    public void setChestPlate(ChestPlate chestPlate) {
        this.chestPlate = chestPlate;
    }
    public void setLeggings(Leggings leggings) {
        this.leggings = leggings;
    }
    public void setBoots(Boots boots) {
        this.boots = boots;
    }
    public void setWeapons(Weapon[] weapons) {
        this.weapons = weapons;
    }

    private void applyBonus(Loot equipment) {
        if (equipment == null) return;
        if (equipment instanceof Weapon) {
            atk += ((Weapon) equipment).getAtkBonus();
            def += ((Weapon) equipment).getDefBonus();
            crt += ((Weapon) equipment).getCrtBonus();
        } else if (equipment instanceof Armor) {
            hp += ((Armor) equipment).getHpBonus();
            mp += ((Armor) equipment).getMpBonus();
            atk += ((Armor) equipment).getAtkBonus();
            def += ((Armor) equipment).getDefBonus();
            crt += ((Armor) equipment).getCrtBonus();
            spd += ((Armor) equipment).getSpdBonus();

            if (((Armor) equipment).getStatusEffects() == null) return;
            for (StatusEffects statusEffect : ((Armor) equipment).getStatusEffects()) {
                this.addStatusEffect(statusEffect);
            }
        }
    }
    private void removeBonus(Loot equipment) {
        if (equipment instanceof Weapon) {
            atk -= ((Weapon) equipment).getAtkBonus();
            def -= ((Weapon) equipment).getDefBonus();
            crt -= ((Weapon) equipment).getCrtBonus();
        } else if (equipment instanceof Armor) {
            hp -= ((Armor) equipment).getHpBonus();
            mp -= ((Armor) equipment).getMpBonus();
            atk -= ((Armor) equipment).getAtkBonus();
            def -= ((Armor) equipment).getDefBonus();
            crt -= ((Armor) equipment).getCrtBonus();
            spd -= ((Armor) equipment).getSpdBonus();

            if (((Armor) equipment).getStatusEffects() == null) return;
            for (StatusEffects statusEffect : ((Armor) equipment).getStatusEffects()) {
                this.removeStatusEffect(statusEffect);
            }
        }
    }
    public boolean equipWeapon(Weapon weapon) {
        if (weapon == null) return false;
        if (this.weapons[0] == null) { // 1st hand already empty
            this.weapons[0] = weapon;
            this.applyBonus(weapon);
            if (weapon.getNumHands() == 2) { // 2-handed weapon
                this.weapons[1] = weapon;
            }
            return true;
        } else if (this.weapons[1] == null) { // Only 2nd hand empty
            if (weapon.getNumHands() == 2) {
                System.out.println("Can't equip 2-handed weapon on 2nd hand");
                return false;
            }
            this.weapons[1] = weapon;
            this.applyBonus(weapon);
            return true;
        } else {
            System.out.println("Can't equip more than 2 weapons");
            return false;
        }
    }
    public boolean equipArmor(Armor armor) {
        if (armor == null) return false;
        switch (armor.getArmorType()) {
            case HELMET:
                if (this.helmet != null) {
                    System.out.println("Helmet slot already occupied");
                    return false;
                }
                this.helmet = (Helmet) armor;
                this.applyBonus(this.helmet);
                return true;
            case CHEST_PLATE:
                if (this.chestPlate != null) {
                    System.out.println("Chest plate slot already occupied");
                    return false;
                }
                this.chestPlate = (ChestPlate) armor;
                this.applyBonus(this.chestPlate);
                return true;
            case LEGGINGS:
                if (this.leggings != null) {
                    System.out.println("Leggings slot already occupied");
                    return false;
                }
                this.leggings = (Leggings) armor;
                this.applyBonus(this.leggings);
                return true;
            case BOOTS:
                if (this.boots != null) {
                    System.out.println("Boots slot already occupied");
                    return false;
                }
                this.boots = (Boots) armor;
                this.applyBonus(this.boots);
                return true;
        }
        return false;
    }
    public boolean equip(Loot equipment) {
        if (equipment instanceof Weapon) {
            for (CharacterClass characterClass : ((Weapon) equipment).getCharacterClasses()) {
                if (characterClass == this.characterClass) {
                    return this.equipWeapon((Weapon) equipment);
                }
            }
            System.out.println("Can't equip weapon of different class");
            return false;
        } else if (equipment instanceof Armor) {
            if (((Armor) equipment).getCharacterClass() != this.characterClass) {
                System.out.println("Can't equip armor of different class");
                return false;
            }
            return this.equipArmor((Armor) equipment);
        }
        return false;
    }

    public boolean unEquipWeapon(Weapon weapon) {
        if (weapon == null) return false;
        if (this.weapons[0] == weapon) { // 1st hand
            this.removeBonus(weapon);
            this.weapons[0] = null;
            if (weapon.getNumHands() == 2) { // 2-handed weapon
                this.weapons[1] = null;
            }
            return true;
        } else if (this.weapons[1] == weapon) { // 2nd hand
            this.removeBonus(weapon);
            this.weapons[1] = null;
            return true;
        } else {
            System.out.println("Weapon not equipped");
            return false;
        }
    }

    public boolean unEquipArmor(Armor armor) {
        if (armor == null) return false;
        switch (armor.getArmorType()) {
            case HELMET:
                if (this.helmet == null) {
                    System.out.println("Helmet slot already empty");
                    return false;
                }
                this.removeBonus(this.helmet);
                this.helmet = null;
                return true;
            case CHEST_PLATE:
                if (this.chestPlate == null) {
                    System.out.println("Chest plate slot already empty");
                    return false;
                }
                this.removeBonus(this.chestPlate);
                this.chestPlate = null;
                return true;
            case LEGGINGS:
                if (this.leggings == null) {
                    System.out.println("Leggings slot already empty");
                    return false;
                }
                this.removeBonus(this.leggings);
                this.leggings = null;
                return true;
            case BOOTS:
                if (this.boots == null) {
                    System.out.println("Boots slot already empty");
                    return false;
                }
                this.removeBonus(this.boots);
                this.boots = null;
                return true;
        }
        return false;
    }

    public boolean unEquip(Loot equipment) {
        if (equipment instanceof Weapon) {
            return this.unEquipWeapon((Weapon) equipment);
        } else if (equipment instanceof Armor) {
            return this.unEquipArmor((Armor) equipment);
        }
        return false;
    }

    private void removeAllBonuses() {
        this.removeBonus(this.helmet);
        this.removeBonus(this.chestPlate);
        this.removeBonus(this.leggings);
        this.removeBonus(this.boots);
        this.removeBonus(this.weapons[0]);
        this.removeBonus(this.weapons[1]);
    }
    private void putBackAllBonuses() {
        this.applyBonus(this.helmet);
        this.applyBonus(this.chestPlate);
        this.applyBonus(this.leggings);
        this.applyBonus(this.boots);
        this.applyBonus(this.weapons[0]);
        this.applyBonus(this.weapons[1]);
    }

    public void addExp(int exp) {
        this.exp += exp;
        System.out.println(this.name + " gained " + exp + " exp!");
        int level = this.calculateLevelForExp(this.exp); // Calculate level for current exp
        while (level > this.level) { // Level up until appropriate level is reached
            this.levelUp();
        }
    }
    private int calculateLevelForExp(int exp) { // Calculate level for given exp
        int level = 0;
        int xpForNextLevel = 50;

        while (exp >= xpForNextLevel) {
            level++;
            xpForNextLevel += 20 * level; // Update the XP required for the next level
        }

        return level;
    }
    public int getRemainingXpForNextLevel() {
        int xpForNextLevel = 50 + 20 * level;
        return xpForNextLevel - this.exp;
    }
    public void levelUp() {
        this.level++;
        this.removeAllBonuses(); // Ignore bonuses from equipment
        // Increase stats
        this.maxHp = (int) Math.round(this.maxHp * 1.15);
        this.maxMp = (int) Math.round(this.maxMp * 1.08);
        this.atk = (int) Math.round(this.atk * 1.12);
        this.def = (int) Math.round(this.def * 1.1);
        this.crt = (int) Math.round(this.crt * 1.1);
        this.spd = (int) Math.round(this.spd * 1.1);
        this.putBackAllBonuses(); // Re-apply bonuses from equipment
        this.hp = this.maxHp;
        this.mp = this.maxMp;
        System.out.println("Level up! Level " + this.level);
        System.out.println("XP for next level: " + this.getRemainingXpForNextLevel());
    }

    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(BLUE_BOLD+"Name: "+BLUE).append(this.name).append("\n");
        sb.append(GREEN_BOLD+"Level: "+GREEN).append(this.level).append("\n");
        sb.append(PURPLE_BOLD+"HP: "+PURPLE).append(this.hp).append("/").append(this.maxHp).append("\n");
        sb.append(PURPLE_BOLD+"MP: "+PURPLE).append(this.mp).append("/").append(this.maxMp).append("\n");
        sb.append(PURPLE_BOLD+"ATK: "+PURPLE).append(this.atk).append("\n");
        sb.append(PURPLE_BOLD+"DEF: "+PURPLE).append(this.def).append("\n");
        sb.append(PURPLE_BOLD+"CRT: "+PURPLE).append(this.crt).append("\n");
        sb.append(PURPLE_BOLD+"SPD: "+PURPLE).append(this.spd).append("\n");
        sb.append(YELLOW_BOLD+"Class: "+YELLOW).append(this.characterClass).append("\n");
        sb.append(YELLOW_BOLD+"Helmet: "+YELLOW).append(this.helmet).append("\n");
        sb.append(YELLOW_BOLD+"Chest plate: "+YELLOW).append(this.chestPlate).append("\n");
        sb.append(YELLOW_BOLD+"Leggings: "+YELLOW).append(this.leggings).append("\n");
        sb.append(YELLOW_BOLD+"Boots: "+YELLOW).append(this.boots).append("\n");
        sb.append(YELLOW_BOLD+"Weapon 1: "+YELLOW).append(this.weapons[0]).append("\n");
        sb.append(YELLOW_BOLD+"Weapon 2: "+YELLOW).append(this.weapons[1]).append("\n");
        for (Action action : this.actions) {
            sb.append(action.toString()).append("\n");
        }
        return sb.toString();
    }
    public String smallToString() {
        StringBuilder sb = new StringBuilder();
        sb.append("Name: ").append(this.name).append("\t\t").append("Class: ").append(this.characterClass).append("\n");
        sb.append("HP: ").append(this.hp).append("/").append(this.maxHp).append("\t\t");
        sb.append("MP: ").append(this.mp).append("/").append(this.maxMp).append("\n");
        sb.append("Status effects: ").append(this.effectsToString()).append("\n");
        return sb.toString();
    }


}
