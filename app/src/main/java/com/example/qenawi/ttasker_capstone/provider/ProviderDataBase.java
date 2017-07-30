package com.example.qenawi.ttasker_capstone.provider;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by QEnawi on 7/28/2017.
 */

public class ProviderDataBase extends SQLiteOpenHelper {
    private static final int version = 1;
    private static final String Dp = "datax.db";
    private static final String quireUpdate = "ALTER TABLE "
            + ContractProvider.TableName + " ADD COLUMN " + "null" + " string;";
    private static final String quire =
            "CREATE TABLE " + ContractProvider.TableName + " (" +
                    ContractProvider.B_id + " INTEGER PRIMARY KEY AUTOINCREMENT," +
                    ContractProvider.TaskTitle + " VARCHAR(20)," +
                    ContractProvider.TaskContent + " VARCHAR(100)," +
                    ContractProvider.Taskstate + " VARCHAR(20)," +
                    ContractProvider.TaskDate + " VARCHAR(20)," +
                    ContractProvider.TaskUey + " VARCHAR(60)," +
                    ContractProvider.ProjectName + " VARCHAR(20)," +
                    ContractProvider.ProjectKey + " VARCHAR(20)," +
                    ContractProvider.Dummy + " VARCHAR(20)" +
                    ")";

    public ProviderDataBase(Context context) {
        super(context, Dp, null, version);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        sqLiteDatabase.execSQL(quire);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
        if (i < 2) {/* ad required column */}
    }
}
