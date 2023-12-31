package FinalFantasy.Battles;

import FinalFantasy.Actions.Action;
import FinalFantasy.Actions.ActionTypes;
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

    public void startBattle() {
        orderTurns();
        while (true) {
            if (isBattleOver()) {
                endBattle(isVictory());
                break;
            }
            for (Character character : this.turnOrder) {
                if (character.getHp() == 0) continue;
                if (character instanceof Enemy enemy) {
                    Action action = enemy.getActions().get((int) (Math.random() * enemy.getActions().size()));
                    Character target;
                    if (action.getActionType() == ActionTypes.MAGICAL_RECOVERY) {
                        target = this.enemies.get((int) (Math.random() * this.enemies.size()));
                    } else {
                        target = this.players.get((int) (Math.random() * this.players.size()));
                    }
                    enemy.doAction(action, target);
                } else if (character instanceof Player player) {
                    promptPlayerAction(player);
                }
            }
        }
    }

    private boolean isBattleOver() {
        return this.players.stream().allMatch(player -> player.getHp() == 0)
                || this.enemies.stream().allMatch(enemy -> enemy.getHp() == 0);
    }
    private boolean isVictory() {
        return this.enemies.stream().allMatch(enemy -> enemy.getHp() == 0);
    }

    private void endBattle(boolean isVictory) {
        if (isVictory) {
            System.out.println("You won!");
        } else {
            System.out.println("You lost!");
        }
    }

    public void promptPlayerAction(Player player) {
        StringBuilder sb = new StringBuilder();
        sb.append("What will ").append(player.getName()).append(" do?\n");
        for (int i = 0; i < player.getActions().size(); i++) {
            sb.append(i + 1).append(". ").append(player.getActions().get(i).smallToString()).append("\n");
        }
        System.out.println(sb);
    }
}
