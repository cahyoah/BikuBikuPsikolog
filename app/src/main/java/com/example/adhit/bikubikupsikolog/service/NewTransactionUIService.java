package com.example.adhit.bikubikupsikolog.service;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.util.Log;

import com.example.adhit.bikubikupsikolog.data.model.ChatRoomHistory;
import com.example.adhit.bikubikupsikolog.data.model.Transaction;
import com.example.adhit.bikubikupsikolog.data.network.RetrofitClient;
import com.example.adhit.bikubikupsikolog.receiver.NewTransactionReceiver;
import com.example.adhit.bikubikupsikolog.receiver.RoomChatUpdateHistoryReceiver;
import com.example.adhit.bikubikupsikolog.receiver.RoomChatUpdateReceiver;
import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.reflect.TypeToken;
import com.qiscus.sdk.data.model.QiscusChatRoom;
import com.qiscus.sdk.data.remote.QiscusApi;
import com.qiscus.sdk.util.QiscusRxExecutor;

import org.json.JSONException;
import org.json.JSONObject;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class NewTransactionUIService extends Service {
    private static final String TAG = Transaction.class.getSimpleName();
    private Timer mTimer;
    public NewTransactionUIService() {
    }


    @Override
    public IBinder onBind(Intent intent) {
        // TODO: Return the communication channel to the service.
        throw new UnsupportedOperationException("Not yet implemented");
    }
    @Override
    public void onLowMemory() {
        super.onLowMemory();
        Log.d(TAG, "Low memory");
        mTimer = null;
        requestDataWithInterval();

    }
    @Override
    public void onCreate() {
        super.onCreate();
        Log.d(TAG, "onCreate Service ui");

        requestDataWithInterval();
    }
    private void requestDataWithInterval() {

        mTimer = new Timer();
        mTimer.schedule(new TimerTask() {
            @Override
            public void run() {
                ArrayList<Transaction> transactionList1 = new ArrayList<>();
                sendToReceiver((ArrayList<Transaction>) transactionList1);

                RetrofitClient.getInstance()
                        .getApi()
                        .getAllTransaction()
                        .enqueue(new Callback<JsonObject>() {
                            @Override
                            public void onResponse(Call<JsonObject> call, Response<JsonObject> response) {
                                if(response.isSuccessful()){
                                    JsonObject body = response.body();
                                    boolean status = body.get("status").getAsBoolean();
                                    if(status){
                                        JsonArray transactionArray = body.get("result").getAsJsonArray();
                                        Type type = new TypeToken<List<Transaction>>(){}.getType();
                                        ArrayList<Transaction> transactionList =  new Gson().fromJson(transactionArray, type);
                                        ArrayList<Transaction> transactionList1 = new ArrayList<>();
                                        for(int i=0; i<transactionList.size(); i++){
                                            if(transactionList.get(i).getStatusTrx().equals("0")){
                                                transactionList1.add(transactionList.get(i));
                                            }
                                        }
                                        if(transactionList1.size() ==0){
                                            transactionList1 = null;
                                        }
                                        sendToReceiver( transactionList1);
                                    }
                                }
                            }

                            @Override
                            public void onFailure(Call<JsonObject> call, Throwable t) {

                            }
                        });

            }
        }, 3000, 3000);
    }

    private void sendToReceiver(ArrayList<Transaction> transactionArrayList) {

        Intent intent = new Intent();
        intent.setAction(NewTransactionReceiver.TAG);
        intent.putParcelableArrayListExtra("new_transaction", transactionArrayList);
        sendBroadcast(intent);
    }


    @Override
    public void onDestroy() {
        super.onDestroy();
        mTimer.cancel();
        Log.d(TAG, "onDestroy Service");
    }

}
