package com.example.adhit.bikubikupsikolog.presenter;

import com.example.adhit.bikubikupsikolog.data.local.SaveUserData;
import com.example.adhit.bikubikupsikolog.data.model.Psikolog;
import com.example.adhit.bikubikupsikolog.data.network.RetrofitClient;
import com.example.adhit.bikubikupsikolog.ui.editprofil.EditProfilView;
import com.google.gson.JsonObject;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class EditProfilPresenter {
    private EditProfilView editProfilView;

    public EditProfilPresenter(EditProfilView editProfileView){
        this.editProfilView = editProfileView;
    }

    public void getDataAccount(){
        editProfilView.getDataAccount(SaveUserData.getInstance().getPsikolog());
    }

    public void changeDataAccount(
            String name,
            String username,
            String email,
            String aim,
            String wa,
            String idLine,
            String bio){
        RetrofitClient.getInstance()
                .getApi()
                .changeDataAccount(name, username, email, "psikolog", wa, idLine, bio)
                .enqueue(new Callback<JsonObject>() {
                    @Override
                    public void onResponse(Call<JsonObject> call, Response<JsonObject> response) {
                        if(response.isSuccessful()){
                            JsonObject body = response.body();
                            boolean status = body.get("status").getAsBoolean();
                            if(status){
                                String message = body.get("message").getAsString();

                                Psikolog user = SaveUserData.getInstance().getPsikolog();
                                user.setNama(name);
                                user.setUsername(username);
                                user.setEmail(email);
                                user.setWa(wa);
                                user.setIdLine(idLine);
                                user.setBio(bio);
                                SaveUserData.getInstance().savePsikolog(user);
                                editProfilView.onSuccessChangeDataAccount(message);
                            }else{
                                String message = body.get("message").getAsString();
                                editProfilView.onFailedChangeDataAccount(message);
                            }
                        }else {
                            editProfilView.onFailedChangeDataAccount("Perubahan Gagal");
                        }
                    }

                    @Override
                    public void onFailure(Call<JsonObject> call, Throwable t) {
                        editProfilView.onFailedChangeDataAccount(t.toString());
                    }
                });
    }
}
