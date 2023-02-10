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

public class BuyWindow extends Application {
    //    public static void main(String[] args) {
//        launch(args);
//    }
    @Override
    public void start(Stage primaryStage) throws FileNotFoundException {

        GridPane root = getGridPane();

        Label label = new Label(" Now you can buy a new Car! ");
        //Button startButton = new Button(" Start! "); //zmienic ustawienia przycisku na mozliwosc losowania nowego auta

//        Image image = new Image(path);
//        ImageView imageView = new ImageView(image);
        String path = "src/main/resources/autko1.jpg";
        FileInputStream input = new FileInputStream(path);
        Image image = new Image(input);
        ImageView imageView = new ImageView(image);
        imageView.setFitWidth(200);
        imageView.setFitHeight(150);

        addElementsToRoot(root, label, imageView);
        root.setAlignment(Pos.CENTER);
        setScene(primaryStage, root);
        //setStartButton(startButton);
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
        //root.add(startButton, 0, 1);
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
        App app = new App(); //mozna jako argument podac numer poziomu do wyswietlenia
        app.start(new Stage());
    }

}


