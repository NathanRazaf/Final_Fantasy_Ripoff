package FinalFantasy.Loot;

public class Loot {
    protected final String name;
    protected final int value;
    protected final String description;

    public Loot(String name, int value, String description) {
        this.name = name;
        this.value = value;
        this.description = description;
    }

    public String getName() {
        return this.name;
    }
    public int getValue() {
        return this.value;
    }
    public String getDescription() {
        return this.description;
    }

    public String toString() {
        return this.name + " (" + this.value + " gil)\n" + this.description;
    }

}
