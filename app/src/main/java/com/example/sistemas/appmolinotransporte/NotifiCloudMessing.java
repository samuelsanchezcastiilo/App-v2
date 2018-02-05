package com.example.sistemas.appmolinotransporte;

import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.media.RingtoneManager;
import android.net.Uri;
import android.support.v4.app.NotificationCompat;


import com.example.sistemas.appmolinotransporte.DespachoConductores.Model.Conductor;
import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;

/**
 * Created by developer on 20/12/17.
 */

public class NotifiCloudMessing extends FirebaseMessagingService{

      public  static  HomeActivity homeActivity;
      int tab = R.id.notification;
    private static  int newnoitifi;
    public NotifiCloudMessing() {
        homeActivity = new HomeActivity();
    }

    public void   onMessageReceived(RemoteMessage remoteMessage)
    {
        super.onMessageReceived(remoteMessage);
        Conductor conductor =  new Conductor();
        showNotification(conductor);
        homeActivity.sihay();
        homeActivity.prueba(++newnoitifi);

        //parametros que voy a recibir de la notificacion
    }
    public void showNotification(Conductor conductor)
    {
        Intent intent = new Intent(getApplicationContext(), HomeActivity.class);
        intent.addFlags(intent.FLAG_ACTIVITY_CLEAR_TOP);
        PendingIntent pendingIntent = PendingIntent.getActivity(this,0,intent,
                PendingIntent.FLAG_UPDATE_CURRENT);
        Uri defaultSoundUri  = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);
        NotificationCompat.Builder noBuilder = new NotificationCompat.Builder(this)
                .setSmallIcon(R.drawable.addimg)
                .setContentTitle(conductor.getEstado())
                .setContentText(conductor.getApellido())
                .setAutoCancel(true)
                .setSound(defaultSoundUri)
                .setVibrate(new long[]{0,300,200,300})
                .setContentIntent(pendingIntent);
        NotificationManager notificationManager =
         (NotificationManager)getSystemService(Context.NOTIFICATION_SERVICE);
        notificationManager.notify(0,noBuilder.build());
    }
}
