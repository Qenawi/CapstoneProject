package com.example.qenawi.ttasker_capstone.modle;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by QEnawi on 7/19/2017.
 */

public class TaskItem implements Parcelable {
    public static final Creator<TaskItem> CREATOR = new Creator<TaskItem>() {
        @Override
        public TaskItem createFromParcel(Parcel in) {
            return new TaskItem(in);
        }

        @Override
        public TaskItem[] newArray(int size) {
            return new TaskItem[size];
        }
    };
    private String name;
    private String taskName;
    private String taskDesc;
    private String date;
    private String done;
    protected TaskItem(Parcel in) {
        name = in.readString();
        taskName = in.readString();
        taskDesc = in.readString();
        date = in.readString();
        done = in.readString();
    }
    public TaskItem(String taskName, String taskDesc, String date, String done, String name) {
        this.taskName = taskName;
        this.taskDesc = taskDesc;
        this.date = date;
        this.done = done;
        this.name = name;
    }

    public TaskItem() {

    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getTaskName() {
        return taskName;
    }

    public String getTaskDesc() {
        return taskDesc;
    }

    public String getDate() {
        return date;
    }

    public Boolean getDoneB() {
        if (this.done.equals("1")) return true;
        else return false;
    }

    public String getDone() {
        return this.done;
    }

    public void setDone(String done) {
        this.done = done;
    }

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
