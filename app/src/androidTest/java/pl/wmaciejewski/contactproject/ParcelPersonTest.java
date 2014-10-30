package pl.wmaciejewski.contactproject;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Parcel;
import android.test.AndroidTestCase;
import android.test.RenamingDelegatingContext;

import pl.wmaciejewski.contactproject.database.DatabaseProvider;
import pl.wmaciejewski.contactproject.database.dao.GroupDAO;
import pl.wmaciejewski.contactproject.database.dao.PersonDAO;
import pl.wmaciejewski.contactproject.database.entitys.Person;
import pl.wmaciejewski.contactproject.modelView.ParcelPerson;

import static pl.wmaciejewski.contactproject.PersonDAOTest.setPhpotoAsByteArray;

/**
 * Created by w.maciejewski on 2014-10-30.
 */
public class ParcelPersonTest extends AndroidTestCase {

    private RenamingDelegatingContext context;
    private DatabaseProvider databaseProvider;
    private PersonDAO personDAO;
    private GroupDAO groupDao;

    @Override
    protected void setUp() throws Exception {
        super.setUp();

        this.context = new RenamingDelegatingContext(getContext(), "test_");
        this.databaseProvider = new DatabaseProvider(context);
        this.databaseProvider.open();
        this.groupDao = new GroupDAO(databaseProvider.getDatabase());
        this.groupDao.removeAll();
        this.personDAO = new PersonDAO(databaseProvider.getDatabase());
        this.personDAO.removeAll();
        Bitmap icon = BitmapFactory.decodeResource(context.getResources(),
                R.drawable.no_photo);
        Person person=new Person("Janek", "Wisniewski", "janek@wisniewski.pl", "666666666", null, setPhpotoAsByteArray(icon), null);
        personDAO.create(person);

    }

    @Override
    protected void tearDown() throws Exception {
        super.tearDown();
        personDAO.removeAll();
        this.databaseProvider.close();

    }

    public void testParcelInput(){
        personDAO.getByName("Janek").get(0);
        ParcelPerson parcelPerson=new ParcelPerson(personDAO.getByName("Janek").get(0));
        Parcel parcel = Parcel.obtain();
        parcelPerson.writeToParcel(parcel, 0);
        parcel.setDataPosition(0);
        ParcelPerson createFromParcel = ParcelPerson.CREATOR.createFromParcel(parcel);

        assertEquals(parcelPerson.getPerson(), createFromParcel.getPerson());
    }
}
