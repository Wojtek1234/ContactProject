package pl.wmaciejewski.contactproject;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.test.AndroidTestCase;
import android.test.RenamingDelegatingContext;

import java.io.ByteArrayOutputStream;
import java.util.ArrayList;
import java.util.List;

import pl.wmaciejewski.contactproject.database.DatabaseProvider;
import pl.wmaciejewski.contactproject.database.dao.GroupDAO;
import pl.wmaciejewski.contactproject.database.dao.PersonDAO;
import pl.wmaciejewski.contactproject.database.entitys.Group;
import pl.wmaciejewski.contactproject.database.entitys.Person;

/**
 * Created by w.maciejewski on 2014-10-29.
 */
public class PersonDAOTest extends AndroidTestCase {


    private static String TEST_NAME = "grupa1";
    private GroupDAO groupDao;
    private PersonDAO personDAO;
    private DatabaseProvider databaseProvider;
    private Context context;

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

    }

    @Override
    protected void tearDown() throws Exception {
        super.tearDown();
        this.groupDao.removeAll();
        this.personDAO.removeAll();

        this.databaseProvider.close();
    }

    public void testAddPerson() {
        Bitmap icon = BitmapFactory.decodeResource(context.getResources(),
                R.drawable.no_photo);
        this.groupDao.create(TEST_NAME);
        Group group = this.groupDao.getByName(TEST_NAME).get(0);
        Person person = new Person("Janek", "Wisniewski", "janek@wisniewski.pl", "666666666", null, setPhpotoAsByteArray(icon), group.getId());
        personDAO.create(person);

        Person person1 = personDAO.getAll().get(0);

        assertEquals(person.getName(), person1.getName());
        assertEquals(person.getSurname(), person1.getSurname());

        assertEquals(person.getEmail(), person1.getEmail());
    }

    public void testGetPersonsByGroup() {
        Bitmap icon = BitmapFactory.decodeResource(context.getResources(),
                R.drawable.no_photo);
        this.groupDao.create(TEST_NAME);
        this.groupDao.create(TEST_NAME + "1");

        List<Group> groups = this.groupDao.getAll();
        List<Person> persons = new ArrayList<Person>();
        persons.add(new Person("Janek", "Wisniewski", "janek@wisniewski.pl", "666666666", null, setPhpotoAsByteArray(icon), groups.get(0).getId()));
        persons.add(new Person("Janek1", "Wisniewski", "janek@wisniewski.pl", "666666666", null, setPhpotoAsByteArray(icon), groups.get(0).getId()));
        persons.add(new Person("Janek2", "Wisniewski", "janek@wisniewski.pl", "666666666", null, setPhpotoAsByteArray(icon), groups.get(1).getId()));
        persons.add(new Person("Janek3", "Wisniewski", "janek@wisniewski.pl", "666666666", null, setPhpotoAsByteArray(icon), groups.get(1).getId()));


        for (Person person : persons) {
            this.personDAO.create(person);
        }

        List<Person> group1Persons = this.personDAO.getByGropID(groups.get(0).getId());
        List<Person> group2Persons = this.personDAO.getByGropID(groups.get(1).getId());

        assertTrue(group1Persons.size() == 2);
        assertTrue(group2Persons.size() == 2);

        assertTrue("Janek".equals(group1Persons.get(0).getName()));
        assertEquals((group1Persons.get(1).getName()), "Janek1");

        assertEquals((group2Persons.get(0).getName()), "Janek2");
        assertEquals((group2Persons.get(1).getName()), "Janek3");

    }

    public void testGetOrder(){
        Bitmap icon = BitmapFactory.decodeResource(context.getResources(),
                R.drawable.no_photo);
        List<Person> persons = new ArrayList<Person>();
        persons.add(new Person("D",null,null,null,null,setPhpotoAsByteArray(icon),null));
        persons.add(new Person("a",null,null,null,null,setPhpotoAsByteArray(icon),null));
        persons.add(new Person("B",null,null,null,null,setPhpotoAsByteArray(icon),null));
        persons.add(new Person("b",null,null,null,null,setPhpotoAsByteArray(icon),null));
        for (Person person : persons) {
            this.personDAO.create(person);
        }

        List<Person> persons1 = this.personDAO.getAll();
        assertEquals(persons1.get(0).getName(),"a");
        assertEquals(persons1.get(1).getName(),"B");
        assertEquals(persons1.get(2).getName(),"b");
        assertEquals(persons1.get(3).getName(),"D");


    }


    private byte[] setPhpotoAsByteArray(Bitmap bmp) {
        ByteArrayOutputStream stream = new ByteArrayOutputStream();
        bmp.compress(Bitmap.CompressFormat.PNG, 100, stream);
        return stream.toByteArray();
    }
}
