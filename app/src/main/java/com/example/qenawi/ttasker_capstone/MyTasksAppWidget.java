package com.example.qenawi.ttasker_capstone;

import android.appwidget.AppWidgetManager;
import android.appwidget.AppWidgetProvider;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.widget.RemoteViews;

/**
 * Implementation of App Widget functionality.
 */
public class MyTasksAppWidget extends AppWidgetProvider {
    static void updateAppWidget(Context context, AppWidgetManager appWidgetManager, int appWidgetId)
    {
        //called from Baking app service to ubdate widget data
        RemoteViews remoteViews;
        remoteViews = getBakingIngrediantRemoteView(context);
        appWidgetManager.updateAppWidget(appWidgetId, remoteViews);

    }

    @Override
    public void onUpdate(Context context, AppWidgetManager appWidgetManager, int[] appWidgetIds)
    {
        // time update
        // There may be multiple widgets active, so update all of them
        BakingAppService.startActionUpdateWidget(context);
    }

    public static void updatePlantWidgets(Context context, AppWidgetManager appWidgetManager, int[] appWidgetIds) {
        for (int appWidgetId : appWidgetIds) {
            updateAppWidget(context, appWidgetManager, appWidgetId);
        }
    }

    @Override
    public void onReceive(Context context, Intent intent) {
        super.onReceive(context, intent);
        final String Action0=context.getString(R.string.Action0);
        if (intent.getAction().equals(Action0))
        {
            BakingAppService.startActionUpdateWidget(context);
        }
    }

    private static RemoteViews getBakingIngrediantRemoteView(Context context)
    {
        RemoteViews views = new RemoteViews(context.getPackageName(), R.layout.my_tasks_app_widget);
        Intent intent = new Intent(context, GridWidgetService.class);
        views.setRemoteAdapter(R.id.widget_grid_view, intent);
        // Handle empty ->
        views.setEmptyView(R.id.widget_grid_view, R.id.empty_view);
        return views;
    }

    @Override
    public void onEnabled(Context context)
    {
        // Enter relevant functionality for when the first widget is created
        //   BakingAppService.startActionUpdateWidget(context,null);
    }

    @Override
    public void onDisabled(Context context) {
        // Enter relevant functionality for when the last widget is disabled
    }
    @Override
    public void onAppWidgetOptionsChanged(Context context, AppWidgetManager appWidgetManager,
                                          int appWidgetId, Bundle newOptions)
    {
        BakingAppService.startActionUpdateWidget(context);
        super.onAppWidgetOptionsChanged(context, appWidgetManager, appWidgetId, newOptions);
    }

}

