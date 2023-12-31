package FinalFantasy.Character.Enemy;

import FinalFantasy.Actions.Action;
import FinalFantasy.Character.Character;
import FinalFantasy.Character.CharacterClass;
import FinalFantasy.Character.Enemy.EnemyTypes.GoblinTypes;

public class Goblin extends Enemy {
    public Goblin(int level, boolean isBoss) {
        super(generateName(level, isBoss), level, isBoss, 150, 80, 30, 30, 15, 27, CharacterClass.MELEE, 10);

    }

    private static String generateName(int level, boolean isBoss) {
        if (isBoss) {
            return GoblinTypes.GOBLIN_KING.getName();
        }
        else if (0 <= level && level <= 8) {
            return GoblinTypes.RED_GOBLIN.getName();
        }
        else if (9 <= level && level <= 20) {
            return GoblinTypes.BLUE_GOBLIN.getName();
        }
        else if (21 <= level && level <= 30) {
            return GoblinTypes.BLACK_GOBLIN.getName();
        }
        else if (30 <= level && level <= 42) {
            return GoblinTypes.WHITE_GOBLIN.getName();
        }
        else if (43 <= level && level <= 60) {
            return GoblinTypes.GOLD_GOBLIN.getName();
        }
        else {
            return GoblinTypes.GOBLIN_KING.getName();
        }
    }
}
