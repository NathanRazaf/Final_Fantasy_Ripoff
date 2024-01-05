package FinalFantasy.Enums.EnemyTypes;

public enum SorcererTypes implements java.io.Serializable {
    RED_SORCERER("Red Sorcerer"),
    BLUE_SORCERER("Blue Sorcerer"),
    BLACK_SORCERER("Black Sorcerer"),
    WHITE_SORCERER("White Sorcerer"),
    GOLD_SORCERER("Gold Sorcerer"),
    SORCERER_KING("Sorcerer King");

    private final String name;

    SorcererTypes(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }
}
