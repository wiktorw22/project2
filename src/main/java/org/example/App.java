package org.example;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.HPos;
import javafx.geometry.Pos;
import javafx.scene.Group;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.*;
import javafx.stage.Stage;

public class App extends Application {
    private int fieldSize = 30;
    private int windowHeight;
    private int windowWidth;
    private CarMap map;
    private SimulationEngine engine;
    private GridPane grid = new GridPane();
    public App(){
        int width = 4;
        int height = 4;
        int timeSleep = 300;
        windowHeight = fieldSize * (height+2);
        windowWidth = fieldSize * (width+2);
        this.map = new CarMap(new Vector2d(4, 0), CarType.T1);
        this.map.setMapHeight(5);
        this.map.setMapWidth(5);
        this.engine = new SimulationEngine(new Vector2d(4, 0), 3, new Vector2d(0, 3), map, this);
        engine.setMoveDelay(timeSleep);

    }
    @Override
    public void start(Stage primaryStage) {
        // code to set up the window
        primaryStage.setTitle(" Traffic Run! ");
        primaryStage.setWidth(400);
        primaryStage.setHeight(300);
        GridPane root = new GridPane();
        Button startButton = new Button("Start");
        Label label = new Label(" welcome to the game! ");
        root.add(label, 0, 0);
        root.add(startButton, 1, 0);
        root.setAlignment(Pos.CENTER);
        startButton.setOnAction(event -> {
            primaryStage.close();
            Stage newWindow = new Stage();
            newWindow.setTitle(" Traffic Run! ");
            newWindow.setWidth(400);
            newWindow.setHeight(300);
            //StackPane root1 = new StackPane();
            Scene scene = new Scene(grid, 400, 300);
            //newGrid();
            Thread thread = new Thread(engine);
            thread.start();
            engine.run();
            newGrid();
            newWindow.setScene(scene);
            newWindow.show();
//            Thread thread = new Thread(engine);
//            thread.start();

        });

        Scene scene = new Scene(root, 400, 300);
        primaryStage.setScene(scene);
        primaryStage.show();
    }
    public void newGrid(){

        int width = fieldSize ;
        int height = fieldSize;
        int objectSize = fieldSize;

        grid.setGridLinesVisible(true);
        grid.setMinWidth(width);
        grid.setMinHeight(height);
        grid.getColumnConstraints().add(new ColumnConstraints(width));
        Label startLabel = new Label("y\\x");
        grid.getRowConstraints().add(new RowConstraints(height));
        GridPane.setHalignment(startLabel, HPos.CENTER);
        grid.add(startLabel, 0, 0);

        for (int i = 1; i <= 5; i++){ //4, to mapWidth-1
            Label label = new Label(Integer.toString(i -1));
            grid.getColumnConstraints().add(new ColumnConstraints(width));
            grid.add(label, i, 0);
            GridPane.setHalignment(label, HPos.CENTER);
        }

        for (int i = 1 ; i <=  5; i++){ //4, to mapHeight-1
            Label label = new Label(Integer.toString(i - 1));
            grid.getRowConstraints().add(new RowConstraints(height));
            grid.add(label, 0,i);
            GridPane.setHalignment(label, HPos.CENTER);
        }

        for (int x = 0; x < 5; x++){ //5, to mapWidth
            for (int y = 0; y < 5; y++){ //5, to mapHeight
                Vector2d position = new Vector2d(x, y);

                VBox box;
                if(this.map.isOccupied(position)){
                    String path = "src/main/resources/autko.png";
                    box = new GuiElementBox(path, objectSize).getVBox();
                }

                else{
                    box = new VBox();
                }

                grid.add(box, x+1, y+1);
                GridPane.setHalignment(box, HPos.CENTER);
            }
        }

    }
    public void refresh() {
        Platform.runLater( () -> {
            this.grid.getChildren().clear();
            this.grid.getColumnConstraints().clear();
            this.grid.getRowConstraints().clear();
            grid.setGridLinesVisible(false);
            this.newGrid();
        });
    }


//    public static void main(String[] args) {
//        launch(args);
//    }
}