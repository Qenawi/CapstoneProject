package com.example.qenawi.ttasker_capstone.modle;

import java.io.Serializable;

/**
 * Created by QEnawi on 7/19/2017.
 */

public class taskItem implements Serializable
{
    public taskItem(String taskName, String taskDesc, String date, String done)
    {
        this.taskName = taskName;
        this.taskDesc = taskDesc;
        this.date = date;
        this.done = done;
    }
    String taskName;
    String taskDesc;
    public taskItem()
    {
    }
    String date;
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
    public Boolean getDone()
    {
        if (this.done.equals("1"))return true;
        else return  false;
    }
    String done;
}
