package com.packtudo.javastickynotes.javastickynotes.util;

import static com.packtudo.javastickynotes.javastickynotes.util.constants.ConstantNumber.UM;
import static java.lang.Boolean.TRUE;
import static java.util.Objects.nonNull;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.packtudo.javastickynotes.javastickynotes.config.SQLiteJDBCDriverConnection;
import com.packtudo.javastickynotes.javastickynotes.model.Card;

public class DBHelper {

    private static final Connection conx = SQLiteJDBCDriverConnection.connect();

    public static void updatePosition(int id, double x, double y) {
        run(() -> {
            try (final PreparedStatement statement = conx.prepareStatement(
                "update card set x = ?, y = ? where id = ?")) {
                statement.setDouble(1, x);
                statement.setDouble(2, y);
                statement.setDouble(3, id);
                statement.executeUpdate();
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        });
    }

    private static void run(final Runnable runnable) {
        new Thread(runnable).start();
    }

    public static List<Card> listAllVisibleCards() {
        return listAllCards("where visivel = 1");
    }

    public static void updateContent(final Integer id, final String content) {
        try (final PreparedStatement statement = conx.prepareStatement("update card set conteudo = ? where id = ?")) {
            statement.setString(1, content);
            statement.setInt(2, id);
            statement.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public static Card createNewCard(final Card baseCard) {
        try (PreparedStatement stmtmax = conx.prepareStatement("select max(ID) from CARD")) {
            try (ResultSet resultSetMax = stmtmax.executeQuery()) {
                if (resultSetMax.next()) {
                    final Card newCard = Card.builder()
                        .id(resultSetMax.getInt(1) + 1)
                        .conteudo("")
                        .x(baseCard.getX() - 50)
                        .y(baseCard.getY())
                        .visivel(TRUE)
                        .titulo("Card")
                        .build();
                    try (PreparedStatement statement = conx.prepareStatement(
                        "INSERT INTO CARD VALUES (?, ?, ?, ?, ?, ?)")) {
                        statement.setInt(1, newCard.getId());
                        statement.setString(2, newCard.getConteudo());
                        statement.setDouble(3, newCard.getX());
                        statement.setDouble(4, newCard.getY());
                        statement.setInt(5, newCard.getVisivel() ? 1 : 0);
                        statement.setString(6, newCard.getTitulo());
                        statement.executeUpdate();
                        return newCard;
                    }
                }
            }
            return null;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public static void createNewHistoryCard(final Card card) {
        try (PreparedStatement statement = conx.prepareStatement(
            "INSERT INTO CARD_HISTORICO VALUES (?, ?, ?, ?, ?, ?)")) {
            statement.setInt(1, card.getId());
            statement.setString(2, card.getConteudo());
            statement.setDouble(3, card.getX());
            statement.setDouble(4, card.getY());
            statement.setInt(5, card.getVisivel() ? 1 : 0);
            statement.setString(6, card.getTitulo());
            statement.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public static List<Card> listAllCards() {
        return listAllCards(null);
    }
    private static List<Card> listAllCards(final String where) {
        final List<Card> cards = new ArrayList<>();
        final String query = "select * from card " + (nonNull(where) ? where : "");
        try (final PreparedStatement statement = conx.prepareStatement(query)) {
            final ResultSet resultSet = statement.executeQuery();
            while(resultSet.next()) {
                cards.add(
                    Card.builder()
                        .id(resultSet.getInt("ID"))
                        .conteudo(resultSet.getString("CONTEUDO"))
                        .x(resultSet.getDouble("X"))
                        .y(resultSet.getDouble("Y"))
                        .titulo(resultSet.getString("TITULO"))
                        .visivel(UM.equals(resultSet.getInt("VISIVEL")))
                        .build()
                );
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return cards;
    }

    public static Card toggleVisible(final Card card) {
        try (final PreparedStatement statement = conx.prepareStatement("update card set visivel = ? where id = ?")) {
            final boolean toggle = !card.getVisivel();
            final int visivel = toggle ? 1 : 0;
            statement.setInt(1, visivel);
            statement.setInt(2, card.getId());
            statement.executeUpdate();
            return card.toBuilder().visivel(toggle).build();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public static void remove(final Card card) {
        createNewHistoryCard(card);
        try (final PreparedStatement statement = conx.prepareStatement("delete from card where id = ?")) {
            final boolean toggle = !card.getVisivel();
            statement.setInt(1, card.getId());
            statement.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
