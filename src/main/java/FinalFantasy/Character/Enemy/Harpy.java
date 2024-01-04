package FinalFantasy.Character.Enemy;

import FinalFantasy.Actions.Action;
import FinalFantasy.Actions.MagicalAttack;
import FinalFantasy.Actions.PhysicalAttack;
import FinalFantasy.Character.Character;
import FinalFantasy.Character.CharacterClass;
import FinalFantasy.Character.Enemy.EnemyTypes.HarpyTypes;
import FinalFantasy.StatusEffects;

public class Harpy extends Enemy {
    public Harpy(int level, boolean isBoss) {
        super(generateName(level, isBoss), level, isBoss, 175, 90, 25, 25, 20, 30, CharacterClass.RANGED, 15);
        this.goldGiven = 13+level*3;
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
        else if (9 <= level && level <= 20) {
            return HarpyTypes.BLUE_HARPY.getName();
        }
        else if (21 <= level && level <= 30) {
            return HarpyTypes.BLACK_HARPY.getName();
        }
        else if (30 <= level && level <= 42) {
            return HarpyTypes.WHITE_HARPY.getName();
        }
        else if (43 <= level && level <= 60) {
            return HarpyTypes.GOLD_HARPY.getName();
        }
        else {
            return HarpyTypes.HARPY_QUEEN.getName();
        }
    }

}
