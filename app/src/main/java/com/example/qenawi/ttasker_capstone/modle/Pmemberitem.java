package com.example.qenawi.ttasker_capstone.modle;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by QEnawi on 7/21/2017.
 */

public class Pmemberitem implements Parcelable {
    public static final Creator<Pmemberitem> CREATOR = new Creator<Pmemberitem>() {
        @Override
        public Pmemberitem createFromParcel(Parcel in) {
            return new Pmemberitem(in);
        }

        @Override
        public Pmemberitem[] newArray(int size) {
            return new Pmemberitem[size];
        }
    };
    private String key;
    private String name;

    public Pmemberitem() {
    }

    protected Pmemberitem(Parcel in) {
        key = in.readString();
        name = in.readString();
    }

    public Pmemberitem(String key, String name) {
        this.key = key;
        this.name = name;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(key);
        parcel.writeString(name);
    }
}
