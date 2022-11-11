package com.packtudo.javastickynotes.javastickynotes.events;

import com.packtudo.javastickynotes.javastickynotes.model.Card;

import javafx.event.EventType;

public class ToggleCardEvent extends CardEvent {

    public static final EventType<ToggleCardEvent> TOGGLE_CARD_EVENT_EVENT_TYPE = new EventType(CARD_EVENT_EVENT_TYPE,
        "ToggleCardEvent");

    private final Card card;

    public ToggleCardEvent(final Card card) {
        super(TOGGLE_CARD_EVENT_EVENT_TYPE);
        this.card = card;
    }


    @Override
    public void invokeHandler(final CardEventHandler handler) {
        handler.onEvent(card);
    }
}
