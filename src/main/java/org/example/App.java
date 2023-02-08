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
    private Scene windowScene;
    public App(){
        int width = 4;
        int height = 4;
        int timeSleep = 600;
        windowHeight = fieldSize * (height+2);
        windowWidth = fieldSize * (width+2);
        this.map = new CarMap(new Vector2d(4, 0), CarType.T1);
        this.map.setMapHeight(5);
        this.map.setMapWidth(5);
        this.engine = new SimulationEngine(new Vector2d(4, 0), 3, new Vector2d(0, 3), map, this);
        this.windowScene = new Scene(grid, 400, 300);
        engine.setMoveDelay(timeSleep);

    }
    @Override
    public void start(Stage primaryStage) {

        newGrid();
        VBox vBox = new VBox(grid);

        Scene scene = new Scene(vBox, windowWidth, windowHeight);
        String title = " Traffic Run! ";
        primaryStage.setTitle(title);

        vBox.getChildren().add(nextStepButton(primaryStage));

        primaryStage.setScene(scene);
        primaryStage.show();

        Thread thread = new Thread(engine);
        thread.start();

        engine.run();

    }
    public Button nextStepButton(Stage primaryStage) {
        Button nextStepButton = new Button(" Next step! ");
        nextStepButton.setOnAction((action) -> {
            try {
                Thread.sleep(600); //timeSleep
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
            this.map.car.move();
            this.refresh();
        });
        return nextStepButton;
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