package com.udafil.dhruvamsharma.bakingandroidapp;

import android.app.PendingIntent;
import android.appwidget.AppWidgetManager;
import android.appwidget.AppWidgetProvider;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.widget.RemoteViews;

import com.udafil.dhruvamsharma.bakingandroidapp.data.RecipeRepository;
import com.udafil.dhruvamsharma.bakingandroidapp.main.MainActivity;

/**
 * Implementation of App Widget functionality.
 */
public class RecipeWidget extends AppWidgetProvider {

    //Default condition
    private static int position = 1;


    static void updateAppWidget(Context context, AppWidgetManager appWidgetManager,
                                int appWidgetId, int modelPosition) {

        StringBuffer widgetText = new StringBuffer();
        String ingredientSet = null;

        // Construct the RemoteViews object
        RemoteViews views = new RemoteViews(context.getPackageName(), R.layout.recipe_widget);



        ingredientSet = RecipeRepository.getInstance().getRecipe(modelPosition, context);

        //Toast.makeText(context, ingredientSet, Toast.LENGTH_SHORT).show();

        if (ingredientSet != null) {

            widgetText.append(ingredientSet);

        } else {
            //TODO handle error conditions
        }

        Intent intent = new Intent(context, MainActivity.class);
        PendingIntent pendingIntent = PendingIntent.getActivity(context, 0, intent, 0);

        views.setOnClickPendingIntent( R.id.appwidget_text, pendingIntent);


        views.setTextViewText(R.id.appwidget_text, widgetText);

        // Instruct the widget manager to update the widget
        appWidgetManager.updateAppWidget(appWidgetId, views);


    }

    @Override
    public void onUpdate(Context context, AppWidgetManager appWidgetManager, int[] appWidgetIds) {
        selectRecipe(position , context);
    }

    @Override
    public void onEnabled(Context context) {
        // Enter relevant functionality for when the first widget is created
    }

    @Override
    public void onDisabled(Context context) {
        // Enter relevant functionality for when the last widget is disabled
    }

    public static void selectRecipe( int i, Context context) {

        position = i;

        AppWidgetManager appWidgetManager = AppWidgetManager.getInstance(context);
        int[] appWidgetIds = appWidgetManager.getAppWidgetIds( new ComponentName(context, RecipeWidget.class));

        // There may be multiple widgets active, so update all of them
        for (int appWidgetId : appWidgetIds) {
            updateAppWidget(context, appWidgetManager, appWidgetId, i);
        }

    }
}

