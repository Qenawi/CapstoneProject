package com.example.qenawi.ttasker_capstone.modle;

import android.os.Parcel;
import android.os.Parcelable;

import java.io.Serializable;

/**
 * Created by QEnawi on 7/21/2017.
 */

public class projectsitem implements Parcelable
{
    public projectsitem()
    {
    }

    private String pname;

    public projectsitem(String pname, String adminKey, String chatroomKey) {
        this.pname = pname;
        this.adminKey = adminKey;
        this.chatroomKey = chatroomKey;
    }

    private String adminKey;

    protected projectsitem(Parcel in) {
        pname = in.readString();
        adminKey = in.readString();
        chatroomKey = in.readString();
    }

    public static final Creator<projectsitem> CREATOR = new Creator<projectsitem>() {
        @Override
        public projectsitem createFromParcel(Parcel in) {
            return new projectsitem(in);
        }

        @Override
        public projectsitem[] newArray(int size) {
            return new projectsitem[size];
        }
    };

    public String getPname() {
        return pname;
    }


    public String getAdminKey() {
        return adminKey;
    }


    public String getChatroomKey() {
        return chatroomKey;
    }


   private   String chatroomKey;

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(pname);
        parcel.writeString(adminKey);
        parcel.writeString(chatroomKey);
    }
}
