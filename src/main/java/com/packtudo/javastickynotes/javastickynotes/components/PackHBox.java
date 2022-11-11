package com.packtudo.javastickynotes.javastickynotes.components;

import javafx.scene.Node;
import javafx.scene.layout.HBox;
import lombok.Getter;

@Getter
public class PackHBox extends HBox {

    private final int idCard;

    public PackHBox(final int idCard, final Node... children) {
        super(children);
        this.idCard = idCard;
    }
}
