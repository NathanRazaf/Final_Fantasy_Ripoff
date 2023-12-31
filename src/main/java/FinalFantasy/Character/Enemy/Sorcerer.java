package FinalFantasy.Character.Enemy;

import FinalFantasy.Actions.Action;
import FinalFantasy.Character.Character;
import FinalFantasy.Character.CharacterClass;
import FinalFantasy.Character.Enemy.EnemyTypes.SorcererTypes;

public class Sorcerer extends Enemy {
    public Sorcerer(int level, boolean isBoss) {
        super(generateName(level, isBoss), level, isBoss,160, 110, 20, 30, 20, 25, CharacterClass.MAGIC, 20);
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
