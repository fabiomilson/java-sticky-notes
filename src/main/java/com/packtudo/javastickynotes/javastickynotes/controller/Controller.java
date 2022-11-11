package com.packtudo.javastickynotes.javastickynotes.controller;

import javafx.scene.Node;
import javafx.scene.input.MouseEvent;
import javafx.stage.Window;

public abstract class Controller {

    protected double xOffset;
    protected double yOffset;

    protected double getY(final MouseEvent mouseEvent) {
        return mouseEvent.getScreenY() + yOffset;
    }

    protected double getX(final MouseEvent mouseEvent) {
        return mouseEvent.getScreenX() + xOffset;
    }

    protected Window getWindow(final MouseEvent mouseEvent) {
        return ((Node) mouseEvent.getTarget()).getScene().getWindow();
    }

}
