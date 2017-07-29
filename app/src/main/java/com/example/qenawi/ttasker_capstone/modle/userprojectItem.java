package com.example.qenawi.ttasker_capstone.modle;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by QEnawi on 7/21/2017.
 */

public class userprojectItem implements Parcelable
{
    public userprojectItem()
    {
    }
    public userprojectItem(String pkey, String pname)
    {
        this.pkey = pkey;
        this.pname = pname;
    }

    private  String pkey;

    protected userprojectItem(Parcel in) {
        pkey = in.readString();
        pname = in.readString();
    }

    public static final Creator<userprojectItem> CREATOR = new Creator<userprojectItem>() {
        @Override
        public userprojectItem createFromParcel(Parcel in) {
            return new userprojectItem(in);
        }

        @Override
        public userprojectItem[] newArray(int size) {
            return new userprojectItem[size];
        }
    };

    public String getPkey() {
        return pkey;
    }

    public void setPkey(String pkey) {
        this.pkey = pkey;
    }

    public String getPname() {
        return pname;
    }

    public void setPname(String pname) {
        this.pname = pname;
    }

    private    String pname;

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(pkey);
        parcel.writeString(pname);
    }
}
