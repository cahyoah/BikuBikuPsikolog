package com.example.adhit.bikubikupsikolog.receiver;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

import com.example.adhit.bikubikupsikolog.data.model.Transaction;
import com.qiscus.sdk.data.model.QiscusChatRoom;

import java.util.List;

public class NewTransactionReceiver extends BroadcastReceiver {

    public static final String TAG = NewTransactionReceiver.class.getSimpleName();
    private final PeriodicCheckTransactionReceiverListener mListener;

    public NewTransactionReceiver(PeriodicCheckTransactionReceiverListener listener) {
        mListener = listener;
    }

    @Override
    public void onReceive(Context context, Intent intent) {


        mListener.handleFromReceiver(intent.getParcelableArrayListExtra("new_transaction"));
    }

    public interface PeriodicCheckTransactionReceiverListener {

        void handleFromReceiver(List<Transaction> transactionList);
    }
}
