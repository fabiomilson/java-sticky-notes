package com.packtudo.javastickynotes.javastickynotes.controller;

import static java.util.Objects.requireNonNull;
import static org.junit.jupiter.api.Assertions.assertNull;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import com.packtudo.javastickynotes.javastickynotes.PackStickyNotes;
import com.packtudo.javastickynotes.javastickynotes.components.DisabledSelectionModel;
import com.packtudo.javastickynotes.javastickynotes.components.PackListView;
import com.packtudo.javastickynotes.javastickynotes.components.PackStage;
import com.packtudo.javastickynotes.javastickynotes.components.PackXCell;
import com.packtudo.javastickynotes.javastickynotes.model.Card;

import javafx.stage.Stage;

public final class SingletonController {

    private static Stage stage;
    private static PackListView listView;
    private static final Map<Integer, PackStage> STAGES_CARD_MAP = new HashMap<>();
    private static PackStickyNotes packStickyNotes;

    public static void setStageInstance(final Stage stage) {
        requireNonNull(stage);
        assertNull(SingletonController.stage);
        SingletonController.stage = stage;
    }

    public static void setApplicationInstance(final PackStickyNotes packStickyNotes) {
        SingletonController.packStickyNotes = packStickyNotes;
    }

    public static PackStickyNotes getApplicationInstance() {
        return SingletonController.packStickyNotes;
    }

    public static List<Card> getAllCardsInMemory() {
        return STAGES_CARD_MAP.values().stream().map(PackStage::getCard)
            .collect(Collectors.toList());
    }


    public static Stage getGlobalStage(){
        return SingletonController.stage;
    }

    public static PackListView getGlobalListView() {
        return listView;
    }

    public static void createGlobalListView(final List<Card> cards) {
        assertNull(SingletonController.listView);
        SingletonController.listView = new PackListView() {{
            setSelectionModel(new DisabledSelectionModel<>());
            setCellFactory(param -> new PackXCell());
            getStyleClass().add("remove-focus");
            getStylesheets().add(this.getClass().getClassLoader().getResource("extra-sources/css/geral.css").toExternalForm());
            getItems().addAll(cards);
            setFocusTraversable(false);
        }};
    }

    public static void addCardAndStage(final Card card, final PackStage stage) {
        STAGES_CARD_MAP.put(card.getId(), stage);
    }

    public static void changeCardStageMemory(final Card card) {
        STAGES_CARD_MAP.get(card.getId()).setCard(card);
    }

    public static PackStage getStageFromMemory(final Card card) {
        return STAGES_CARD_MAP.get(card.getId());
    }

    public static boolean containsCard(final Card card) {
        return STAGES_CARD_MAP.containsKey(card.getId());
    }

    public static void removeCardMemory(final Card card) {
        final PackStage packStage = STAGES_CARD_MAP.remove(card.getId());
        packStage.hide();
        getGlobalListView().getItems()
            .stream()
            .filter(c -> c.getId().equals(card.getId()))
            .findFirst()
            .ifPresent(c -> getGlobalListView().getItems().remove(c));
    }

}
