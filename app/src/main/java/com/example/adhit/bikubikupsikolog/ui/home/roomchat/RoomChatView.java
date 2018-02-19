package com.example.adhit.bikubikupsikolog.ui.home.roomchat;

import com.example.adhit.bikubikupsikolog.data.model.ChatRoom;
import com.qiscus.sdk.data.model.QiscusChatRoom;

import java.util.List;

/**
 * Created by adhit on 05/01/2018.
 */

public interface RoomChatView {

    void onFailure(String s);

    void showData(List<ChatRoom> carList);

    void openRoomChat(QiscusChatRoom qiscusChatRoom);

    void onFailureOpenRoomChat(String string);
}
