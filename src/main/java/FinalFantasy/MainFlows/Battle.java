package FinalFantasy.MainFlows;

import FinalFantasy.Actions.Action;
import FinalFantasy.Enums.ActionTypes;
import FinalFantasy.Character.Character;
import FinalFantasy.Character.Enemy.Enemy;
import FinalFantasy.Character.Enemy.Goblin;
import FinalFantasy.Character.Enemy.Harpy;
import FinalFantasy.Character.Enemy.Sorcerer;
import FinalFantasy.Character.Player.Player;
import Utilities.InputManager;

import java.util.ArrayList;

import static Utilities.ConsoleColors.*;
import static Utilities.Utility.randomIntRange;

public class Battle implements java.io.Serializable {
    private final ArrayList<Player> players;
    private final ArrayList<Enemy> enemies;
    private final ArrayList<Character> turnOrder = new ArrayList<>();

    public Battle(ArrayList<Player> players, ArrayList<Enemy> enemies) {
        this.players = players;
        this.enemies = enemies;
        this.orderTurns();
        this.showBattleState();
    }
    public Battle(ArrayList<Player> players) {
        this.players = players;
        int averageLevel = (int) this.players.stream().mapToInt(Player::getLevel).average().orElse(1);
        this.enemies = new ArrayList<>();
        for (int i=0; i<3; i++) { // spawns between 1 and 3 enemies
            double rand = Math.pow(0.75, i); // 100% chance of 1 enemy, 75% chance of 2 enemies, 56.25% chance of 3 enemies
            if (Math.random() < rand) {
                Enemy enemy;
                double random = Math.random();
                // 33% chance of goblin, 33% chance of harpy, 33% chance of sorcerer
                if (random < 0.33) {
                    enemy = new Goblin(Math.max(0, randomIntRange(averageLevel - 1, averageLevel + 1)), Math.random() < 0.1);
                } else if (random < 0.66) {
                    enemy = new Harpy(Math.max(0, randomIntRange(averageLevel - 1, averageLevel + 1)), Math.random() < 0.1);
                } else {
                    enemy = new Sorcerer(Math.max(0, randomIntRange(averageLevel - 1, averageLevel + 1)), Math.random() < 0.1);
                }
                this.enemies.add(enemy);
            }
        }
        this.orderTurns();
        this.showBattleState();
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

    public boolean doBattle() {
        while (true) {
            orderTurns();
            for (Character character : this.turnOrder) {
                character.applyEffects();
                if (isBattleOver()) {
                    return isVictory();
                }
                System.out.println(character.getName() + "'s turn");
                try {
                    Thread.sleep(1000); // Delay for 1000 milliseconds (1 second)
                } catch (InterruptedException e) {
                    // Handle the InterruptedException
                    e.printStackTrace();
                }
                if (character.getHp() == 0) {
                    System.out.println(character.getName() + " is dead");
                    if (character instanceof Player player) {
                        this.players.remove(player);
                        System.out.println(player.getName() + " has been removed from the battle and will never come back. (yes there's no revives in this game)");
                    }
                    continue;
                }
                if (character instanceof Enemy enemy) {
                    makeEnemyAction(enemy);
                } else if (character instanceof Player player) {
                    promptPlayerAction(player);
                }
                showBattleState();
                try {
                    Thread.sleep(1000); // Delay for 1000 milliseconds (1 second)
                } catch (InterruptedException e) {
                    // Handle the InterruptedException
                    e.printStackTrace();
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

    public void makeEnemyAction(Enemy enemy){
        Action action = enemy.getActions().get((int) (Math.random() * enemy.getActions().size()));
        while (action.getMpCost() > enemy.getMp()) {
            action = enemy.getActions().get((int) (Math.random() * enemy.getActions().size()));
        }
        Character target;
        if (action.getActionType() == ActionTypes.MAGICAL_RECOVERY) {
            target = this.enemies.get((int) (Math.random() * this.enemies.size()));
            while (target.getHp() == 0) {
                target = this.enemies.get((int) (Math.random() * this.enemies.size()));
            }
        } else {
            target = this.players.get((int) (Math.random() * this.players.size()));
            while (target.getHp() == 0) {
                target = this.players.get((int) (Math.random() * this.players.size()));
            }
        }
        enemy.doAction(action, target);
    }
    public void promptPlayerAction(Player player) {
        StringBuilder sb = new StringBuilder();
        sb.append(CYAN + "What will ").append(player.getName()).append(" do?\n");
        for (int i = 0; i < player.getActions().size(); i++) {
            sb.append(i + 1).append(". ").append(player.getActions().get(i).smallToString()).append("\n");
        }
        System.out.println(sb);
        System.out.println("Enter the index of the action you want to perform");
        int actionIndex = InputManager.getInstance().nextInt() - 1;
        while (actionIndex < 0 || actionIndex >= player.getActions().size()) {
            System.out.println("Please enter a valid action index");
            actionIndex = InputManager.getInstance().nextInt() - 1;
        }
        while (player.getActions().get(actionIndex).getMpCost() > player.getMp()) {
            System.out.println("You don't have enough MP to perform this action");
            actionIndex = InputManager.getInstance().nextInt() - 1;
        }
        Action action = player.getActions().get(actionIndex);
        Character target;
        if (action.getActionType() == ActionTypes.MAGICAL_RECOVERY) {
            System.out.println("Who do you want to help?");
            for (int i = 0; i < this.players.size(); i++) {
                System.out.println(i + 1 + ". " + this.players.get(i).getName());
            }
            int targetIndex = InputManager.getInstance().nextInt() - 1;
            while (targetIndex < 0 || targetIndex >= this.players.size() || this.players.get(targetIndex).getHp() == 0) {
                System.out.println("Please enter a valid target index");
                targetIndex = InputManager.getInstance().nextInt() - 1;
            }
            target = this.players.get(targetIndex);
        } else {
            System.out.println("Who do you want to target?");
            for (int i = 0; i < this.enemies.size(); i++) {
                System.out.println(i + 1 + ". " + this.enemies.get(i).getName());
            }
            int targetIndex = InputManager.getInstance().nextInt() - 1;
            while (targetIndex < 0 || targetIndex >= this.enemies.size() || this.enemies.get(targetIndex).getHp() == 0) {
                System.out.println("Please enter a valid target index");
                targetIndex = InputManager.getInstance().nextInt() - 1;
            }
            target = this.enemies.get(targetIndex);
        }
        System.out.println(RESET);
        player.doAction(action, target);
    }

    public void showBattleState() {
        System.out.println(PURPLE + "Battle State:" + RESET);
        System.out.println(GREEN_BACKGROUND + " Players: " + RESET);
        for (Player player : this.players) {
            System.out.println(GREEN + player.smallToString() + RESET);
        }
        System.out.println(RED_BACKGROUND + " Enemies: " + RESET);
        for (Enemy enemy : this.enemies) {
            System.out.println(RED + enemy.toString() + RESET);
        }
    }


}
