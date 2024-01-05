package FinalFantasy.MainFlows;

import FinalFantasy.Character.Player.Player;
import FinalFantasy.Loot.Equipment.Armors.Armor;
import FinalFantasy.Loot.Equipment.Weapons.Weapon;
import FinalFantasy.Loot.Loot;
import Utilities.InputManager;

import java.util.ArrayList;
import java.util.HashMap;

public class Inventory implements java.io.Serializable {
    private int gold = 0;
    public ArrayList<Player> players;
    public HashMap<Loot, Integer> inventory = new HashMap<>();

    public Inventory(ArrayList<Player> players) {
        this.players = players;
    }
    public void displayInventory() {
        System.out.println("Your inventory:");
        System.out.println("Gold: " + this.gold);
        if (this.inventory.isEmpty()) {
            System.out.println("Your inventory is empty!");
        }
        for (Loot loot : this.inventory.keySet()) {
            System.out.println(loot.getName() + " x" + this.inventory.get(loot));
        }
    }
    public void display() {
        boolean continueDisplaying = true;
        while (continueDisplaying) {
            displayInventory();
            System.out.println("What do you want to do?");
            System.out.println("1. Equip loot");
            System.out.println("2. Un-equip loot");
            System.out.println("3. Exit");
            int choice = 0;
            while (choice < 1 || choice > 3) {
                choice = InputManager.getInstance().nextInt();
            }
            switch (choice) {
                case 1:
                    this.equipLoot();
                    break;
                case 2:
                    this.unEquipLoot();
                    break;
                case 3:
                    continueDisplaying = false;
                    break;
            }
        }

    }

    public HashMap<Armor, Integer> getArmors() {
        HashMap<Armor, Integer> armors = new HashMap<>();
        for (Loot loot : this.inventory.keySet()) {
            if (loot instanceof Armor) {
                armors.put((Armor) loot, this.inventory.get(loot));
            }
        }
        return armors;
    }
    public HashMap<Weapon, Integer> getWeapons() {
        HashMap<Weapon, Integer> weapons = new HashMap<>();
        for (Loot loot : this.inventory.keySet()) {
            if (loot instanceof Weapon) {
                weapons.put((Weapon) loot, this.inventory.get(loot));
            }
        }
        return weapons;
    }
    public void printEquippables() {
        for (Armor armor : this.getArmors().keySet()) {
            System.out.println(armor.getName() + " x" + this.getArmors().get(armor));
        }
        for (Weapon weapon : this.getWeapons().keySet()) {
            System.out.println(weapon.getName() + " x" + this.getWeapons().get(weapon));
        }
    }
    public Loot getLootByName(String name) {
        for (Loot loot : this.inventory.keySet()) {
            if (loot.getName().equals(name)) {
                return loot;
            }
        }
        return null;
    }

    public void equipLoot() {
        printEquippables();
        System.out.println("Who do you want to equip?");
        for (int i = 0; i < this.players.size(); i++) {
            System.out.println(i + 1 + ". " + this.players.get(i).getName());
        }
        int playerIndex = InputManager.getInstance().nextInt() - 1;
        while (playerIndex < 0 || playerIndex >= this.players.size()) {
            System.out.println("Please enter a valid player index");
            playerIndex = InputManager.getInstance().nextInt() - 1;
        }
        Player player = this.players.get(playerIndex);
        Loot equipment = null;
        System.out.println("What do you want to equip? Write the name of the loot or 0 to exit");
        while (equipment == null) {
            String name = InputManager.getInstance().nextLine();
            if (name.equals("0")) {
                return;
            }
            equipment = this.getLootByName(name);
            if (equipment == null) {
                System.out.println("Please enter a valid loot name");
            }
        }
        if (player.equip(equipment)) {
            this.inventory.put(equipment, this.inventory.get(equipment) - 1);
            if (this.inventory.get(equipment) == 0) {
                this.inventory.remove(equipment);
            }
        }
    }

    public void unEquipLoot() {
        System.out.println("Who do you want to un-equip?");
        for (int i = 0; i < this.players.size(); i++) {
            System.out.println(i + 1 + ". " + this.players.get(i).getName());
        }
        int playerIndex = InputManager.getInstance().nextInt() - 1;
        while (playerIndex < 0 || playerIndex >= this.players.size()) {
            System.out.println("Please enter a valid player index");
            playerIndex = InputManager.getInstance().nextInt() - 1;
        }
        Player player = this.players.get(playerIndex);
        System.out.println(player);
        Loot equipment = null;
        System.out.println("What do you want to un-equip? Write the name of the loot or 0 to exit");
        while (equipment == null) {
            String name = InputManager.getInstance().nextLine();
            if (name.equals("0")) {
                return;
            }
            equipment = this.getLootByName(name);
            if (equipment == null) {
                System.out.println("Please enter a valid loot name");
            }
        }
        if (player.unEquip(equipment)) {
            this.addLoot(equipment);
        }
    }

    public int getGold() {
        return gold;
    }
    public HashMap<Loot, Integer> getInventory() {
        return inventory;
    }
    public void removeGold(int gold) {
        this.gold -= gold;
    }
    public void addGold(int gold) {
        this.gold += gold;
    }
    public void addLoot(Loot loot) {
        if (this.inventory.containsKey(loot)) {
            this.inventory.put(loot, this.inventory.get(loot) + 1);
        } else {
            this.inventory.put(loot, 1);
        }
    }
    public void removeLoot(Loot loot) {
        if (this.inventory.containsKey(loot)) {
            this.inventory.put(loot, this.inventory.get(loot) - 1);
            if (this.inventory.get(loot) == 0) {
                this.inventory.remove(loot);
            }
        }
    }
}
