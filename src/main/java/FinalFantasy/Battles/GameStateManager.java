package FinalFantasy.Battles;

import FinalFantasy.Character.CharacterClass;
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

import java.util.ArrayList;
import java.util.HashMap;
import FinalFantasy.InputManager;
import FinalFantasy.StatusEffects;

public class GameStateManager {
    private static GameStateManager instance = null;
    private final ArrayList<Player> players = new ArrayList<>();
    private final Inventory inventory = new Inventory(this.players);
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
        inventory.display();
    }
    public void start() {
        this.setUpPlayers();
        //mock inventory
        inventory.addLoot(new Boots("Boots of Speed", 20, "Gives speed boost", 15, 10, 0, 10, 0, 20, CharacterClass.RANGED, new StatusEffects[]{StatusEffects.ACCELERATED}));
        inventory.addLoot(new ChestPlate("Chestplate of the Warrior", 20, "Gives strength boost", 25, 5, 10, 15, 0, 0, CharacterClass.MELEE, new StatusEffects[]{StatusEffects.STRENGTHENED}));
        inventory.addLoot(new Leggings("Leggings of magic", 20, "Gives magic boost", 15, 25, 0, 10, 0, 0, CharacterClass.MAGIC, null));
        inventory.addLoot(new Sword("Sword of the Warrior", 20, "Gives strength boost", 35, 10));
        inventory.addLoot(new Bow("Bow of the Archer", 20, "Gives critical hit boost", 15, 25));

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
            case 2: showInventory();
            case 3: return this.startBattle();
            case 0: return false;
        }
        return true;
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
                inventory.addLoot(loot);
            }
            totalXpGiven += enemy.getExpGiven();
            inventory.addGold(enemy.getGoldGiven());
            System.out.println("You got " + enemy.getGoldGiven() + " gold from " + enemy.getName());
        }
        for (Player player : battle.getPlayers()) {
            player.addExp(totalXpGiven);
        }
    }

}
