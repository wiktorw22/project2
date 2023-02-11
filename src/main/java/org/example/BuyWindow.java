package org.example;

import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.Random;

public class BuyWindow extends Application {
    private int costOfBuyingNewCar = 5; //koszt zakupu kolejnego auta
    private App app;
    private CarMap map;
    private SimulationEngine engine;
    public BuyWindow(App app){
        this.app = app;
        this.map = this.app.getCarMap();
        this.engine = this.app.getSimulationEngine();
    }
    public int getCostOfBuyingNewCar(){
        return this.costOfBuyingNewCar;
    }
    public void setCostOfBuyingNewCar(int costOfBuyingNewCar){
        this.costOfBuyingNewCar = costOfBuyingNewCar;
    }
    @Override
    public void start(Stage primaryStage) throws FileNotFoundException, InterruptedException {
        GridPane root = getGridPane();
        Label label = new Label(" You just bought a new Car! ");

        Random random = new Random();
        int randomNumber = random.nextInt(5); //wylosuj numer autka z listy possibleCarsToGet
        String path;
        if(randomNumber == 0){
            path = "src/main/resources/autko.png";
        }
        else if(randomNumber == 1){
            path = "src/main/resources/autko1.jpg";
        }
        else if(randomNumber == 2){
            path = "src/main/resources/autko2.jpg";
        }
        else if(randomNumber == 3){
            path = "src/main/resources/autko3.jpg";
        }
        else{
            path = "src/main/resources/autko4.png";
        }

        FileInputStream input = new FileInputStream(path);
        Image image = new Image(input);
        ImageView imageView = new ImageView(image);
        imageView.setFitWidth(200);
        imageView.setFitHeight(150);

        addElementsToRoot(root, label, imageView);
        root.setAlignment(Pos.CENTER);
        setScene(primaryStage, root);
        Thread.sleep(600);
        startSimulation(randomNumber);
    }
    private static void setScene(Stage primaryStage, GridPane root) {
        Scene scene = new Scene(root, 400, 520);
        primaryStage.setScene(scene);
        primaryStage.setTitle(" Buy window ");
        primaryStage.show();
    }
    private static void addElementsToRoot(GridPane root, Label label, ImageView imageView){
        root.add(label, 0, 0);
        root.add(imageView, 0, 1);
    }
    private static GridPane getGridPane() {
        GridPane root = new GridPane();
        root.setPadding(new Insets(10));
        root.setVgap(8);
        root.setHgap(10);
        return root;
    }
    private void startSimulation(int randomNumber) {
        int actualGamerAmountOfCoins = this.app.getGamerAmountOfCoins();
        int prevLevel = this.app.getNumberOfLevel();
        int prevWidth = this.app.getCarMap().getMapWidth();
        int prevHeight = this.app.getCarMap().getMapHeight();
        App app = new App(randomNumber); //randomNumber to numer wylosowanego autka
        app.setGamerAmountOfCoins(actualGamerAmountOfCoins - this.costOfBuyingNewCar);
        app.setNumberOfLevel(prevLevel);
        app.getCarMap().setMapWidth(prevWidth);
        app.getCarMap().setMapHeight(prevHeight);
        app.start(new Stage());
    }
}


