package com.packtudo.javastickynotes.javastickynotes.events;

import com.packtudo.javastickynotes.javastickynotes.model.Card;

import javafx.event.EventHandler;

public abstract class CardEventHandler implements EventHandler<CardEvent> {

    public abstract void onEvent(final Card card);

    @Override
    public void handle(final CardEvent event) {
        event.invokeHandler(this);
    }
}
