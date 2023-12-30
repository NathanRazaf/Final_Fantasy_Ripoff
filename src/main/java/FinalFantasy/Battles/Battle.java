package FinalFantasy.Battles;

import FinalFantasy.Character.Character;
import FinalFantasy.Character.Enemy.Enemy;
import FinalFantasy.Character.Player.Player;

import java.util.ArrayList;

public class Battle {
    private final ArrayList<Player> players;
    private final ArrayList<Enemy> enemies;
    private final ArrayList<Character> turnOrder = new ArrayList<>();
    public Battle(ArrayList<Player> players, ArrayList<Enemy> enemies) {
        this.players = players;
        this.enemies = enemies;
        this.orderTurns();
    }
    private void orderTurns() {
        this.turnOrder.addAll(this.players);
        this.turnOrder.addAll(this.enemies);
        this.turnOrder.sort((a, b) -> Integer.compare(b.getSpd(), a.getSpd()));
    }

    public ArrayList<Player> getPlayers() {
        return this.players;
    }
    public ArrayList<Enemy> getEnemies() {
        return this.enemies;
    }
    public ArrayList<Character> getTurnOrder() {
        return this.turnOrder;
    }


}
