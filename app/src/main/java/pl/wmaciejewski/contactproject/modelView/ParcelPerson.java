package pl.wmaciejewski.contactproject.modelView;


import android.os.Parcel;
import android.os.Parcelable;

import pl.wmaciejewski.contactproject.database.entitys.Person;

/**
 * Created by Wojtek on 2014-10-29.
 */
public class ParcelPerson implements Parcelable {

    private Person person;

    public ParcelPerson(Person person){
        this.person=person;
    }
    public Person getPerson() {
        return person;
    }

    public void setPerson(Person person) {
        this.person = person;
    }



    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeLong(this.person.getId());
        parcel.writeString(this.person.getName() );
        parcel.writeString(this.person.getSurname());
        parcel.writeString(this.person.getEmail() );
        parcel.writeString(this.person.getPhoneNumber() );
        if (this.person.getImage()==null) parcel.writeString("null");
        else parcel.writeString(this.person.getImage().toString());
        parcel.writeInt(this.person.getSmallImage().length);
        parcel.writeByteArray(this.person.getSmallImage());
        parcel.writeLong(this.person.getGroupId());
    }

    private  ParcelPerson(Parcel in){

        Long id=in.readLong();
        String name=in.readString();
        String surname=in.readString();
        String email=in.readString();
        String phonenumber=in.readString();
        String imageUri=in.readString();
        byte[] imageSmall=new  byte[in.readInt()];
        in.readByteArray(imageSmall);

        Long groupid=in.readLong();
        this.person=new Person(id,name,surname,email,phonenumber,imageUri,imageSmall,groupid);


    }

    public static final Parcelable.Creator<ParcelPerson> CREATOR = new Parcelable.Creator<ParcelPerson>() {

        @Override
        public ParcelPerson createFromParcel(Parcel source) {
            return new ParcelPerson(source);
        }

        @Override
        public ParcelPerson[] newArray(int size) {
            return new ParcelPerson[size];
        }
    };
}
