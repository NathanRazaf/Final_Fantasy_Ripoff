package FinalFantasy.Loot;

import FinalFantasy.Enums.CharacterClass;
import FinalFantasy.Character.Enemy.Enemy;
import FinalFantasy.Character.Enemy.Goblin;
import FinalFantasy.Character.Enemy.Harpy;
import FinalFantasy.Character.Enemy.Sorcerer;
import FinalFantasy.Enums.LootTiers;
import FinalFantasy.Loot.Equipment.Armors.Boots;
import FinalFantasy.Loot.Equipment.Armors.ChestPlate;
import FinalFantasy.Loot.Equipment.Armors.Helmet;

import java.util.HashMap;
import java.util.Map;

public enum LootTable implements java.io.Serializable {
    INSTANCE;
    private final Map<Class<? extends Enemy>, HashMap<Loot, LootTiers>> lootTable;

    LootTable() {
        lootTable = new HashMap<>();
        addLoot(Goblin.class, new Loot("Goblin's Tooth", 10, "A tooth from a goblin. It's sharp and pointy."), LootTiers.COMMON);
        addLoot(Goblin.class, new Loot("Goblin's Ear", 15, "An ear from a goblin. It's pointy and gross."), LootTiers.COMMON);
        addLoot(Goblin.class, new Loot("Goblin's Eye", 25, "An eye from a goblin. A bit rare."), LootTiers.UNCOMMON);
        addLoot(Goblin.class, new Loot("Goblin's Heart", 50, "A heart from a goblin. Very rare to find in such an intact state."), LootTiers.RARE);
        addLoot(Goblin.class, new Helmet("Goblin's Helmet", 100, "A helmet created by the goblin race.", 20, 0, 0, 6, 0, 0, CharacterClass.MELEE, null), LootTiers.RARE);

        addLoot(Harpy.class, new Loot("Harpy's Feather", 10, "A feather from a harpy. It's soft and fluffy."), LootTiers.COMMON);
        addLoot(Harpy.class, new Loot("Harpy's Beak", 15, "A beak from a harpy. It's pointy and gross."), LootTiers.COMMON);
        addLoot(Harpy.class, new Loot("Harpy's Talon", 25, "A talon from a harpy. A bit rare."), LootTiers.UNCOMMON);
        addLoot(Harpy.class, new Loot("Harpy's Heart", 50, "A heart from a harpy. Very rare to find in such an intact state."), LootTiers.RARE);
        addLoot(Harpy.class, new ChestPlate("Harpy's Armor", 100, "A light chest plate made by the harpies' clan.", 25, 10, 0, 7, 0, 5, CharacterClass.RANGED, null), LootTiers.RARE);

        addLoot(Sorcerer.class, new Loot("Sorcerer's Hair", 10, "A hair from a sorcerer. It's soft and fluffy."), LootTiers.COMMON);
        addLoot(Sorcerer.class, new Loot("Sorcerer's Ear", 15, "An ear from a sorcerer. It's pointy and gross."), LootTiers.COMMON);
        addLoot(Sorcerer.class, new Loot("Sorcerer's Eye", 25, "An eye from a sorcerer. A bit rare."), LootTiers.UNCOMMON);
        addLoot(Sorcerer.class, new Loot("Sorcerer's Heart", 50, "A heart from a sorcerer. Very rare to find in such an intact state."), LootTiers.RARE);
        addLoot(Sorcerer.class, new Boots("Sorcerer's Boots", 100, "A pair of boots made by the sorcerers' clan.", 15, 5, 0, 5, 0, 10, CharacterClass.MAGIC, null), LootTiers.RARE);
    }
    public void addLoot(Class<? extends Enemy> monsterClass, Loot loot, LootTiers lootTier) {
        if (!lootTable.containsKey(monsterClass)) {
            lootTable.put(monsterClass, new HashMap<>());
        }
        lootTable.get(monsterClass).put(loot, lootTier);
    }

    public Map<Loot, LootTiers> getMonsterLoot(Class<? extends Enemy> monsterClass) {
        return lootTable.get(monsterClass);
    }
}
