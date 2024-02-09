package FF_GUI;

import FinalFantasy.MainFlows.GameState;
import FinalFantasy.MainFlows.GameStateManager;
import Utilities.SerializationUtil;
import javafx.application.Application;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.io.IOException;

public class MainApp extends Application {

    private static final String DATA_FILE = "gameState.ser";
    private static GameStateManager gameStateManager;
    public static void main(String[] args) {
        launch(args);
    }
    @Override
    public void start(Stage primaryStage) throws Exception {
        try {
            // Try to load existing data
            gameStateManager = SerializationUtil.loadData(DATA_FILE);
        } catch (IOException | ClassNotFoundException e) {
            // If no data is found or an error occurs, create new data
            gameStateManager = new GameStateManager();
        }
        Runtime.getRuntime().addShutdownHook(new Thread(() -> {
            try {
                SerializationUtil.saveData(gameStateManager, DATA_FILE);
                System.out.println("Data saved successfully.");
            } catch (IOException e) {
                System.out.println("Error saving data: " + e.getMessage());
            }
        }));

        primaryStage.setTitle("FINAL FANTASY RIPOFF");

        Label titleLabel = new Label("FINAL FANTASY RIPOFF");
        titleLabel.setStyle("-fx-font-size: 24px; -fx-text-fill: #333;");

        Label instructionLabel = new Label("Click on a load slot, then on the Confirm button, to load or create a new game on this slot:");
        instructionLabel.setStyle("-fx-font-size: 16px; -fx-text-fill: #666;");

        ListView<String> gameSlots = new ListView<>();
        populateGameSlots(gameSlots);

        VBox layout = getvBox(primaryStage, gameSlots, titleLabel, instructionLabel);

        Scene scene = new Scene(layout, 400, 300);
        primaryStage.setScene(scene);
        primaryStage.show();

        try {
            SerializationUtil.saveData(gameStateManager, DATA_FILE);
        } catch (IOException e) {
            System.out.println("Error saving data: " + e.getMessage());
        }
    }

    private static VBox getvBox(Stage stage, ListView<String> gameSlots, Label titleLabel, Label instructionLabel) {
        Button confirmButton = new Button("Confirm");
        confirmButton.setOnAction(event -> {
            int selectedIndex = gameSlots.getSelectionModel().getSelectedIndex();
            if (selectedIndex != -1) { // Check if a slot has been selected
                GameState selectedGameState = gameStateManager.getGameStates()[selectedIndex];
                if (selectedGameState == null) {
                    // Create a new GameState and load it
                    selectedGameState = new GameState(selectedIndex);
                    gameStateManager.setGameState(selectedIndex, selectedGameState);
                    stage.close();
                    MainMenuGUI.showCharacterCreation(selectedGameState);
                } else {
                    // Load existing GameState
                    stage.close();
                    MainMenuGUI.showMainMenu(selectedGameState);
                }
            }
        });

        VBox layout = new VBox(10, titleLabel, instructionLabel, gameSlots, confirmButton);
        layout.setAlignment(Pos.CENTER);
        layout.setStyle("-fx-padding: 20px;");
        return layout;
    }

    private void populateGameSlots(ListView<String> gameSlots) {
        gameSlots.getItems().clear(); // Clear previous items if any

        for (GameState gameState : gameStateManager.getGameStates()) {
            if (gameState != null) {
                gameSlots.getItems().add(gameState.toString()); // Add the gameState's string representation
            } else {
                gameSlots.getItems().add("Empty save slot"); // Add placeholder text for null gameStates
            }
        }
    }

}
