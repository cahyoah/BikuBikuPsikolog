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

public class HistoryRoomService extends Service {
    private static final String TAG = Transaction.class.getSimpleName();
    private Timer mTimer;
    public HistoryRoomService() {
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
        mTimer.cancel();
        mTimer = null;
        requestDataWithInterval();

    }
    @Override
    public void onCreate() {
        super.onCreate();
        Log.d(TAG, "onCreate Service");

        requestDataWithInterval();
    }
    private void requestDataWithInterval() {
        mTimer = new Timer();
        mTimer.schedule(new TimerTask() {
            @Override
            public void run() {

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
                                        Type type = new TypeToken<List<ChatRoomHistory>>(){}.getType();
                                        ArrayList<ChatRoomHistory> chatRoomPsychologyHistoryList =  new Gson().fromJson(transactionArray, type);
                                        ArrayList<ChatRoomHistory> chatRoomPsychologyHistoryList1 = new ArrayList<>();
                                        for(int i=0; i<chatRoomPsychologyHistoryList.size(); i++){
                                            if(chatRoomPsychologyHistoryList.get(i).getStatusTrx().equals("1")){
                                                chatRoomPsychologyHistoryList1.add(chatRoomPsychologyHistoryList.get(i));
                                            }
                                        }
                                        sendToReceiverChatRoomHistory(chatRoomPsychologyHistoryList1);
                                    }else{
                                    }
                                }else {
                                }
                            }

                            @Override
                            public void onFailure(Call<JsonObject> call, Throwable t) {

                            }
                        });

            }
        }, 3000, 3000);
    }

    private void sendToReceiverChatRoomHistory(ArrayList<ChatRoomHistory> chatRoomHistoryList) {
        Intent intent = new Intent();
        intent.setAction(RoomChatUpdateHistoryReceiver.TAG);
        intent.putParcelableArrayListExtra("room_chat_history",  chatRoomHistoryList);
        sendBroadcast(intent);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        mTimer.cancel();
        Log.d(TAG, "onDestroy Service");
    }

}
