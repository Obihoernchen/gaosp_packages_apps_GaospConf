package com.android.gaospconf;

import android.app.PendingIntent;
import android.appwidget.AppWidgetManager;
import android.appwidget.AppWidgetProvider;
import android.content.Context;
import android.content.Intent;
import android.widget.RemoteViews;

public class widget extends AppWidgetProvider {
	@Override
	public void onUpdate(Context context, AppWidgetManager appWidgetManager, int[] appWidgetIds) {
		// Execute ClearCache Service
		context.startService(new Intent(context,ClearCache.class));
		for (int appWidgetId : appWidgetIds) { 
            // Execute ClearCache Service on click
            Intent intent = new Intent(context,ClearCache.class);
            PendingIntent pendingIntent = PendingIntent.getService(context, 0, intent, 0);
            // Get the layout for the App Widget and attach an on-click listener to the button
            RemoteViews views = new RemoteViews(context.getPackageName(), R.layout.widget);
            views.setOnClickPendingIntent(R.id.imageButton, pendingIntent);
            // Tell the AppWidgetManager to perform an update on the current app widget
            appWidgetManager.updateAppWidget(appWidgetId, views);           
        }
	}
}
