package com.example.qenawi.ttasker_capstone.modle;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by QEnawi on 10/22/2016.
 */
public class Usersdataitem implements Parcelable {
    public static final Creator<Usersdataitem> CREATOR = new Creator<Usersdataitem>() {
        @Override
        public Usersdataitem createFromParcel(Parcel in) {
            return new Usersdataitem(in);
        }

        @Override
        public Usersdataitem[] newArray(int size) {
            return new Usersdataitem[size];
        }
    };
    private String name;
    private String email;
    private String photo_url;

    public Usersdataitem() {
    }

    public Usersdataitem(String email, String name, String photo_url) {
        this.email = email;
        this.name = name;
        this.photo_url = photo_url;
    }

    protected Usersdataitem(Parcel in) {
        name = in.readString();
        email = in.readString();
        photo_url = in.readString();
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPhoto_url() {
        return photo_url;
    }

    public void setPhoto_url(String photo_url) {
        this.photo_url = photo_url;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(name);
        parcel.writeString(email);
        parcel.writeString(photo_url);
    }
}
