package mipt.hw.service.db;

import jakarta.annotation.PostConstruct;
import lombok.AllArgsConstructor;
import mipt.hw.Main;
import org.springframework.stereotype.Component;

import javax.sql.DataSource;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Objects;
import java.util.stream.Collectors;

@Component
@AllArgsConstructor
public class Database {

    @FunctionalInterface
    public interface SQLFunction<T, R> {
        R apply(T object) throws SQLException;
    }

    @FunctionalInterface
    public interface SQLConsumer<T> {
        void accept(T object) throws SQLException;
    }

    @PostConstruct
    public void create() throws SQLException, IOException {
        String sqlCreationCommand = getSqlCreationCommand();
        preparedStatement(sqlCreationCommand, initDb -> {
            initDb.execute();
        });
    }

    public void connection(Database.SQLConsumer<? super Connection> consumer) throws SQLException {
        Objects.requireNonNull(consumer);
        try (Connection conn = connectionPool.getConnection()) {
            consumer.accept(conn);
        }
    }

    public <R> R connection(Database.SQLFunction<? super Connection, ? extends R> function) throws SQLException {
        Objects.requireNonNull(function);
        try (Connection conn = connectionPool.getConnection()) {
            return function.apply(conn);
        }
    }

    public <R> R statement(Database.SQLFunction<? super Statement, ? extends R> function) throws SQLException {
        Objects.requireNonNull(function);
        return connection(conn -> {
            try (Statement stmt = conn.createStatement()) {
                return function.apply(stmt);
            }
        });
    }

    public void statement(Database.SQLConsumer<? super Statement> consumer) throws SQLException {
        Objects.requireNonNull(consumer);
        connection(conn -> {
            try (Statement stmt = conn.createStatement()) {
                consumer.accept(stmt);
            }
        });
    }

    public <R> R preparedStatement(String sql, Database.SQLFunction<? super PreparedStatement, ? extends R> function) throws SQLException {
        Objects.requireNonNull(function);
        return connection(conn -> {
            try (PreparedStatement stmt = conn.prepareStatement(sql)) {
                return function.apply(stmt);
            }
        });
    }

    public void preparedStatement(String sql, Database.SQLConsumer<? super PreparedStatement> consumer) throws SQLException {
        Objects.requireNonNull(consumer);
        connection(conn -> {
            try (PreparedStatement stmt = conn.prepareStatement(sql)) {
                consumer.accept(stmt);
            }
        });
    }

    ////////////////////////////////////////////////////////////////////////////
    ////////                       Internals.                           ////////
    ////////////////////////////////////////////////////////////////////////////

    private String getSqlCreationCommand() throws IOException {
        String dbCreateScriptFilename = "dbcreate.sql";
        try (InputStream src = Main.class.getResourceAsStream(dbCreateScriptFilename);
             InputStream resource = Objects.requireNonNull(src);
             InputStreamReader isr = new InputStreamReader(resource, StandardCharsets.UTF_8);
             BufferedReader br = new BufferedReader(isr)) {
            return br.lines().collect(Collectors.joining("\n"));
        }
    }

    private final DataSource connectionPool;
}
