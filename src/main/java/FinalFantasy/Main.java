package FinalFantasy;

import FinalFantasy.Battles.Battle;
import FinalFantasy.Character.Player.Player;
import FinalFantasy.Character.Player.PlayerClasses.Archer;
import FinalFantasy.Character.Player.PlayerClasses.Warrior;
import FinalFantasy.Character.Player.PlayerClasses.Wizard;

import java.util.ArrayList;

public class Main {
    public static void main(String[] args) {
        ArrayList<Player> players = new ArrayList<>();
        players.add(new Warrior("Kevin"));
        players.add(new Archer("Bob"));
        players.add(new Wizard("Alice"));
        Battle battle = new Battle(players);
        if (battle.startBattle()) {
            System.out.println("You won!");
        } else {
            System.out.println("You lost!");
        }
    }
}