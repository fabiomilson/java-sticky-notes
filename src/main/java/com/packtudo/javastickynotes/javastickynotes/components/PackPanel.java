package com.packtudo.javastickynotes.javastickynotes.components;

import org.kordamp.bootstrapfx.scene.layout.Panel;

import com.packtudo.javastickynotes.javastickynotes.controller.CardController;

import javafx.scene.layout.BorderPane;
import lombok.Getter;

@Getter
public class PackPanel extends Panel {

    private final CardController controller;
    private final String title;

    public PackPanel(final String title, final CardController controller, final BorderPane borderPane){
        super(title);
        this.title = title;
        this.controller = controller;
        registerEvents();
        registerStyles();
        setBody(borderPane);
    }

    private void registerStyles() {
        getStyleClass().add("panel-primary");
    }

    public void registerEvents() {
        getTop().setOnMousePressed(controller::preDragEvent);
        getTop().setOnMouseDragged(controller::dragEvent);
        getTop().setOnMouseReleased(controller::dragReleased);
    }

}
