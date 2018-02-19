package com.example.adhit.bikubikupsikolog.ui.home.history;


import android.app.Fragment;
import android.content.Intent;
import android.content.IntentFilter;
import android.net.ConnectivityManager;
import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.example.adhit.bikubikupsikolog.R;
import com.example.adhit.bikubikupsikolog.adapter.ChatHistoryAdapter;
import com.example.adhit.bikubikupsikolog.data.model.ChatRoomHistory;
import com.example.adhit.bikubikupsikolog.presenter.HistoryPresenter;
import com.example.adhit.bikubikupsikolog.receiver.RoomChatUpdateHistoryReceiver;
import com.example.adhit.bikubikupsikolog.service.HistoryRoomService;
import com.example.adhit.bikubikupsikolog.service.NewTransactionUIService;
import com.example.adhit.bikubikupsikolog.ui.chat.ChatActivity;
import com.example.adhit.bikubikupsikolog.util.ShowAlert;
import com.qiscus.sdk.data.model.QiscusChatRoom;

import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 */
public class HistoryFragment extends Fragment implements HistoryView, ChatHistoryAdapter.OnDetailListener, RoomChatUpdateHistoryReceiver.PeriodicCheckRoomChatHistoryReceiverListener {

    private ChatHistoryAdapter chatHistoryAdapter;
    private HistoryPresenter historyPresenter;
    private RecyclerView rvChatHistory;
    private ProgressBar pbLoading;
    private TextView tvError;
    private SwipeRefreshLayout srlChatHistory;
    private RoomChatUpdateHistoryReceiver mBroadcast;


    public HistoryFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        registerReceiver();
        View view = inflater.inflate(R.layout.fragment_history, container, false);
        rvChatHistory = view.findViewById(R.id.rv_chat_history);
        pbLoading = view.findViewById(R.id.pb_loading);
        tvError = view.findViewById(R.id.tv_error);
        srlChatHistory = view.findViewById(R.id.srl_chat_history);
        srlChatHistory.setOnRefreshListener(() -> {
            tvError.setText("");
            pbLoading.setVisibility(View.VISIBLE);
            rvChatHistory.setVisibility(View.GONE);
            historyPresenter.getChattingHistoryList();
            srlChatHistory.setRefreshing(false);
        });
        initView();
        return view;
    }

    public void initView() {
        chatHistoryAdapter = new ChatHistoryAdapter(getActivity());
        chatHistoryAdapter.setOnClickDetailListener(this);
        rvChatHistory.setLayoutManager(new LinearLayoutManager(getActivity()));
        rvChatHistory.setAdapter(chatHistoryAdapter);
        historyPresenter = new HistoryPresenter(this);
        historyPresenter.getChattingHistoryList();
    }
    private void registerReceiver() {
        mBroadcast = new RoomChatUpdateHistoryReceiver(this);
        IntentFilter filter = new IntentFilter(ConnectivityManager.CONNECTIVITY_ACTION);
        filter.addAction(RoomChatUpdateHistoryReceiver.TAG);
        getActivity().registerReceiver(mBroadcast, filter);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        unregisterReceiver();
    }

    @Override
    public void onStart() {
        super.onStart();
        historyPresenter.getChattingHistoryList();
        registerReceiver();
    }

    @Override
    public void onPause() {
        super.onPause();
        unregisterReceiver();
    }

    @Override
    public void onStop() {
        super.onStop();
        unregisterReceiver();
    }

    private void unregisterReceiver() {
        try {
            if (mBroadcast != null) {
                getActivity().unregisterReceiver(mBroadcast);
            }
        } catch (Exception e) {
            Log.i("", "broadcastReceiver is already unregistered");
            mBroadcast = null;
        }

    }



    @Override
    public void openRoomChat(QiscusChatRoom qiscusChatRoom) {
        Intent intent = ChatActivity.generateIntent(getActivity(), qiscusChatRoom, true);
        startActivity(intent);
    }

    @Override
    public void onFailureOpenRoomChat(String string) {
        ShowAlert.showToast(getActivity(), string);
    }

    @Override
    public void onFailureHistoryList(String s) {
        pbLoading.setVisibility(View.GONE);
        tvError.setText(s);
    }

    @Override
    public void showDataHistoryList(List<ChatRoomHistory> chatRoomPsychologyHistoryList1) {
        pbLoading.setVisibility(View.GONE);
        rvChatHistory.setVisibility(View.VISIBLE);
        chatHistoryAdapter.setData(chatRoomPsychologyHistoryList1);
    }

    @Override
    public void onItemDetailClicked(int idRoom) {
        historyPresenter.openRoomChat(getActivity(), idRoom);
    }

    @Override
    public void showMessage() {
        ShowAlert.showToast(getActivity(),getResources().getString(R.string.text_no_history_chat));
    }

    @Override
    public void onResume() {

        super.onResume();
        registerReceiver();
        historyPresenter.getChattingHistoryList();
    }

    @Override
    public void handleFromReceiver(List<ChatRoomHistory> chatRoomHistoryList) {
        chatHistoryAdapter.setData(chatRoomHistoryList);
    }
}
