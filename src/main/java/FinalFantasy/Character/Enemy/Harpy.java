package FinalFantasy.Character.Enemy;

import FinalFantasy.Actions.MagicalAttack;
import FinalFantasy.Actions.PhysicalAttack;
import FinalFantasy.Enums.CharacterClass;
import FinalFantasy.Enums.EnemyTypes.HarpyTypes;
import FinalFantasy.Enums.StatusEffects;

public class Harpy extends Enemy implements java.io.Serializable {
    public Harpy(int level, boolean isBoss) {
        super(generateName(level, isBoss), level, isBoss, 175, 90, 25, 25, 20, 30, CharacterClass.RANGED, 32);
        this.goldGiven = 28+level*3;
        this.actions.add(new PhysicalAttack("Harpy Claw", 0, "A basic claw attack", 30, 90, null, null, 0, false));
        this.actions.add(new MagicalAttack("Harpy Wind Blade", 10, "A wind blade created by the harpy's sharp wings", 50, 80, null, null, 0, false));
        this.actions.add(new PhysicalAttack("Harpy Feather Storm", 30, "A fury of feathers stunning the enemy", 70, 70, new StatusEffects[]{StatusEffects.CONFUSED}, null, 2, false));
    }

    private static String generateName(int level, boolean isBoss) {
        if (isBoss) {
            return HarpyTypes.HARPY_QUEEN.getName();
        }
        else if (0 <= level && level <= 8) {
            return HarpyTypes.RED_HARPY.getName();
        }
        else if (9 <= level && level <= 19) {
            return HarpyTypes.BLUE_HARPY.getName();
        }
        else if (20 <= level && level <= 28) {
            return HarpyTypes.BLACK_HARPY.getName();
        }
        else if (29 <= level && level <= 41) {
            return HarpyTypes.WHITE_HARPY.getName();
        }
        else if (42 <= level && level <= 49) {
            return HarpyTypes.GOLD_HARPY.getName();
        }
        else {
            return HarpyTypes.HARPY_QUEEN.getName();
        }
    }

}
