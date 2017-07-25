package com.example.qenawi.ttasker_capstone.modle;

import java.io.Serializable;

/**
 * Created by QEnawi on 7/19/2017.
 */

public class taskItem implements Serializable
{
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
}
