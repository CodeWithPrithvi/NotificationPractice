package com.prithviraj.notificationpractice;

import android.app.Activity;
import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.Icon;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.Manifest;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.res.ResourcesCompat;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class MainActivity extends AppCompatActivity {
    Button btn;
    private final static String CHANNEL_ID="APP NAME";
    private final static int REQ_CODE=100;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            if (ActivityCompat.checkSelfPermission(this,Manifest.permission.POST_NOTIFICATIONS) != PackageManager.PERMISSION_GRANTED) {
                requestPermissions(new String[] {Manifest.permission.POST_NOTIFICATIONS}, 1);

            }
            else {
                requestPermissions(new String[] {Manifest.permission.POST_NOTIFICATIONS}, 1);
            }
        }
        Intent intent=new Intent(getApplicationContext(),MainActivity2.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        PendingIntent pi= PendingIntent.getActivities(getApplicationContext(),REQ_CODE, new Intent[]{intent},PendingIntent.FLAG_UPDATE_CURRENT | PendingIntent.FLAG_MUTABLE);

        Drawable drawable= ResourcesCompat.getDrawable(getResources(),R.drawable.cat,null);
        BitmapDrawable bitmapDrawable=(BitmapDrawable) drawable;
        Bitmap caticon = bitmapDrawable.getBitmap();

        //Big Picture Style
        Notification.BigPictureStyle bigPictureStyle = new Notification.BigPictureStyle()
                .bigPicture(((BitmapDrawable) (ResourcesCompat.getDrawable(getResources(),R.drawable.batman,null))).getBitmap())
                .bigLargeIcon(caticon)
                .setBigContentTitle("Batman")
                .setSummaryText("He is Vengeance");
        //Inbox style
        Notification.InboxStyle inboxStyle = new Notification.InboxStyle()
                .addLine("Line 1")
                .addLine("Line 2")
                .addLine("Line 3")
                .addLine("Line 4")
                .addLine("Line 5")
                .addLine("Line 6")
                .addLine("Line 7")
                .addLine("Line 8")
                .addLine("Line 9")
                .setBigContentTitle("This is a example of Inbox style")
                .setSummaryText("The message is");

        btn = findViewById(R.id.button);
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                NotificationManager nm= (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
                Notification notification;
                notification=new Notification.Builder(getApplicationContext())
                        .setLargeIcon(caticon)
                        .setSmallIcon(Icon.createWithBitmap(caticon))
                        .setContentTitle("This is a test Notification")
                        .setContentText("This notification is sent by Prithviraj")
                        .setStyle(inboxStyle)
                        .setContentIntent(pi)
                        .setChannelId(CHANNEL_ID)
                        .build();
                nm.createNotificationChannel(new NotificationChannel(CHANNEL_ID,"Channel Name",NotificationManager.IMPORTANCE_HIGH));
                nm.notify(100,notification);
            }
        });

    }
}