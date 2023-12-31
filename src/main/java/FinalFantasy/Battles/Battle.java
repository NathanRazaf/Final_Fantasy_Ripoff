package FinalFantasy.Battles;

import FinalFantasy.Actions.Action;
import FinalFantasy.Actions.ActionTypes;
import FinalFantasy.Character.Character;
import FinalFantasy.Character.Enemy.Enemy;
import FinalFantasy.Character.Enemy.Goblin;
import FinalFantasy.Character.Enemy.Harpy;
import FinalFantasy.Character.Enemy.Sorcerer;
import FinalFantasy.Character.Player.Player;

import java.util.ArrayList;

public class Battle {
    final String RESET = "\u001B[0m";
    final String RED = "\u001B[31m";
    final String GREEN = "\u001B[32m";
    final String YELLOW = "\u001B[33m";
    final String BLUE = "\u001B[34m";
    final String PURPLE = "\u001B[35m";
    final String CYAN = "\u001B[36m";
    final String WHITE = "\u001B[37m";
    final String BLACK = "\u001B[30m";
    final String BRIGHT_RED = "\u001B[91m";
    final String BRIGHT_GREEN = "\u001B[92m";
    private final ArrayList<Player> players;
    private final ArrayList<Enemy> enemies;
    private final ArrayList<Character> turnOrder = new ArrayList<>();
    public Battle(ArrayList<Player> players, ArrayList<Enemy> enemies) {
        this.players = players;
        this.enemies = enemies;
        this.orderTurns();
    }

    public Battle(ArrayList<Player> players) {
        this.players = players;
        int averageLevel = (int) this.players.stream().mapToInt(Player::getLevel).average().orElse(1);
        this.enemies = new ArrayList<>();
        for (int i=0; i<3; i++) { // spawns between 1 and 3 enemies
            double rand = Math.pow(0.5, i); // 100% chance of 1 enemy, 50% chance of 2 enemies, 25% chance of 3 enemies
            if (Math.random() < rand) {
                Enemy enemy;
                double random = Math.random();
                // 33% chance of goblin, 33% chance of harpy, 33% chance of sorcerer
                if (random < 0.33) {
                    enemy = new Goblin(randomIntRange(averageLevel - 1, averageLevel + 1), Math.random() < 0.1);
                } else if (random < 0.66) {
                    enemy = new Harpy(randomIntRange(averageLevel - 1, averageLevel + 1), Math.random() < 0.1);
                } else {
                    enemy = new Sorcerer(randomIntRange(averageLevel - 1, averageLevel + 1), Math.random() < 0.1);
                }
                this.enemies.add(enemy);
            }
        }
        this.orderTurns();
    }
    private void orderTurns() {
        this.turnOrder.clear();
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

    public void showBattleState() {
        System.out.println(PURPLE + "Battle State:" + RESET);
        System.out.println(BRIGHT_GREEN + "Players:");
        for (Player player : this.players) {
            System.out.println(GREEN + player.toString());
        }
    }

    private int randomIntRange(int min, int max) {
        return (int) (Math.random() * (max - min + 1) + min);
    }

}
