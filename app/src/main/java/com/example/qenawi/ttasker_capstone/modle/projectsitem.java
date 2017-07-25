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

    private String pname;

    public projectsitem(String pname, String adminKey, String chatroomKey) {
        this.pname = pname;
        this.adminKey = adminKey;
        this.chatroomKey = chatroomKey;
    }

    private String adminKey;

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
}
