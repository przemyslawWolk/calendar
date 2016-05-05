import service.ICalendarDao;
import service.MapCalendarDaoImpl;

public class MapCalendarDaoImplTest extends AbstractCalendarDaoTest {
    @Override
    protected ICalendarDao getDao() {
        return new MapCalendarDaoImpl();
    }
}
