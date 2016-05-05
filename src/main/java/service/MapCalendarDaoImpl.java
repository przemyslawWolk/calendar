package service;

import models.CalendarNote;

import java.util.HashMap;

public class MapCalendarDaoImpl implements ICalendarDao {
    HashMap<String, String> hashMap = new HashMap();

    public void create(CalendarNote calendarNote) {
        hashMap.put(keyDate, valueNote);
    }

    public CalendarNote read(String keyDate) {
        return hashMap.get(keyDate);
    }

    public void update(CalendarNote calendarNote) {
        hashMap.put(keyDate, valueNote);
    }

    public void delete(CalendarNote calendarNote) {
        hashMap.remove(keyDate);
    }
}
