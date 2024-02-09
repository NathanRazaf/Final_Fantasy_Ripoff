package FF_GUI;

import FinalFantasy.Character.Enemy.Enemy;
import FinalFantasy.Character.Player.Player;
import FinalFantasy.Character.Player.PlayerClasses.Archer;
import FinalFantasy.Character.Player.PlayerClasses.Warrior;
import FinalFantasy.Character.Player.PlayerClasses.Wizard;
import FinalFantasy.Loot.Loot;
import FinalFantasy.MainFlows.Battle;
import FinalFantasy.MainFlows.GameState;
import FinalFantasy.MainFlows.GameStateManager;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Region;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Stage;

import static FF_GUI.CharacterDisplayerGUI.showCharacterDetails;

public class MainMenuGUI {
    public static void showCharacterCreation(GameState gameState) {
        CharacterDisplayerGUI.setGameState(gameState);
        Stage stage = new Stage();
        Label titleLabel = new Label("Character Creation");
        titleLabel.setStyle("-fx-font-size: 24px; -fx-text-fill: #333;");
        VBox formLayout = new VBox(10); // Vertical box with spacing 10
        formLayout.setAlignment(Pos.CENTER);
        formLayout.getChildren().add(titleLabel);
        formLayout.setStyle("-fx-padding: 10px;");
        // Iterate to create multiple name fields and class selectors
        for (int i = 0; i < 4; i++) {
            HBox row = new HBox(10); // Horizontal box with spacing 10

            // Name field
            TextField nameField = new TextField();
            nameField.setPromptText("Character Name");

            // Class selector
            ComboBox<String> classSelector = new ComboBox<>();
            classSelector.getItems().addAll("Warrior", "Archer", "Wizard");

            row.getChildren().addAll(new Label("Character " + (i + 1) + ":"), nameField, classSelector);
            formLayout.getChildren().add(row);
        }

        // Submit button
        Button submitButton = getButton(gameState, formLayout, stage);
        formLayout.getChildren().add(submitButton);

        Scene scene = new Scene(formLayout, 400, 300);
        stage.setScene(scene);
        stage.show();
    }

    private static Button getButton(GameState gameState, VBox formLayout, Stage stage) {
        Button submitButton = new Button("Create Characters");
        submitButton.setOnAction(event -> {
            boolean isValid = true;
            for (Node child : formLayout.getChildren()) {
                if (child instanceof HBox row) {
                    TextField nameField = (TextField) row.getChildren().get(1);
                    ComboBox<String> classSelector = (ComboBox<String>) row.getChildren().get(2);
                    String name = nameField.getText();
                    String className = classSelector.getValue();
                    if (name.isEmpty() || className == null) {
                        isValid = false;
                        break;
                    }
                }
            }

            // If the form is not valid, show the alert
            if (!isValid) {
                Stage alertStage = new Stage();
                VBox alertLayout = new VBox(10);
                alertLayout.setAlignment(Pos.CENTER);
                alertLayout.setStyle("-fx-padding: 10px;");
                Label alertLabel = new Label("Please fill in all fields");
                Button closeButton = new Button("Close");
                closeButton.setOnAction(e -> alertStage.close());
                alertLayout.getChildren().addAll(alertLabel, closeButton);
                alertStage.setScene(new Scene(alertLayout, 200, 100));
                alertStage.initModality(Modality.APPLICATION_MODAL);
                alertStage.showAndWait();
                return; // Stop further processing
            }
            // Handle form submission here
            for (Node child : formLayout.getChildren()) {
                if (child instanceof HBox row) {
                    TextField nameField = (TextField) row.getChildren().get(1);
                    ComboBox<String> classSelector = (ComboBox<String>) row.getChildren().get(2);
                    String name = nameField.getText();
                    String className = classSelector.getValue();

                    // Create character here
                    switch (className) {
                        case "Warrior":
                            gameState.getPlayers().add(new Warrior(name));
                            break;
                        case "Archer":
                            gameState.getPlayers().add(new Archer(name));
                            break;
                        case "Wizard":
                            gameState.getPlayers().add(new Wizard(name));
                            break;
                    }
                }
            }


            // Close the window
            stage.close();

            // Enter the main menu
            showMainMenu(gameState);
        });
        return submitButton;
    }


    public static void showMainMenu(GameState gameState) {
        Stage stage = new Stage();
        VBox layout = new VBox(10);

        layout.setAlignment(Pos.CENTER);
        layout.setStyle("-fx-padding: 10px;");

        // Display gold quantity
        Label goldLabel = new Label("Gold: " + gameState.getInventory().getGold());
        layout.getChildren().add(goldLabel);

        ListView<Player> partyListView = new ListView<>();
        partyListView.getItems().addAll(gameState.getPlayers());

        // Set a custom cell factory to display the player details as text
        partyListView.setCellFactory(lv -> new ListCell<Player>() {
            @Override
            protected void updateItem(Player player, boolean empty) {
                super.updateItem(player, empty);
                if (empty || player == null) {
                    setText(null);
                } else {
                    setText(player.smallToString());
                }
            }
        });

        // Event handler for the entire ListView
        partyListView.setOnMouseClicked(event -> {
            Player selectedPlayer = partyListView.getSelectionModel().getSelectedItem();
            if (selectedPlayer != null) {
                showCharacterDetails(selectedPlayer);
            }
        });
        layout.getChildren().add(partyListView);

        layout.getChildren().add(new Region());

        // List of decisions
        ListView<Button> decisionListView = new ListView<>();
        Button shopButton = new Button("Shop");
        shopButton.setOnAction(event -> {
            ShopGUI.setGameState(gameState);
            ShopGUI.showShop();
        });
        Button inventoryButton = new Button("Inventory");
        inventoryButton.setOnAction(event -> {
            InventoryGUI.setGameState(gameState);
            InventoryGUI.showInventory();
        });

        Button battleButton = new Button("Battle");
        battleButton.setOnAction(event -> {
            Battle battle = new Battle(gameState.getPlayers());
            if (BattleGUI.showBattle(battle)) {
                // Victory
                rewardBattle(gameState, battle);
                actualizeView(gameState, goldLabel, partyListView);
            } else {
                // Defeat
                Stage defeatStage = new Stage();
                VBox defeatLayout = new VBox(10);
                defeatLayout.setAlignment(Pos.CENTER);
                defeatLayout.setStyle("-fx-padding: 10px;");
                Label defeatLabel = new Label("You have been defeated.");
                Label gameOverLabel = new Label("Your save file will be deleted.");
                defeatLayout.getChildren().addAll(defeatLabel, gameOverLabel);
                Button closeButton = new Button("Close");
                closeButton.setOnAction(e -> {
                    defeatStage.close();
                    GameStateManager.getInstance().deleteGameState(gameState.getIndex());
                    System.exit(0);
                });
                defeatLayout.getChildren().add(closeButton);
                defeatStage.setScene(new Scene(defeatLayout, 200, 100));
                defeatStage.initModality(Modality.APPLICATION_MODAL);
                defeatStage.setOnCloseRequest(e -> {
                    GameStateManager.getInstance().deleteGameState(gameState.getIndex());
                    System.exit(0);
                });
                defeatStage.showAndWait();
            }
        });
        Button innButton = new Button("Inn");
        innButton.setOnAction(event -> {
            InnGUI.setGameState(gameState);
            InnGUI.showInn();
            actualizeView(gameState, goldLabel, partyListView);
        });
        Button quitButton = new Button("Quit");
        quitButton.setOnAction(event -> {
            System.exit(0);
        });

        decisionListView.getItems().addAll(shopButton, inventoryButton, battleButton, innButton, quitButton);
        layout.getChildren().add(decisionListView);

        Scene mainMenuScene = new Scene(layout, 400, 550);
        stage.setScene(mainMenuScene);
        stage.setTitle("Main Menu");
        stage.show();
    }

    private static void rewardBattle(GameState gameState, Battle battle) {
        int totalXpGiven = 0;
        for (Enemy enemy : battle.getEnemies()) {
            for (Loot loot : enemy.getLootGiven()) {
                gameState.getInventory().addLoot(loot);
            }
            totalXpGiven += enemy.getExpGiven();
            gameState.getInventory().addGold(enemy.getGoldGiven());
            System.out.println("You got " + enemy.getGoldGiven() + " gold from " + enemy.getName());
        }
        for (Player player : battle.getPlayers()) {
            player.addExp(totalXpGiven/gameState.getPlayers().size());
        }
    }

    private static void actualizeView(GameState gameState, Label goldLabel, ListView<Player> partyListView) {
        partyListView.getItems().clear();
        partyListView.getItems().addAll(gameState.getPlayers());
        goldLabel.setText("Gold: " + gameState.getInventory().getGold());
    }
}
