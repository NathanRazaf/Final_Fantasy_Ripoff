package FinalFantasy.Character.Player.PlayerClasses;

import FinalFantasy.Actions.Action;
import FinalFantasy.Character.Character;
import FinalFantasy.Character.CharacterClass;
import FinalFantasy.Character.Player.Player;

public class Archer extends Player {
    public Archer(String name) {
        super(name, 250, 100, 30, 30, 25, 20, CharacterClass.RANGED);

    }

    @Override
    public void doAction(Action action, Character target) {

    }
}
