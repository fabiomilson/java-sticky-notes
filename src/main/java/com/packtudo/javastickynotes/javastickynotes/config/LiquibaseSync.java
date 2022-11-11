package com.packtudo.javastickynotes.javastickynotes.config;

import java.sql.Connection;
import java.util.HashMap;
import java.util.Map;

import liquibase.Liquibase;
import liquibase.Scope;
import liquibase.database.Database;
import liquibase.database.DatabaseFactory;
import liquibase.database.jvm.JdbcConnection;
import liquibase.resource.ClassLoaderResourceAccessor;

public class LiquibaseSync {

    public static void sync() throws Exception {
        try(final Connection connection = SQLiteJDBCDriverConnection.connect()){
            final Map<String, Object> config = new HashMap<>();
            Scope.child(config, () -> {
                final Database database = DatabaseFactory.getInstance().findCorrectDatabaseImplementation(new JdbcConnection(connection));
                final Liquibase liquibase = new liquibase.Liquibase("db-scripts/master-changelog.xml", new ClassLoaderResourceAccessor(), database);
                liquibase.update();
            });
        }
    }
}
