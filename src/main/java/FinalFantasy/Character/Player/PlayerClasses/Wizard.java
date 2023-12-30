package FinalFantasy.Character.Player.PlayerClasses;

import FinalFantasy.Actions.Action;
import FinalFantasy.Character.Character;
import FinalFantasy.Character.CharacterClass;
import FinalFantasy.Character.Player.Player;

public class Wizard extends Player {
    public Wizard(String name) {
        super(name,200, 180, 25, 20, 20, 20, CharacterClass.MAGIC);
    }


    @Override
    public void doAction(Action action, Character target) {

    }
}
