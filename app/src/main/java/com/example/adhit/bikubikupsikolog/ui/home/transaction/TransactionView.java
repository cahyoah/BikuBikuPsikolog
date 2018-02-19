package com.example.adhit.bikubikupsikolog.ui.home.transaction;

import com.example.adhit.bikubikupsikolog.data.model.Transaction;
import com.qiscus.sdk.data.model.QiscusChatRoom;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by adhit on 05/01/2018.
 */

public interface TransactionView {

    void showDataTransaction(List<Transaction> transactionList);

    void onFailure(String message);

    void openRoomChat(QiscusChatRoom qiscusChatRoom);

    void onFailureOpenRoomChat(String string);

    void onSuccesChangeStatus();
}
