package service;

import models.CalendarNote;

public interface ICalendarDao {
    void create(CalendarNote calendarNote);
    CalendarNote read(String keyDate);
    void update(CalendarNote calendarNote);
    void delete(CalendarNote calendarNote);
}
