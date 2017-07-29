package com.example.qenawi.ttasker_capstone;

import android.app.IntentService;
import android.appwidget.AppWidgetManager;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;

/**
 * An {@link IntentService} subclass for handling asynchronous task requests in
 * a service on a separate handler thread.
 */
public class BakingAppService extends IntentService
{
    public static final String ACTION_GET_INGRDIANT ="com.example.android.mygarden.action.update_widget";
    public BakingAppService()
    {
        super(BakingAppService.class.getName());
    }
    public static void startActionUpdateWidget(Context context)
    {
        Intent intent = new Intent(context, BakingAppService.class);
        intent.setAction(ACTION_GET_INGRDIANT);
        context.startService(intent);
    }
    @Override
    protected void onHandleIntent(Intent intent)
    {
        if (intent != null)
        {
            final String action = intent.getAction();
            if (ACTION_GET_INGRDIANT.equals(action))
            {
                handleActionUpdateWidget();
            }
        }
    }
    private void handleActionUpdateWidget()
    {
        AppWidgetManager appWidgetManager = AppWidgetManager.getInstance(this);
        int[] appWidgetIds = appWidgetManager.getAppWidgetIds(new ComponentName(this, MyTasksAppWidget.class));
        appWidgetManager.notifyAppWidgetViewDataChanged(appWidgetIds, R.id.widget_grid_view);
        MyTasksAppWidget.updatePlantWidgets(this,appWidgetManager,appWidgetIds);
    }

}
