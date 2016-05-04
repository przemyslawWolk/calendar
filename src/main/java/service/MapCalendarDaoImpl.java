package service;

import java.util.HashMap;

public class MapCalendarDaoImpl implements ICalendarDao {
    HashMap<String, String> hashMap = new HashMap();

    public void create(String keyDate, String valueNote) {
        hashMap.put(keyDate, valueNote);
    }

    public String read(String keyDate) {
        return hashMap.get(keyDate);
    }

    public void update(String keyDate, String valueNote) {
        hashMap.put(keyDate, valueNote);
    }

    public void delete(String keyDate) {
        hashMap.remove(keyDate);
    }
}
