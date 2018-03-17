package com.ankush.lettersandus;

import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Intent;
import android.media.RingtoneManager;
import android.net.Uri;
import android.support.v4.app.NotificationCompat;
import android.util.Log;

import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;

/**
 * Created by nEW u on 9/13/2017.
 */

public class LnUsFirebaseMessagingService extends FirebaseMessagingService {
    @Override
    public void onMessageReceived(RemoteMessage remoteMessage) {
        if (remoteMessage.getData().size() > 0) {
            Log.d("LNUS", "Message data payload: " + remoteMessage.getData().toString());
        }
        Log.d("LNUS","dukduk");
        if (remoteMessage.getNotification() != null) {
            Log.d("LNUS", "Message Notification Title: " + remoteMessage.getNotification().getTitle());
        }
        Intent intent = new Intent(this, ShowActivity.class);
        intent.putExtra("LETTER_TEXT", remoteMessage.getData().get("text"));
        PendingIntent pendingIntent = PendingIntent.getActivity(this, 0 /* Request code */, intent,
                PendingIntent.FLAG_ONE_SHOT);
        Uri defaultSoundUri= RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);
        NotificationCompat.Builder mBuilder =
                new NotificationCompat.Builder(this)
                        .setSmallIcon(R.mipmap.ic_launcher)
                        .setSound(defaultSoundUri)
                        .setContentIntent(pendingIntent)
                        .setContentTitle(remoteMessage.getNotification().getTitle())
                        .setContentText(remoteMessage.getNotification().getBody());
       // NotificationCompat.Builder mBuilder;
    // Sets an ID for the notification
            int mNotificationId = 1;
    // Gets an instance of the NotificationManager service
            NotificationManager mNotifyMgr =
                    (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
    // Builds the notification and issues it.
            mNotifyMgr.notify(mNotificationId, mBuilder.build());
    }
}
