package pl.wmaciejewski.contactproject.database.entitys;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.media.Image;
import android.net.Uri;

import java.util.BitSet;

import pl.wmaciejewski.contactproject.R;
import pl.wmaciejewski.contactproject.database.MySQLConnector;

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
    private Bitmap smallImage;



    public Person(Long id, String name, String surname, String email, String phoneNumber, String imageUri, byte[] imageArray) {
        this.id = id;
        this.name = name;
        this.surname = surname;
        this.email = email;
        this.phoneNumber = phoneNumber;
        this.image = Uri.parse(imageUri);
        this.smallImage=getBitmapFromByteArray(imageArray);

    }

    private Bitmap getBitmapFromByteArray(byte[] bytes) {
        try {
            return BitmapFactory.decodeByteArray(bytes, 0, bytes.length);
        }catch(NullPointerException ne){
            return  null;
        }
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

    public Bitmap getSmallImage() {
        return smallImage;
    }

    public void setSmallImage(Bitmap smallImage) {
        this.smallImage = smallImage;
    }
}
