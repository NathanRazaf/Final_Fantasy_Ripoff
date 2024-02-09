package FF_GUI;

import FinalFantasy.Character.Player.Player;
import FinalFantasy.MainFlows.GameState;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Stage;

import static Utilities.Utility.randomIntRange;

public class InnGUI {
    private static GameState gameState;

    public static void setGameState(GameState gameState) {
        InnGUI.gameState = gameState;
    }

    public static void showInn() {
        Stage stage = new Stage();
        VBox layout = new VBox(10);
        layout.setStyle("-fx-padding: 10px;");

        Label titleLabel = new Label("Inn");
        titleLabel.setStyle("-fx-font-size: 24px; -fx-text-fill: #333;");
        layout.getChildren().add(titleLabel);


        int teamLevel = gameState.getPlayers().stream().mapToInt(Player::getLevel).sum() / gameState.getPlayers().size();
        int cost = randomIntRange(10*teamLevel, 20*teamLevel);

        Label costLabel = new Label("It will cost " + cost + " gold to rest at the inn. Do you want to rest?");
        layout.getChildren().add(costLabel);

        Button confirmButton = getButton(cost, stage);
        layout.getChildren().add(confirmButton);

        stage.setScene(new Scene(layout, 400, 150));
        stage.initModality(Modality.APPLICATION_MODAL);
        stage.showAndWait();
    }

    private static Button getButton(int cost, Stage stage) {
        Button confirmButton = new Button("Confirm");
        confirmButton.setOnAction(e -> {
            if (gameState.getInventory().getGold() >= cost) {
                gameState.getInventory().removeGold(cost);
                gameState.getPlayers().forEach(player -> player.setHp(player.getMaxHp()));
                gameState.getPlayers().forEach(player -> player.setMp(player.getMaxMp()));
                stage.close();
            } else {
                Stage errorStage = new Stage();
                VBox errorLayout = new VBox(10);
                errorLayout.setStyle("-fx-padding: 10px;");
                Label errorLabel = new Label("You don't have enough gold to rest at the inn");
                errorLayout.getChildren().add(errorLabel);
                Scene errorScene = new Scene(errorLayout, 200, 150);
                errorStage.setScene(errorScene);
                errorStage.initModality(Modality.APPLICATION_MODAL);
                errorStage.showAndWait();
            }
        });
        return confirmButton;
    }
}
