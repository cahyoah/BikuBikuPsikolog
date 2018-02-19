package com.example.adhit.bikubikupsikolog.ui.home.transaction;


import android.app.Fragment;
import android.content.Intent;
import android.content.IntentFilter;
import android.net.ConnectivityManager;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.Switch;
import android.widget.TextView;

import com.example.adhit.bikubikupsikolog.R;
import com.example.adhit.bikubikupsikolog.adapter.TransactionAdapter;
import com.example.adhit.bikubikupsikolog.data.model.Transaction;
import com.example.adhit.bikubikupsikolog.presenter.TransactionPresenter;
import com.example.adhit.bikubikupsikolog.receiver.NewTransactionReceiver;
import com.example.adhit.bikubikupsikolog.service.NewTransactionUIService;
import com.example.adhit.bikubikupsikolog.service.RoomChatService;
import com.example.adhit.bikubikupsikolog.ui.chat.ChatActivity;
import com.qiscus.sdk.data.model.QiscusChatRoom;

import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 */
public class TransactionFragment extends Fragment implements TransactionView, TransactionAdapter.OnDetailListener, NewTransactionReceiver.PeriodicCheckTransactionReceiverListener {

    private TransactionAdapter adapter;
    private TransactionPresenter transactionPresenter;
    private RecyclerView rvNewRequest;
    private Switch swOnOff;
    private NewTransactionReceiver mBroadcast;
    private ProgressBar pbLoading;
    private TextView tvError;
    private Intent mService;


    public TransactionFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_transaction, container, false);
        swOnOff = view.findViewById(R.id.sw_on_off);
        rvNewRequest = view.findViewById(R.id.rv_new_request);
        pbLoading = view.findViewById(R.id.pb_loading);
        tvError = view.findViewById(R.id.tv_error);
        initView();
        return view;
    }
    public void initView(){
        adapter =  new TransactionAdapter(getActivity());
        adapter.setOnClickDetailListener(this);
        rvNewRequest.setLayoutManager(new LinearLayoutManager(getActivity()));
        rvNewRequest.setAdapter(adapter);
        transactionPresenter = new TransactionPresenter(this);
        transactionPresenter.getAllTransaction();
        tvError.setText("");
        registerReceiver();
    }


    @Override
    public void showDataTransaction(List<Transaction> transactionList) {
        if(transactionList.size()>=0){
            pbLoading.setVisibility(View.GONE);
            rvNewRequest.setVisibility(View.VISIBLE);
            adapter.setData(transactionList);
            tvError.setVisibility(View.GONE);
            tvError.setText("");
        }
        if(transactionList.size() ==0){
            tvError.setText("Belum ada transaksi baru");
            tvError.setVisibility(View.VISIBLE);

        }

    }

    @Override
    public void onFailure(String message) {
        pbLoading.setVisibility(View.GONE);
        rvNewRequest.setVisibility(View.GONE);
        tvError.setText(message);
        tvError.setVisibility(View.VISIBLE);
    }

    @Override
    public void openRoomChat(QiscusChatRoom qiscusChatRoom) {
        Intent intent = ChatActivity.generateIntent(getActivity(), qiscusChatRoom, false);
        startActivity(intent);
    }

    @Override
    public void onFailureOpenRoomChat(String string) {

    }

    @Override
    public void onSuccesChangeStatus() {
        transactionPresenter.getAllTransaction();
    }

    @Override
    public void onResume() {
        super.onResume();
        registerReceiver();
    }

    @Override
    public void onPause() {
        super.onPause();
        unregisterReceiver();
    }

    @Override
    public void onItemDetailClicked(Transaction transaction, String status) {
        if(status.equals("cancel")){
            System.out.println(status);
            transactionPresenter.changeTransactionStatus(getActivity(), transaction.getLayanan(), transaction.getInvoice(),0,status);

        }
        if(status.equals("accept")){
            //transactionPresenter.createRoomChat(getActivity(), transaction.getIdBiquers(), transaction.getIdBiquers(), transaction.getInvoice());
            transactionPresenter.changeTransactionStatus(getActivity(), transaction.getLayanan(), transaction.getInvoice(),0,status);

        }
    }
    private void registerReceiver() {
        mBroadcast = new NewTransactionReceiver(this);
        IntentFilter filter = new IntentFilter(ConnectivityManager.CONNECTIVITY_ACTION);
        filter.addAction(NewTransactionReceiver.TAG);
        getActivity().registerReceiver(mBroadcast, filter);
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

    @Override
    public void onStart() {
        super.onStart();
        mService = new Intent(getActivity(), NewTransactionUIService.class);
        getActivity().startService(mService);
    }


    @Override
    public void onStop() {
        super.onStop();
        getActivity().stopService(mService);
        unregisterReceiver();
    }


    @Override
    public void handleFromReceiver(List<Transaction> transactionList) {
        if(transactionList != null){
             if(transactionList.size()>0 ){
                pbLoading.setVisibility(View.GONE);
                rvNewRequest.setVisibility(View.VISIBLE);
                adapter.setData(transactionList);
                tvError.setVisibility(View.GONE);
                tvError.setText("");
            }
        }else {
            pbLoading.setVisibility(View.GONE);
            tvError.setText("Belum ada transaksi baru");
            tvError.setVisibility(View.VISIBLE);
            rvNewRequest.setVisibility(View.GONE);
        }
    }
}
