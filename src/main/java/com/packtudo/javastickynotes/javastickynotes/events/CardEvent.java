package com.packtudo.javastickynotes.javastickynotes.events;

import javafx.event.Event;
import javafx.event.EventType;

public abstract class CardEvent extends Event {

    protected static final EventType<CardEvent> CARD_EVENT_EVENT_TYPE = new EventType(ANY);

    public CardEvent(final EventType<? extends Event> eventType) {
        super(eventType);
    }

    public abstract void invokeHandler(final CardEventHandler handler);

}
