package com.packtudo.javastickynotes.javastickynotes.components;

import static com.packtudo.javastickynotes.javastickynotes.controller.SingletonController.getAllCardsInMemory;
import static com.packtudo.javastickynotes.javastickynotes.controller.SingletonController.getGlobalListView;

import com.packtudo.javastickynotes.javastickynotes.controller.SingletonController;
import com.packtudo.javastickynotes.javastickynotes.model.Card;
import com.packtudo.javastickynotes.javastickynotes.util.DBHelper;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;

public class PackNewCardButton extends Button {

    public PackNewCardButton(final Card card) {
        super();
        setText("+");
        getStyleClass().setAll("btn-info");
        setMinHeight(25);
        setMinWidth(25);
        setAlignment(Pos.CENTER);
        setPadding(Insets.EMPTY);
        setOnMouseClicked((event) -> {
            final Card newCard = DBHelper.createNewCard(card);
            SingletonController.getApplicationInstance().createNewCard(newCard);
            getGlobalListView().getItems().clear();
            getGlobalListView().getItems().addAll(getAllCardsInMemory());
        });
    }
}
