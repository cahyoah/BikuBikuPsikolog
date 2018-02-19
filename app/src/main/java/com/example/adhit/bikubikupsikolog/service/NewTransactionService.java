package com.example.adhit.bikubikupsikolog.service;

import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.media.RingtoneManager;
import android.net.Uri;
import android.support.v4.app.NotificationCompat;
import android.support.v4.content.ContextCompat;

import com.example.adhit.bikubikupsikolog.R;
import com.example.adhit.bikubikupsikolog.data.model.ChatRoomHistory;
import com.example.adhit.bikubikupsikolog.data.model.Transaction;
import com.example.adhit.bikubikupsikolog.data.network.RetrofitClient;
import com.example.adhit.bikubikupsikolog.ui.home.HomeActivity;
import com.google.android.gms.gcm.GcmNetworkManager;
import com.google.android.gms.gcm.GcmTaskService;
import com.google.android.gms.gcm.TaskParams;
import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.reflect.TypeToken;

import org.json.JSONArray;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by adhit on 19/01/2018.
 */

public class NewTransactionService extends GcmTaskService {
    public static String TAG_TRANSACTION = "Transaction";
    @Override
    public int onRunTask(TaskParams taskParams) {
        int result = 0;
        if (taskParams.getTag().equals(TAG_TRANSACTION)){
           result = GcmNetworkManager.RESULT_SUCCESS;
          // showNotification(getApplicationContext(), "aaa", "aa",123);
            getNewTransaction();
        }
        return result;
    }

    public  void getNewTransaction(){
        RetrofitClient.getInstance()
                .getApi()
                .getAllTransaction()
                .enqueue(new Callback<JsonObject>() {
                    @Override
                    public void onResponse(Call<JsonObject> call, Response<JsonObject> response) {
                        if(response.isSuccessful()){
                            JsonObject body= response.body();
                            boolean status= body.get("status").getAsBoolean();
                            if(status){
                                JsonArray transactionArray = body.get("result").getAsJsonArray();
                                Type type = new TypeToken<List<Transaction>>(){}.getType();
                                List<Transaction> transactionList =  new Gson().fromJson(transactionArray, type);
                                for(int i=0; i<transactionList.size(); i++){
                                    if(transactionList.get(i).getStatusTrx().equals("0") ){
                                        showNotification(getApplicationContext(),
                                                "Permintaaan konsultasi",
                                                transactionList.get(i).getNamaBiquers()+" ingin berkonsultasi dengan anda",
                                                Integer.parseInt(transactionList.get(i).getIdBiquers()));

                                    }
                                }
                            }
                        }
                    }

                    @Override
                    public void onFailure(Call<JsonObject> call, Throwable t) {

                    }
                });
    }


    private void showNotification(Context context, String title, String message, int notifId){
        Intent resultIntent = new Intent(this, HomeActivity.class);
       PendingIntent resultPendingIntent =
                PendingIntent.getActivity(
                        this,
                        0,
                        resultIntent,
                        PendingIntent.FLAG_UPDATE_CURRENT
                );
        NotificationManager notificationManagerCompat = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
        Uri alarmSound = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);
        NotificationCompat.Builder builder = new NotificationCompat.Builder(context)
                .setContentTitle(title)
                .setContentIntent(resultPendingIntent)
                .setSmallIcon(R.drawable.logo)
                .setContentText(message)
                .setAutoCancel(true)
                .setColor(ContextCompat.getColor(context, android.R.color.black))
                .setVibrate(new long[]{1000, 1000, 1000, 1000, 1000})
                .setSound(alarmSound);

        notificationManagerCompat.notify(notifId, builder.build());
    }

}