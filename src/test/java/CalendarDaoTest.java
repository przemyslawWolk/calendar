import service.ICalendarDao;
import service.SQLiteCalendarDaoImpl;

public class CalendarDaoTest {

    public static final String FIRST_KEY = "Maj 2016 Czw 19";
    public static final String FIRST_VALUE = "przykladowa notateczka 1";
    public static final String SECOND_KEY = "Maj 2016 Pt 27";
    public static final String SECOND_VALUE = "przykladowa notateczka 2";
    public static final String THIRD_KEY = "Maj 2016 Pt 6";
    public static final String THIRD_VALUE = "przykladowa notateczka 3";

    public static final String FIRST_UPDATE = "zmieniona notateczka 1";
    public static final String SECOND_UPDATE = "zmieniona notateczka 1";
    public static final String THIRD_UPDATE = "zmieniona notateczka 1";
    public static SQLiteCalendarDaoImpl dao;

    public static void main(String[] args) {
        createReadTest();
        updateTest();
        deleteTest();
    }

    public static void createReadTest() {
        dao = new SQLiteCalendarDaoImpl();
        dao.create(FIRST_KEY, FIRST_VALUE);
        dao.create(SECOND_KEY, SECOND_VALUE);
        dao.create(THIRD_KEY, THIRD_VALUE);

        String firstResult = dao.read(FIRST_KEY);

        if (!firstResult.equals(FIRST_VALUE)) {
            throw new AssertionError();
        }

        String secondResult = dao.read(SECOND_KEY);
        assert secondResult.equals(SECOND_VALUE);

        String thirdResult = dao.read(THIRD_KEY);
        assert thirdResult.equals(THIRD_VALUE);

        System.out.println("TEST READ PASSED");
    }

    public static void updateTest() {
        dao.update(FIRST_KEY, FIRST_UPDATE);
        dao.update(SECOND_KEY, SECOND_UPDATE);
        dao.update(THIRD_KEY, THIRD_UPDATE);

        String firstResult = dao.read(FIRST_KEY);
        assert firstResult.equals(FIRST_UPDATE);

        String secondResult = dao.read(SECOND_KEY);
        assert secondResult.equals(SECOND_UPDATE);

        String thirdResult = dao.read(THIRD_KEY);
        assert thirdResult.equals(THIRD_UPDATE);

        System.out.println("TEST UPDATE PASSED");
    }

    public static void deleteTest() {
        dao.delete(FIRST_KEY);
        dao.delete(SECOND_KEY);
        dao.delete(THIRD_KEY);

        String firstResult = dao.read(FIRST_KEY);
        assert !firstResult.equals(FIRST_UPDATE);

        String secondResult = dao.read(SECOND_KEY);
        assert !secondResult.equals(SECOND_UPDATE);

        String thirdResult = dao.read(THIRD_KEY);
        assert !thirdResult.equals(THIRD_UPDATE);

        System.out.println("TEST DELETE PASSED");
    }
}
