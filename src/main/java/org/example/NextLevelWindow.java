package org.example;

import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;

public class NextLevelWindow extends Application {
//    public static void main(String[] args) {
//        launch(args);
//    }
    private App app;
    public NextLevelWindow(App app){
        this.app = app;
    }
    @Override
    public void start(Stage primaryStage) {

        GridPane root = getGridPane();

        Label label = new Label(" You've reached next level! ");
        Button startButton = new Button(" Start! ");

        addElementsToRoot(root, label, startButton);
        root.setAlignment(Pos.CENTER);
        setScene(primaryStage, root);
        setStartButton(startButton);
    }
    private static void setScene(Stage primaryStage, GridPane root) {
        Scene scene = new Scene(root, 40, 52);
        primaryStage.setScene(scene);
        primaryStage.setTitle(" Start window ");
        primaryStage.show();
    }
    private static void addElementsToRoot(GridPane root, Label label, Button startButton){
        root.add(label, 0, 0);
        root.add(startButton, 0, 1);
    }
    private static GridPane getGridPane() {
        GridPane root = new GridPane();
        root.setPadding(new Insets(10));
        root.setVgap(8);
        root.setHgap(10);
        return root;
    }
    private void setStartButton(Button startButton){
        startButton.setOnAction(event -> {
            startSimulation();
        });
    }
    private void startSimulation() {
        int amountOfPrevCoins = this.app.getGamerAmountOfCoins();
        int prevLevel = this.app.getNumberOfLevel();
        int prevWidth = this.app.getCarMap().getMapWidth();
        int prevHeight = this.app.getCarMap().getMapHeight();
        int prevCarNumber = 0;
        if(this.app != null){
            prevCarNumber = this.app.getPrevCarNumber();
        }
        App app = new App(prevCarNumber); //mozna jako argument podac numer poziomu do wyswietlenia
        app.setGamerAmountOfCoins(amountOfPrevCoins);
        app.setNumberOfLevel(prevLevel+1);
        app.getCarMap().setMapWidth(prevWidth+1);
        app.getCarMap().setMapHeight(prevHeight+1);
        app.start(new Stage());
    }

}

