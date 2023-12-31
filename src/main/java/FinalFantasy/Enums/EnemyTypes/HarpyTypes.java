package FinalFantasy.Enums.EnemyTypes;

public enum HarpyTypes implements java.io.Serializable {
    RED_HARPY("Red Harpy"),
    BLUE_HARPY("Blue Harpy"),
    BLACK_HARPY("Black Harpy"),
    WHITE_HARPY("White Harpy"),
    GOLD_HARPY("Gold Harpy"),
    HARPY_QUEEN("Harpy Queen");

    private final String name;

    HarpyTypes(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }
}
