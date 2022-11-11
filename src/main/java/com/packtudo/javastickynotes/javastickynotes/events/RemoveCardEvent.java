package com.packtudo.javastickynotes.javastickynotes.events;

import com.packtudo.javastickynotes.javastickynotes.model.Card;

import javafx.event.EventType;

public class RemoveCardEvent extends CardEvent {

    public static final EventType<RemoveCardEvent> REMOVE_CARD_EVENT_EVENT_TYPE = new EventType(CARD_EVENT_EVENT_TYPE,
        "RemoveCardEvent");

    private final Card card;

    public RemoveCardEvent(final Card card) {
        super(REMOVE_CARD_EVENT_EVENT_TYPE);
        this.card = card;
    }


    @Override
    public void invokeHandler(final CardEventHandler handler) {
        handler.onEvent(card);
    }
}
