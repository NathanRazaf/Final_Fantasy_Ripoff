package FinalFantasy;
import FinalFantasy.Battles.GameStateManager;
public class Main {
    public static void main(String[] args) {
        GameStateManager gameStateManager = GameStateManager.getInstance();
        gameStateManager.start();
    }
}