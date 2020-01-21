package com.example.gbese.callmemo;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.support.v4.app.NotificationCompat;

import static android.content.Context.NOTIFICATION_SERVICE;



/**
 *  This is our class for intercepting the incoming calls and inform the state accordingly.
 *  we are also building a notification and notification chanel
 *  then the notification will be displayed we the broadcast receiver  receive a broadcast
 **/


public class OurPhoneStateReceiver extends BroadcastReceiver {
   // set ID for the notification channel
    private final String CHANEL_ID = "notifyme";
    //set the ID for the notification
    private static final int THEID = 45612;

    /**
     * the is the method for broadcast receiver
     * @param context take context as a parameter
     * @param intent take intent as a parameter
     */
    @Override
    public void onReceive(Context context, Intent intent) {
        // calling  now the method which we will use later for building a notification
        createNotification(context,"tkmsg","press to put your callnotes  ","Callmemo");

    }
    //building the  notification
    protected void createNotification(Context context, String tkmsg, String msgtext, String appname){
        // in the next line we are calling a method which used to create a notification channel
        createnotify(context,"notify","includeAll");
        PendingIntent pendingintent= PendingIntent.getActivity(context,0,new Intent(context,UserNote.class),0);
        NotificationCompat.Builder notification =  new NotificationCompat.Builder(context,CHANEL_ID);
        notification.setAutoCancel(true);
        notification.setSmallIcon(R.drawable.mm);
        notification.setTicker(tkmsg);
        notification.setWhen(System.currentTimeMillis());
        notification.setContentTitle(msgtext);
        notification.setContentText(appname);
        notification.setContentIntent(pendingintent);
        notification.setDefaults(NotificationCompat.DEFAULT_SOUND);
        NotificationManager mn=(NotificationManager) context.getSystemService(NOTIFICATION_SERVICE);
        mn.notify(THEID,notification.build());


    }
//this will create a chanel for the notification
protected void createnotify(Context context, String Notifications, String IncludeAll){
        if(Build.VERSION.SDK_INT>=Build.VERSION_CODES.O){

            int importance = NotificationManager.IMPORTANCE_DEFAULT;
            NotificationChannel notificationChannel = new NotificationChannel(CHANEL_ID, Notifications,importance);
            notificationChannel.setDescription(IncludeAll);
            NotificationManager notificationManager = (NotificationManager) context.getSystemService(NOTIFICATION_SERVICE);
            notificationManager.createNotificationChannel(notificationChannel);

        }
    }
    }

