package com.packtudo.javastickynotes.javastickynotes.model;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder(toBuilder = true)
public class Card {

    private Integer id;
    private String conteudo;
    private String titulo;
    private Double x;
    private Double y;
    private Boolean visivel;

    @Override
    public String toString() {
        return id + " - " + getTitulo();
    }

    public String getTituloFormatado() {
        return id + " - " + getTitulo();
    }
}
