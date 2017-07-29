package com.example.qenawi.ttasker_capstone.provider;

import android.content.ContentProvider;
import android.content.ContentUris;
import android.content.ContentValues;
import android.content.Context;
import android.content.UriMatcher;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.support.annotation.Nullable;

import com.example.qenawi.ttasker_capstone.R;

/**
 * Created by QEnawi on 7/28/2017.
 */

public class providerMain  extends ContentProvider
{
    //base objects
    public static final int Data = 1; //task directory
    public static final int DataWithId = 2;  // single item by id
    private static final UriMatcher mUriMatcher = getUriMatcher();
    private providerDataBase Dbinstance;
    private static UriMatcher getUriMatcher()
    {
        UriMatcher uriMatcher = new UriMatcher(UriMatcher.NO_MATCH);//base case
        uriMatcher.addURI(ContractProvider.ProviderName, ContractProvider.const_path, Data);
        uriMatcher.addURI(ContractProvider.ProviderName, ContractProvider.const_path + "/#", DataWithId);
        return uriMatcher;
    }

    @Override
    public boolean onCreate()
    {
        Context context = getContext();
        Dbinstance= new providerDataBase(context);
        return true;
    }

    @Nullable
    @Override
    public Cursor query(Uri uri, String[] projection, String selection, String[] selectionArgs, String sortOrder)
    {
        int match = mUriMatcher.match(uri);
        SQLiteDatabase db=Dbinstance.getWritableDatabase();
        Cursor retCursor;
        switch (match)
        {
            case Data:
                retCursor = db.query(ContractProvider.TableName,null,null,null,null,null,sortOrder);
                break;
            default:
                throw new UnsupportedOperationException(getContext().getString(R.string.RESPONSE2) + uri);
        }
        try
        {
            retCursor.setNotificationUri(getContext().getContentResolver(), uri);
        }catch (Exception e){e.printStackTrace();}
        return retCursor;
    }

    @Nullable
    @Override
    public String getType(Uri uri)/*The returned mime type should be in the format vnd.<uri pattern>./vnd.<name>.<type>.
    Where the <uri pattern> for a single row should be android.cursor.item, for multiple rows android.cursor.dir
    and the <name> should be globally unique (use the package name).
    <type> should be unique to the corresponding URI. So now let’s update our code to implement getType as follows:*/ {
        int match = mUriMatcher.match(uri);
        switch (match) {
            case Data:
                return getContext().getString(R.string.RESPONSE1)+ "/" + ContractProvider.ProviderName + "/" + ContractProvider.const_path;
            case DataWithId:
                return getContext().getString(R.string.RESPONSE1) + "/" + ContractProvider.ProviderName + "/" + ContractProvider.const_path;
            default:
                throw new UnsupportedOperationException(getContext().getString(R.string.RESPONSE2) + uri);
        }
    }

    @Nullable
    @Override
    public Uri insert(Uri uri, ContentValues contentValues)
    {
        // final SQLiteDatabase db = mDbHelper.getWritableDatabase();
        Uri returnUri=null;
        SQLiteDatabase db=Dbinstance.getWritableDatabase();
        final int match = mUriMatcher.match(uri);
        //  Table1 v1;
        //      v1 = new Table1(contentValues.get(Contract.C_MOVIE_ID).toString(),contentValues.get(Contract.C_OVERVIEW).toString(), contentValues.get(Contract.C_POSTER_PATH).toString(), contentValues.get(Contract.C_RELEASE_DATE).toString(), contentValues.get(Contract.C_TITLE).toString(),contentValues.get(Contract.VOTEAVG).toString());
        switch (match) {
            case Data:
                long id = db.insertOrThrow(ContractProvider.TableName,null,contentValues);
                if (id > 0) {
                    returnUri = ContentUris.withAppendedId(ContractProvider.CONTENT_URI, id);
                } else {
                    throw new android.database.SQLException( getContext().getString(R.string.RESPONSE3)+ uri);
                }
                break;

            default:
                throw new UnsupportedOperationException(getContext().getString(R.string.RESPONSE2)+ uri);
        }
        getContext().getContentResolver().notifyChange(uri, null);
        return returnUri;

    }
    @Override
    /*
    * @param uri The full URI to query, including a row ID (if a specific record is requested).
     * @param selection An optional restriction to apply to rows when deleting.
     * @return The number of rows affected.
     */
    public int delete(Uri uri, String selection, String[] selectionArgs)
    {
        // final SQLiteDatabase db = mDbHelper.getWritableDatabase();
        int match = mUriMatcher.match(uri);
        int res ;
        SQLiteDatabase db=Dbinstance.getWritableDatabase();
        switch (match)
        {
            case Data:
                res=db.delete(ContractProvider.TableName," dummy = ?",new String[] {selection});
                break;
            default:
                throw new UnsupportedOperationException(getContext().getString(R.string.RESPONSE3) + uri);
        }
        if (res!=-1)
        {
            getContext().getContentResolver().notifyChange(uri, null);
        }
        return res;
    }
    @Override
    public int update(Uri uri, ContentValues contentValues, String s, String[] strings) {

        return 1;
    }


}
