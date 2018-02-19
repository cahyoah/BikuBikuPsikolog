package com.example.adhit.bikubikupsikolog.ui.home.history;

import com.example.adhit.bikubikupsikolog.data.model.ChatRoomHistory;
import com.qiscus.sdk.data.model.QiscusChatRoom;

import java.util.List;

/**
 * Created by adhit on 11/01/2018.
 */

public interface HistoryView {


    void openRoomChat(QiscusChatRoom qiscusChatRoom);

    void onFailureOpenRoomChat(String string);

    void onFailureHistoryList(String s);

    void showDataHistoryList(List<ChatRoomHistory> chatRoomPsychologyHistoryList1);
}
