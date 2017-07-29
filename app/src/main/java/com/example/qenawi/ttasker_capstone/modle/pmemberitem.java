package com.example.qenawi.ttasker_capstone.modle;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by QEnawi on 7/21/2017.
 */

public class pmemberitem implements Parcelable
{
    public pmemberitem()
    {
    }

    protected pmemberitem(Parcel in)
    {
        key = in.readString();
        name = in.readString();
    }

    public static final Creator<pmemberitem> CREATOR = new Creator<pmemberitem>() {
        @Override
        public pmemberitem createFromParcel(Parcel in) {
            return new pmemberitem(in);
        }

        @Override
        public pmemberitem[] newArray(int size) {
            return new pmemberitem[size];
        }
    };

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    private String key;

    public String getName()
    {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public pmemberitem(String key, String name) {
        this.key = key;
        this.name = name;
    }

    private String name;

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
