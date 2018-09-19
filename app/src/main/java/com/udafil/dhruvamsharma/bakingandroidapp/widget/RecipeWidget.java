package com.udafil.dhruvamsharma.bakingandroidapp.widget;

import android.app.PendingIntent;
import android.appwidget.AppWidgetManager;
import android.appwidget.AppWidgetProvider;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.widget.RemoteViews;

import com.udafil.dhruvamsharma.bakingandroidapp.R;
import com.udafil.dhruvamsharma.bakingandroidapp.data.RecipeRepository;
import com.udafil.dhruvamsharma.bakingandroidapp.data.model.RecipeModel;
import com.udafil.dhruvamsharma.bakingandroidapp.main.MainActivity;
import com.udafil.dhruvamsharma.bakingandroidapp.utils.GsonInstance;

/**
 * Implementation of App Widget functionality.
 */
public class RecipeWidget extends AppWidgetProvider {

    //Default condition
    private static int position = 1;


    static void updateAppWidget(Context context, AppWidgetManager appWidgetManager,
                                int appWidgetId, int modelPosition) {

        StringBuffer widgetText = new StringBuffer();
        RecipeModel ingredientSet = null;

        Intent intent = new Intent(context, WidgetService.class);
        intent.putExtra(AppWidgetManager.EXTRA_APPWIDGET_ID, appWidgetId);
        intent.setData(Uri.parse(intent.toUri(Intent.URI_INTENT_SCHEME)));
        intent.putExtra("RECIPE NUMBER", modelPosition);


        // Construct the RemoteViews object
        RemoteViews views = new RemoteViews(context.getPackageName(), R.layout.recipe_widget);

        views.setRemoteAdapter(R.id.widget_list_lv, intent);

        // Set cut corner background for API 23+
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            views.setInt( R.id.widget_background,"setBackgroundResource", R.drawable.ic_detail_activity_background);
        }

        ingredientSet = GsonInstance.getGsonInstance().fromJson(RecipeRepository.getInstance().getRecipe(modelPosition, context), RecipeModel.class);


        if (ingredientSet != null) {

            widgetText.append(ingredientSet.getName());

        } else {
            //TODO handle error conditions
        }

        Intent startActivityIntent = new Intent(context, MainActivity.class);
        PendingIntent pendingIntent = PendingIntent.getActivity(context, 0, startActivityIntent, 0);

        views.setOnClickPendingIntent( R.id.open_recipe_app_btn, pendingIntent);


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

    @Override
    public void onReceive(Context context, Intent intent) {
        super.onReceive(context, intent);

        //handles broadcast messages to the receiver.

    }
}

