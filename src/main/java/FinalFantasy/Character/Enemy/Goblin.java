package FinalFantasy.Character.Enemy;

import FinalFantasy.Actions.PhysicalAttack;
import FinalFantasy.Enums.CharacterClass;
import FinalFantasy.Enums.EnemyTypes.GoblinTypes;

public class Goblin extends Enemy implements java.io.Serializable {
    public Goblin(int level, boolean isBoss) {
        super(generateName(level, isBoss), level, isBoss, 150, 80, 30, 30, 15, 27, CharacterClass.MELEE, 30);
        this.goldGiven = 25+level*3;
        this.actions.add(new PhysicalAttack("Goblin Punch", 0, "A basic punch", 30, 90, null, null, 0, false));
        this.actions.add(new PhysicalAttack("Goblin Stab", 10, "A fast stab", 50, 80, null, null, 0, false));
        this.actions.add(new PhysicalAttack("Goblin Rush", 20, "A rapid succession of attacks", 70, 70, null, null, 0, false));

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
