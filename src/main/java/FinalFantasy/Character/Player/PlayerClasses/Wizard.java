package FinalFantasy.Character.Player.PlayerClasses;

import FinalFantasy.Actions.MagicalAttack;
import FinalFantasy.Actions.MagicalRecovery;
import FinalFantasy.Actions.PhysicalAttack;
import FinalFantasy.Enums.CharacterClass;
import FinalFantasy.Character.Player.Player;

public class Wizard extends Player implements java.io.Serializable {
    public Wizard(String name) {
        super(name,200, 180, 25, 20, 20, 20, CharacterClass.MAGIC);
        this.actions.add(new PhysicalAttack("Staff Bash", 0, "A basic staff attack", 25, 90, null, null, 0, false));
        this.actions.add(new MagicalAttack("Fireball", 10, "A basic fireball spell", 40, 85, null, null, 0, false));
        this.actions.add(new MagicalAttack("Lightning", 15, "A powerful lightning spell", 65, 80, null, null, 0, false));
        this.actions.add(new MagicalAttack("Meteor", 20, "A powerful meteor spell", 100, 70, null, null, 0, false));
        this.actions.add(new MagicalRecovery("Light Heal", 20, "A basic healing spell", 25,  null, null, 0, true));

    }
}
