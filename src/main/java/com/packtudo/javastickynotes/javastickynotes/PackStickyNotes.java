package com.packtudo.javastickynotes.javastickynotes;

import static com.packtudo.javastickynotes.javastickynotes.controller.SingletonController.getGlobalListView;
import static com.packtudo.javastickynotes.javastickynotes.controller.SingletonController.removeCardMemory;

import java.util.List;

import com.packtudo.javastickynotes.javastickynotes.components.PackBorderPane;
import com.packtudo.javastickynotes.javastickynotes.components.PackHBox;
import com.packtudo.javastickynotes.javastickynotes.components.PackNewCardButton;
import com.packtudo.javastickynotes.javastickynotes.components.PackPanel;
import com.packtudo.javastickynotes.javastickynotes.components.PackScene;
import com.packtudo.javastickynotes.javastickynotes.config.LiquibaseSync;
import com.packtudo.javastickynotes.javastickynotes.config.PackApplication;
import com.packtudo.javastickynotes.javastickynotes.controller.CardController;
import com.packtudo.javastickynotes.javastickynotes.controller.SingletonController;
import com.packtudo.javastickynotes.javastickynotes.events.CardEventHandler;
import com.packtudo.javastickynotes.javastickynotes.events.RemoveCardEvent;
import com.packtudo.javastickynotes.javastickynotes.events.ToggleCardEvent;
import com.packtudo.javastickynotes.javastickynotes.model.Card;
import com.packtudo.javastickynotes.javastickynotes.util.DBHelper;
import com.packtudo.javastickynotes.javastickynotes.util.ResizeHelper;

import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

public class PackStickyNotes extends PackApplication {

    @Override
    public void start(final Stage stage) throws Exception {
        LiquibaseSync.sync();
        final List<Card> cards = DBHelper.listAllCards();

        SingletonController.setStageInstance(stage);
        SingletonController.createGlobalListView(cards);
        SingletonController.setApplicationInstance(this);

        final PackPanel panel = new PackPanel("Cards", new CardController(), new PackBorderPane(getGlobalListView()));
        final PackScene scene = new PackScene(-1, panel);

        stage.initStyle(StageStyle.UNDECORATED);
        stage.setMinHeight(350);
        stage.setMinWidth(260);
        stage.addEventHandler(ToggleCardEvent.TOGGLE_CARD_EVENT_EVENT_TYPE, new CardEventHandler() {
            @Override
            public void onEvent(final Card card) {
                showCard(card);
            }
        });

        stage.addEventHandler(RemoveCardEvent.REMOVE_CARD_EVENT_EVENT_TYPE, new CardEventHandler() {
            @Override
            public void onEvent(final Card card) {
                DBHelper.remove(card);
                removeCardMemory(card);
            }
        });

        stage.setScene(scene);
        stage.show();

        final Button buttonNew = new PackNewCardButton(Card.builder().x(stage.getX()).y(stage.getY()).build());
        final PackHBox actionsBox = new PackHBox(-1, buttonNew);
        actionsBox.setAlignment(Pos.TOP_RIGHT);

        ((GridPane) panel.getTop()).getChildren().add(actionsBox);

        showCards();
        ResizeHelper.addResizeListener(stage);
    }

    public static void main(String[] args) {
        launch();
    }
}
