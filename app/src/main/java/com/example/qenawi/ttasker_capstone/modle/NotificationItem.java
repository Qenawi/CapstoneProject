package com.example.qenawi.ttasker_capstone.modle;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by QEnawi on 7/25/2017.
 */

public class NotificationItem implements Parcelable {
    public static final Creator<NotificationItem> CREATOR = new Creator<NotificationItem>() {
        @Override
        public NotificationItem createFromParcel(Parcel in) {
            return new NotificationItem(in);
        }

        @Override
        public NotificationItem[] newArray(int size) {
            return new NotificationItem[size];
        }
    };
    private String notftype, notfcontent, notftitle, notfprojectkey, notftaskkey;
    private String notfstate;
    private String notftimestamp;

    protected NotificationItem(Parcel in) {
        notftype = in.readString();
        notfcontent = in.readString();
        notftitle = in.readString();
        notfprojectkey = in.readString();
        notftaskkey = in.readString();
        notfstate = in.readString();
        notftimestamp = in.readString();
    }

    public NotificationItem() {
    }

    public NotificationItem(String notftype, String notfcontent, String notftitle, String notfprojectkey, String notftaskkey, String timestamp, String notfstate) {

        this.notftype = notftype;
        this.notfcontent = notfcontent;
        this.notftitle = notftitle;
        this.notfprojectkey = notfprojectkey;
        this.notftaskkey = notftaskkey;
        this.notftimestamp = timestamp;
        this.notfstate = notfstate;
    }

    public String getNotftype()
    {
        return notftype;
    }

    public void setNotftype(String notftype) {
        this.notftype = notftype;
    }

    public String getNotfcontent() {
        return notfcontent;
    }

    public void setNotfcontent(String notfcontent) {
        this.notfcontent = notfcontent;
    }

    public String getNotftitle() {
        return notftitle;
    }

    public void setNotftitle(String notftitle) {
        this.notftitle = notftitle;
    }

    public String getNotfprojectkey() {
        return notfprojectkey;
    }

    public void setNotfprojectkey(String notfprojectkey) {
        this.notfprojectkey = notfprojectkey;
    }

    public String getNotftaskkey() {
        return notftaskkey;
    }

    public void setNotftaskkey(String notftaskkey) {
        this.notftaskkey = notftaskkey;
    }

    public String getNotftimestamp() {
        return notftimestamp;
    }

    public void setNotftimestamp(String notftimestamp) {
        this.notftimestamp = notftimestamp;
    }

    public String getNotfstate() {
        return notfstate;
    }

    public void setNotfstate(String notfstate) {
        this.notfstate = notfstate;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(notftype);
        parcel.writeString(notfcontent);
        parcel.writeString(notftitle);
        parcel.writeString(notfprojectkey);
        parcel.writeString(notftaskkey);
        parcel.writeString(notfstate);
        parcel.writeString(notftimestamp);
    }
}
