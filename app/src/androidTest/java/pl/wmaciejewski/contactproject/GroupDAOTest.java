package pl.wmaciejewski.contactproject;

import android.content.Context;
import android.database.sqlite.SQLiteConstraintException;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.test.AndroidTestCase;
import android.test.RenamingDelegatingContext;

import junit.framework.Assert;

import java.io.ByteArrayOutputStream;
import java.util.ArrayList;
import java.util.List;

import pl.wmaciejewski.contactproject.database.DatabaseProvider;
import pl.wmaciejewski.contactproject.database.dao.GroupDAO;
import pl.wmaciejewski.contactproject.database.dao.PersonDAO;
import pl.wmaciejewski.contactproject.database.entitys.Group;
import pl.wmaciejewski.contactproject.database.entitys.Person;

/**
 * Created by w.maciejewski on 2014-10-28.
 */
public class GroupDAOTest extends AndroidTestCase {

    private GroupDAO groupDao;
    private DatabaseProvider databaseProvider;
    private Context context;
    private static String TEST_NAME="grupa1";
    @Override
    protected void setUp() throws Exception {
        super.setUp();



        this.context = new RenamingDelegatingContext(getContext(), "test_");
        this.databaseProvider=new DatabaseProvider(context);
        this.databaseProvider.open();
        this.groupDao=new GroupDAO(databaseProvider.getDatabase());
        this.groupDao.removeAll();

    }

    @Override
    protected void tearDown() throws Exception {
        super.tearDown();
        this.groupDao.removeAll();
        this.databaseProvider.close();
    }



    public void testAddGroup(){
        this.groupDao.create(TEST_NAME);
        Group group=this.groupDao.getByName(TEST_NAME).get(0);
        Group group1=this.groupDao.getAll().get(0);

        assertEquals(group.getGroupName(),group1.getGroupName());
        assertEquals(group.getId(),group1.getId());


    }

    public void testExceptionAddingSameGroup(){

        try
        {
            this.groupDao.create(TEST_NAME);
            this.groupDao.create(TEST_NAME);
            Assert.fail("Shoild throw some exception");
        }
        catch(SQLiteConstraintException e)
        {

        }
    }


}
