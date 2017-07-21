package com.example.qenawi.ttasker_capstone.modle;

import java.io.Serializable;

/**
 * Created by QEnawi on 7/21/2017.
 */

public class userprojectItem implements Serializable
{
    public userprojectItem()
    {
    }
    public userprojectItem(String pkey, String pname)
    {
        this.pkey = pkey;
        this.pname = pname;
    }

    String pkey;

    public String getPkey() {
        return pkey;
    }

    public void setPkey(String pkey) {
        this.pkey = pkey;
    }

    public String getPname() {
        return pname;
    }

    public void setPname(String pname) {
        this.pname = pname;
    }

    String pname;
}
