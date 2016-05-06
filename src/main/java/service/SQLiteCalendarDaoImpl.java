package service;


import models.CalendarNote;

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
        dropTables(true);
        createTables();
    }

    private boolean dropTables(boolean enable) {
        if (enable) {
            String dropTablesQuery = "DROP TABLE notes";
            try {
                stat.execute(dropTablesQuery);
            } catch (SQLException e) {
                System.err.println("Blad przy usuwaniu tabeli " + e.getMessage());
            }
            return false;
        } else {
            return false;
        }
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
    public void create(CalendarNote calendarNote) {
        try {
            PreparedStatement prepStmt = conn.prepareStatement(
                    "insert into notes values (?, ?);");
            prepStmt.setString(1, calendarNote.getDate());
            prepStmt.setString(2, calendarNote.getNote());
            prepStmt.execute();
        } catch (SQLException e) {
            System.err.println("Blad przy wypozyczaniu " + e.getSQLState());
        }
    }

    @Override
    public CalendarNote read(String keyDate) {
        CalendarNote cn = null;
        String note = "";
        try {
            ResultSet result = stat.executeQuery("SELECT * FROM notes WHERE date='" + keyDate + "'");
            while (result.next()) {
                note = result.getString("note");
                cn = new CalendarNote(keyDate,note);
            }
        } catch (SQLException e) {
            System.err.println("Blad przy odczytywaniu tresci " + e.getSQLState());
            return null;
        }
        return cn;
    }

    @Override
    public void update(CalendarNote calendarNote) {
        try {
            PreparedStatement prepStmt = conn.prepareStatement(
                    "UPDATE notes SET note='" + calendarNote.getNote() + "' WHERE date='" + calendarNote.getDate() + "'");
            prepStmt.execute();
        } catch (SQLException e) {
            System.err.println("Blad przy updatowaniu " + e.getSQLState());
        }
    }

    @Override
    public void delete(CalendarNote calendarNote) {
        try {
            PreparedStatement prepStmt = conn.prepareStatement(
                    "DELETE FROM notes WHERE date='" + calendarNote.getDate() + "'");
            prepStmt.execute();
        } catch (SQLException e) {
            System.err.println("Blad przy updatowaniu " + e.getSQLState());
        }
    }
}
