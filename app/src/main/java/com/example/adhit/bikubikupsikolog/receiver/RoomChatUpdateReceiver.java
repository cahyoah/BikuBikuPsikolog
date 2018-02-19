package com.example.adhit.bikubikupsikolog.receiver;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

import com.example.adhit.bikubikupsikolog.data.model.ChatRoom;
import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.qiscus.sdk.data.model.QiscusChatRoom;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class RoomChatUpdateReceiver extends BroadcastReceiver {

    public static final String TAG = RoomChatUpdateReceiver.class.getSimpleName();
    private final PeriodicCheckRoomChatReceiverListener mListener;

    public RoomChatUpdateReceiver(PeriodicCheckRoomChatReceiverListener listener) {
        mListener = listener;
    }

    @Override
    public void onReceive(Context context, Intent intent) {


        mListener.handleFromReceiver(intent.getParcelableArrayListExtra("room_chat"));
    }

    public interface PeriodicCheckRoomChatReceiverListener {

        void handleFromReceiver(List<ChatRoom> qiscusChatRoomList);
    }
}
