package com.example.adhit.bikubikupsikolog.service;

import android.content.Context;

import com.google.android.gms.gcm.GcmNetworkManager;
import com.google.android.gms.gcm.PeriodicTask;

public class NewTransactionTask {
    private GcmNetworkManager mGcmNetworkManager;

    public NewTransactionTask(Context context){
        mGcmNetworkManager = GcmNetworkManager.getInstance(context);
    }

    public void createPeriodicTask() {
        com.google.android.gms.gcm.Task periodicTask = new PeriodicTask.Builder()
                .setService(NewTransactionService.class)
                .setPeriod(7)
                .setFlex(5)
                .setTag(NewTransactionService.TAG_TRANSACTION)
                .setPersisted(true)
                .build();

        mGcmNetworkManager.schedule(periodicTask);
    }


    public int getGCM(){
        if(mGcmNetworkManager == null){
            return 0;
        }
        return 1;
    }
    public void cancelPeriodicTask(){
        if (mGcmNetworkManager != null){
            mGcmNetworkManager.cancelTask(NewTransactionService.TAG_TRANSACTION, NewTransactionService.class);
        }
    }
}
