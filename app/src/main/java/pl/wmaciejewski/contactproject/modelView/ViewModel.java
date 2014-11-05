package pl.wmaciejewski.contactproject.modelView;


import java.util.List;

import pl.wmaciejewski.contactproject.database.DatabaseProvider;
import pl.wmaciejewski.contactproject.database.dao.GroupDAO;
import pl.wmaciejewski.contactproject.database.dao.PersonDAO;
import pl.wmaciejewski.contactproject.database.entitys.Person;

/**
 * Created by w.maciejewski on 2014-10-29.
 */
public class ViewModel {
    private static ViewModel ourInstance;
    private PersonListAdapter customAdapter;
    private PersonDAO personDAO;
    private GroupDAO groupDAO;
    private PersonListAdapter personListAdapter;



    public ViewModel(DatabaseProvider databaseProvider) {

        personDAO=new PersonDAO(databaseProvider.getDatabase());
        groupDAO=new GroupDAO(databaseProvider.getDatabase());

    }

    public List<Person> getPersonList(){
        return  this.personDAO.getAll();
    }
    public void addPerson(Person person){
        this.personDAO.create(person);
    }
    public Person getById(Long id){
        return this.personDAO.getById(id);
    }

    public void delatePerson(Long id){
        personDAO.removeById(id);
    }
}
