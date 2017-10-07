package com.example.radhika.alarmclock;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;
import android.widget.ToggleButton;

import java.util.Calendar;

public class MainActivity extends AppCompatActivity {

    TimePicker alarmTimePicker;
    PendingIntent pendingIntent;
    AlarmManager alarmManager;
    Context context;
    TextView alarmStatus;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        this.context = this;
        alarmStatus = (TextView) findViewById(R.id.alarmStatus);
        final Intent i = new Intent(this.context, AlarmReceiver.class);

        alarmManager = (AlarmManager) getSystemService(ALARM_SERVICE);
        final Calendar cal = Calendar.getInstance();
        alarmTimePicker = (TimePicker) findViewById(R.id.timePicker);



        Button startAlarm = (Button) findViewById(R.id.startAlarm);
        startAlarm.setOnClickListener(new View.OnClickListener() {
            public void onClick (View v) {
                cal.add(Calendar.SECOND, 3);
                int hour = alarmTimePicker.getCurrentHour();
                int minute = alarmTimePicker.getCurrentMinute();
                cal.set(Calendar.HOUR_OF_DAY, hour);
                cal.set(Calendar.MINUTE, minute);
                i.putExtra("extra", "yes");
                pendingIntent = PendingIntent.getBroadcast(MainActivity.this, 0, i, PendingIntent.FLAG_UPDATE_CURRENT);
                alarmManager.set(AlarmManager.RTC_WAKEUP, cal.getTimeInMillis(),
                        pendingIntent);


                if (hour > 12) {
                    hour-=12;
                }
                String min = "" + minute;
                if (minute < 10) {
                    min = "0" + min;
                }
                setAlarmText("Alarm On: " + hour + ":" + min);
            }
        });
        Button stopAlarm = (Button) findViewById(R.id.stopAlarm);
        stopAlarm.setOnClickListener(new View.OnClickListener() {
            public void onClick (View v) {
                i.putExtra("extra", "no");
                sendBroadcast(i);
                alarmManager.cancel(pendingIntent);
                setAlarmText("Alarm: Off");
            }
        });




    }
    public void setAlarmText(String s) {
        alarmStatus.setText(s);
    }
}
