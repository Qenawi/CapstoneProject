package com.example.qenawi.ttasker_capstone.modle;

import java.io.Serializable;

/**
 * Created by QEnawi on 4/17/2017.
 */

public class smsitem implements Serializable
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
}
