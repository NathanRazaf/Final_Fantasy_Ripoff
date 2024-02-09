package FF_GUI;

import FinalFantasy.Actions.Action;
import FinalFantasy.Character.Character;
import FinalFantasy.Character.Enemy.Enemy;
import FinalFantasy.Character.Player.Player;
import FinalFantasy.MainFlows.Battle;
import javafx.animation.PauseTransition;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.util.Duration;

import java.util.Iterator;

public class BattleGUI {
    private static boolean canQuit = false;
    private static Action selectedAction = null;
    private static Character selectedTarget = null;
    private static Iterator<Character> turnIterator;
    private static Battle battle;
    private static Scene scene;
    private static ListView<String> moveHistoryList;
    private static ListView<String> actionsAvailableList;
    public static boolean showBattle(Battle battle) {
        Stage stage = new Stage();
        VBox layout = new VBox(10);
        layout.setStyle("-fx-padding: 10px;");
        HBox characterLayout = new HBox(10);
        VBox playerLayout = new VBox(8);
        VBox enemyLayout = new VBox(8);

        ListView<String> playerList = new ListView<>();
        playerList.setId("playerList");
        playerList.setPrefHeight(800);
        ListView<String> enemyList = new ListView<>();
        enemyList.setId("enemyList");
        enemyList.setPrefHeight(800);

        updateCharacterStates(battle, playerList, enemyList);

        playerLayout.getChildren().add(playerList);
        enemyLayout.getChildren().add(enemyList);
        characterLayout.getChildren().addAll(playerLayout, enemyLayout);

        VBox.setVgrow(characterLayout, Priority.ALWAYS); // This line makes characterLayout grow

        VBox bottomLayout = new VBox(8);
        moveHistoryList = new ListView<>();
        moveHistoryList.setId("moveHistoryList");
        bottomLayout.getChildren().add(moveHistoryList);
        actionsAvailableList = new ListView<>();
        actionsAvailableList.setId("actionsAvailableList");
        bottomLayout.getChildren().add(actionsAvailableList);

        Button confirmButton = new Button("Confirm move");
        confirmButton.setId("confirmButton");

        layout.getChildren().addAll(characterLayout, bottomLayout, confirmButton);

        Scene scene = new Scene(layout, 800, 600);
        stage.setScene(scene);
        stage.setTitle("Battle");
        BattleGUI.battle = battle;
        BattleGUI.scene = scene;
        BattleGUI.moveHistoryList = (ListView<String>) scene.lookup("#moveHistoryList");
        BattleGUI.actionsAvailableList = (ListView<String>) scene.lookup("#actionsAvailableList");

        battle.orderTurns();
        turnIterator = battle.getTurnOrder().iterator();
        proceedToNextTurn();

        stage.initModality(Modality.APPLICATION_MODAL);
        stage.setOnCloseRequest(event -> {
            if (!canQuit) {
                event.consume(); // Empêche la fermeture de la fenêtre
            }
        });
        stage.showAndWait();
        return battle.isVictory();
    }


    private static void updateCharacterStates(Battle battle, ListView<String> playerList, ListView<String> enemyList) {
        playerList.getItems().clear();
        enemyList.getItems().clear();
        for (Player player : battle.getPlayers()) {
            playerList.getItems().add(player.smallToString());
        }
        for (Enemy enemy : battle.getEnemies()) {
            String enemyString = enemy.toString();
            enemyList.getItems().add(enemyString);
            // Add click event to each enemy
            enemyList.setOnMouseClicked(event -> {
                String selectedEnemyString = enemyList.getSelectionModel().getSelectedItem();
                // Find the enemy object corresponding to the clicked string
                for (Enemy e : battle.getEnemies()) {
                    if (e.toString().equals(selectedEnemyString)) {
                        selectedTarget = e;
                        break;
                    }
                }
            });
        }
    }


    private static void proceedToNextTurn() {
        if (!turnIterator.hasNext()) {
            battle.orderTurns(); // Reorder turns if all turns are done
            turnIterator = battle.getTurnOrder().iterator(); // Restart the iterator if all turns are done
        }

        if (battle.isBattleOver()) {
            // Close the stage or handle battle end
            updateCharacterStates(battle, (ListView<String>) scene.lookup("#playerList"), (ListView<String>) scene.lookup("#enemyList"));
            addToMoveHistory("Battle is over. You can now close the window.");
            canQuit = true;
            return;
        }

        // Pause for a moment before proceeding to the next turn
        PauseTransition pause = new PauseTransition(Duration.seconds(1));
        pause.setOnFinished(event -> {
            Character character = turnIterator.next();
            handleTurn(character);
        });
        pause.play();
    }

    private static void handleTurn(Character character) {
        // ... [Handle Character's Turn as before, excluding the loop and wait logic] ...
        updateCharacterStates(battle, (ListView<String>) scene.lookup("#playerList"), (ListView<String>) scene.lookup("#enemyList"));


        addToMoveHistory(character.getName() + "'s turn");

        if (character.getHp() == 0) {
            addToMoveHistory(character.getName() + " is dead");
            if (character instanceof Player player) {
                battle.getPlayers().remove(player);
                addToMoveHistory(player.getName() + " has been removed from the battle and will never come back. (yes there's no revives in this game)");
            }
            proceedToNextTurn();
            return;
        }
        selectedTarget = null; // Reset selected target
        selectedAction = null; // Reset selected action

        if (character instanceof Player player) {
            setupPlayerActions(player);
        } else if (character instanceof Enemy enemy) {
            battle.makeEnemyAction(enemy);
            String action;
            if (enemy.getLastTarget() == null) {
                action = enemy.getName() + " missed his attack";
            } else {
                action = enemy.getName() + " used " + enemy.getLastMoveUsed().getName() + " on " + enemy.getLastTarget().getName() + " and dealt " + enemy.getLastDamageDealt() + " damage";
            }
            addToMoveHistory(action);
            proceedToNextTurn(); // Proceed to next turn after enemy action
        }
    }

    private static void setupPlayerActions(Player player) {
        // Setup available actions for player
        actionsAvailableList.getItems().clear();
        for (Action action : player.getActions()) {
            String actionString = action.smallToString();
            actionsAvailableList.getItems().add(actionString);
            // Add click event to each action
            actionsAvailableList.setOnMouseClicked(event -> {
                String selectedActionString = actionsAvailableList.getSelectionModel().getSelectedItem();
                // Find the action object corresponding to the clicked string
                for (Action a : player.getActions()) {
                    if (a.smallToString().equals(selectedActionString)) {
                        selectedAction = a;
                        break;
                    }
                }
            });
        }

        Button confirmButton = (Button) scene.lookup("#confirmButton");
        confirmButton.setOnAction(event -> {
            // Check if an action and target are selected
            if (selectedAction != null && selectedTarget != null) {
                if (player.getMp() < selectedAction.getMpCost()) {
                    addToMoveHistory("Not enough MP to use " + selectedAction.getName());
                } else if (selectedTarget.getHp() == 0) {
                    addToMoveHistory(selectedTarget.getName() + " is already dead");
                }
                else {
                    int currHp = selectedTarget.getHp();
                    player.doAction(selectedAction, selectedTarget);
                    String action = player.getName() + " used " + selectedAction.getName() + " on " + selectedTarget.getName();
                    addToMoveHistory(action);
                    if (selectedTarget.getHp() == 0) {
                        addToMoveHistory(selectedTarget.getName() + " died");
                    }
                    else if (selectedTarget.getHp() < currHp) {
                        addToMoveHistory(selectedTarget.getName() + " took " + (currHp - selectedTarget.getHp()) + " damage");
                    }
                    else if (selectedTarget.getHp() > currHp) {
                        addToMoveHistory(selectedTarget.getName() + " recovered " + (selectedTarget.getHp() - currHp) + " HP");
                    } else {
                        addToMoveHistory("The action missed its target");
                    }
                    // Log action, clear lists, and proceed to next turn
                    proceedToNextTurn();
                }
            }
            else {
                Stage stage = new Stage();
                VBox layout = new VBox(10);
                layout.getChildren().add(new Label("Please select an action and a target."));
                Scene scene = new Scene(layout, 400, 300);
                stage.setScene(scene);
                stage.setTitle("Error");
                stage.initModality(Modality.APPLICATION_MODAL);
                stage.showAndWait();
            }
        });
    }

    private static void addToMoveHistory(String message) {
        moveHistoryList.getItems().add(message);
        moveHistoryList.scrollTo(moveHistoryList.getItems().size() - 1); // Défile vers le dernier élément
    }
}
