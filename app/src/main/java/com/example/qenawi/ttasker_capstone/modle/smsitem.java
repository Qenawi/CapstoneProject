package com.example.qenawi.ttasker_capstone.modle;

import android.os.Parcel;
import android.os.Parcelable;

import java.io.Serializable;

/**
 * Created by QEnawi on 4/17/2017.
 */

public class smsitem implements Parcelable
{
    public smsitem()
    {
    }

    public smsitem(String msg, String camo_lnk, String time, String sender)
    {
        this.msg = msg;this.time =time;
        this.sender=sender;
        this.camo_lnk=camo_lnk;
    }

    protected smsitem(Parcel in) {
        msg = in.readString();
        camo_lnk = in.readString();
        time = in.readString();
        sender = in.readString();
    }

    public static final Creator<smsitem> CREATOR = new Creator<smsitem>() {
        @Override
        public smsitem createFromParcel(Parcel in) {
            return new smsitem(in);
        }

        @Override
        public smsitem[] newArray(int size) {
            return new smsitem[size];
        }
    };

    public String getMsg()
    {
        return msg;
    }

   private String msg;

    public String getCamo_lnk() {
        return camo_lnk;
    }

    private String camo_lnk;
    public String getTime()
    {
        return time;
    }

    private String time;

    public String getSender() {
        return sender;
    }

    private String sender;

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(msg);
        parcel.writeString(camo_lnk);
        parcel.writeString(time);
        parcel.writeString(sender);
    }
}
