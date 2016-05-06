package service;

import models.CalendarNote;

import java.util.ArrayList;
import java.util.Calendar;

public class CalendarService {
    private ICalendarDao calendarDao;
    private ArrayList<String> monthList = new ArrayList<>();
    private Calendar cal = Calendar.getInstance();
    private Calendar presentCal = Calendar.getInstance();
    private int numberOfYear = presentCal.get(Calendar.YEAR);
    private int month;
    private Integer year;
    private int monthNumber = presentCal.get(Calendar.MONTH);

    public CalendarService(ICalendarDao calendarDao) {
        this.calendarDao = calendarDao;
    }

    public void create(CalendarNote calendarNote) {
        calendarDao.create(calendarNote);
    }

    public CalendarNote read(String keyDate) {
        return calendarDao.read(keyDate);
    }

    public void update(CalendarNote calendarNote) {
        calendarDao.update(calendarNote);
    }

    public void delete(CalendarNote calendarNote) {
        calendarDao.delete(calendarNote);
    }

    public String nameOfMonth() {
        cal.set(numberOfYear, monthNumber, 1);
        month = cal.get(Calendar.MONTH);
        year = cal.get(Calendar.YEAR);
        String yearString = year.toString();

        monthList.add("Styczeń");
        monthList.add("Luty");
        monthList.add("Marzec");
        monthList.add("Kwiecień");
        monthList.add("Maj");
        monthList.add("Czerwiec");
        monthList.add("Lipiec");
        monthList.add("Sierpień");
        monthList.add("Wrzesień");
        monthList.add("Październik");
        monthList.add("Listopad");
        monthList.add("Grudzień");

        return monthList.get(month) + " " + yearString;
    }

    public void increaseMonthByOne() {
        ++monthNumber;
    }

    public void decreaseMonthByOne() {
        --monthNumber;
    }

    public int getMonthNumber() {
        return monthNumber;
    }

    public int getNumberOfYear() {
        return numberOfYear;
    }

    public Calendar getPresentCal() {
        return presentCal;
    }

    public Calendar getCal() {
        return cal;
    }
}
