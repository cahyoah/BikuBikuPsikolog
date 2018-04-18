package com.example.adhit.bikubikupsikolog.presenter;

import com.example.adhit.bikubikupsikolog.data.local.SaveUserData;
import com.example.adhit.bikubikupsikolog.data.local.SaveUserToken;
import com.example.adhit.bikubikupsikolog.data.local.Session;
import com.example.adhit.bikubikupsikolog.data.model.Psikolog;
import com.example.adhit.bikubikupsikolog.data.network.RetrofitClient;
import com.example.adhit.bikubikupsikolog.ui.home.account.AccountView;
import com.google.gson.JsonObject;
import com.qiscus.sdk.Qiscus;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by adhit on 08/01/2018.
 */

public class AccountPresenter {
    private AccountView accountView;


    public AccountPresenter (AccountView accountView){
        this.accountView = accountView;
    }
    public void showDataProfile(){
        Psikolog psikolog = SaveUserData.getInstance().getPsikolog();
        accountView.showDataProfile(psikolog);
    }

    public void updateStatus(String status1){
        RetrofitClient.getInstance()
                .getApi()
                .updatestatus(status1)
                .enqueue(new Callback<JsonObject>() {
                    @Override
                    public void onResponse(Call<JsonObject> call, Response<JsonObject> response) {
                        if(response.isSuccessful()){
                            JsonObject body = response.body();
                            boolean status = body.get("status").getAsBoolean();
                            String message = body.get("message").getAsString();
                            if(status){
                                if(status1.equals("online")){
                                    Session.getInstance().setStatus(true);
                                }else {
                                    Session.getInstance().setStatus(false);
                                }
                                accountView.onSuccessUpdateStatus(message);
                            }else {
                                accountView.onFailedUpdateStatus(message);
                            }
                        }else {
                            accountView.onFailedUpdateStatus("Terjadi Kesalahan");
                        }
                    }

                    @Override
                    public void onFailure(Call<JsonObject> call, Throwable t) {

                        accountView.onFailedUpdateStatus(t.getMessage());
                    }
                });

    }

    private void getStatus(){
        RetrofitClient.getInstance()
                .getApi()
                .getstatus()
                .enqueue(new Callback<JsonObject>() {
                    @Override
                    public void onResponse(Call<JsonObject> call, Response<JsonObject> response) {
                        if(response.isSuccessful()){
                            JsonObject body = response.body();
                            boolean status = body.get("status").getAsBoolean();

                            if(status){
                                String message = body.get("message").getAsString();
                                if(message.equals("online")){
                                    Session.getInstance().setStatus(true);
                                }else {
                                    Session.getInstance().setStatus(false);
                                }
                                accountView.onSuccessGetStatus(message);
                            }else {
                                accountView.onFailedGetStatus("Terjadi Kesalahan");
                            }
                        }else {
                            accountView.onFailedGetStatus("Terjadi Kesalahan");
                        }
                    }

                    @Override
                    public void onFailure(Call<JsonObject> call, Throwable t) {
                        accountView.onFailedGetStatus(t.getMessage());
                    }
                });
    }

    public void logout(){
        if(Session.getInstance().isOn()){
            accountView.onFailedLogout();
        }else {
            SaveUserData.getInstance().removePsikolog();
            Session.getInstance().setLogin(false);
            Qiscus.clearUser();
            SaveUserToken.getInstance().removeUserToken();
            accountView.gotoLogin();
        }
    }

}
