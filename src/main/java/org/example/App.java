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
    private int fieldSize = 35;
    private int windowHeight;
    private int windowWidth;
    private CarMap map;
    private SimulationEngine engine;
    private GridPane grid = new GridPane();
    private Scene windowScene;
    private VBox vBox;
    private HBox hBoxStartPos;
    private HBox hBoxEndPos;
    private HBox hBoxCoins;
    private HBox hBoxLevel;
    private ProgressBar progressBar;
    private MovingWrongCars movingWrongCars;
    private int randomNumber;
    private int gamerAmountOfCoins;
    private int numberOfLevel;
    private int width;
    private int height;
    public App(int randomNumber){ //numer wskazujacy typ naszego auta

        int timeSleep = 600;
        this.randomNumber = randomNumber;
        this.map = new CarMap(new Vector2d(4, 0), this);
        //this.map.setMapHeight(7);
        //this.map.setMapWidth(7);
        this.engine = new SimulationEngine(new Vector2d(4, 0), map, this);
        this.engine.setNewCar(randomNumber);
        //zmodyfikuj liczbe monet na koncie gracza (w klasie StartWindow)
        this.map.makePossibleCars(); //utworzy liste aut mozliwych do zdobycia w trakcie rozgrywki
        this.movingWrongCars = new MovingWrongCars(this.map);
        this.windowScene = new Scene(grid, 400, 300);
        engine.setMoveDelay(timeSleep);

    }
    public int getPrevCarNumber(){
        return this.randomNumber;
    }
    public void setPrevCarNumber(int randomNumber){
        this.randomNumber = randomNumber;
    }
    public CarMap getCarMap(){
        return this.map;
    }
    public SimulationEngine getSimulationEngine(){
        return this.engine;
    }
    public void setNumberOfLevel(int level){
        this.numberOfLevel = level;
    }
    public int getNumberOfLevel(){
        return this.numberOfLevel;
    }
    @Override
    public void start(Stage primaryStage) {

        height = this.getCarMap().getMapHeight();
        width = this.getCarMap().getMapWidth();

        windowHeight = fieldSize * (height+5);
        windowWidth = fieldSize * (width+5);

        newGrid(this.getRandomNumber());
        vBox = new VBox(grid);

        Scene scene = new Scene(vBox, windowWidth, windowHeight);
        String title = " Traffic Run! ";
        primaryStage.setTitle(title);

        printNumberOfLevel(vBox);
        printAmountOfCoins(vBox);
        printProgressBar();

        vBox.getChildren().add(nextStepButton(primaryStage));
        vBox.getChildren().add(buyButton(primaryStage));

        printStartField(vBox);
        printEndField(vBox);

        primaryStage.setScene(scene);
        primaryStage.show();

//        Thread thread = new Thread(engine); //watek spowodowalby ponowne uruchomienie metody run rownolegle!
//        thread.start();

        movingWrongCars.run();

        engine.run();

    }
    public int getGamerAmountOfCoins(){
        return this.gamerAmountOfCoins;
    }
    public void setGamerAmountOfCoins(int amount){
        this.gamerAmountOfCoins = amount;
    }
    public void printAmountOfCoins(VBox vBox){
        //wypisanie aktualnej liczby zdobytych przez gracza monet
        Label amountOfCoins = new Label(" Coins: ");
        Label amountOfCoinsValue = new Label(Integer.toString(this.getGamerAmountOfCoins()));
        hBoxCoins = new HBox(amountOfCoins, amountOfCoinsValue);
        vBox.getChildren().add(hBoxCoins);
    }
    public void printNumberOfLevel(VBox vBox){
        //wypisanie aktualnego numeru poziomu
        Label numberOfLevel = new Label(" Level: ");
        Label numberOfLevelValue = new Label(Integer.toString(this.getNumberOfLevel()));
        hBoxLevel = new HBox(numberOfLevel, numberOfLevelValue);
        vBox.getChildren().add(hBoxLevel);
    }
    public void printStartField(VBox vBox){
        //wypisanie pola startu
        Label startField = new Label(" Start: ");
        Label startFieldValue = new Label(this.getSimulationEngine().getStartPosition().toString());
        hBoxStartPos = new HBox(startField, startFieldValue);
        vBox.getChildren().add(hBoxStartPos);
    }
    public void printEndField(VBox vBox){
        //wypisanie pola mety
        Label finishField = new Label(" Finish: ");
        Label finishFieldValue = new Label(new Vector2d(this.getSimulationEngine().getStartPosition().getX(), this.map.getMapHeight()-1).toString());
        hBoxEndPos = new HBox(finishField, finishFieldValue);
        vBox.getChildren().add(hBoxEndPos);
    }
    public void nextLevel(){
        NextLevelWindow window = new NextLevelWindow(this);
        window.start(new Stage()); //zaczynamy grę od nowa //TODO sprawic azeby wchodzic na kolejne poziomy
    }
    public void deletePrevAmountOfCoins(){
        vBox.getChildren().remove(hBoxCoins);
    }
    public void deletePrevStartField(){
        vBox.getChildren().remove(hBoxStartPos);
    }
    public void deletePrevFinishField(){
        vBox.getChildren().remove(hBoxEndPos);
    }
    public void deletePrevLevelNumber(){
        vBox.getChildren().remove(hBoxLevel);
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
            this.refresh(this.getRandomNumber());
            if(this.map.car.getCarPosition().getY() == this.map.getMapHeight()-1){
                nextLevel();
            }
        });
        return nextStepButton;
    }
    public Button buyButton(Stage primaryStage) {
        Button buyButton = new Button(" Buy a new car ");
        //przycisk powinien byc aktywny jesli zaczynamy gre na danym poziomie i mamy wystarczajaco duzo monet zebranych (np 0 na potrzeby testow)
        BuyWindow window = new BuyWindow(this);
        window.setCostOfBuyingNewCar(5); //koszt zakupu nowego auta
        if(this.getGamerAmountOfCoins() >= window.getCostOfBuyingNewCar() && this.map.car.getCarPosition().equals(this.engine.getStartPosition())) {
            buyButton.setOnAction((action) -> {
                try {
                    window.start(primaryStage);
                } catch (FileNotFoundException e) {
                    throw new RuntimeException(e);
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
            });
        }
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
    public void newGrid(int randomNumber){

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

        for (int i = 1; i <= this.map.getMapWidth(); i++){ //4, to mapWidth-1
            Label label = new Label(Integer.toString(i -1));
            grid.getColumnConstraints().add(new ColumnConstraints(width));
            grid.add(label, i, 0);
            GridPane.setHalignment(label, HPos.CENTER);
        }

        for (int i = 1 ; i <= this.map.getMapHeight(); i++){ //4, to mapHeight-1
            Label label = new Label(Integer.toString(i - 1));
            grid.getRowConstraints().add(new RowConstraints(height));
            grid.add(label, 0,i);
            GridPane.setHalignment(label, HPos.CENTER);
        }

        for (int x = 0; x < this.map.getMapWidth(); x++){ //5, to mapWidth
            for (int y = 0; y < this.map.getMapHeight(); y++){ //5, to mapHeight
                Vector2d position = new Vector2d(x, y);

                VBox box;
                if(this.map.isOccupied(position)){
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
    public int getRandomNumber(){
        return this.randomNumber;
    }
    public void refresh(int randomNumber) {
        Platform.runLater( () -> {
            this.grid.getChildren().clear();
            this.grid.getColumnConstraints().clear();
            this.grid.getRowConstraints().clear();
            grid.setGridLinesVisible(false);
            deletePrevAmountOfCoins();
            printAmountOfCoins(vBox);
            this.newGrid(randomNumber);
        });
    }

//    public static void main(String[] args) {
//        launch(args);
//    }
}