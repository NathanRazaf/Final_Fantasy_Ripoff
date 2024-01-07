package FinalFantasy.Character.Player.PlayerClasses;

import FinalFantasy.Actions.MagicalAttack;
import FinalFantasy.Actions.MagicalRecovery;
import FinalFantasy.Actions.PhysicalAttack;
import FinalFantasy.Enums.CharacterClass;
import FinalFantasy.Character.Player.Player;
import FinalFantasy.Enums.StatusEffects;

public class Warrior extends Player implements java.io.Serializable {
    public Warrior(String name) {
        super(name, 300, 50, 35, 30, 25, 30, CharacterClass.MELEE);
        this.actions.add(new PhysicalAttack("Normal Slash", 0, "A basic slashing attack", 50, 99, null, null, 0, false));
        this.actions.add(new PhysicalAttack("Heavy Slash", 10, "A powerful attack filled with spirit", 75, 95, null, null, 0, false));
        this.actions.add(new PhysicalAttack("Berserk", 20, "A powerful attack that sacrifices defense", 100, 90, null, new StatusEffects[]{StatusEffects.WEAKENED}, 2, false));
        this.actions.add(new MagicalAttack("Warrior's Bane", 30, "The ultimate attack of a warrior", 50, 100, null, null, 0, true));
        this.actions.add(new MagicalRecovery("Light Heal", 20, "A basic healing spell", 25,  null, null, 0, true));
    }

    @Override
    public void tryAddActions() {

    }
}