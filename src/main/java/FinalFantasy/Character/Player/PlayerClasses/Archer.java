package FinalFantasy.Character.Player.PlayerClasses;

import FinalFantasy.Actions.MagicalAttack;
import FinalFantasy.Actions.PhysicalAttack;
import FinalFantasy.Enums.CharacterClass;
import FinalFantasy.Character.Player.Player;
import FinalFantasy.Enums.StatusEffects;

public class Archer extends Player implements java.io.Serializable {
    public Archer(String name) {
        super(name, 250, 100, 30, 30, 25, 20, CharacterClass.RANGED);
        this.actions.add(new PhysicalAttack("Normal Shot", 0, "A basic arrow shot", 30, 90, null, null, 0, false));
        this.actions.add(new PhysicalAttack("Heavy Shot", 10, "A powerful arrow shot", 50, 80, null, null, 0, false));
        this.actions.add(new PhysicalAttack("Snipe", 35, "A powerful arrow shot that never misses", 70, 100, null, null, 0, false));
        this.actions.add(new MagicalAttack("Poison Shot", 20, "A shot that poisons the enemy", 40, 85, new StatusEffects[]{StatusEffects.POISONED}, null, 2, false));
    }
}
