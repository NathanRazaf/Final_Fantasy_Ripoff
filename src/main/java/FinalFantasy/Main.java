package FinalFantasy;
import FinalFantasy.MainFlows.GameStateManager;

public class Main implements java.io.Serializable {
    public static void main(String[] args) {
        GameStateManager gsm = GameStateManager.loadState();
        gsm.startMenu();
    }
}