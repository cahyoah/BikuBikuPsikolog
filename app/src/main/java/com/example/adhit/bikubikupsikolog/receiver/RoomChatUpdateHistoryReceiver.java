package com.example.adhit.bikubikupsikolog.receiver;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

import com.example.adhit.bikubikupsikolog.data.model.ChatRoomHistory;
import com.qiscus.sdk.data.model.QiscusChatRoom;

import java.util.List;

public class RoomChatUpdateHistoryReceiver extends BroadcastReceiver {

    public static final String TAG = RoomChatUpdateHistoryReceiver.class.getSimpleName();
    private final PeriodicCheckRoomChatHistoryReceiverListener mListener;

    public RoomChatUpdateHistoryReceiver(PeriodicCheckRoomChatHistoryReceiverListener listener) {
        mListener = listener;
    }

    @Override
    public void onReceive(Context context, Intent intent) {


        mListener.handleFromReceiver(intent.getParcelableArrayListExtra("room_chat_history"));
    }

    public interface PeriodicCheckRoomChatHistoryReceiverListener {

        void handleFromReceiver(List<ChatRoomHistory> chatRoomHistoryList);
    }
}
