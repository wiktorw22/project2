package org.example;

import javafx.geometry.Pos;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.VBox;

import java.io.FileInputStream;
import java.io.FileNotFoundException;

public class GuiElementBox {
    private ImageView image;
    private VBox vBox = new VBox();

    public GuiElementBox(String path, int size){
        try {
            Image image = new Image(new FileInputStream(path));
            this.image = new ImageView(image);
            this.image.setFitWidth(size);
            this.image.setFitHeight(size);

            this.vBox = new VBox(this.image);
            this.vBox.setAlignment(Pos.CENTER);

        }
        catch (FileNotFoundException e){
            throw new RuntimeException(" File not found! ");
        }
    }

    public VBox getVBox(){
        return this.vBox;
    }
}
