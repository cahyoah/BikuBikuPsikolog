package com.example.adhit.bikubikupsikolog.presenter;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

import com.example.adhit.bikubikupsikolog.R;
import com.example.adhit.bikubikupsikolog.data.local.SaveUserData;
import com.example.adhit.bikubikupsikolog.data.local.SaveUserToken;
import com.example.adhit.bikubikupsikolog.data.local.Session;
import com.example.adhit.bikubikupsikolog.data.model.Psikolog;
import com.example.adhit.bikubikupsikolog.data.network.RetrofitClient;
import com.example.adhit.bikubikupsikolog.ui.login.LoginView;
import com.example.adhit.bikubikupsikolog.util.Constant;
import com.example.adhit.bikubikupsikolog.util.ShowAlert;
import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.google.gson.reflect.TypeToken;
import com.qiscus.sdk.Qiscus;


import java.io.IOException;
import java.lang.reflect.Type;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.HttpException;
import retrofit2.Response;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

import static android.content.ContentValues.TAG;

/**
 * Created by adhit on 05/01/2018.
 */

public class LoginPresenter {

    private LoginView loginView;
    public LoginPresenter(LoginView loginView){
        this.loginView = loginView;
    }

    public void checkLogin() {
        if (Session.getInstance().isLogin()) loginView.gotoHome();
    }


    public void Login(final Context context, final String username, String password){
        ShowAlert.showProgresDialog(context);
        RetrofitClient.getInstance()
                .getApi()
                .login(username, password)
                .enqueue(new Callback<JsonObject>() {
                    @Override
                    public void onResponse(Call<JsonObject> call, Response<JsonObject> response) {
                        if(response.isSuccessful()){
                            JsonObject body = response.body();
                            boolean status = body.get("status").getAsBoolean();
                            if(status){
                                JsonObject psikologObject = body.get("result").getAsJsonObject();
                                String psikologStatus = psikologObject.get("status_psikolog").getAsString();
                                if(psikologStatus.equals("1")){
                                    Type type = new TypeToken<Psikolog>(){}.getType();
                                    Psikolog psikolog = new Gson().fromJson(psikologObject, type);
                                    loginSDK(psikolog);
                                }else{
                                    loginView.showMessageSnackbar("Anda tidak terdaftar sebagai psikolog");
                                }
                            }else{
                                loginView.showMessageSnackbar("Username atau password salah");
                            }
                        }else {
                            loginView.showMessageSnackbar(context.getResources().getString(R.string.text_login_failed));
                        }

                    }
                    @Override
                    public void onFailure(Call<JsonObject> call, Throwable t) {
                        loginView.showMessageSnackbar(context.getResources().getString(R.string.text_login_failed));
                        ShowAlert.closeProgresDialog();
                    }
                });
    }

    public void loginSDK(Psikolog psikolog){
        Qiscus.setUser(psikolog.getId()+"p", psikolog.getToken())
                .withUsername(psikolog.getNama())
                .save()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(qiscusAccount -> {
                    ShowAlert.closeProgresDialog();
                    SaveUserToken.getInstance().saveUserToken(psikolog.getId(), psikolog.getToken());
                    SaveUserData.getInstance().savePsikolog(psikolog);
                    Session.getInstance().setLogin(true);
                    loginView.showMessage("Selamat Datang " + psikolog.getNama());
                    loginView.gotoHome();

                }, throwable -> {
                    ShowAlert.closeProgresDialog();
                    if (throwable instanceof HttpException) { //Error response from server
                        HttpException e = (HttpException) throwable;
                        try {
                            String errorMessage = e.response().errorBody().string();
                            Log.e(TAG, errorMessage);
                            loginView.showMessageSnackbar(errorMessage);
                        } catch (IOException e1) {
                            loginView.showMessageSnackbar("Unexpected error!");
                            e1.printStackTrace();
                        }
                    } else if (throwable instanceof IOException) { //Error from network
                        loginView.showMessageSnackbar("Can not connect to qiscus server!");
                    } else { //Unknown error
                        loginView.showMessageSnackbar("Unexpected error!");
                    }

                });
        ShowAlert.closeProgresDialog();
    }


}
