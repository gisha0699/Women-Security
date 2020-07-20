package com.example.login;

    import android.app.PendingIntent;
import android.appwidget.AppWidgetManager;
import android.appwidget.AppWidgetProvider;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
    import android.telephony.SmsManager;
    import android.widget.RemoteViews;
    import android.widget.Toast;

    import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

/**
 * Implementation of App Widget functionality.
 */
public class NewAppWidget extends AppWidgetProvider {
    //private FirebaseAuth firebaseAuth;

    String loc,list;

    @Override
    public void onUpdate(Context context, AppWidgetManager appWidgetManager, int[] appWidgetIds) {
        // There may be multiple widgets active, so update all of them
        for (int appWidgetId : appWidgetIds) {
            String number="9764993043";  //put police number in place of this number
            Intent intent=new Intent(Intent.ACTION_CALL);
            intent.setData(Uri.parse("tel:"+number));
            PendingIntent pendingIntent= PendingIntent.getActivity(context,0,intent,0);
            RemoteViews remoteViews=new RemoteViews(context.getPackageName(),R.layout.new_app_widget);
            remoteViews.setOnClickPendingIntent(R.id.appwidget_button,pendingIntent);
            appWidgetManager.updateAppWidget(appWidgetId,remoteViews);


        }

    }





}

