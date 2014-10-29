package pl.wmaciejewski.contactproject;

import android.test.AndroidTestCase;
import android.test.RenamingDelegatingContext;

import pl.wmaciejewski.contactproject.database.DatabaseProvider;
import pl.wmaciejewski.contactproject.database.dao.GroupDAO;
import pl.wmaciejewski.contactproject.database.entitys.Group;

/**
 * Created by w.maciejewski on 2014-10-28.
 */
public class GroupDAOTest extends AndroidTestCase {

    private GroupDAO groupDao;
    private DatabaseProvider databaseProvider;

    private static String TEST_NAME="grupa1";
    @Override
    protected void setUp() throws Exception {
        super.setUp();


        RenamingDelegatingContext context
                = new RenamingDelegatingContext(getContext(), "test_");
        databaseProvider=new DatabaseProvider(context);
        databaseProvider.open();
        groupDao=new GroupDAO(databaseProvider.getDatabase());
        groupDao.removeAll();

    }

    @Override
    protected void tearDown() throws Exception {
        super.tearDown();
        groupDao.removeAll();
        databaseProvider.close();
    }



    public void testAddGroup(){
        groupDao.createGroup(TEST_NAME);
        Group group=groupDao.getGroup(TEST_NAME);
        Group group1=groupDao.getAllGroups().get(0);

        assertEquals(group.getGroupName(),group1.getGroupName());
        assertEquals(group.getId(),group1.getId());


    }

}
