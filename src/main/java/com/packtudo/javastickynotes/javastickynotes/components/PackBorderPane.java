package com.packtudo.javastickynotes.javastickynotes.components;

import javafx.scene.Node;
import javafx.scene.layout.BorderPane;

public class PackBorderPane extends BorderPane {

    public PackBorderPane(final Node node){
        super();
        setCenter(node);
    }
}
