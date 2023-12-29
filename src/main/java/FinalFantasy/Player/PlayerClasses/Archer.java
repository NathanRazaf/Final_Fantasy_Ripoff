package FinalFantasy.Player.PlayerClasses;

import FinalFantasy.Character.Character;
import FinalFantasy.Character.CharacterClass;
import FinalFantasy.Player.Player;

public class Archer extends Player {
    public Archer() {
        super(250, 100, 30, 30, 25, 20, CharacterClass.RANGED);

    }

    @Override
    public void attack(Character enemy) {

    }
}
