package pl.wmaciejewski.contactproject.database.entitys;

/**
 * Created by w.maciejewski on 2014-10-28.
 */
public class Group {

    private long id;
    private String groupName;


    public Group(String string) {
        this.groupName=string;
    }

    public Group(long aLong, String string) {
        this.id=aLong;
        this.groupName=string;
    }


    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getGroupName() {
        return groupName;
    }

    public void setGroupName(String groupName) {
        this.groupName = groupName;
    }


}
