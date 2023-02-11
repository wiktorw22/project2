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
    private int randomNumber;
    private int gamerAmountOfCoins;
    private int numberOfLevel;
    private int width;
    private int height;
    private HBox hBoxStartPos;
    private HBox hBoxEndPos;
    private HBox hBoxCoins;
    private HBox hBoxLevel;
    private VBox vBox;
    private ProgressBar progressBar;
    private CarMap map;
    private SimulationEngine engine;
    private MovingWrongCars movingWrongCars;
    private GridPane grid = new GridPane();
    private Scene windowScene;
    public App(int randomNumber){ //randomNumber, to numer wskazujacy typ naszego auta

        int timeSleep = 600;
        this.randomNumber = randomNumber;
        this.map = new CarMap(this);
        this.engine = new SimulationEngine(new Vector2d(4, 0), map, this);
        this.engine.setNewCar(randomNumber);
        this.map.makePossibleCars(); //metoda ta tworzy liste aut mozliwych do zdobycia w trakcie rozgrywki
        this.movingWrongCars = new MovingWrongCars(this.map);
        this.windowScene = new Scene(grid, 400, 300);
        engine.setMoveDelay(timeSleep);

    }
    public int getPrevCarNumber(){
        return this.randomNumber;
    }
    public int getRandomNumber(){
        return this.randomNumber;
    }
    public CarMap getCarMap(){
        return this.map;
    }
    public SimulationEngine getSimulationEngine(){
        return this.engine;
    }
    public int getNumberOfLevel(){
        return this.numberOfLevel;
    }
    public int getGamerAmountOfCoins(){
        return this.gamerAmountOfCoins;
    }
    public void setNumberOfLevel(int level){
        this.numberOfLevel = level;
    }
    public void setGamerAmountOfCoins(int amount){
        this.gamerAmountOfCoins = amount;
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

        vBox.getChildren().add(nextStepButton());
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
    public void carsCollision(){
        for (WrongCar value : this.map.wrongCarList) {
            if(value.getCarPosition().equals(this.map.car.getCarPosition())){
                this.map.car.setPosition(this.engine.getStartPosition()); //przenies gracza ponownie na poczatek (START)
                refreshProgressBarNegative(progressBar); //zaczynamy pokonywac trase od poczatku
                break;
            }
        }
    }
    public void nextLevel(){
        NextLevelWindow window = new NextLevelWindow(this);
        window.start(new Stage()); //wyswietla okienko informujace o przejsciu gracza na kolejny poziom
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
    public void deletePrevAmountOfCoins(){ //usuwa poprzednia liczbe monet gracza aby moc wyswietlic aktualna
        vBox.getChildren().remove(hBoxCoins);
    }
    public void printProgressBar(){ //wyswietli pasek zapelniajacy sie wraz z pokonywaniem trasy przez auto
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
    public Button nextStepButton() { //wykonuje kolejny ruch samochodzika
        Button nextStepButton = new Button(" Next step! ");
        nextStepButton.setOnAction((action) -> {
            try {
                Thread.sleep(600); //timeSleep
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
            this.map.car.move();
            refreshProgressBarPositive(progressBar);
            Thread thread = new Thread(movingWrongCars);
            thread.start();
            carsCollision(); //obsluguje sytuacje zderzen aut na mapie
            this.refresh(this.getRandomNumber());
            if(this.map.car.getCarPosition().getY() == this.map.getMapHeight()-1){
                nextLevel();
            }
        });
        return nextStepButton;
    }
    public Button buyButton(Stage primaryStage) {
        Button buyButton = new Button(" Buy a new car ");
        //przycisk powinien byc aktywny jesli zaczynamy gre na danym poziomie i mamy wystarczajaco duzo monet zebranych
        BuyWindow window = new BuyWindow(this);
        window.setCostOfBuyingNewCar(window.getCostOfBuyingNewCar()+1); //koszt zakupu nowego auta
        if(this.getGamerAmountOfCoins() >= window.getCostOfBuyingNewCar() && this.map.car.getCarPosition().equals(this.engine.getStartPosition())) {
            buyButton.setOnAction((action) -> {
                try {
                    window.start(primaryStage);
                } catch (FileNotFoundException | InterruptedException e) {
                    throw new RuntimeException(e);
                }
            });
        }
        return buyButton;
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

        for (int i = 1; i <= this.map.getMapWidth(); i++){
            Label label = new Label(Integer.toString(i -1));
            grid.getColumnConstraints().add(new ColumnConstraints(width));
            grid.add(label, i, 0);
            GridPane.setHalignment(label, HPos.CENTER);
        }

        for (int i = 1 ; i <= this.map.getMapHeight(); i++){
            Label label = new Label(Integer.toString(i - 1));
            grid.getRowConstraints().add(new RowConstraints(height));
            grid.add(label, 0,i);
            GridPane.setHalignment(label, HPos.CENTER);
        }

        for (int x = 0; x < this.map.getMapWidth(); x++){
            for (int y = 0; y < this.map.getMapHeight(); y++){
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

                grid.add(box, x+1, y+1);
                GridPane.setHalignment(box, HPos.CENTER);
            }
        }

    }
    public void refresh(int randomNumber) { //odswieza aktualny stan mapy (jej widok dla uzytkownika)
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
}