package FinalFantasy.Loot;

import FinalFantasy.Character.CharacterClass;
import FinalFantasy.Character.Enemy.Enemy;
import FinalFantasy.Character.Enemy.Goblin;
import FinalFantasy.Character.Enemy.Harpy;
import FinalFantasy.Character.Enemy.Sorcerer;
import FinalFantasy.Loot.Equipment.Armors.Armor;
import FinalFantasy.Loot.Equipment.Armors.Boots;
import FinalFantasy.Loot.Equipment.Armors.ChestPlate;
import FinalFantasy.Loot.Equipment.Armors.Helmet;

import java.util.HashMap;
import java.util.Map;

public enum LootTable {
    INSTANCE;
    private final Map<Class<? extends Enemy>, HashMap<Integer, HashMap<Loot, Integer>>> lootTable;

    LootTable() {
        lootTable = new HashMap<>();
        addLoot(Goblin.class, 0, new Loot("Goblin's Tooth", 10, "A tooth from a goblin. It's sharp and pointy."), 90);
        addLoot(Goblin.class, 0, new Loot("Goblin's Ear", 15, "An ear from a goblin. It's pointy and gross."), 75);
        addLoot(Goblin.class, 0, new Loot("Goblin's Eye", 25, "An eye from a goblin. A bit rare."), 50);
        addLoot(Goblin.class, 0, new Loot("Goblin's Heart", 50, "A heart from a goblin. Very rare to find in such an intact state."), 25);
        addLoot(Goblin.class, 0, new Helmet("Goblin's Helmet", 100, "A helmet created by the goblin race.", 20, 0, 0, 6, 0, 0, CharacterClass.MELEE, null), 10);

        addLoot(Harpy.class, 0, new Loot("Harpy's Feather", 10, "A feather from a harpy. It's soft and fluffy."), 90);
        addLoot(Harpy.class, 0, new Loot("Harpy's Beak", 15, "A beak from a harpy. It's pointy and gross."), 75);
        addLoot(Harpy.class, 0, new Loot("Harpy's Talon", 25, "A talon from a harpy. A bit rare."), 50);
        addLoot(Harpy.class, 0, new Loot("Harpy's Heart", 50, "A heart from a harpy. Very rare to find in such an intact state."), 25);
        addLoot(Harpy.class, 0, new ChestPlate("Harpy's Armor", 100, "A light chest plate made by the harpies' clan.", 25, 10, 0, 7, 0, 5, CharacterClass.RANGED, null), 10);

        addLoot(Sorcerer.class, 0, new Loot("Sorcerer's Hair", 10, "A hair from a sorcerer. It's soft and fluffy."), 90);
        addLoot(Sorcerer.class, 0, new Loot("Sorcerer's Ear", 15, "An ear from a sorcerer. It's pointy and gross."), 75);
        addLoot(Sorcerer.class, 0, new Loot("Sorcerer's Eye", 25, "An eye from a sorcerer. A bit rare."), 50);
        addLoot(Sorcerer.class, 0, new Loot("Sorcerer's Heart", 50, "A heart from a sorcerer. Very rare to find in such an intact state."), 25);
        addLoot(Sorcerer.class, 0, new Boots("Sorcerer's Boots", 100, "A pair of boots made by the sorcerers' clan.", 15, 5, 0, 5, 0, 10, CharacterClass.MAGIC, null), 10);
    }
    public void addLoot(Class<? extends Enemy> monsterClass, int level, Loot loot, int dropChance) {
        lootTable.computeIfAbsent(monsterClass, k -> new HashMap<>())
                .computeIfAbsent(level, k -> new HashMap<>())
                .put(loot, dropChance);
    }

    public Map<Loot, Integer> getLootForMonsterAndLevel(Class<? extends Enemy> monsterClass, int level) {
        return lootTable.getOrDefault(monsterClass, new HashMap<>())
                .getOrDefault(level, new HashMap<>());
    }
}
