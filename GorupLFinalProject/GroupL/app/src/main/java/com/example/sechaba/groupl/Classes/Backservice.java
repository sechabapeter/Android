package com.example.sechaba.groupl.Classes;

import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.media.audiofx.BassBoost;
import android.os.Build;
import android.provider.Settings;
import android.support.v4.app.NotificationCompat;
import android.support.v4.app.TaskStackBuilder;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.backendless.Backendless;
import com.backendless.messaging.PublishOptions;
import com.backendless.push.BackendlessPushService;
import com.example.sechaba.groupl.Adapter.UserAdpter;
import com.example.sechaba.groupl.Homescreen;
import com.example.sechaba.groupl.MainActivity;
import com.example.sechaba.groupl.MaintenanceReportList;
import com.example.sechaba.groupl.R;
import com.example.sechaba.groupl.ReportLearnerView;
import com.example.sechaba.groupl.Roles_Users;
import com.example.sechaba.groupl.ShowrResolved;
import com.example.sechaba.groupl.UserDataList;

/**
 * Created by DLMLAPTOP on 9/17/2017.
 */

public class Backservice extends BackendlessPushService {
    Intent resultIntent;

    @Override
    public boolean onMessage(Context context, Intent intent) {

        String tickerText = intent.getStringExtra(PublishOptions.ANDROID_TICKER_TEXT_TAG);
        String contentTitle = intent.getStringExtra(PublishOptions.ANDROID_CONTENT_TITLE_TAG);
        String contentText = intent.getStringExtra(PublishOptions.ANDROID_CONTENT_TEXT_TAG);
        String messageFromNotification = intent.getStringExtra("message");

        String[] token = messageFromNotification.split("#");
        String message = token[0];

        NotificationCompat.Builder nBuilder = new NotificationCompat.Builder(context);
        nBuilder.setSmallIcon(getNotificationIcon(nBuilder))
                .setTicker(tickerText)
                .setContentTitle(contentTitle)
                .setContentText(contentText)
                .setVibrate(new long[]{100, 2000, 500, 2000})
                .setSound(Settings.System.DEFAULT_NOTIFICATION_URI)
                .setAutoCancel(true);


        NotificationCompat.InboxStyle inbox = new NotificationCompat.InboxStyle();
        inbox.setBigContentTitle(contentTitle);
        if (token.length > 1) {
            for (int i = 1; i < token.length; i++) {
                inbox.addLine(token[i]);
            }
        }
        nBuilder.setStyle(inbox);


        if (contentTitle.contains("New User Registration")) {
            resultIntent = new Intent(context, UserDataList.class);
        } else if (contentTitle.contains("Learner report")) {
            resultIntent = new Intent(context, ReportLearnerView.class);
        } else if (contentTitle.contains("Maintenance Report")) {
            resultIntent = new Intent(context, MaintenanceReportList.class);
            //
        } else if (contentTitle.contains("Report Resolved")) {
            resultIntent = new Intent(context, ShowrResolved.class);
        } else if (contentTitle.contains("Decision Made")) {
            resultIntent = new Intent(context, ReportLearnerView.class);
        }
        resultIntent.putExtra("title", contentTitle);
        resultIntent.putExtra("token", token);

        TaskStackBuilder builder = TaskStackBuilder.create(context);
        builder.addNextIntent(resultIntent);

        PendingIntent resultPI = builder.getPendingIntent(0, PendingIntent.FLAG_UPDATE_CURRENT);
        nBuilder.setContentIntent(resultPI);

        nBuilder.setFullScreenIntent(resultPI, false);
        NotificationManager notification = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
        notification.notify(1, nBuilder.build());

        return false;
    }

    private int getNotificationIcon(NotificationCompat.Builder notificationBuilder) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.ICE_CREAM_SANDWICH) {
            notificationBuilder.setColor(Color.YELLOW);
            return R.drawable.bell;

        } else {
            return R.drawable.bell;

        }
    }

}