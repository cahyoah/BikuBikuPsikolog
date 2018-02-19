package com.example.adhit.bikubikupsikolog.presenter;

import android.content.Context;

import com.example.adhit.bikubikupsikolog.R;
import com.example.adhit.bikubikupsikolog.data.local.SaveUserData;
import com.example.adhit.bikubikupsikolog.data.model.Transaction;
import com.example.adhit.bikubikupsikolog.data.network.RetrofitClient;
import com.example.adhit.bikubikupsikolog.ui.home.transaction.TransactionView;
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
 * Created by adhit on 05/01/2018.
 */

public class TransactionPresenter {
    private TransactionView transactionView;

    public TransactionPresenter(TransactionView transactionView){
        this.transactionView = transactionView;
    }

    public void getAllTransaction(){
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
                                List<Transaction> transactionList =  new Gson().fromJson(transactionArray, type);
                                List<Transaction> transactionList1 = new ArrayList<>();
                                for(int i=0; i<transactionList.size(); i++){
                                    if(transactionList.get(i).getStatusTrx().equals("0")){
                                      transactionList1.add(transactionList.get(i));
                                    }
                                }
                                transactionView.showDataTransaction(transactionList1);
                            }else{
                                transactionView.onFailure(body.get("message").getAsString());
                            }
                        }else{
                            transactionView.onFailure("No Data Found");
                        }
                    }

                    @Override
                    public void onFailure(Call<JsonObject> call, Throwable t) {
                        transactionView.onFailure("Server Error");
                    }
                });

    }



    public void changeTransactionStatus(Context context, String service, String invoice, int idRoom, String status){
        RetrofitClient.getInstance()
                .getApi()
                .changeTransactionStatus(service, invoice , status)
                .enqueue(new Callback<JsonObject>() {
                    @Override
                    public void onResponse(Call<JsonObject> call, Response<JsonObject> response) {
                        if(response.isSuccessful()){
                            JsonObject body = response.body();
                            boolean statusResponse = body.get("status").getAsBoolean();
                            if(statusResponse){
//                                if(status.equals("accept")){
//                                    openRoomChat(context, idRoom);
//                                }
                                transactionView.onSuccesChangeStatus();
                            }else{

                            }
                        }else{

                        }
                    }

                    @Override
                    public void onFailure(Call<JsonObject> call, Throwable t) {
                        System.out.println(t.getMessage());
                    }
                });
    }


}
