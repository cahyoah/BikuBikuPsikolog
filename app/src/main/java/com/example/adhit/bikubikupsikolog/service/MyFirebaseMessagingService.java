package com.example.adhit.bikubikupsikolog.service;

import android.util.Log;

import com.google.firebase.messaging.RemoteMessage;
import com.qiscus.sdk.service.QiscusFirebaseService;

/**
 * Created by adhit on 02/02/2018.
 */

public class MyFirebaseMessagingService extends QiscusFirebaseService {
    @Override
    public void onMessageReceived(RemoteMessage remoteMessage) {
        Log.d("run", "run");
        if (handleMessageReceived(remoteMessage)) { // For qiscus
            return;
        }

        //Your FCM PN here
    }
}