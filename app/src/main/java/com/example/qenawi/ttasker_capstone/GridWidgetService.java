package com.example.qenawi.ttasker_capstone;

import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.widget.RemoteViews;
import android.widget.RemoteViewsService;

import com.example.qenawi.ttasker_capstone.provider.ContractProvider;

import java.util.ArrayList;

/**
 * Created by QEnawi on 6/27/2017.
 */
// adapter->
public class GridWidgetService extends RemoteViewsService
{
    @Override
    public RemoteViewsFactory onGetViewFactory(Intent intent)
    {

        return new GridRemoteViewsFactory(this.getApplicationContext());
    }
}

class GridRemoteViewsFactory implements RemoteViewsService.RemoteViewsFactory {
    private ArrayList<String> data=new ArrayList<>();
    private Context mContext;
    public GridRemoteViewsFactory(Context applicationContext)
    {
        mContext = applicationContext;
    }
    @Override
    public void onCreate()
    {

    }
    //called on start and when notifyAppWidgetViewDataChanged is called
    @Override
    public void onDataSetChanged()
    {
    get();
    }
    @Override
    public void onDestroy()
    {
    }
    @Override
    public int getCount()
    {
        if (data==null||data.size()==0) return 0;
        return data.size();
    }
    @Override
    public RemoteViews getViewAt(int position)
    {
        //bind
        RemoteViews views = new RemoteViews(mContext.getPackageName(), R.layout.list_item_txt2);
        // Update the plant image
        views.setTextViewText(R.id.item2text2, data.get(position));
        return views;
    }
    @Override
    public RemoteViews getLoadingView() {
        return null;
    }
    @Override
    public int getViewTypeCount() {
        return 1; // Treat all items in the GridView the same
    }
    @Override
    public long getItemId(int i) {
        return i;
    }
    @Override
    public boolean hasStableIds() {
        return true;
    }
    private   void get()
    {
        data.clear();
        Cursor result=mContext.getContentResolver().query(ContractProvider.CONTENT_URI,null,null,null,ContractProvider.B_id);
        if (result!=null)
        {
            result.moveToFirst();
        }
        while (!result.isAfterLast())
        {
            data.add(result.getString(result.getColumnIndex(ContractProvider.TaskTitle)));
            result.moveToNext();
        }
    }
}


