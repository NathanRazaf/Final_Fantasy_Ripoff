package FF_GUI;

import FinalFantasy.Loot.Loot;
import FinalFantasy.MainFlows.GameState;
import FinalFantasy.MainFlows.Shop;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.Spinner;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.util.HashMap;
import java.util.Map;

public class ShopGUI {
    private static GameState gameState;
    private static Shop shop;

    public static void setGameState(GameState gameState) {
        ShopGUI.gameState = gameState;
    }
    public static void showShop() {
        if (shop == null) shop = new Shop(gameState);
        Stage stage = new Stage();
        VBox layout = new VBox(10);
        layout.setAlignment(Pos.CENTER);
        layout.setStyle("-fx-padding: 10px;");

        Label titleLabel = new Label("Shop");
        titleLabel.setStyle("-fx-font-size: 24px; -fx-text-fill: #333;");
        layout.getChildren().add(titleLabel);

        Button buyButton = new Button("Buy");
        buyButton.setOnAction(e -> showBuyShop());
        Button sellButton = new Button("Sell");
        sellButton.setOnAction(e -> showSellShop());
        Button actualizeButton = new Button("Actualize");
        actualizeButton.setOnAction(e -> shop = new Shop(gameState));

        layout.getChildren().addAll(buyButton, sellButton, actualizeButton);

        stage.setScene(new Scene(layout, 200, 170));
        stage.initModality(Modality.APPLICATION_MODAL);
        stage.showAndWait();
    }

    public static void showBuyShop() {
        Stage stage = new Stage();
        VBox layout = new VBox(10);
        layout.setAlignment(Pos.CENTER);
        layout.setStyle("-fx-padding: 10px;");

        Label titleLabel = new Label("Buy");
        titleLabel.setStyle("-fx-font-size: 24px; -fx-text-fill: #333;");
        layout.getChildren().add(titleLabel);

        Label goldLabel = new Label("Gold: " + gameState.getInventory().getGold());
        layout.getChildren().add(goldLabel);

        ListView<String> shopItems = new ListView<>();
        for (Map.Entry<Loot, Integer> entry : shop.getShopInventory().entrySet()) {
            shopItems.getItems().add(entry.getKey().getName() + " - " + entry.getValue());
        }

        shopItems.setOnMouseClicked(e -> {
            String selectedItem = shopItems.getSelectionModel().getSelectedItem();
            Loot loot = null;
            for (Map.Entry<Loot, Integer> entry : shop.getShopInventory().entrySet()) {
                if (entry.getKey().getName().equals(selectedItem.split(" - ")[0])) {
                    loot = entry.getKey();
                    break;
                }
            }
            if (loot == null) return;
            showBuyShopDetails(loot, shop.getShopInventory().get(loot));
            actualizeListView(shopItems, shop.getShopInventory());
            goldLabel.setText("Gold: " + gameState.getInventory().getGold());
        });

        layout.getChildren().add(shopItems);

        stage.setScene(new Scene(layout, 300, 600));
        stage.initModality(Modality.APPLICATION_MODAL);
        stage.showAndWait();
    }

    private static void showBuyShopDetails(Loot loot, int maxQty) {
        Stage stage = new Stage();
        VBox layout = new VBox(10);
        layout.setAlignment(Pos.CENTER);
        layout.setStyle("-fx-padding: 10px;");

        Label titleLabel = new Label("Buy " + loot.getName() + " ?");
        titleLabel.setStyle("-fx-font-size: 24px; -fx-text-fill: #333;");
        layout.getChildren().add(titleLabel);

        Label goldLabel = new Label("Gold: " + gameState.getInventory().getGold());
        layout.getChildren().add(goldLabel);

        Label lootLabel = new Label(loot.toString());
        layout.getChildren().add(lootLabel);

        // Ajout d'un Spinner pour sélectionner la quantité
        Spinner<Integer> quantitySpinner = new Spinner<>(1, maxQty, 1);
        layout.getChildren().add(quantitySpinner);

        Button buyButton = new Button("Buy");
        buyButton.setOnAction(e -> {
            int quantity = quantitySpinner.getValue();
            if (quantity > maxQty) {
                quantity = maxQty;
            }
            if (quantity * loot.getValue() > gameState.getInventory().getGold()) {
                canNotBuy(quantity);
                return;
            }
            gameState.getInventory().addLoot(loot, quantity);
            gameState.getInventory().removeGold(loot.getValue() * quantity);
            shop.removeSomeLoot(loot, quantity);
            stage.close();
        });

        layout.getChildren().add(buyButton);

        stage.setScene(new Scene(layout, 450, 450));
        stage.initModality(Modality.APPLICATION_MODAL);
        stage.showAndWait();
    }


    private static void showSellShop() {
        Stage stage = new Stage();
        VBox layout = new VBox(10);
        layout.setAlignment(Pos.CENTER);
        layout.setStyle("-fx-padding: 10px;");

        Label titleLabel = new Label("Sell");
        titleLabel.setStyle("-fx-font-size: 24px; -fx-text-fill: #333;");
        layout.getChildren().add(titleLabel);

        Label goldLabel = new Label("Gold: " + gameState.getInventory().getGold());
        layout.getChildren().add(goldLabel);

        ListView<String> inventoryItems = new ListView<>();
        for (Loot loot : gameState.getInventory().getInventory().keySet()) {
            inventoryItems.getItems().add(loot.getName() + " - " + gameState.getInventory().getInventory().get(loot) + " (" + loot.getValue() + " gold/unit)");
        }

        inventoryItems.setOnMouseClicked(e -> {
            String selectedItem = inventoryItems.getSelectionModel().getSelectedItem();
            Loot loot = null;
            for (Map.Entry<Loot, Integer> entry : gameState.getInventory().getInventory().entrySet()) {
                if (entry.getKey().getName().equals(selectedItem.split(" - ")[0])) {
                    loot = entry.getKey();
                    break;
                }
            }
            showSellShopDetails(loot, gameState.getInventory().getInventory().get(loot));
            actualizeListView(inventoryItems, gameState.getInventory().getInventory());
            goldLabel.setText("Gold: " + gameState.getInventory().getGold());
        });

        layout.getChildren().add(inventoryItems);

        stage.setScene(new Scene(layout, 300, 600));
        stage.initModality(Modality.APPLICATION_MODAL);
        stage.showAndWait();
    }

    private static void showSellShopDetails(Loot loot, int maxQty) {
        Stage stage = new Stage();
        VBox layout = new VBox(10);
        layout.setAlignment(Pos.CENTER);
        layout.setStyle("-fx-padding: 10px;");

        Label titleLabel = new Label("Sell " + loot.getName() + " ?");
        titleLabel.setStyle("-fx-font-size: 24px; -fx-text-fill: #333;");
        layout.getChildren().add(titleLabel);

        Label goldLabel = new Label("Gold: " + gameState.getInventory().getGold());
        layout.getChildren().add(goldLabel);

        Label lootLabel = new Label(loot.toString());
        layout.getChildren().add(lootLabel);

        // Ajout d'un Spinner pour sélectionner la quantité
        Spinner<Integer> quantitySpinner = new Spinner<>(1, maxQty, 1);
        layout.getChildren().add(quantitySpinner);

        Button buyButton = new Button("Sell");
        buyButton.setOnAction(e -> {
            int quantity = quantitySpinner.getValue();
            if (quantity > maxQty) {
                quantity = maxQty;
            }
            gameState.getInventory().removeLoot(loot, quantity);
            gameState.getInventory().addGold(loot.getValue() * quantity);
            stage.close();
        });

        layout.getChildren().add(buyButton);

        stage.setScene(new Scene(layout, 450, 450));
        stage.initModality(Modality.APPLICATION_MODAL);
        stage.showAndWait();
    }

    private static void canNotBuy(int qty) {
        Stage stage = new Stage();
        VBox layout = new VBox(10);
        layout.setAlignment(Pos.CENTER);
        layout.setStyle("-fx-padding: 10px;");

        Label titleLabel = new Label("You can't buy " + qty + " of this item");
        layout.getChildren().add(titleLabel);

        Button closeButton = new Button("Close");
        closeButton.setOnAction(e -> {
            stage.close();
        });
        layout.getChildren().add(closeButton);

        stage.setScene(new Scene(layout, 300, 150));
        stage.initModality(Modality.APPLICATION_MODAL);
        stage.showAndWait();
    }

    private static void actualizeListView(ListView<String> listView, HashMap<Loot, Integer> inventory) {
        listView.getItems().clear();
        for (Map.Entry<Loot, Integer> entry : inventory.entrySet()) {
            listView.getItems().add(entry.getKey().getName() + " - " + entry.getValue());
        }
    }
}
