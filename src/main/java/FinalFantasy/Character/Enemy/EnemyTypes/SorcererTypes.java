package FinalFantasy.Character.Enemy.EnemyTypes;

public enum SorcererTypes {
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
