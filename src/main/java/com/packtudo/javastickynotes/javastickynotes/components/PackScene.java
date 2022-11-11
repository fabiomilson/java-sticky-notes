package com.packtudo.javastickynotes.javastickynotes.components;

import org.kordamp.bootstrapfx.BootstrapFX;

import javafx.beans.NamedArg;
import javafx.beans.binding.BooleanBinding;
import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.scene.Parent;
import javafx.scene.Scene;
import lombok.Getter;

@Getter
public class PackScene extends Scene {

    private final int idCard;
    public BooleanProperty upPressed = new SimpleBooleanProperty();
    public BooleanProperty rightPressed = new SimpleBooleanProperty();

    public BooleanBinding anyPressed = rightPressed.and(upPressed);

    public PackScene(final int idCard, @NamedArg("root") Parent root) {
        super(root);
        this.idCard = idCard;
        getStylesheets().add(BootstrapFX.bootstrapFXStylesheet());
    }
}
