package com.packtudo.javastickynotes.javastickynotes.components;

import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

public class ImageButton extends Button {

    private final String STYLE_NORMAL = "-fx-background-color: transparent; -fx-cursor: hand; -fx-padding: 5 5 5 5";
    private final String STYLE_PRESSED = "-fx-background-color: transparent; -fx-cursor: hand; -fx-padding: 5 5 5 5";

    ImageView image = new ImageView();

    public ImageButton(Image originalImage, double h, double w) {

        image.setImage(originalImage);
        //image.setFitHeight(h);
     //   image.setFitHeight(w);
        setWidth(image.getFitWidth());
        setHeight(image.getFitHeight());
        image.setPreserveRatio(true);
        setGraphic(image);
     //   setStyle(STYLE_NORMAL);
        getStylesheets().add(this.getClass().getClassLoader().getResource("extra-sources/css/button-list.css").toExternalForm());  // add the CSS stylesheet
        getStyleClass().add("button-list");



       // setOnMousePressed(event -> setStyle(STYLE_PRESSED));
       // setOnMouseReleased(event -> setStyle(STYLE_NORMAL));
    }

    public void changeImage(Image image) {
        this.image.setImage(image);
    }

}
