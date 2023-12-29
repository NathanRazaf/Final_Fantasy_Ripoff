package FinalFantasy.Player;

import FinalFantasy.Character.Character;
import FinalFantasy.Character.CharacterClass;

public abstract class Player extends Character {
    private int level = 1;
    public Player(int hp, int mp, int atk, int def, int crt, int spd, CharacterClass characterClass) {
        super(hp, mp, atk, def, crt, spd, characterClass);
    }
    public Player(CharacterClass characterClass) {
        super(200, 150, 30, 30, 20, 20, characterClass);
    }

    public abstract void attack(Character enemy);
}
