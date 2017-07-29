package com.example.qenawi.ttasker_capstone.modle;

import android.os.Parcel;
import android.os.Parcelable;

import java.io.Serializable;

/**
 * Created by QEnawi on 7/19/2017.
 */

public class taskItem implements Parcelable
{
    protected taskItem(Parcel in) {
        name = in.readString();
        taskName = in.readString();
        taskDesc = in.readString();
        date = in.readString();
        done = in.readString();
    }

    public static final Creator<taskItem> CREATOR = new Creator<taskItem>() {
        @Override
        public taskItem createFromParcel(Parcel in) {
            return new taskItem(in);
        }

        @Override
        public taskItem[] newArray(int size) {
            return new taskItem[size];
        }
    };

    public String getName() {
        return name;
    }

    public taskItem(String taskName, String taskDesc, String date, String done, String name)
    {
        this.taskName = taskName;
        this.taskDesc = taskDesc;
        this.date = date;
        this.done = done;
        this.name=name;
    }

    public void setName(String name)
    {
        this.name = name;
    }

    private  String name;
    private String taskName;
    private  String taskDesc;
    public taskItem()
    {

    }
    private String date;
    public String getTaskName()
    {
        return taskName;
    }
    public String getTaskDesc() {
        return taskDesc;
    }
    public String getDate() {
        return date;
    }
    public Boolean getDoneB()
    {
        if (this.done.equals("1"))return true;
        else return  false;
    }
    public String getDone()
    {
        return  this.done;
    }

    public void setDone(String done)
    {
        this.done = done;
    }

    private   String done;

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(name);
        parcel.writeString(taskName);
        parcel.writeString(taskDesc);
        parcel.writeString(date);
        parcel.writeString(done);
    }
}
