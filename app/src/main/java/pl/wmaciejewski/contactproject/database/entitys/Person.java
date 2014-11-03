package pl.wmaciejewski.contactproject.database.entitys;

import android.net.Uri;

import java.util.Arrays;

/**
 * Created by w.maciejewski on 2014-10-28.
 */
public class Person {


    private long id;
    private long groupId;
    private String name;
    private String surname;
    private String email;
    private String phoneNumber;
    private Uri image;
    private byte[] smallImage;
    private boolean checked=false;

    public boolean isChecked() {
        return checked;
    }

    public void setChecked(boolean checked) {
        this.checked = checked;
    }

    public Person(String name, String surname, String email, String phoneNumber, String imageUri, byte[] imageArray, Long groupId) {
        this.id=0;
        this.name = name;
        this.surname = surname;
        this.email = email;
        this.phoneNumber = phoneNumber;
        if (imageUri == null) this.image = null;
        else this.image = Uri.parse(imageUri);
        this.smallImage =imageArray;
        if(groupId==null)this.groupId=0;
        else this.groupId = groupId;
    }

    public Person(Long id, String name, String surname, String email, String phoneNumber, String imageUri, byte[] imageArray, Long groupId) {
        this.id = id;
        this.name = name;
        this.surname = surname;
        this.email = email;
        this.phoneNumber = phoneNumber;
        if (imageUri == null) this.image = null;
        this.smallImage =imageArray;

        if(groupId==null) this.groupId=0;
        else this.groupId = groupId;

    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public long getId() {

        return id;
    }

    public void setId(long id) {
        this.id = id;
    }


    public long getGroupId() {
        return groupId;
    }

    public void setGroupId(long groupId) {
        this.groupId = groupId;
    }

    public String getSurname() {
        return surname;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public Uri getImage() {
        return image;
    }

    public void setImage(Uri image) {
        this.image = image;
    }

    public byte[] getSmallImage() {
        return smallImage;
    }

    public void setSmallImage(byte[] smallImage) {
        this.smallImage = smallImage;
    }

    @Override
    public final boolean equals( Object object )
    {
        if( !(object instanceof Person) )
            return false;
        final Person person = (Person)object;
        return person.getName().equals( this.name ) && person.getId()== this.getId()
                && person.getName().equals(this.getName())
                && person.getSurname().equals( this.getSurname())
                && person.getEmail().equals(this.getEmail())
                && person.getPhoneNumber().equals(this.getPhoneNumber())
                && Arrays.equals(person.getSmallImage(),this.getSmallImage());

    }

    @Override
    public final int hashCode()
    {
        int result;
        result = 20;
        result = 34 * result + (int)this.getId();
        return result;
    }



}
