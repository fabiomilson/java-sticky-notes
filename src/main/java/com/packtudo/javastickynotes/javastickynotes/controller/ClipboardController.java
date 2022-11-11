package com.packtudo.javastickynotes.javastickynotes.controller;

import static org.apache.commons.lang3.BooleanUtils.isFalse;
import static org.apache.commons.lang3.RandomStringUtils.randomAlphabetic;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.Base64;

import javax.imageio.ImageIO;

import javafx.embed.swing.SwingFXUtils;
import javafx.event.EventHandler;
import javafx.scene.image.Image;
import javafx.scene.input.Clipboard;
import javafx.scene.input.DataFormat;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyCodeCombination;
import javafx.scene.input.KeyCombination;
import javafx.scene.input.KeyEvent;
import javafx.scene.web.WebEngine;

public class ClipboardController {

    private final KeyCombination KEY_COMBINATION_CONTROL_DOWN_AND_V = new KeyCodeCombination(KeyCode.V,
        KeyCombination.CONTROL_DOWN);

    public EventHandler<KeyEvent> controlDownAndV(final WebEngine engine) {
        return keyEvent -> {
            if (KEY_COMBINATION_CONTROL_DOWN_AND_V.match(keyEvent)) {
                final Clipboard clipboard = Clipboard.getSystemClipboard();
                final boolean isImage = tryPasteImage(engine, clipboard);
                if(isFalse(isImage)) {
                    final boolean isLink = tryPasteLink(engine, clipboard);
                }
            }
        };
    }

    private boolean tryPasteLink(final WebEngine engine, final Clipboard clipboard) {
        final String url = (String) clipboard.getContent(DataFormat.PLAIN_TEXT);
        if(url.substring(0 ,4).contains("http") || url.substring(0, 3).contains("www")) {
            final String command = String.format(
                "$('#summernote').summernote('createLink', {text: '%s', url: '%s', isNewWindow: true});",
                url,
                url);
            engine.executeScript(command);
            return true;
        }
        return false;
    }

    private boolean tryPasteImage(final WebEngine engine, final Clipboard clipboard) {
        try(final ByteArrayOutputStream s = new ByteArrayOutputStream()) {
            ImageIO.write(SwingFXUtils.fromFXImage((Image) clipboard.getContent(DataFormat.IMAGE), null),
                "png", s);
            final byte[] res = s.toByteArray();
            final byte[] encode = Base64.getEncoder().encode(res);
            final String image = new String(encode);
            final String fileName = randomAlphabetic(20);
            final String command = String.format(
                "$('#summernote').summernote('insertImage','data:image/png;base64,%s','%s.png');",
                image,
                fileName);
            engine.executeScript(command);
            return true;
        } catch (Exception e) {
            System.out.println(e);
            return false;
        }
    }
}
