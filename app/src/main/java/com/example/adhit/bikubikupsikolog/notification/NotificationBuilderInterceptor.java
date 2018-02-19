package com.example.adhit.bikubikupsikolog.notification;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.ContextWrapper;
import android.content.Intent;
import android.graphics.Color;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.support.v4.app.NotificationCompat;
import android.support.v4.app.RemoteInput;

import com.example.adhit.bikubikupsikolog.BikuBikuPsikolog;
import com.qiscus.sdk.data.model.QiscusComment;
import com.qiscus.sdk.data.model.QiscusNotificationBuilderInterceptor;
import com.qiscus.sdk.data.model.QiscusPushNotificationMessage;
import com.qiscus.sdk.util.QiscusPushNotificationUtil;
import com.qiscus.sdk.util.QiscusTextUtil;

import org.json.JSONException;
import org.json.JSONObject;

import static com.qiscus.sdk.util.QiscusPushNotificationUtil.KEY_NOTIFICATION_REPLY;

/**
 * Created by adhit on 12/01/2018.
 */

public class NotificationBuilderInterceptor implements QiscusNotificationBuilderInterceptor {



    @Override
    public boolean intercept(NotificationCompat.Builder notificationBuilder, QiscusComment qiscusComment) {
        JSONObject payload = null;
        try {
            System.out.println("sender "+ qiscusComment.getSender());
            if(!qiscusComment.getExtraPayload().equals("null")){
                payload = new JSONObject(qiscusComment.getExtraPayload());
            }
            if(qiscusComment.getExtraPayload().equals("null") || payload.get("type").equals("user_test")){
                PendingIntent pendingIntent;
                Intent openIntent = new Intent("com.qiscus.OPEN_COMMENT_PN");
                openIntent.putExtra("data", qiscusComment);
                pendingIntent = PendingIntent.getBroadcast(BikuBikuPsikolog.getContext(), qiscusComment.getRoomId(), openIntent, PendingIntent.FLAG_CANCEL_CURRENT);
                String getRepliedTo = qiscusComment.getSender();
                RemoteInput remoteInput = new RemoteInput.Builder(KEY_NOTIFICATION_REPLY)
                        .setLabel(QiscusTextUtil.getString(com.qiscus.sdk.R.string.qiscus_reply_to, getRepliedTo.toUpperCase()))
                        .build();
                NotificationCompat.Action replyAction = new NotificationCompat.Action.Builder(android.R.drawable.ic_menu_send,
                        QiscusTextUtil.getString(com.qiscus.sdk.R.string.qiscus_reply_to, getRepliedTo.toUpperCase()), pendingIntent)
                        .addRemoteInput(remoteInput)
                        .build();
                notificationBuilder.addAction(replyAction);
                notificationBuilder.setPriority(NotificationCompat.PRIORITY_HIGH);
                notificationBuilder.setContentText(qiscusComment.getMessage());
                notificationBuilder.setContentTitle(qiscusComment.getSender());
                return true;

            }else{
                payload = new JSONObject(qiscusComment.getExtraPayload());
                if (payload.getString("type").equals("closed_chat")) {

                    notificationBuilder.setContentTitle(qiscusComment.getSender());
                    notificationBuilder.setPriority(NotificationCompat.PRIORITY_HIGH);
                    notificationBuilder.setContentText(qiscusComment.getMessage());
                    return true;
                }
            }

        } catch (JSONException e) {
            e.printStackTrace();
        }
        return true;
    }

}
