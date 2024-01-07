package FinalFantasy.MainFlows;

import FinalFantasy.Character.Enemy.Harpy;
import FinalFantasy.Character.Enemy.Sorcerer;
import FinalFantasy.Enums.CharacterClass;
import FinalFantasy.Character.Enemy.Enemy;
import FinalFantasy.Character.Player.Player;
import FinalFantasy.Character.Player.PlayerClasses.Archer;
import FinalFantasy.Character.Player.PlayerClasses.Warrior;
import FinalFantasy.Character.Player.PlayerClasses.Wizard;
import FinalFantasy.Loot.Equipment.Armors.Boots;
import FinalFantasy.Loot.Equipment.Armors.ChestPlate;
import FinalFantasy.Loot.Equipment.Armors.Leggings;
import FinalFantasy.Loot.Equipment.Weapons.Bow;
import FinalFantasy.Loot.Equipment.Weapons.Sword;
import FinalFantasy.Loot.Loot;

import java.io.IOException;
import java.util.ArrayList;

import Utilities.InputManager;
import FinalFantasy.Enums.StatusEffects;
import Utilities.SerializationUtil;

import static Utilities.Utility.randomIntRange;

public class GameState implements java.io.Serializable {
    private int index;
    private Shop shop = null;
    private final ArrayList<Player> players = new ArrayList<>();
    private final Inventory inventory = new Inventory(this.players);
    public GameState(int index) {
        this.index = index;
    }
    public void setUpPlayers() {
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
        inventory.display();

    }
    private void showShop() {
        int teamLevel = this.players.stream().mapToInt(Player::getLevel).sum() / this.players.size();
        if (this.shop == null) {
            this.shop = new Shop(teamLevel, this.inventory);
            shop.displayShop();
            return;
        }
        System.out.println("Do you want a new shop? (y/n)");
        String input = "";
        while (!input.equals("y") && !input.equals("n")) {
            input = InputManager.getInstance().nextLine();
        }
        if (input.equals("y")) {
            this.shop = new Shop(teamLevel, this.inventory);
        }
        shop.displayShop();

    }
    public void load() throws IOException {
        if (players.isEmpty()) setUpPlayers();

        boolean isRunning = true;

        while (isRunning) {
            isRunning = this.displayMainMenu();
        }
    }

    public boolean displayMainMenu() {
        System.out.println("What do you want to do?");
        System.out.println("1. Go to the shop");
        System.out.println("2. Go to the inventory");
        System.out.println("3. Start a random battle");
        System.out.println("4. Display players");
        System.out.println("5. Go to inn");
        System.out.println("0. Quit the game");
        int input = InputManager.getInstance().nextInt();
        while (input < 0 || input > 5) {
            System.out.println("Please enter a valid input");
            input = InputManager.getInstance().nextInt();
        }
        switch (input) {
            case 1 -> showShop();
            case 2 -> showInventory();
            case 3 -> startBattle();
            case 4 -> displayPlayers();
            case 5 -> goToInn();
            case 0 -> {return false;}
        }
        return true;
    }

    private void goToInn() {
        int teamLevel = this.players.stream().mapToInt(Player::getLevel).sum() / this.players.size();
        int cost = randomIntRange(10*teamLevel, 20*teamLevel);
        System.out.println("It will cost you " + cost + " gold to rest at the inn. Do you want to rest? (y/n)");
        String input = "";
        while (!input.equals("y") && !input.equals("n")) {
            input = InputManager.getInstance().nextLine();
        }
        if (input.equals("y")) {
            if (this.inventory.getGold() < cost) {
                System.out.println("You don't have enough gold to rest at the inn");
                return;
            }
            this.inventory.removeGold(cost);
            for (Player player : this.players) {
                player.setHp(player.getMaxHp());
                player.setMp(player.getMaxMp());
            }
            System.out.println("You have been fully healed!");
        }
    }
    private void startBattle() {
        Battle battle = new Battle(this.players);
        boolean isVictory = battle.doBattle();
        if (isVictory) rewardBattle(battle);
        else {
            GameStateManager.getInstance().deleteGameState(this.index);
            System.exit(0);
        }

    }
    private void rewardBattle(Battle battle) {
        int totalXpGiven = 0;
        for (Enemy enemy : battle.getEnemies()) {
            for (Loot loot : enemy.getLootGiven()) {
                inventory.addLoot(loot);
            }
            totalXpGiven += enemy.getExpGiven();
            inventory.addGold(enemy.getGoldGiven());
            System.out.println("You got " + enemy.getGoldGiven() + " gold from " + enemy.getName());
        }
        for (Player player : battle.getPlayers()) {
            player.addExp(totalXpGiven/this.players.size());
        }
    }

    private void displayPlayers() {
        for (int i=0; i<this.players.size(); i++) {
            System.out.println(i+1 + ". " + this.players.get(i).toString() + "\n");
        }

    }

    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("Gold : ").append(this.inventory.getGold()).append("\n");
        for (int i=0; i<this.players.size(); i++) {
            sb.append(i+1).append(". ").append(this.players.get(i).verySmallToString()).append("\n");
        }
        return sb.toString();
    }
}
