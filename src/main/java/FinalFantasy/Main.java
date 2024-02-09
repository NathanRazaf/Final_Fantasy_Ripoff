package FinalFantasy;
import FinalFantasy.MainFlows.GameStateManager;
import Utilities.SerializationUtil;

import java.io.IOException;

public class Main implements java.io.Serializable {
    private static final String DATA_FILE = "gameState.ser";
    private static GameStateManager gameStateManager;
    public static void main(String[] args) {
        try {
            // Try to load existing data
            gameStateManager = SerializationUtil.loadData(DATA_FILE);
        } catch (IOException | ClassNotFoundException e) {
            // If no data is found or an error occurs, create new data
            gameStateManager = new GameStateManager();
        }

        gameStateManager.startMenu();


        Runtime.getRuntime().addShutdownHook(new Thread(() -> {
            try {
                SerializationUtil.saveData(gameStateManager, DATA_FILE);
                System.out.println("Data saved successfully.");
            } catch (IOException e) {
                System.out.println("Error saving data: " + e.getMessage());
            }
        }));
        try {
            SerializationUtil.saveData(gameStateManager, DATA_FILE);
        } catch (IOException e) {
            System.out.println("Error saving data: " + e.getMessage());
        }
    }
}