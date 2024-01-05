package FinalFantasy.Character.Enemy;

import FinalFantasy.Actions.MagicalAttack;
import FinalFantasy.Enums.CharacterClass;
import FinalFantasy.Enums.EnemyTypes.SorcererTypes;
import FinalFantasy.Enums.StatusEffects;

public class Sorcerer extends Enemy implements java.io.Serializable {
    public Sorcerer(int level, boolean isBoss) {
        super(generateName(level, isBoss), level, isBoss,160, 110, 20, 30, 20, 25, CharacterClass.MAGIC, 35);
        this.goldGiven = 30+level*3;
        this.actions.add(new MagicalAttack("Sorcerer Fireball", 0, "A basic fireball attack", 30, 90, null, null, 0, false));
        this.actions.add(new MagicalAttack("Sorcerer Ice Shard", 20, "A shard of ice that slows down the enemy hit", 50, 80, new StatusEffects[]{StatusEffects.SLOWED}, null, 2, false));
        this.actions.add(new MagicalAttack("Sorcerer Lightning Bolt", 30, "A bolt of lightning that confuses the enemy hit", 70, 70, new StatusEffects[]{StatusEffects.CONFUSED}, null, 2, false));
    }

    private static String generateName(int level, boolean isBoss) {
        if (isBoss) {
            return SorcererTypes.SORCERER_KING.getName();
        }
        else if (0 <= level && level <= 8) {
            return SorcererTypes.RED_SORCERER.getName();
        }
        else if (9 <= level && level <= 20) {
            return SorcererTypes.BLUE_SORCERER.getName();
        }
        else if (21 <= level && level <= 30) {
            return SorcererTypes.BLACK_SORCERER.getName();
        }
        else if (30 <= level && level <= 42) {
            return SorcererTypes.WHITE_SORCERER.getName();
        }
        else if (43 <= level && level <= 60) {
            return SorcererTypes.GOLD_SORCERER.getName();
        }
        else {
            return SorcererTypes.SORCERER_KING.getName();
        }
    }
}
