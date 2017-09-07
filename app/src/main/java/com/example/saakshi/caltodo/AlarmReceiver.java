package com.example.saakshi.caltodo;

import android.app.AlarmManager;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.media.Ringtone;
import android.media.RingtoneManager;
import android.net.Uri;
import android.support.v4.app.NotificationCompat;
import android.view.View;
import android.widget.Button;

public class AlarmReceiver extends BroadcastReceiver {
int i=0;
    @Override
    public void onReceive(Context context, Intent intent) {
        NotificationCompat.Builder mbuilder = new NotificationCompat.Builder(context).setContentTitle("Notification")
                .setSmallIcon(android.R.drawable.ic_btn_speak_now).setAutoCancel(false).setContentText("My alarm!!");
        Intent resultIntent = new Intent(context, MainActivity.class);
        resultIntent.putExtra("id", i);


        PendingIntent resultPendingIntent = PendingIntent.getActivity(context, i++, resultIntent, PendingIntent.FLAG_UPDATE_CURRENT);
        mbuilder.setContentIntent(resultPendingIntent);
        NotificationManager mnotificationManager = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
        mnotificationManager.notify(i++, mbuilder.build());
       /* Uri alarmSound = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);
        mbuilder.setSound(alarmSound);
*/
       /* Uri alarms = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_ALARM);
        Ringtone r = RingtoneManager.getRingtone(context, alarms);
        r.play();
        r.stop();
       // r.stop();
*/
        Uri notification = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);
        Ringtone r = RingtoneManager.getRingtone(context, notification);
        r.play();



    }

}
