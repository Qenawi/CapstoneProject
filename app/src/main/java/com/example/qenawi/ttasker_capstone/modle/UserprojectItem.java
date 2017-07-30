package com.example.qenawi.ttasker_capstone.modle;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by QEnawi on 7/21/2017.
 */

public class UserprojectItem implements Parcelable {
    public static final Creator<UserprojectItem> CREATOR = new Creator<UserprojectItem>() {
        @Override
        public UserprojectItem createFromParcel(Parcel in) {
            return new UserprojectItem(in);
        }

        @Override
        public UserprojectItem[] newArray(int size) {
            return new UserprojectItem[size];
        }
    };
    private String pkey;
    private String pname;

    public UserprojectItem() {
    }

    public UserprojectItem(String pkey, String pname) {
        this.pkey = pkey;
        this.pname = pname;
    }

    protected UserprojectItem(Parcel in) {
        pkey = in.readString();
        pname = in.readString();
    }

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
