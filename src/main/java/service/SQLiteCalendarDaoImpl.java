package service;


import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class SQLiteCalendarDaoImpl implements ICalendarDao {
    public static final String DRIVER = "org.sqlite.JDBC";
    public static final String DB_URL = "jdbc:sqlite:database.db";

    private Connection conn;
    private Statement stat;

    public SQLiteCalendarDaoImpl() {
        try {
            Class.forName(SQLiteCalendarDaoImpl.DRIVER);
        } catch (ClassNotFoundException e) {
            System.err.println("Brak sterownika JDBC " + e.getMessage());
        }
        try {
            conn = DriverManager.getConnection(DB_URL);
            stat = conn.createStatement();
        } catch (SQLException e) {
            System.err.println("Blad przy tworzeniu tabeli " + e.getMessage());
        }
        dropTables();
        createTables();
    }

    private boolean dropTables() {

        String dropTablesQuery = "DROP TABLE notes";
        try {
            stat.execute(dropTablesQuery);
        } catch (SQLException e) {
            System.err.println("Blad przy usuwaniu tabeli " + e.getMessage());
            return false;
        }
        return false;
    }

    public boolean createTables() {

        String createNoteTable = "CREATE TABLE IF NOT EXISTS notes (date varchar(255) PRIMARY KEY, note varchar(255))";
        try {
            stat.execute(createNoteTable);
        } catch (SQLException e) {
            System.err.println("Blad przy tworzeniu tabeli " + e.getMessage());
            return false;
        }
        return true;
    }

    @Override
    public void create(String keyDate, String valueNote) {
        try {
            PreparedStatement prepStmt = conn.prepareStatement(
                    "insert into notes values (?, ?);");
            prepStmt.setString(1, keyDate);
            prepStmt.setString(2, valueNote);
            prepStmt.execute();
        } catch (SQLException e) {
            System.err.println("Blad przy wypozyczaniu " + e.getSQLState());
        }
    }

    @Override
    public String read(String keyDate) {
        String note = "";
        try {
            ResultSet result = stat.executeQuery("SELECT * FROM notes WHERE date='" + keyDate + "'");
            while (result.next()) {
                note = result.getString("note");
            }
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
        return note;
    }

    @Override
    public void update(String keyDate, String valueNote) {
        try {
            PreparedStatement prepStmt = conn.prepareStatement(
                    "UPDATE notes SET note='" + valueNote +"' WHERE date='" + keyDate + "'");
            prepStmt.execute();
        } catch (SQLException e) {
            System.err.println("Blad przy updatowaniu " + e.getSQLState());
        }
    }

    @Override
    public void delete(String keyDate) {
        try {
            PreparedStatement prepStmt = conn.prepareStatement(
                    "DELETE FROM notes WHERE date='" + keyDate +"'");
            prepStmt.execute();
        } catch (SQLException e) {
            System.err.println("Blad przy updatowaniu " + e.getSQLState());
        }
    }
}
