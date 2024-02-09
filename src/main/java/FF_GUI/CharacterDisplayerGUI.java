package FF_GUI;

import FinalFantasy.Actions.Action;
import FinalFantasy.Character.Player.Player;
import FinalFantasy.Loot.Loot;
import FinalFantasy.MainFlows.GameState;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Region;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Stage;

public class CharacterDisplayerGUI {
    private static GameState gameState;
    static String selectedStyle = "-fx-background-color: #f0f8ff; -fx-border-color: #b0c4de; -fx-padding: 10px;";

    public static void setGameState(GameState gameState) {
        CharacterDisplayerGUI.gameState = gameState;
    }
    public static void showCharacterDetails(Player player) {
        Stage detailStage = new Stage();
        VBox leftLayout = new VBox(10);
        VBox rightLayout = new VBox(10);

        leftLayout.setAlignment(Pos.CENTER_LEFT);
        rightLayout.setAlignment(Pos.CENTER_RIGHT);
        leftLayout.setStyle("-fx-padding: 10px;");
        rightLayout.setStyle("-fx-padding: 10px;");

        // Detailed information about the player
        Label nameLabel = new Label("Name: " + player.getName());
        Label classLabel = new Label("Class: " + player.getCharacterClass().toString());
        // Add more labels for other stats like HP, MP, equipment, etc.
        Label hpLabel = new Label("HP: " + player.getHp() + "/" + player.getMaxHp());
        Label mpLabel = new Label("MP: " + player.getMp() + "/" + player.getMaxMp());
        Label attackLabel = new Label("Attack: " + player.getAtk());
        Label defenseLabel = new Label("Defense: " + player.getDef());
        Label crtLabel = new Label("Critical: " + player.getCrt());
        Label spdLabel = new Label("Speed: " + player.getSpd());
        leftLayout.getChildren().addAll(nameLabel, classLabel,
                hpLabel, mpLabel, attackLabel, defenseLabel, crtLabel, spdLabel); // Add more labels as needed

        // Equipment
        VBox top = new VBox(15); // Increased spacing
        HBox mid = new HBox(20); // Increased spacing for weapons
        VBox bot = new VBox(15); // Increased spacing
        top.setAlignment(Pos.CENTER);
        bot.setAlignment(Pos.CENTER);
        mid.setAlignment(Pos.CENTER);
        Label helmetLabel = player.getHelmet() == null ? new Label("No helmet")
                : new Label("Helmet: " + player.getHelmet().smallToString());
        Label chestPlateLabel = player.getChestPlate() == null ? new Label("No chest plate")
                : new Label("Armor: " + player.getChestPlate().smallToString());
        top.getChildren().addAll(helmetLabel, chestPlateLabel);
        Label leftWeaponLabel = player.getWeapons()[0] == null ? new Label("No left weapon")
                : new Label("Left Weapon: " + player.getWeapons()[0].smallToString());
        Label rightWeaponLabel = player.getWeapons()[1] == null ? new Label("No right weapon")
                : new Label("Right Weapon: " + player.getWeapons()[1].smallToString());
        mid.getChildren().addAll(leftWeaponLabel, new Region(), rightWeaponLabel); // Add Region as a spacer
        Label leggingsLabel = player.getLeggings() == null ? new Label("No leggings")
                : new Label("Leggings: " + player.getLeggings().smallToString());
        Label bootsLabel = player.getBoots() == null ? new Label("No boots")
                : new Label("Boots: " + player.getBoots().smallToString());
        bot.getChildren().addAll(leggingsLabel, bootsLabel);

        // Check if the player has a helmet and set the event handler
        if (player.getHelmet() != null) {
            helmetLabel.setOnMouseClicked(event -> {
                showEquipmentDetails(player, player.getHelmet(), helmetLabel);
                resetOtherLabelsStyle(chestPlateLabel, leftWeaponLabel, rightWeaponLabel, leggingsLabel, bootsLabel);
            });
        }
        // Check if the player has a chest plate and set the event handler
        if (player.getChestPlate() != null) {
            chestPlateLabel.setOnMouseClicked(event -> {
                showEquipmentDetails(player, player.getChestPlate(), chestPlateLabel);
                resetOtherLabelsStyle(helmetLabel, leftWeaponLabel, rightWeaponLabel, leggingsLabel, bootsLabel);
            });
        }

        // Check if the player has a left weapon and set the event handler
        if (player.getWeapons()[0] != null) {
            leftWeaponLabel.setOnMouseClicked(event -> {
                showEquipmentDetails(player, player.getWeapons()[0], leftWeaponLabel);
                resetOtherLabelsStyle(helmetLabel, chestPlateLabel, rightWeaponLabel, leggingsLabel, bootsLabel);
            });
        }

        // Check if the player has a right weapon and set the event handler
        if (player.getWeapons()[1] != null) {
            rightWeaponLabel.setOnMouseClicked(event -> {
                showEquipmentDetails(player, player.getWeapons()[1], rightWeaponLabel);
                resetOtherLabelsStyle(helmetLabel, chestPlateLabel, leftWeaponLabel, leggingsLabel, bootsLabel);
            });
        }

        // Check if the player has leggings and set the event handler
        if (player.getLeggings() != null) {
            leggingsLabel.setOnMouseClicked(event -> {
                showEquipmentDetails(player, player.getLeggings(), leggingsLabel);
                resetOtherLabelsStyle(helmetLabel, chestPlateLabel, leftWeaponLabel, rightWeaponLabel, bootsLabel);
            });
        }

        // Check if the player has boots and set the event handler
        if (player.getBoots() != null) {
            bootsLabel.setOnMouseClicked(event -> {
                showEquipmentDetails(player, player.getBoots(), bootsLabel);
                resetOtherLabelsStyle(helmetLabel, chestPlateLabel, leftWeaponLabel, rightWeaponLabel, leggingsLabel);
            });
        }

        top.setStyle("-fx-padding: 10;");
        mid.setStyle("-fx-padding: 10;");
        bot.setStyle("-fx-padding: 10;");

        rightLayout.getChildren().addAll(top, mid, bot);

        Button actionsButton = new Button("Show actions");
        actionsButton.setOnAction(event -> {
            showActions(player);
        });
        rightLayout.getChildren().add(actionsButton);

        HBox layout = new HBox(10, leftLayout, rightLayout);
        layout.setAlignment(Pos.CENTER);
        layout.setStyle("-fx-padding: 10px;");
        Scene detailScene = new Scene(layout, 800, 500);
        detailStage.setScene(detailScene);
        detailStage.setTitle("Character Details");
        detailStage.initModality(Modality.APPLICATION_MODAL); // Make this window modal
        detailStage.showAndWait(); // Show and wait for it to be closed
    }

    private static void showEquipmentDetails(Player player, Loot equipment, Label label) {
        label.setStyle(selectedStyle);
        Stage equipmentStage = new Stage();
        VBox equipmentLayout = new VBox(10);
        equipmentLayout.setAlignment(Pos.CENTER);
        equipmentLayout.setStyle("-fx-padding: 10px;");
        Label detailsLabel = new Label(equipment.toString());
        equipmentLayout.getChildren().add(detailsLabel);
        Button unequipButton = new Button("Unequip");
        unequipButton.setOnAction(event -> {
            player.unEquip(equipment);
            gameState.getInventory().addLoot(equipment);
            equipmentStage.close();
        });
        equipmentLayout.getChildren().add(unequipButton);
        Scene equipmentScene = new Scene(equipmentLayout, 300, 200);
        equipmentStage.setScene(equipmentScene);
        equipmentStage.setTitle(equipment.getName());
        equipmentStage.initModality(Modality.APPLICATION_MODAL);
        equipmentStage.showAndWait();
    }

    private static void showActions(Player player) {
        Stage actionsStage = new Stage();
        VBox actionsLayout = new VBox(10);
        actionsLayout.setAlignment(Pos.CENTER);
        actionsLayout.setStyle("-fx-padding: 10px;");
        Label actionsLabel = new Label("Actions of " + player.getName());
        actionsLayout.getChildren().add(actionsLabel);

        ListView<String> actionsListView = new ListView<>();
        for (Action action : player.getActions()) {
            actionsListView.getItems().add(action.toStringNoColor());
        }

        actionsLayout.getChildren().add(actionsListView);
        Scene actionsScene = new Scene(actionsLayout, 500, 400);
        actionsStage.setScene(actionsScene);
        actionsStage.setTitle("Actions");
        actionsStage.initModality(Modality.APPLICATION_MODAL);
        actionsStage.showAndWait();
    }
    private static void resetOtherLabelsStyle(Label... labels) {
        for (Label lbl : labels) {
            lbl.setStyle(""); // Reset to default style
        }
    }
}
