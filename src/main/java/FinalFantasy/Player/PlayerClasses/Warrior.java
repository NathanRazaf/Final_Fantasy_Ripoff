package FinalFantasy.Player.PlayerClasses;

import FinalFantasy.Actions.PhysicalAttack;
import FinalFantasy.Character.Character;
import FinalFantasy.Character.CharacterClass;
import FinalFantasy.Player.Player;
import FinalFantasy.StatusEffects;

public class Warrior extends Player {
    public Warrior() {
        super(300, 50, 35, 30, 25, 30, CharacterClass.MELEE);
        this.actions.add(new PhysicalAttack("Normal Slash", 0, "A basic slashing attack", 40, 85, null, null, 0, false));
        this.actions.add(new PhysicalAttack("Heavy Slash", 10, "A powerful attack filled with spirit", 65, 80, null, null, 0, false));
        this.actions.add(new PhysicalAttack("Berserk", 20, "A powerful attack that sacrifices defense", 100, 70, null, new StatusEffects[]{StatusEffects.WEAKENED}, 2, false));
    }
    public String toString() {
        return this.actions.toString();
    }
}
