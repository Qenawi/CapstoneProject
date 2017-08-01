package com.example.qenawi.ttasker_capstone;

import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.widget.RemoteViews;
import android.widget.RemoteViewsService;

import com.example.qenawi.ttasker_capstone.modle.TaskItem;
import com.example.qenawi.ttasker_capstone.provider.ContractProvider;

import java.util.ArrayList;

/**
 * Created by QEnawi on 6/27/2017.
 */
// adapter->
public class GridWidgetService extends RemoteViewsService {
    @Override
    public RemoteViewsFactory onGetViewFactory(Intent intent) {

        return new GridRemoteViewsFactory(this.getApplicationContext());
    }
}

class GridRemoteViewsFactory implements RemoteViewsService.RemoteViewsFactory {
    private ArrayList<TaskItem> data = new ArrayList<>();
    private Context mContext;

    public GridRemoteViewsFactory(Context applicationContext) {
        mContext = applicationContext;
    }

    @Override
    public void onCreate() {

    }

    //called on start and when notifyAppWidgetViewDataChanged is called
    @Override
    public void onDataSetChanged() {
        get();
    }

    @Override
    public void onDestroy() {
    }

    @Override
    public int getCount() {
        if (data == null || data.size() == 0) return 0;
        return data.size();
    }

    @Override
    public RemoteViews getViewAt(int position)
    {
        RemoteViews views = new RemoteViews(mContext.getPackageName(), R.layout.widgetitem);
        try {
            //bind
            // Update the plant image
            views.setTextViewText(R.id.NAME, data.get(position).getName() + '\t' + data.get(position).getTaskName());
            views.setTextViewText(R.id.DATA, data.get(position).getTaskDesc());
            views.setTextViewText(R.id.DATE, data.get(position).getDate());
            views.setTextViewText(R.id.checkBox, data.get(position).getDoneB().toString());
        }catch (Exception ignore){}


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

    private void get() {
        data.clear();
        Cursor result = mContext.getContentResolver().query(ContractProvider.CONTENT_URI, null, null, null, ContractProvider.B_id);
        if (result != null) {
            result.moveToFirst();
        }
        while (!result.isAfterLast()) {
            TaskItem da = new TaskItem(
                    result.getString(result.getColumnIndex(ContractProvider.TaskTitle)),
                    result.getString(result.getColumnIndex(ContractProvider.TaskContent)),
                    result.getString(result.getColumnIndex(ContractProvider.TaskDate)),
                    result.getString(result.getColumnIndex(ContractProvider.Taskstate)),
                    result.getString(result.getColumnIndex(ContractProvider.ProjectName)));
            data.add(da);
            result.moveToNext();
        }
    }
}


