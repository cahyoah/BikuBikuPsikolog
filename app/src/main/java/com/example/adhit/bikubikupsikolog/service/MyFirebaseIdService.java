package com.example.adhit.bikubikupsikolog.service;

import com.google.firebase.iid.FirebaseInstanceId;
import com.google.firebase.messaging.RemoteMessage;
import com.qiscus.sdk.service.QiscusFirebaseIdService;
import com.qiscus.sdk.service.QiscusFirebaseService;

/**
 * Created by adhit on 02/02/2018.
 */

public class MyFirebaseIdService extends QiscusFirebaseIdService {
    @Override
    public void onTokenRefresh() {
        super.onTokenRefresh(); // Must call super

        // Below is your own apps specific code
        // e.g register the token to your backend
        String refreshedToken = FirebaseInstanceId.getInstance().getToken();
       // sendTokenToMyBackend(refreshedToken);
    }
}

