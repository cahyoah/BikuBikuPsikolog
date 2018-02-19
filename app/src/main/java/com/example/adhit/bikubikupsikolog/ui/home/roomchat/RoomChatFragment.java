package com.example.adhit.bikubikupsikolog.ui.home.roomchat;


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
import com.example.adhit.bikubikupsikolog.adapter.RoomChatAdapter;
import com.example.adhit.bikubikupsikolog.data.model.ChatRoom;
import com.example.adhit.bikubikupsikolog.presenter.RoomChatPresenter;
import com.example.adhit.bikubikupsikolog.receiver.RoomChatUpdateReceiver;
import com.example.adhit.bikubikupsikolog.service.RoomChatService;
import com.example.adhit.bikubikupsikolog.ui.chat.ChatActivity;
import com.example.adhit.bikubikupsikolog.util.ShowAlert;
import com.qiscus.sdk.data.model.QiscusChatRoom;

import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 */
public class RoomChatFragment extends Fragment implements RoomChatView, RoomChatAdapter.OnDetailListener, RoomChatUpdateReceiver.PeriodicCheckRoomChatReceiverListener {

    private RoomChatAdapter adapter;
    private RoomChatPresenter chatPresenter;
    private RecyclerView rvChat;
    private ProgressBar pbLoading;
    private TextView tvError;
    private SwipeRefreshLayout srlChatRoom;
    private RoomChatUpdateReceiver mBroadcast;

    public RoomChatFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_room_chat, container, false);
        rvChat = view.findViewById(R.id.rv_chat);
        pbLoading = view.findViewById(R.id.pb_loading);
        tvError = view.findViewById(R.id.tv_error);
        srlChatRoom = view.findViewById(R.id.srl_chat);
        srlChatRoom.setOnRefreshListener(() -> {
            tvError.setText("");
            pbLoading.setVisibility(View.VISIBLE);
            rvChat.setVisibility(View.GONE);
            chatPresenter.getChattingRoomList();
            srlChatRoom.setRefreshing(false);
        });
        initView();
        return view;
    }
    private void registerReceiver() {
        mBroadcast = new RoomChatUpdateReceiver(this);
        IntentFilter filter = new IntentFilter(ConnectivityManager.CONNECTIVITY_ACTION);
        filter.addAction(RoomChatUpdateReceiver.TAG);
        getActivity().registerReceiver(mBroadcast, filter);
    }


    @Override
    public void onStart() {
        super.onStart();
        registerReceiver();
        chatPresenter.getChattingRoomList();
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


    @Override
    public void onDestroy() {
        super.onDestroy();
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


    public void initView() {

        adapter = new RoomChatAdapter(getActivity());
        adapter.setOnClickDetailListener(this);
        rvChat.setLayoutManager(new LinearLayoutManager(getActivity()));
        rvChat.setAdapter(adapter);
        chatPresenter = new RoomChatPresenter(this);
        chatPresenter.getChattingRoomList();

    }

    @Override
    public void onResume() {
        super.onResume();
        registerReceiver();
        chatPresenter.getChattingRoomList();
    }

    @Override
    public void onFailure(String s) {
        rvChat.setVisibility(View.GONE);
        pbLoading.setVisibility(View.GONE);
        tvError.setVisibility(View.VISIBLE);
        tvError.setText(s);

    }

    @Override
    public void showData(List<ChatRoom> carList) {
        rvChat.setVisibility(View.GONE);
        tvError.setText("Anda belum melakukan chat");
        tvError.setVisibility(View.VISIBLE);
        if(carList.size()==0 || carList.isEmpty()){
            rvChat.setVisibility(View.GONE);
            tvError.setText("Anda belum melakukan chat");
            tvError.setVisibility(View.VISIBLE);
        }else{
            tvError.setVisibility(View.GONE);
            pbLoading.setVisibility(View.GONE);
            rvChat.setVisibility(View.VISIBLE);
            adapter.setData(carList);
            rvChat.setVisibility(View.VISIBLE);
        }
    }

    @Override
    public void openRoomChat(QiscusChatRoom qiscusChatRoom) {
        Intent intent = ChatActivity.generateIntent(getActivity(), qiscusChatRoom, false);
        startActivity(intent);
    }

    @Override
    public void onFailureOpenRoomChat(String string) {
        ShowAlert.showToast(getActivity(), string);
    }

    @Override
    public void onItemDetailClicked(int idRoom) {

       chatPresenter.openRoomChat(getActivity(), idRoom);
    }

    @Override
    public void showMessageFailure() {
        ShowAlert.showToast(getActivity(),getResources().getString(R.string.text_no_history_chat));
    }

    @Override
    public void handleFromReceiver(List<ChatRoom> qiscusChatRoomList) {
        if(qiscusChatRoomList !=null){
            if(qiscusChatRoomList.size() ==0){
                rvChat.setVisibility(View.GONE);
                pbLoading.setVisibility(View.GONE);
                tvError.setVisibility(View.VISIBLE);
                tvError.setText("Anda belum melakukan chat");
            }
            if(qiscusChatRoomList.size()>0){
                pbLoading.setVisibility(View.GONE);
                tvError.setVisibility(View.GONE);
                rvChat.setVisibility(View.VISIBLE);
                adapter.setData(qiscusChatRoomList);
            }
        }else{
            rvChat.setVisibility(View.GONE);
            pbLoading.setVisibility(View.GONE);
            tvError.setVisibility(View.VISIBLE);
            tvError.setText("Anda belum melakukan chat");
        }
    }


}
