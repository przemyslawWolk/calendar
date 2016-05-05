import models.CalendarNote;
import org.junit.Before;
import org.junit.Test;
import service.ICalendarDao;
import service.SQLiteCalendarDaoImpl;

public abstract class AbstractCalendarDaoTest {
    private static final String FIRST_KEY = "Maj 2016 Czw 19";
    private static final String FIRST_VALUE = "przykladowa notateczka 1";
    private static final String FIRST_UPDATE = "zmieniona notateczka 1";

    private static final String SECOND_KEY = "Maj 2016 Pt 27";
    private static final String SECOND_VALUE = "przykladowa notateczka 2";
    private static final String SECOND_UPDATE = "zmieniona notateczka 2";

    private static final String THIRD_KEY = "Maj 2016 Pt 6";
    private static final String THIRD_VALUE = "przykladowa notateczka 3";
    private static final String THIRD_UPDATE = "zmieniona notateczka 3";

    private static final CalendarNote FIRST_NOTE = new CalendarNote(FIRST_KEY, FIRST_VALUE);
    private static final CalendarNote SECOND_NOTE = new CalendarNote(SECOND_KEY, SECOND_VALUE);
    private static final CalendarNote THIRD_NOTE = new CalendarNote(THIRD_KEY, THIRD_VALUE);

    private static ICalendarDao dao;

    @Before
    public void setUp() {
        dao = getDao();
        dao.create(FIRST_NOTE);
        dao.create(SECOND_NOTE);
        dao.create(THIRD_NOTE);
    }

    @Test
    public void createReadTest() {

        CalendarNote firstResult = dao.read(FIRST_KEY);

        if (!firstResult.getNote().equals(FIRST_VALUE)) {
            throw new AssertionError();
        }

        CalendarNote secondResult = dao.read(SECOND_KEY);
        assert secondResult.getNote().equals(SECOND_VALUE);

        CalendarNote thirdResult = dao.read(THIRD_KEY);
        assert thirdResult.getNote().equals(THIRD_VALUE);

        System.out.println("TEST READ PASSED");
    }

    @Test
    public void updateTest() {

        dao.update(new CalendarNote(FIRST_KEY, FIRST_UPDATE));
        dao.update(new CalendarNote(SECOND_KEY, SECOND_UPDATE));
        dao.update(new CalendarNote(THIRD_KEY, THIRD_UPDATE));

        CalendarNote firstResult = dao.read(FIRST_KEY);
        assert firstResult.getNote().equals(FIRST_UPDATE);

        CalendarNote secondResult = dao.read(SECOND_KEY);
        assert secondResult.getNote().equals(SECOND_UPDATE);

        CalendarNote thirdResult = dao.read(THIRD_KEY);
        assert thirdResult.getNote().equals(THIRD_UPDATE);

        System.out.println("TEST UPDATE PASSED");
    }

    @Test
    public void deleteTest() {

        dao.delete(FIRST_NOTE);
        dao.delete(SECOND_NOTE);

        CalendarNote firstResult = dao.read(FIRST_KEY);
        assert firstResult == null;

        CalendarNote secondResult = dao.read(SECOND_KEY);
        assert secondResult == null;

        CalendarNote thirdResult = dao.read(THIRD_KEY);
        assert thirdResult.getNote().equals(THIRD_VALUE);

        System.out.println("TEST DELETE PASSED");
    }

    protected abstract ICalendarDao getDao();
}

