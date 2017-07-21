package com.example.qenawi.ttasker_capstone.modle;

import java.io.Serializable;

/**
 * Created by QEnawi on 7/21/2017.
 */

public class pmemberitem implements Serializable
{
    public pmemberitem()
    {
    }
    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    private String key;

    public String getName()
    {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public pmemberitem(String key, String name) {
        this.key = key;
        this.name = name;
    }

    private String name;
}
