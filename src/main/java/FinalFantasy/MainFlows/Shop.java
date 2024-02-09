package FinalFantasy.MainFlows;

import FinalFantasy.Character.Player.Player;
import FinalFantasy.Enums.ArmorTypes;
import FinalFantasy.Enums.CharacterClass;
import FinalFantasy.Enums.WeaponTypes;
import Utilities.InputManager;
import FinalFantasy.Loot.Equipment.Armors.*;
import FinalFantasy.Loot.Equipment.Weapons.*;
import FinalFantasy.Loot.Loot;
import FinalFantasy.Enums.StatusEffects;
import Utilities.Utility;


import java.util.*;

import static Utilities.Utility.*;

public class Shop implements java.io.Serializable {
    private final Inventory playerInventory;
    private final HashMap<Loot, Integer> shopInventory = new HashMap<>();

    public Shop(int level, Inventory playerInventory) {
        generateShopInventory(level);
        this.playerInventory = playerInventory;
    }

    public Shop(GameState gameState) {
        this(gameState.getPlayers().stream().mapToInt(Player::getLevel).sum() / gameState.getPlayers().size(), gameState.getInventory());
    }

    public HashMap<Loot, Integer> getShopInventory() {
        return shopInventory;
    }

    private void generateShopInventory(int level) {
        generateArmors(level);
        generateWeapons(level);
    }
    private void printShopInventory() {
        // Determine the maximum length of the strings in each column
        int maxNameLength = "Item Name".length();
        int maxValueLength = "Price".length();
        int maxStockLength = "in stock".length();

        // Calculate the maximum length needed for the name, value, and stock columns
        for (Map.Entry<Loot, Integer> entry : shopInventory.entrySet()) {
            maxNameLength = Math.max(maxNameLength, entry.getKey().getName().length());
            maxValueLength = Math.max(maxValueLength, String.valueOf(entry.getKey().getValue()).length());
            maxStockLength = Math.max(maxStockLength, String.valueOf(entry.getValue()).length());
        }

        // Create the format string for printing each line
        String format = "%-" + (maxNameLength + 2) + "s - Price: %" + (maxValueLength + 2) + "s gil - %" + maxStockLength + "s in stock%n";

        // Print the header
        System.out.printf(format, "Item Name", "Price", "Stock");

        // Create and print the separator line
        String separator = new String(new char[maxNameLength + 2]).replace('\0', '-') + "   " +
                new String(new char[maxValueLength + 4 + " gil".length()]).replace('\0', '-') + "   " +
                new String(new char[maxStockLength + " in stock".length()]).replace('\0', '-');
        System.out.println(separator);

        // Print each item in the shop inventory
        for (Map.Entry<Loot, Integer> entry : shopInventory.entrySet()) {
            System.out.printf(format,
                    entry.getKey().getName(),
                    entry.getKey().getValue(),
                    entry.getValue());
        }
    }

    public void displayShop() {
        printShopInventory();
        System.out.println("What would you like to do?");
        System.out.println("1. Search for an item");
        System.out.println("2. Sell an item");
        System.out.println("3. Exit");
        int choice = 0;
        while (choice < 1 || choice > 3) {
            choice = InputManager.getInstance().nextInt();
        }
        switch (choice) {
            case 1 -> searchItem();
            case 2 -> sellItem();
            case 3 -> {
            }
        }

    }
    private void generateArmors(int level) {
        for (ArmorTypes armorType : ArmorTypes.values()) {
            for (int i=0; i<Math.floor(Math.random()*4); i++) {
                Armor armor = generateArmor(level, armorType);
                shopInventory.put(armor, randomIntRange(1, 10));
            }
        }
    }
    private Armor generateArmor(int level, ArmorTypes armorType) {
        int value, hpBonus, mpBonus, defBonus, spdBonus;
        CharacterClass characterClass = CharacterClass.values()[(int) (Math.random() * CharacterClass.values().length)];
        StatusEffects[] statusEffects = generateRandomBuffs(StatusEffects.POISONED, StatusEffects.CONFUSED, StatusEffects.SLOWED, StatusEffects.WEAKENED);
        Armor armor = null;
        switch (armorType) {
            case HELMET:
                value = 50 + statusEffects.length*15;
                hpBonus = 20;
                mpBonus = 10;
                defBonus = 10;
                armor = new Helmet("Helmet lvl."+level+" ID:"+ Utility.generateId(),
                        randomIntRange(value*level-10, value*level+10),
                        "A helmet or whatever",
                        randomIntRange(hpBonus*level-5, hpBonus*level+5),
                        randomIntRange(mpBonus*level-5, mpBonus*level+5),
                        randomIntRange(0, level),
                        randomIntRange(defBonus*level-5, defBonus*level+5),
                        randomIntRange(0, level),
                        randomIntRange(0, level),
                        characterClass,
                        statusEffects);
                break;
            case CHEST_PLATE:
                value = 100 + statusEffects.length*15;
                hpBonus = 50;
                mpBonus = 20;
                defBonus = 20;
                armor = new ChestPlate("Chest Plate lvl."+level+" ID:"+ Utility.generateId(),
                        randomIntRange(value*level-10, value*level+10),
                        "A chest plate or whatever",
                        randomIntRange(hpBonus*level-5, hpBonus*level+5),
                        randomIntRange(mpBonus*level-5, mpBonus*level+5),
                        randomIntRange(0, level),
                        randomIntRange(defBonus*level-5, defBonus*level+5),
                        randomIntRange(0, level),
                        randomIntRange(0, level),
                        characterClass,
                        statusEffects);
                break;
            case LEGGINGS:
                value = 75 + statusEffects.length*15;
                hpBonus = 30;
                mpBonus = 15;
                defBonus = 15;
                spdBonus = 5;
                armor = new Leggings("Leggings lvl."+level+" ID:"+ Utility.generateId(),
                        randomIntRange(value*level-10, value*level+10),
                        "A pair of leggings or whatever",
                        randomIntRange(hpBonus*level-5, hpBonus*level+5),
                        randomIntRange(mpBonus*level-5, mpBonus*level+5),
                        randomIntRange(0, level),
                        randomIntRange(defBonus*level-5, defBonus*level+5),
                        randomIntRange(0, level),
                        randomIntRange(spdBonus*level-5, spdBonus*level+5),
                        characterClass,
                        statusEffects);
                break;
            case BOOTS:
                value = 40 + statusEffects.length*15;
                hpBonus = 15;
                mpBonus = 8;
                defBonus = 8;
                spdBonus = 15;
                armor = new Boots("Boots lvl."+level+" ID:"+ Utility.generateId(),
                        randomIntRange(value*level-10, value*level+10),
                        "A pair of boots or whatever",
                        randomIntRange(hpBonus*level-5, hpBonus*level+5),
                        randomIntRange(mpBonus*level-5, mpBonus*level+5),
                        randomIntRange(0, level),
                        randomIntRange(defBonus*level-5, defBonus*level+5),
                        randomIntRange(0, level),
                        randomIntRange(spdBonus*level-5, spdBonus*level+5),
                        characterClass,
                        statusEffects);
                break;
        }
        return armor;
    }
    private StatusEffects[] generateRandomBuffs(StatusEffects... excludedEffects) {
        List<StatusEffects> statusEffects = new ArrayList<>();
        List<StatusEffects> exclusions = Arrays.asList(excludedEffects);

        for (StatusEffects statusEffect : StatusEffects.values()) {
            if (exclusions.contains(statusEffect)) {
                continue;
            }
            if (Math.random() < 0.5) {
                statusEffects.add(statusEffect);
            }
        }
        return statusEffects.toArray(new StatusEffects[0]);
    }
    private void generateWeapons(int level) {
        for (WeaponTypes weaponType : WeaponTypes.values()) {
            for (int i=0; i<Math.floor(Math.random()*4); i++) {
                Weapon weapon = generateWeapon(level, weaponType);
                shopInventory.put(weapon, randomIntRange(1, 10));
            }
        }
    }
    private Weapon generateWeapon(int level, WeaponTypes weaponType) {
        int value, atkBonus, defBonus, crtBonus;
        Weapon weapon = null;
        switch (weaponType) {
            case SWORD:
                value = 100;
                atkBonus = 30;
                crtBonus = 15;
                weapon = new Sword("Sword lvl."+level+" ID:"+ Utility.generateId(),
                        randomIntRange(value*level-10, value*level+10),
                        "A sword or whatever",
                        randomIntRange(atkBonus*level-5, atkBonus*level+5),
                        randomIntRange(crtBonus*level-5, crtBonus*level+5));
                break;
            case SHIELD:
                value = 80;
                defBonus = 30;
                weapon = new Shield("Shield lvl."+level+" ID:"+ Utility.generateId(),
                        randomIntRange(value*level-10, value*level+10),
                        "A shield or whatever",
                        randomIntRange(defBonus*level-5, defBonus*level+5));
                break;
            case AXE:
                value = 120;
                atkBonus = 60;
                crtBonus = 35;
                weapon = new Axe("Axe lvl."+level+" ID:"+ Utility.generateId(),
                        randomIntRange(value*level-10, value*level+10),
                        "An axe or whatever",
                        randomIntRange(atkBonus*level-5, atkBonus*level+5),
                        randomIntRange(crtBonus*level-5, crtBonus*level+5));
                break;
            case SPEAR:
                value = 80;
                atkBonus = 20;
                crtBonus = 10;
                weapon = new Spear("Spear lvl."+level+" ID:"+ Utility.generateId(),
                        randomIntRange(value*level-10, value*level+10),
                        "A spear or whatever",
                        randomIntRange(atkBonus*level-5, atkBonus*level+5),
                        randomIntRange(crtBonus*level-5, crtBonus*level+5));
                break;
            case BOW:
                value = 100;
                atkBonus = 40;
                crtBonus = 20;
                weapon = new Bow("Bow lvl."+level+" ID:"+ Utility.generateId(),
                        randomIntRange(value*level-10, value*level+10),
                        "A bow or whatever",
                        randomIntRange(atkBonus*level-5, atkBonus*level+5),
                        randomIntRange(crtBonus*level-5, crtBonus*level+5));
                break;
            case CROSSBOW:
                value = 120;
                atkBonus = 50;
                defBonus = 10;
                crtBonus = 25;
                weapon = new Crossbow("Crossbow lvl."+level+" ID:"+ Utility.generateId(),
                        randomIntRange(value*level-10, value*level+10),
                        "A crossbow or whatever",
                        randomIntRange(atkBonus*level-5, atkBonus*level+5),
                        randomIntRange(defBonus*level-5, defBonus*level+5),
                        randomIntRange(crtBonus*level-5, crtBonus*level+5));
                break;
            case STAFF:
                value = 80;
                atkBonus = 20;
                defBonus = 5;
                crtBonus = 10;
                weapon = new Staff("Staff lvl."+level+" ID:"+ Utility.generateId(),
                        randomIntRange(value*level-10, value*level+10),
                        "A staff or whatever",
                        randomIntRange(atkBonus*level-5, atkBonus*level+5),
                        randomIntRange(defBonus*level-5, defBonus*level+5),
                        randomIntRange(crtBonus*level-5, crtBonus*level+5));
                break;
        }
        return weapon;
    }

    public void searchItem() {
        System.out.println("Enter the ID of the item you want to search for (it has exactly 8 characters):");
        String ID = "";
        while (ID.length() != 8) {
            ID = InputManager.getInstance().nextLine();
        }
        Loot item = null;
        while (item == null) {
            item = searchItemByID(ID);
            if (item == null) {
                System.out.println("No item found with that ID. Please try again.");
                ID = InputManager.getInstance().nextLine();
            }
        }
        interactWithItem(item);
    }
    public void interactWithItem(Loot item) {
        System.out.println(item);
        System.out.println("What would you like to do?");
        System.out.println("1. Buy");
        System.out.println("2. Exit");
        int choice = 0;
        while (choice < 1 || choice > 2) {
            choice = InputManager.getInstance().nextInt();
        }
        switch (choice) {
            case 1 -> buyItem(item);
            case 2 -> {
            }
        }
    }
    public void buyItem(Loot item) {
        System.out.println("How many would you like to buy? You can't buy more than " + shopInventory.get(item) + " of this item");
        System.out.println("You have " + playerInventory.getGold() + " gold");
        int quantity = 0;
        while (quantity < 1 || quantity > shopInventory.get(item)) {
            quantity = InputManager.getInstance().nextInt();
        }
        if (playerInventory.getGold() < item.getValue()*quantity) {
            System.out.println("You don't have enough gold to buy that many!");
            return;
        }
        for (int i=0; i<quantity; i++) {
            playerInventory.addLoot(item);
        }
        playerInventory.removeGold(item.getValue()*quantity);
        shopInventory.put(item, shopInventory.get(item)-quantity);
        if (shopInventory.get(item) == 0) {
            shopInventory.remove(item);
        }
        System.out.println("You spent " + item.getValue()*quantity + " gold by buying " + quantity + " " + item.getName());
    }
    private Loot searchItemByID(String ID) {
        for (Map.Entry<Loot, Integer> entry : shopInventory.entrySet()) {
            if (entry.getKey().getName().contains(ID)) {
                return entry.getKey();
            }
        }
        return null;
    }
    public void sellItem() {
        playerInventory.displayInventory();
        System.out.println("Enter the name of the item you want to sell or 0 to exit");
        String name = "";
        while (name.isEmpty()) {
            name = InputManager.getInstance().nextLine();
        }
        Loot item = null;
        while (item == null) {
            item = playerInventory.getLootByName(name);
            if (name.equals("0")) {
                return;
            }
            if (item == null) {
                System.out.println("No item found with that name. Please try again.");
                name = InputManager.getInstance().nextLine();
            }
        }
        System.out.println(item);
        System.out.println("How many would you like to sell? You can't sell more than " + playerInventory.getInventory().get(item) + " of this item");
        int quantity = 0;
        while (quantity < 1 || quantity > playerInventory.getInventory().get(item)) {
            quantity = InputManager.getInstance().nextInt();
        }
        for (int i=0; i<quantity; i++) {
            playerInventory.removeLoot(item);
        }
        playerInventory.addGold(item.getValue()*quantity);
        System.out.println("You gained " + item.getValue()*quantity + " gold by selling " + quantity + " " + item.getName());
    }

    public void removeSomeLoot(Loot loot, int quantity) {
        shopInventory.put(loot, shopInventory.get(loot)-quantity);
        if (shopInventory.get(loot) == 0) {
            shopInventory.remove(loot);
        }
    }

}
