package org.example;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.geometry.HPos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ProgressBar;
import javafx.scene.layout.*;
import javafx.stage.Stage;

import java.io.FileNotFoundException;

public class App extends Application {
    private int fieldSize = 30;
    private int windowHeight;
    private int windowWidth;
    private CarMap map;
    private SimulationEngine engine;
    private GridPane grid = new GridPane();
    private Scene windowScene;
    private VBox vBox;
    private HBox hBox;
    private ProgressBar progressBar;
    private MovingWrongCars movingWrongCars;
    public App(){
        int width = 10;
        int height = 10;
        int timeSleep = 600;
        windowHeight = fieldSize * (height+2);
        windowWidth = fieldSize * (width+2);
        this.map = new CarMap(new Vector2d(4, 0), CarType.T1);
        this.map.setMapHeight(5);
        this.map.setMapWidth(5);
        this.engine = new SimulationEngine(new Vector2d(4, 0), map, this);
        this.movingWrongCars = new MovingWrongCars(this.map);
        this.windowScene = new Scene(grid, 400, 300);
        engine.setMoveDelay(timeSleep);

    }
    @Override
    public void start(Stage primaryStage) {

        newGrid();
        vBox = new VBox(grid);

        Scene scene = new Scene(vBox, windowWidth, windowHeight);
        String title = " Traffic Run! ";
        primaryStage.setTitle(title);

        printAmountOfCoins(vBox);
        printProgressBar();

        vBox.getChildren().add(nextStepButton(primaryStage));
        vBox.getChildren().add(buyButton(primaryStage));

        primaryStage.setScene(scene);
        primaryStage.show();

//        Thread thread = new Thread(engine); //watek spowodowalby ponowne uruchomienie metody run rownolegle!
//        thread.start();

        movingWrongCars.run();

        engine.run();

    }
    public void printAmountOfCoins(VBox vBox){
        //wypisanie aktualnej liczby zdobytych przez gracza monet
        Label amountOfCoins = new Label(" Amount of coins: ");
        Label amountOfCoinsValue = new Label(Integer.toString(this.map.car.getSumOfCoins()));
        hBox = new HBox(amountOfCoins, amountOfCoinsValue);
        vBox.getChildren().add(hBox);
    }
    public void nextLevel(){
        NextLevelWindow window = new NextLevelWindow();
        window.start(new Stage()); //zaczynamy grę od nowa //TODO sprawic azeby wchodzic na kolejne poziomy
    }
    public void deletePrevAmountOfCoins(HBox hBox){
        vBox.getChildren().remove(hBox);
    }
    public Button nextStepButton(Stage primaryStage) {
        Button nextStepButton = new Button(" Next step! ");
        nextStepButton.setOnAction((action) -> {
            try {
                Thread.sleep(600); //timeSleep
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
            this.map.car.move(); //TODO uwzglednic zebranie napotkanych monet po drodze
            refreshProgressBarPositive(progressBar);
            //moveWrongCars(); //TODO sprobowac zrobic w nowej klasie rownolegle
            Thread thread = new Thread(movingWrongCars);
            thread.start();
//            Thread thread = new Thread(movingWrongCars);
//            thread.start();
//            movingWrongCars.run();
            carsCollision(); //TODO uwzglednic czy wlasciwie jest obslugiwana sytuacja zderzen/przeniesienia autka gracza na start
            this.refresh();
            if(this.map.car.getCarPosition().getY() == this.map.getMapHeight()-1){
                nextLevel();
            }
        });
        return nextStepButton;
    }
    public Button buyButton(Stage primaryStage) {
        Button buyButton = new Button(" Buy a new car HERE ");
        buyButton.setOnAction((action) -> {
            BuyWindow window = new BuyWindow();
            try {
                window.start(primaryStage);
            } catch (FileNotFoundException e) {
                throw new RuntimeException(e);
            }
        });
        return buyButton;
    }
    public void carsCollision(){
        for (WrongCar value : this.map.wrongCarList) {
            if(value.getCarPosition().equals(this.map.car.getCarPosition())){
                this.map.car.setPosition(this.engine.getStartPosition()); //przenies gracza ponownie na poczatek (START)
                this.map.car.setSumOfCoins(0); //gdy powrot na start spowodowany kolizja
                refreshProgressBarNegative(progressBar);
                break;
            }
        }
    }
    public void moveWrongCars() {
        int cnt = 0;
        while(cnt < 10){
            for(int i = 0; i < this.map.wrongCarList.size(); i++){
                this.map.wrongCarList.get(i).moveWrongCar();
            }
            //this.refresh();
            cnt++;
        }
    }
    public void printProgressBar(){
        progressBar = new ProgressBar();
        progressBar.setPrefWidth(200);

        HBox hBoxBar = new HBox(progressBar);
        vBox.getChildren().add(hBoxBar);
        hBoxBar.setPrefWidth(100);

        // Set the initial value of the progress bar
        progressBar.setProgress(0.0);

    }
    public void refreshProgressBarPositive(ProgressBar progressBar){
        progressBar.setProgress((double)(this.map.car.getCarPosition().getY())/(double)(this.map.getMapHeight()-1));
    }
    public void refreshProgressBarNegative(ProgressBar progressBar){
        progressBar.setProgress(0.0);
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
                else if(this.map.isOccupiedCoin(position)){
                    String path = "src/main/resources/coin.png";
                    box = new GuiElementBox(path, objectSize).getVBox();
                }
                else if(this.map.isOccupiedWrong(position)){
                    String path = "src/main/resources/wrongCar.png";
                    box = new GuiElementBox(path, objectSize).getVBox();
                }
                else if(this.map.isOccupiedAsphalt(position)){
                    String path = "src/main/resources/asfalt.jpg";
                    box = new GuiElementBox(path, objectSize).getVBox();
                }
                else {
                    String path = "src/main/resources/pole.jpg";
                    box = new GuiElementBox(path, objectSize).getVBox();
                }
//                else{
//                    box = new VBox();
//                }

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
            deletePrevAmountOfCoins(hBox);
            printAmountOfCoins(vBox);
            this.newGrid();
        });
    }

//    public static void main(String[] args) {
//        launch(args);
//    }
}