package FinalFantasy.MainFlows;

import Utilities.InputManager;
import com.google.gson.Gson;
import java.io.*;

public class GameStateManager implements java.io.Serializable {
    private static GameStateManager instance;
    private static final int NUMBER_OF_SAVES = 3;
    private GameState[] gameStates = new GameState[NUMBER_OF_SAVES];

    // Singleton pattern implementation
    public static GameStateManager getInstance() {
        if (instance == null) {
            instance = new GameStateManager();
        }
        return instance;
    }
    // ...

    public void saveState() {
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream("gameState.ser"))) {
            oos.writeObject(this);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static GameStateManager loadState() {
        GameStateManager gameStateManager;
        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream("gameState.ser"))) {
            gameStateManager = (GameStateManager) ois.readObject();
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
            gameStateManager = new GameStateManager(); // or handle differently
        }
        return gameStateManager;
    }

    // Other methods
    public void startMenu() {
        StringBuilder stringBuilder = new StringBuilder();
        System.out.println("Welcome to Final Fantasy!");
        System.out.println("Choose a load slot to load or create a new game:");
        for (int i=0; i<3; i++) {
            stringBuilder.append("Game state ").append(i+1).append(":\n");
            if (this.gameStates[i] == null) {
                stringBuilder.append("Empty\n\n");
            } else {
                stringBuilder.append(this.gameStates[i]).append("\n");
            }
        }
        System.out.println(stringBuilder);
        System.out.println("Enter the index of the game state you want to load! If it's empty, a new game will be created");
        int gameStateIndex = InputManager.getInstance().nextInt() - 1;
        while (gameStateIndex < 0 || gameStateIndex >= 3) {
            System.out.println("Please enter a valid game state index");
            gameStateIndex = InputManager.getInstance().nextInt() - 1;
        }
        if (this.gameStates[gameStateIndex] == null) {
            this.gameStates[gameStateIndex] = new GameState(gameStateIndex);
            this.gameStates[gameStateIndex].load();
            this.saveState();
            return;
        }
        this.gameStates[gameStateIndex].load();
    }

    public void deleteGameState(int index) {
        this.gameStates[index] = null;
        this.saveState();
    }
}
