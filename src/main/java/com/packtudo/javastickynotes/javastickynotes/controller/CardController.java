package com.packtudo.javastickynotes.javastickynotes.controller;


import com.packtudo.javastickynotes.javastickynotes.components.PackScene;
import com.packtudo.javastickynotes.javastickynotes.util.DBHelper;

import javafx.scene.input.MouseEvent;
import javafx.stage.Window;

public class CardController extends Controller {

    public void preDragEvent(final MouseEvent mouseEvent) {
        final Window window = getWindow(mouseEvent);
        xOffset = (Double.valueOf(window.getX()).isNaN() ? 0 : window.getX()) - mouseEvent.getScreenX();
        yOffset = (Double.valueOf(window.getY()).isNaN() ? 0 : window.getY()) - mouseEvent.getScreenY();
    }

    public void dragEvent(final MouseEvent mouseEvent) {
        final Window window = getWindow(mouseEvent);
        window.setX(getX(mouseEvent));
        window.setY(getY(mouseEvent));
    }

    public void dragReleased(final MouseEvent mouseEvent) {
        final Window window = getWindow(mouseEvent);
        final double x = getX(mouseEvent);
        final double y = getY(mouseEvent);
        final int idCard = ((PackScene) window.getScene()).getIdCard();
        DBHelper.updatePosition(idCard, x, y);
       /* if(STAGES_CARD_MAP.containsKey(idCard)) {
            STAGES_CARD_MAP.get(idCard).getCard().setX(x);
            STAGES_CARD_MAP.get(idCard).getCard().setY(y);
        }*/
    }

}
