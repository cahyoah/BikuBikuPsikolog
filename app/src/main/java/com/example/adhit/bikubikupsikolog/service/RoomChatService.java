package com.example.adhit.bikubikupsikolog.service;

import android.app.Service;
import android.content.Intent;
import android.os.Handler;
import android.os.IBinder;
import android.support.annotation.Nullable;
import android.util.Log;

import com.example.adhit.bikubikupsikolog.data.local.SaveUserData;
import com.example.adhit.bikubikupsikolog.data.model.ChatRoom;
import com.example.adhit.bikubikupsikolog.data.model.ChatRoomHistory;
import com.example.adhit.bikubikupsikolog.data.model.RoomInfo;
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

public class RoomChatService extends Service {

    private static final String TAG = Transaction.class.getSimpleName();
    private Timer mTimer;
    Runnable runnable;
    Handler handler;
    public RoomChatService() {
    }


    @Override
    public void onLowMemory() {
        super.onLowMemory();
//        Log.d(TAG, "Low memory");
//        mTimer = null;
//        requestDataWithInterval();

    }

    @Override
    public IBinder onBind(Intent intent) {
        // TODO: Return the communication channel to the service.
        throw new UnsupportedOperationException("Not yet implemented");
    }
    @Override
    public void onCreate() {
        super.onCreate();
        Log.d(TAG, "onCreate Service");

      //  requestDataWithInterval();
    }
    private void requestDataWithInterval() {

        mTimer = new Timer();
        mTimer.schedule(new TimerTask() {
            @Override
            public void run() {

               getChattingRoomList();
            }
        }, 1000, 1000);



    }



    private void sendToReceiverChatRoom(ArrayList<ChatRoom> qiscusChatRoomList) {
        Intent intent = new Intent();
        intent.setAction(RoomChatUpdateReceiver.TAG);
        intent.putParcelableArrayListExtra("room_chat", qiscusChatRoomList);
        sendBroadcast(intent);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
       //  mTimer.cancel();
        //handler.removeCallbacks(runnable);
        Log.d(TAG, "onDestroy Service");
    }
    public void getChattingRoomList(){
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
                                Type type = new TypeToken<List<ChatRoom>>(){}.getType();
                                List<ChatRoom> transactionList =  new Gson().fromJson(transactionArray, type);
                                ArrayList<ChatRoom> transactionList1 = new ArrayList<>();
                                for(int i=0; i<transactionList.size(); i++){
                                    if(transactionList.get(i).getStatusTrx().equals("2")){
                                        transactionList1.add(transactionList.get(i));
                                    }
                                }
                                cek(transactionList1);
                                //roomChatView.showData(transactionList1);
                            }else{

                            }
                        }else{
                        }
                    }

                    @Override
                    public void onFailure(Call<JsonObject> call, Throwable t) {

                    }
                });


    }

    public void cek(ArrayList<ChatRoom> transactionArrayList){
        ArrayList<String> idRoomList = new ArrayList<>();
        for(int i =0; i<transactionArrayList.size(); i++){
            idRoomList.add((String) transactionArrayList.get(i).getIdRoom());
        }
        RetrofitClient.getInstance()
                .getApiQiscus()
                .getRoomInfo(SaveUserData.getInstance().getPsikolog().getId()+"p", idRoomList)
                .enqueue(new Callback<JsonObject>() {
                    @Override
                    public void onResponse(Call<JsonObject> call, Response<JsonObject> response) {
                        if(response.isSuccessful()){
                            JsonObject body = response.body();
                            JsonObject resultObject = body.get("results").getAsJsonObject();
                            JsonArray roomInfoArray = resultObject.get("rooms_info").getAsJsonArray();
                            Type type = new TypeToken<List<RoomInfo>>(){}.getType();
                            List<RoomInfo> roomInfoList = new Gson().fromJson(roomInfoArray, type);
                            for(int i =0; i<transactionArrayList.size(); i++){
                                for(int j=0; j<roomInfoList.size(); j++){
                                    if(transactionArrayList.get(i).getIdRoom()!=null){
                                        if(transactionArrayList.get(i).getIdRoom().equals(Integer.toString(roomInfoList.get(j).getRoomId()))){
                                            transactionArrayList.get(i).setLastMessage(roomInfoList.get(j).getLastCommentMessage());
                                            transactionArrayList.get(i).setUnreadCount(roomInfoList.get(j).getUnreadCount());
                                        }
                                    }
                                }
                            }
                            sendToReceiverChatRoom(transactionArrayList);
                        }
                    }

                    @Override
                    public void onFailure(Call<JsonObject> call, Throwable t) {

                    }
                });
    }
}
