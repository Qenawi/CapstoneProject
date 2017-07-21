package com.example.qenawi.ttasker_capstone.modle;

import java.io.Serializable;

/**
 * Created by QEnawi on 7/21/2017.
 */

public class projectsitem implements Serializable
{
    public projectsitem()
    {
    }

    String Pname;

    public projectsitem(String pname, String adminKey, String chatroomKey) {
        Pname = pname;
        this.adminKey = adminKey;
        this.chatroomKey = chatroomKey;
    }

    String adminKey;

    public String getPname() {
        return Pname;
    }

    public void setPname(String pname) {
        Pname = pname;
    }

    public String getAdminKey() {
        return adminKey;
    }

    public void setAdminKey(String adminKey) {
        this.adminKey = adminKey;
    }

    public String getChatroomKey() {
        return chatroomKey;
    }

    public void setChatroomKey(String chatroomKey) {
        this.chatroomKey = chatroomKey;
    }

    String chatroomKey;
}
