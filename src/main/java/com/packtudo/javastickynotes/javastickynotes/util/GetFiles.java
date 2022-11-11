package com.packtudo.javastickynotes.javastickynotes.util;

import java.net.URISyntaxException;

public class GetFiles {

    public static String indexHtml() {
        try {
            return GetFiles.class.getClassLoader().getResource("extra-sources/index.html").toURI().toString();
        } catch (URISyntaxException e) {
            throw new RuntimeException(e);
        }
    }
}
