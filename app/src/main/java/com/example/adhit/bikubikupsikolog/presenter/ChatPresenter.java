package com.example.adhit.bikubikupsikolog.presenter;

import android.content.Intent;

import com.example.adhit.bikubikupsikolog.data.network.RetrofitClient;
import com.example.adhit.bikubikupsikolog.ui.chat.ChatView;
import com.google.gson.JsonObject;
import com.qiscus.sdk.Qiscus;
import com.qiscus.sdk.data.model.QiscusComment;

import org.json.JSONObject;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by adhit on 08/01/2018.
 */

public class ChatPresenter {

    private ChatView chatView;
    public ChatPresenter(ChatView chatView){
        this.chatView = chatView;
    }

    public void showMessage(QiscusComment qiscusComment){
        try {
            JSONObject payload = new JSONObject(qiscusComment.getExtraPayload());
            String[] message  = payload.optJSONObject("content").optString("description").split(" ");
            chatView.showMessage(message[0], message[1]);

        } catch (Exception e) {
           chatView.showError(e.getMessage());
        }

    }

    public void sendMessageSDK(String emailSender, String idRoom, String message){
        RetrofitClient.getInstance().getApiQiscus()
                .sendMessage(emailSender, idRoom, message)
                .enqueue(new Callback<JsonObject>() {
                    @Override
                    public void onResponse(Call<JsonObject> call, Response<JsonObject> response) {
                        if(response.isSuccessful()){
                            chatView.showMessage("success");
                        }else{
                            chatView.showMessage("failed");
                        }

                    }

                    @Override
                    public void onFailure(Call<JsonObject> call, Throwable t) {
                        chatView.showMessage("failed");
                    }
                });

    }
}
