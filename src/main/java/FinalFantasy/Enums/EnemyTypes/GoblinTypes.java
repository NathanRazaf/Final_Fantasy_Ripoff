package FinalFantasy.Enums.EnemyTypes;

public enum GoblinTypes implements java.io.Serializable {
    RED_GOBLIN("Red Goblin"),
    BLUE_GOBLIN("Blue Goblin"),
    BLACK_GOBLIN("Black Goblin"),
    WHITE_GOBLIN("White Goblin"),
    GOLD_GOBLIN("Gold Goblin"),
    GOBLIN_KING("Goblin King");

    private final String name;

    GoblinTypes(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }
}
