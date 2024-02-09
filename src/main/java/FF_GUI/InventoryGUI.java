package FF_GUI;

import FinalFantasy.Character.Player.Player;
import FinalFantasy.Enums.CharacterClass;
import FinalFantasy.Loot.Equipment.Armors.Armor;
import FinalFantasy.Loot.Equipment.Weapons.Weapon;
import FinalFantasy.Loot.Loot;
import FinalFantasy.MainFlows.GameState;
import FinalFantasy.MainFlows.Inventory;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Stage;

public class InventoryGUI {
    private static GameState gameState;
    private static Inventory inventory;

    public static void setGameState(GameState gameState) {
        InventoryGUI.gameState = gameState;
    }

    public static void showInventory() {
        inventory = gameState.getInventory();
        Stage stage = new Stage();
        VBox layout = new VBox(10);
        layout.setAlignment(Pos.CENTER);
        layout.setStyle("-fx-padding: 10px;");
        ListView<String> listView = new ListView<>();
        for (Loot item : inventory.getInventory().keySet()) {
            listView.getItems().add(item.getName() + " x" + inventory.getInventory().get(item));
        }
        layout.getChildren().add(listView);

        Button equipButton = new Button("Equip");
        equipButton.setOnAction(e -> {
            equip();
            listView.getItems().clear();
            for (Loot item : inventory.getInventory().keySet()) {
                listView.getItems().add(item.getName() + " x" + inventory.getInventory().get(item));
            }
        });
        layout.getChildren().add(equipButton);

        Scene scene = new Scene(layout, 400, 300);
        stage.setScene(scene);
        stage.initModality(Modality.APPLICATION_MODAL);
        stage.showAndWait();
    }

    private static void equip() {
        Stage stage = new Stage();
        VBox layout = new VBox(10);
        layout.setAlignment(Pos.CENTER);
        layout.setStyle("-fx-padding: 10px;");

        Label instructionLabel = new Label("Select a character to equip the item to:");
        layout.getChildren().add(instructionLabel);

        ListView<String> characterView = new ListView<>();
        for (Player player : gameState.getInventory().getPlayers()) {
            characterView.getItems().add(player.getName() + " (" + player.getCharacterClass() + ")");
        }
        layout.getChildren().add(characterView);

        characterView.setOnMouseClicked(e -> {
            Player player = gameState.getInventory().getPlayers().get(characterView.getSelectionModel().getSelectedIndex());
            equipCharacter(player);
        });

        Scene scene = new Scene(layout, 400, 300);
        stage.setScene(scene);
        stage.initModality(Modality.APPLICATION_MODAL);
        stage.showAndWait();
    }

    private static void equipCharacter(Player player) {
        Stage stage = new Stage();
        VBox layout = new VBox(10);
        layout.setAlignment(Pos.CENTER);
        layout.setStyle("-fx-padding: 10px;");

        Label instructionLabel = new Label("Select an item to equip:");
        layout.getChildren().add(instructionLabel);

        ListView<String> itemListView = new ListView<>();
        for (Loot item : inventory.getInventory().keySet()) {
            if (item instanceof Armor armor && armor.getCharacterClass().equals(player.getCharacterClass())) {
                itemListView.getItems().add(item.getName() + " x" + inventory.getInventory().get(item));
            }
            if (item instanceof Weapon weapon) {
                for (CharacterClass characterClass : weapon.getCharacterClasses()) {
                    if (characterClass.equals(player.getCharacterClass())) {
                        itemListView.getItems().add(item.getName() + " x" + inventory.getInventory().get(item));
                    }
                }
            }
        }
        layout.getChildren().add(itemListView);

        itemListView.setOnMouseClicked(e -> {
            String selectedItem = itemListView.getSelectionModel().getSelectedItem();
            Loot loot = null;
            for (Loot item : inventory.getInventory().keySet()) {
                if (item.getName().equals(selectedItem.split(" x")[0])) {
                    loot = item;
                    break;
                }
            }
            assert loot != null;
            confirmEquip(player, loot);
        });

        Scene scene = new Scene(layout, 400, 300);
        stage.setScene(scene);
        stage.initModality(Modality.APPLICATION_MODAL);
        stage.showAndWait();
    }

    private static void confirmEquip(Player player, Loot loot) {
        Stage stage = new Stage();
        VBox layout = new VBox(10);
        layout.setAlignment(Pos.CENTER);
        layout.setStyle("-fx-padding: 10px;");

        Label instructionLabel = new Label("Equip " + loot.getName() + " to " + player.getName() + "?");
        layout.getChildren().add(instructionLabel);

        Label statsLabel = new Label(loot.toString());
        layout.getChildren().add(statsLabel);

        Button confirmButton = new Button("Confirm");
        confirmButton.setOnAction(e -> {
            if (player.equip(loot)) {
                inventory.removeLoot(loot);
                stage.close();
            } else {
                Label errorLabel = new Label("Unable to equip item");
                layout.getChildren().add(errorLabel);
            }
        });
        layout.getChildren().add(confirmButton);

        Scene scene = new Scene(layout, 400, 300);
        stage.setScene(scene);
        stage.initModality(Modality.APPLICATION_MODAL);
        stage.showAndWait();
    }
}
