package com.example.qenawi.ttasker_capstone.modle;

/**
 * Created by QEnawi on 7/25/2017.
 */

public class NotificationItem
{
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

    public void setNotftimestamp(String notftimestamp)
    {
        this.notftimestamp = notftimestamp;
    }

    public String getNotfstate() {
        return notfstate;
    }

    public void setNotfstate(String notfstate) {
        this.notfstate = notfstate;
    }

    private String notftype,notfcontent,notftitle,notfprojectkey,notftaskkey;
     private String notfstate;
    private String notftimestamp;

    public NotificationItem()
    {
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
}
