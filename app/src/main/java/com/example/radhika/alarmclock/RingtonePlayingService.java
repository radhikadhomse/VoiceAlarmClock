package com.example.radhika.alarmclock;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Intent;
import android.media.MediaPlayer;
import android.os.IBinder;
import android.widget.Toast;

import java.security.Provider;
import java.util.List;
import java.util.Map;

import static android.app.Service.START_NOT_STICKY;

/**
 * Created by Radhika on 10/7/17.
 */

public class RingtonePlayingService extends Service {
    MediaPlayer mediaPlayer;
    private int startId;
    private boolean isRunning;

    public IBinder onBind(Intent i) {
        return null;
    }


    public int onStartCommand(Intent intent, int flags, int startId)
    {

        final NotificationManager mNM = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);

        Intent intent1 = new Intent(this.getApplicationContext(), MainActivity.class);
        PendingIntent pIntent = PendingIntent.getActivity(this, 0, intent1, 0);

        Notification mNotify  = new Notification.Builder(this)
                .setContentTitle("ALARM")
                .setContentText("Click me!")
                .setSmallIcon(R.drawable.img)
                .setContentIntent(pIntent)
                .setAutoCancel(true)
                .getNotification();

        String state = intent.getExtras().getString("extra");

        assert state != null;
        switch (state) {
            case "no":
                startId = 0;
                break;
            case "yes":
                startId = 1;
                break;
            default:
                startId = 0;
                break;
        }


        if(!this.isRunning && startId == 1) {

            mediaPlayer = MediaPlayer.create(this, R.raw.ymd);

            mediaPlayer.start();


            mNM.notify(0, mNotify);

            this.isRunning = true;
            this.startId = 0;

        }
        else if (!this.isRunning && startId == 0){

            this.isRunning = false;
            this.startId = 0;

        }

        else if (this.isRunning && startId == 1){

            this.isRunning = true;
            this.startId = 0;

        }
        else {

            mediaPlayer.stop();
            mediaPlayer.reset();

            this.isRunning = false;
            this.startId = 0;
        }


        return START_NOT_STICKY;

    }


    @Override
    public void onDestroy() {
        super.onDestroy();

        this.isRunning = false;
    }

}
