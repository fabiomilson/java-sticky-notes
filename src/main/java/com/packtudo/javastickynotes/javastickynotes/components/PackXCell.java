package com.packtudo.javastickynotes.javastickynotes.components;

import java.net.URISyntaxException;

import org.kordamp.bootstrapfx.BootstrapFX;

import com.packtudo.javastickynotes.javastickynotes.events.RemoveCardEvent;
import com.packtudo.javastickynotes.javastickynotes.events.ToggleCardEvent;
import com.packtudo.javastickynotes.javastickynotes.model.Card;
import com.packtudo.javastickynotes.javastickynotes.util.DBHelper;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListCell;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.Priority;

public class PackXCell extends ListCell<Card> {

    HBox hbox = new HBox();
    Label label = new Label("(empty)");
    Pane pane = new Pane();
    ImageButton toggleButton;
    ImageButton removeButton;
    Card lastItem;
    Image zoiAberto;
    Image zoiFechado;
    Image trashCan;

    public PackXCell() {
        super();

        try {
            zoiAberto = new Image(
                getClass().getClassLoader().getResource("extra-sources/imgs/eye-icon.png").toURI().toString());

            zoiFechado = new Image(
                getClass().getClassLoader().getResource("extra-sources/imgs/eye-closed.png").toURI().toString());

            trashCan = new Image(
                getClass().getClassLoader().getResource("extra-sources/imgs/trash.png").toURI().toString());
        } catch (URISyntaxException e) {
            throw new RuntimeException(e);
        }

        toggleButton = new ImageButton(zoiAberto, 31, 34);
        removeButton = new ImageButton(trashCan, 31, 34);


     // this.getStylesheets().add(BootstrapFX.bootstrapFXStylesheet());
        hbox.getChildren().addAll(label, pane, toggleButton, removeButton);
        HBox.setHgrow(pane, Priority.ALWAYS);

        toggleButton.setOnMouseClicked(event -> {
            lastItem = DBHelper.toggleVisible(lastItem);
            fireEvent(new ToggleCardEvent(lastItem));
            addClassButtonToggle();
            System.out.println(lastItem + " : " + event);
        });

        removeButton.setOnAction(event -> {
            fireEvent(new RemoveCardEvent(lastItem));
        });

        this.getStyleClass().add("remove-focus");
        this.getStyleClass().add("list-cell");
        this.getStylesheets().add(this.getClass().getClassLoader().getResource("extra-sources/css/geral.css").toExternalForm());  // add the CSS stylesheet

    }

    private void addClassButtonToggle() {
        if (lastItem.getVisivel()) {
            toggleButton.changeImage(zoiAberto);
        } else {
            toggleButton.changeImage(zoiFechado);
        }
    }

    @Override
    protected void updateItem(Card item, boolean empty) {
        super.updateItem(item, empty);
        setText(null);  // No text in label of super class
        if (empty) {
            lastItem = null;
            setGraphic(null);
        } else {
            lastItem = item;
            addClassButtonToggle();
            label.setText(item != null ? String.valueOf(item) : "<null>");
            label.setPadding(new Insets(10, 5, 5,5));
            setGraphic(hbox);
        }
    }
}
