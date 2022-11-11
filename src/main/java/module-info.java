module com.packtudo.javastickynotes.javastickynotes {
    requires javafx.controls;
    requires javafx.fxml;
    requires javafx.media;
    requires javafx.web;
    requires javafx.graphics;

    requires org.junit.jupiter.api;

    requires org.kordamp.bootstrapfx.core;
    requires java.sql;
    requires liquibase.core;
    requires lombok;

    requires java.desktop;

    requires org.kordamp.ikonli.core;
    requires org.kordamp.ikonli.javafx;
    requires org.kordamp.ikonli.lineawesome;
    requires jdk.jsobject;
    requires javafx.swing;

    requires jdk.xml.dom;
    requires org.apache.commons.lang3;

    opens com.packtudo.javastickynotes.javastickynotes to javafx.fxml;
    exports com.packtudo.javastickynotes.javastickynotes;
    exports com.packtudo.javastickynotes.javastickynotes.config;
    opens com.packtudo.javastickynotes.javastickynotes.config to javafx.fxml;
    exports com.packtudo.javastickynotes.javastickynotes.util;
    opens com.packtudo.javastickynotes.javastickynotes.util to javafx.fxml;
    exports com.packtudo.javastickynotes.javastickynotes.controller;
    opens com.packtudo.javastickynotes.javastickynotes.controller to javafx.fxml;
    exports com.packtudo.javastickynotes.javastickynotes.components to javafx.graphics;

}
