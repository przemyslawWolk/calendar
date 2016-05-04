package service;

public interface ICalendarDao {
    void create(String keyDate, String valueNote);
    String read(String keyDate);
    void update(String keyDate, String valueNote);
    void delete(String keyDate);
}
