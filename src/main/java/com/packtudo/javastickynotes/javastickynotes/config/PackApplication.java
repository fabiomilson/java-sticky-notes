package com.packtudo.javastickynotes.javastickynotes.config;

import static com.packtudo.javastickynotes.javastickynotes.controller.SingletonController.addCardAndStage;
import static com.packtudo.javastickynotes.javastickynotes.controller.SingletonController.changeCardStageMemory;
import static com.packtudo.javastickynotes.javastickynotes.controller.SingletonController.containsCard;
import static com.packtudo.javastickynotes.javastickynotes.controller.SingletonController.getApplicationInstance;
import static com.packtudo.javastickynotes.javastickynotes.controller.SingletonController.getStageFromMemory;

import java.awt.Desktop;
import java.net.URI;
import java.util.Base64;
import java.util.List;

import org.kordamp.bootstrapfx.BootstrapFX;
import org.kordamp.bootstrapfx.scene.layout.Panel;
import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.w3c.dom.events.Event;
import org.w3c.dom.events.EventListener;
import org.w3c.dom.events.EventTarget;
import org.w3c.dom.html.HTMLAnchorElement;

import com.packtudo.javastickynotes.javastickynotes.components.PackBorderPane;
import com.packtudo.javastickynotes.javastickynotes.components.PackHBox;
import com.packtudo.javastickynotes.javastickynotes.components.PackNewCardButton;
import com.packtudo.javastickynotes.javastickynotes.components.PackPanel;
import com.packtudo.javastickynotes.javastickynotes.components.PackScene;
import com.packtudo.javastickynotes.javastickynotes.components.PackStage;
import com.packtudo.javastickynotes.javastickynotes.controller.CardController;
import com.packtudo.javastickynotes.javastickynotes.controller.ClipboardController;
import com.packtudo.javastickynotes.javastickynotes.controller.SingletonController;
import com.packtudo.javastickynotes.javastickynotes.model.Card;
import com.packtudo.javastickynotes.javastickynotes.util.DBHelper;
import com.packtudo.javastickynotes.javastickynotes.util.GetFiles;
import com.packtudo.javastickynotes.javastickynotes.util.ResizeHelper;

import javafx.application.Application;
import javafx.beans.value.ChangeListener;
import javafx.concurrent.Worker.State;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.GridPane;
import javafx.scene.web.WebEngine;
import javafx.scene.web.WebView;
import javafx.stage.Stage;
import netscape.javascript.JSObject;

public abstract class PackApplication extends Application {

    protected void showCards() {
        List<Card> cards = DBHelper.listAllVisibleCards();
        cards.forEach(card -> createNewCard(card));
    }

    protected void showCard(final Card card) {
        if (containsCard(card)) {
            final PackStage stageCache = getStageFromMemory(card);
            if (card.getVisivel()) {
                stageCache.show();
                changeCardStageMemory(card);
                return;
            }
            stageCache.hide();
            changeCardStageMemory(card);
        } else {
            createNewCard(card);
        }
    }

    public void createNewCard(final Card card) {
        final PackStage stage = new PackStage(card);
        final WebView editor = new WebView();
        final WebEngine engine = editor.getEngine();
        final Panel panel = new PackPanel(card.getTituloFormatado(), new CardController(), new PackBorderPane(editor));

        engine.getLoadWorker().stateProperty().addListener(engineStateChangeListener(stage, card, engine));
        engine.load(GetFiles.indexHtml());
        editor.setPrefWidth(stage.getMinWidth() - 20);
        editor.setPrefHeight(stage.getMinHeight() - 20);

        final Button buttonNew = new PackNewCardButton(card);

        final PackHBox actionsBox = new PackHBox(card.getId(), buttonNew);
        actionsBox.setAlignment(Pos.TOP_RIGHT);

        ((GridPane) panel.getTop()).getChildren().add(actionsBox);

        PackScene scene = new PackScene(card.getId(), panel);
        scene.getStylesheets().add(BootstrapFX.bootstrapFXStylesheet());
        stage.setTitle("BootstrapFX");
        stage.setScene(scene);
        stage.sizeToScene();
        stage.show();
        final ClipboardController clipboardController = new ClipboardController();
        scene.addEventFilter(KeyEvent.KEY_PRESSED, clipboardController.controlDownAndV(engine));

        stage.setX(card.getX());
        stage.setY(card.getY());
        stage.show();
        addCardAndStage(card, stage);
        SingletonController.getGlobalStage().requestFocus();
        ResizeHelper.addResizeListener(stage);
    }

    private ChangeListener<State> engineStateChangeListener(final Stage stage, final Card card, final WebEngine engine) {
        return (observableValue, oldState, newState) -> {
            if (newState == State.SUCCEEDED) {

                final JSObject window = (JSObject) engine.executeScript("window");
                window.setMember("app", getApplicationInstance());
                window.setMember("idCard", card.getId());
                window.setMember("card", card);

                final String conteudo = Base64.getEncoder().encodeToString(card.getConteudo().getBytes());

                engine.executeScript(String.format("inserirHTML('%s');", conteudo));

                stage.focusedProperty().addListener((arg0, oldPropertyValue, newPropertyValue) -> {
                    if (newPropertyValue) {
                        System.out.println("focus in");
                        engine.executeScript("focusIn();");
                    } else {
                        System.out.println("focus out");
                        engine.executeScript("focusOut();");
                    }
                });

                final Document document = engine.getDocument();
                NodeList nodeList = document.getElementsByTagName("a");
                for (int i = 0; i < nodeList.getLength(); i++)
                {
                    Node node = nodeList.item(i);
                    EventTarget eventTarget = (EventTarget) node;
                    eventTarget.addEventListener("click", getEventListenerClickLink(), false);
                }
            }
        };
    }

    private EventListener getEventListenerClickLink() {
        return evt -> {
            EventTarget target = evt.getCurrentTarget();
            HTMLAnchorElement anchorElement = (HTMLAnchorElement) target;
            String href = anchorElement.getHref();
            //handle opening URL outside JavaFX WebView
            System.out.println(href);
            evt.preventDefault();
            Desktop desktop = Desktop.isDesktopSupported() ? Desktop.getDesktop() : null;
            if (desktop != null && desktop.isSupported(Desktop.Action.BROWSE)) {
                try {
                    desktop.browse(new URI(href));
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        };
    }

    public void updateContent(Card card, String data) {
        DBHelper.updateContent(card.getId(), data);
    }

    public void log(String message) {
        System.out.println(message);
    }

}
