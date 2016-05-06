package service;

import models.CalendarNote;

import java.util.HashMap;

public class MapCalendarDaoImpl implements ICalendarDao {
    HashMap<String, String> hashMap = new HashMap();

    @Override
    public void create(CalendarNote calendarNote) {
        hashMap.put(calendarNote.getDate(), calendarNote.getNote());
    }

    @Override
    public CalendarNote read(String keyDate) {
        CalendarNote cn = new CalendarNote();
        cn.setDate(keyDate);
        cn.setNote(hashMap.get(keyDate));
        if (cn.getNote() == null) {
            cn = null;
        }
        return cn;
    }

    @Override
    public void update(CalendarNote calendarNote) {
        hashMap.put(calendarNote.getDate(), calendarNote.getNote());
    }

    @Override
    public void delete(CalendarNote calendarNote) {
        hashMap.remove(calendarNote.getDate());
    }
}
