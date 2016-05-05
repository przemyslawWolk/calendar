import service.ICalendarDao;
import service.SQLiteCalendarDaoImpl;

public class SQLiteCalendarDaoImplTest extends AbstractCalendarDaoTest {
    @Override
    protected ICalendarDao getDao() {
        return new SQLiteCalendarDaoImpl();
    }
}
