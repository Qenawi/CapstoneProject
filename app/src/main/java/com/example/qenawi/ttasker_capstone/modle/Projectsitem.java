package com.example.qenawi.ttasker_capstone.modle;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by QEnawi on 7/21/2017.
 */

public class Projectsitem implements Parcelable {
    public static final Creator<Projectsitem> CREATOR = new Creator<Projectsitem>() {
        @Override
        public Projectsitem createFromParcel(Parcel in) {
            return new Projectsitem(in);
        }

        @Override
        public Projectsitem[] newArray(int size) {
            return new Projectsitem[size];
        }
    };
    private String pname;
    private String adminKey;
    private String chatroomKey;

    public Projectsitem() {
    }

    public Projectsitem(String pname, String adminKey, String chatroomKey) {
        this.pname = pname;
        this.adminKey = adminKey;
        this.chatroomKey = chatroomKey;
    }

    protected Projectsitem(Parcel in) {
        pname = in.readString();
        adminKey = in.readString();
        chatroomKey = in.readString();
    }

    public String getPname() {
        return pname;
    }

    public String getAdminKey() {
        return adminKey;
    }

    public String getChatroomKey() {
        return chatroomKey;
    }

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
