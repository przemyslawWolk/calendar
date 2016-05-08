package service;

import models.CalendarNote;
import org.hibernate.Session;
import utils.HibernateUtil;

public class HibernateCalendarDaoImpl implements ICalendarDao {

    @Override
    public void create(CalendarNote calendarNote) {
        Session session = HibernateUtil.getSessionFactory().getCurrentSession();
        session.beginTransaction();
        session.save(calendarNote);
        session.getTransaction().commit();
    }

    @Override
    public CalendarNote read(String keyDate) {
        Session session = HibernateUtil.getSessionFactory().getCurrentSession();
        session.beginTransaction();
        CalendarNote cn = session.get(CalendarNote.class, keyDate);
        session.getTransaction().commit();
        return cn;
    }

    @Override
    public void update(CalendarNote calendarNote) {
        Session session = HibernateUtil.getSessionFactory().getCurrentSession();
        session.beginTransaction();
        session.update(calendarNote);
        session.getTransaction().commit();
    }

    @Override
    public void delete(CalendarNote calendarNote) {
        Session session = HibernateUtil.getSessionFactory().getCurrentSession();
        session.beginTransaction();
        session.delete(calendarNote);
        session.getTransaction().commit();
    }
}
