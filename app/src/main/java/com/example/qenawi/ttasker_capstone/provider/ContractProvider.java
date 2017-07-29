package com.example.qenawi.ttasker_capstone.provider;

import android.net.Uri;

/**
 * Created by QEnawi on 7/28/2017.
 */

public class ContractProvider
{
    public  static  final String ProviderName="com.example.qenawi.ttasker_capstone";//my app packge name autority
    public  static  final Uri BaseContentUri=Uri.parse("content://"+ProviderName);
    public  static  final  String const_path="data";
    public static String TableName="taskstable";

    public static final Uri CONTENT_URI= BaseContentUri.buildUpon().appendPath(const_path).build();
    public  static  final String TaskTitle="tasktitle";
    public  static  final String TaskContent="taskcontent";
    public  static  final String Taskstate="taskstate";
    public  static  final String TaskUey="taskukey";
    public  static final  String TaskDate="taskdate";

    public  static  final String ProjectName ="projectname";
    public  static  final String ProjectKey="projectkey"
            ;
    public  static  final String Dummy="dummy";

    public static String B_id="ID";
}
