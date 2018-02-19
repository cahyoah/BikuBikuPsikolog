package com.example.adhit.bikubikupsikolog.presenter;

import android.content.Context;

import com.example.adhit.bikubikupsikolog.R;
import com.example.adhit.bikubikupsikolog.data.model.ChatRoomHistory;
import com.example.adhit.bikubikupsikolog.data.network.RetrofitClient;
import com.example.adhit.bikubikupsikolog.ui.home.history.HistoryView;
import com.example.adhit.bikubikupsikolog.util.ShowAlert;
import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.reflect.TypeToken;
import com.qiscus.sdk.data.model.QiscusChatRoom;
import com.qiscus.sdk.data.remote.QiscusApi;
import com.qiscus.sdk.util.QiscusRxExecutor;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by adhit on 11/01/2018.
 */

public class HistoryPresenter {
    private HistoryView historyView;

    public HistoryPresenter(HistoryView historyView){
        this.historyView = historyView;
    }



    public void getChattingHistoryList(){
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
                                List<ChatRoomHistory> chatRoomPsychologyHistoryList =  new Gson().fromJson(transactionArray, type);
                                List<ChatRoomHistory> chatRoomPsychologyHistoryList1 = new ArrayList<>();
                                for(int i=0; i<chatRoomPsychologyHistoryList.size(); i++){
                                    if(chatRoomPsychologyHistoryList.get(i).getStatusTrx().equals("1")){
                                        chatRoomPsychologyHistoryList1.add(chatRoomPsychologyHistoryList.get(i));
                                    }
                                }
                                historyView.showDataHistoryList(chatRoomPsychologyHistoryList1);
                            }else{
                                historyView.onFailureHistoryList(body.get("message").getAsString());
                            }
                        }else {
                            historyView.onFailureHistoryList("Anda belum pernah melakukan transaksi apapun");
                        }
                    }

                    @Override
                    public void onFailure(Call<JsonObject> call, Throwable t) {
                       historyView.onFailureHistoryList("Server Bermasalah");
                    }
                });
//        RetrofitClient.getInstance().getApiQiscus()
//                .getChatRoomHistory(SaveUserData.getInstance().getPsikolog().getId(), true)
//                .enqueue(new Callback<JsonObject>() {
//                    @Override
//                    public void onResponse(Call<JsonObject> call, Response<JsonObject> response) {
//                        if(response.isSuccessful()){
//                            JsonObject body = response.body();
//                            JsonObject results = body.get("results").getAsJsonObject();
//                            JsonArray data = results.get("rooms_info").getAsJsonArray();
//                            Type type = new TypeToken<List<ChatRoomPsychology>>(){}.getType();
//                            List<ChatRoomPsychology> carList = new Gson().fromJson(data, type);
//                            for(int i=0; i<carList.size();i++){
//
//                                if(!carList.get(i).getLastCommentMessage().equals("Sesi Chat Ditutup") || carList.get(i).getLastCommentMessage().equals("")){
//                                    carList.remove(i);
//                                }
//                            }
//                            historyView.showData(carList);
//                        }else{
//                            historyView.onFailure("Data Not Found");
//                            System.out.println(response.toString());
//                        }
//                    }
//
//                    @Override
//                    public void onFailure(Call<JsonObject> call, Throwable t) {
//                        historyView.onFailure("Server Error");
//                        System.out.println(t.getMessage());
//                    }
//                });
    }

    public void openRoomChat(Context context, int id){
        ShowAlert.showProgresDialog(context);
        QiscusRxExecutor.execute(QiscusApi.getInstance().getChatRoom(id),
                new QiscusRxExecutor.Listener<QiscusChatRoom>() {
                    @Override
                    public void onSuccess(QiscusChatRoom qiscusChatRoom) {
                        historyView.openRoomChat(qiscusChatRoom);
                        ShowAlert.closeProgresDialog();
                    }
                    @Override
                    public void onError(Throwable throwable) {
                        throwable.printStackTrace();
                        historyView.onFailureOpenRoomChat(context.getResources().getString(R.string.text_failed_open_room_chat));
                        ShowAlert.showToast(context,"gagal");
                        ShowAlert.closeProgresDialog();
                    }
                });

    }

}
