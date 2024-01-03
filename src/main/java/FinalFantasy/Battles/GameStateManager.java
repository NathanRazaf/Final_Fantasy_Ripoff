package FinalFantasy.Battles;

import FinalFantasy.Character.Enemy.Enemy;
import FinalFantasy.Character.Player.Player;
import FinalFantasy.Character.Player.PlayerClasses.Archer;
import FinalFantasy.Character.Player.PlayerClasses.Warrior;
import FinalFantasy.Character.Player.PlayerClasses.Wizard;
import FinalFantasy.Loot.Loot;

import java.util.ArrayList;
import java.util.HashMap;
import FinalFantasy.InputManager;

public class GameStateManager {
    private static GameStateManager instance = null;
    private ArrayList<Player> players = new ArrayList<>();
    private HashMap<Loot, Integer> inventory = new HashMap<>();
    public static GameStateManager getInstance() {
        if (instance == null) {
            instance = new GameStateManager();
        }
        return instance;
    }
    private void setUpPlayers() {
        System.out.println("Create your dream team of 4 characters!");
        for (int i=0; i<4; i++) {
            System.out.println("Player " + (i+1) + ":");
            System.out.print("Enter his name: ");
            String name = "";
            while (name.isEmpty()) {
                name = InputManager.getInstance().nextLine();
            }
            System.out.println("Choose a class:");
            System.out.println("1. Warrior");
            System.out.println("2. Archer");
            System.out.println("3. Wizard");
            int classChoice = 0;
            while (classChoice < 1 || classChoice > 3) {
                classChoice = InputManager.getInstance().nextInt();
            }
            switch (classChoice) {
                case 1:
                    this.players.add(new Warrior(name));
                    break;
                case 2:
                    this.players.add(new Archer(name));
                    break;
                case 3:
                    this.players.add(new Wizard(name));
                    break;
            }
            System.out.println(name + " has been added to your team!");
        }
    }
    private void showInventory() {
        System.out.println("Your inventory:");
        if (this.inventory.isEmpty()) {
            System.out.println("Your inventory is empty!");
            return;
        }
        for (Loot loot : this.inventory.keySet()) {
            System.out.println(loot.getName() + " x" + this.inventory.get(loot));
        }
    }
    public void start() {
        this.setUpPlayers();
        boolean isRunning = true;
        while (isRunning) isRunning = this.displayMainMenu();
    }

    public boolean displayMainMenu() {
        System.out.println("What do you want to do?");
        System.out.println("1. Go to the shop");
        System.out.println("2. Go to the inventory");
        System.out.println("3. Start a random battle");
        System.out.println("0. Quit the game");
        int input = InputManager.getInstance().nextInt();
        while (input < 0 || input > 3) {
            System.out.println("Please enter a valid input");
            input = InputManager.getInstance().nextInt();
        }
        switch (input) {
            case 1: return true;
            case 2: return true;
            case 3: return this.startBattle();
            case 0: return false;
        }
        return false;
    }

    private boolean startBattle() {
        Battle battle = new Battle(this.players);
        boolean isVictory = battle.doBattle();
        if (isVictory) rewardBattle(battle);
        return isVictory;
    }
    private void rewardBattle(Battle battle) {
        int totalXpGiven = 0;
        for (Enemy enemy : battle.getEnemies()) {
            for (Loot loot : enemy.getLootGiven()) {
                System.out.println("You got " + loot.getName() + " x1!");
                if (this.inventory.containsKey(loot)) {
                    this.inventory.put(loot, this.inventory.get(loot) + 1);
                } else {
                    this.inventory.put(loot, 1);
                }
            }
            totalXpGiven += enemy.getExpGiven();
        }
        for (Player player : battle.getPlayers()) {
            player.addExp(totalXpGiven);
        }
    }

}
