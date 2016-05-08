import org.hibernate.CacheMode;
import org.junit.After;
import org.junit.Before;
import service.HibernateCalendarDaoImpl;
import service.ICalendarDao;
import utils.HibernateUtil;

public class HibernateCalendarDaoImplTest extends AbstractCalendarDaoTest {
    @Override
    protected ICalendarDao getDao() {
        return new HibernateCalendarDaoImpl();
    }

    @After
    public void clearDatabase() {
        getDao().delete(FIRST_NOTE);
        getDao().delete(SECOND_NOTE);
        getDao().delete(THIRD_NOTE);
    }
}
