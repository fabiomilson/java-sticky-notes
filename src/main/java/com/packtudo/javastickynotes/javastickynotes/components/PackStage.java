package com.packtudo.javastickynotes.javastickynotes.components;

import static com.packtudo.javastickynotes.javastickynotes.controller.SingletonController.getGlobalStage;

import com.packtudo.javastickynotes.javastickynotes.model.Card;

import javafx.stage.Stage;
import javafx.stage.StageStyle;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class PackStage extends Stage {

    private Card card;

    public PackStage(final Card card) {
        this.card = card;
        initOwner(getGlobalStage());
        initStyle(StageStyle.UNDECORATED);
        setMinHeight(350);
        setMinWidth(260);
    }
}
