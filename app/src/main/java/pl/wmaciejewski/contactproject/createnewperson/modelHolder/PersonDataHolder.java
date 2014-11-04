package pl.wmaciejewski.contactproject.createnewperson.modelHolder;

import pl.wmaciejewski.contactproject.database.entitys.Person;

/**
 * Created by w.maciejewski on 2014-10-30.
 */
public class PersonDataHolder {
    private static PersonDataHolder ourInstance = new PersonDataHolder();



    private Person person;

    public static PersonDataHolder getInstance() {
        if(ourInstance == null){
            ourInstance = new PersonDataHolder();
            return ourInstance;
        }else  return ourInstance;
    }

    private PersonDataHolder() {
        this.person=new Person(null,null,null,null,null,null,null);
    }

    public Person getPerson() {
        return person;
    }

    public void setPerson(Person person) {
        this.person = person;
    }

    public void clearPersonFileds(){
        this.person.setId(0);
        this.person.setName(null);
        this.person.setSurname(null);
        this.person.setEmail(null);
        this.person.setPhoneNumber(null);
        this.person.setImage(null);
        this.person.setSmallImage(null);
        this.person.setGroupId(0);
    }
}
