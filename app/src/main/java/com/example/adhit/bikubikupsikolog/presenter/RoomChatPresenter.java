package com.example.adhit.bikubikupsikolog.presenter;

import android.content.Context;

import com.example.adhit.bikubikupsikolog.R;
import com.example.adhit.bikubikupsikolog.data.local.SaveUserData;
import com.example.adhit.bikubikupsikolog.data.model.ChatRoom;
import com.example.adhit.bikubikupsikolog.data.model.RoomInfo;
import com.example.adhit.bikubikupsikolog.data.model.Transaction;
import com.example.adhit.bikubikupsikolog.data.network.RetrofitClient;
import com.example.adhit.bikubikupsikolog.ui.home.roomchat.RoomChatView;
import com.example.adhit.bikubikupsikolog.util.ShowAlert;
import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.reflect.TypeToken;
import com.qiscus.sdk.data.model.QiscusChatRoom;
import com.qiscus.sdk.data.remote.QiscusApi;
import com.qiscus.sdk.util.QiscusRxExecutor;

import org.json.JSONArray;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by adhit on 05/01/2018.
 */

public class RoomChatPresenter {
    private RoomChatView roomChatView;

    public RoomChatPresenter(RoomChatView roomChatView){
        this.roomChatView= roomChatView;
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
                                List<ChatRoom> transactionList1 = new ArrayList<>();
                                for(int i=0; i<transactionList.size(); i++){
                                    if(transactionList.get(i).getStatusTrx().equals("2")){
                                        transactionList1.add(transactionList.get(i));
                                    }
                                }
                                cek(transactionList1);
                                //roomChatView.showData(transactionList1);
                            }else{
                                roomChatView.onFailure(body.get("message").getAsString());
                            }
                        }else{
                            roomChatView.onFailure("No Data Found");
                        }
                    }

                    @Override
                    public void onFailure(Call<JsonObject> call, Throwable t) {
                        roomChatView.onFailure("Server Error");
                    }
                });


    }

    public void cek(List<ChatRoom> transactionArrayList){
        ArrayList<String> idRoomList = new ArrayList<>();
        for(int i =0; i<transactionArrayList.size(); i++){
            idRoomList.add((String) transactionArrayList.get(i).getIdRoom());
        }
        if(idRoomList.size()==0){
            roomChatView.onFailure("Anda belum melakukan chat");
        }else {
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
                                roomChatView.showData(transactionArrayList);
                            }else {
                                roomChatView.onFailure("No Data Found");
                            }
                        }

                        @Override
                        public void onFailure(Call<JsonObject> call, Throwable t) {
                            roomChatView.onFailure("Server Error");
                        }
                    });
        }

    }

    public void openRoomChat(Context context, int id){
        ShowAlert.showProgresDialog(context);
        QiscusRxExecutor.execute(QiscusApi.getInstance().getChatRoom(id),
                new QiscusRxExecutor.Listener<QiscusChatRoom>() {
                    @Override
                    public void onSuccess(QiscusChatRoom qiscusChatRoom) {
                        roomChatView.openRoomChat(qiscusChatRoom);
                        ShowAlert.closeProgresDialog();
                    }
                    @Override
                    public void onError(Throwable throwable) {
                        throwable.printStackTrace();
                        roomChatView.onFailureOpenRoomChat(context.getResources().getString(R.string.text_failed_open_room_chat));
                        ShowAlert.closeProgresDialog();
                        ShowAlert.showToast(context,"gagal");
                    }
                });

    }

}
