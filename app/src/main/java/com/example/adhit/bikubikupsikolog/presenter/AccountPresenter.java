package com.example.adhit.bikubikupsikolog.presenter;

import com.example.adhit.bikubikupsikolog.data.local.SaveUserData;
import com.example.adhit.bikubikupsikolog.data.local.SaveUserToken;
import com.example.adhit.bikubikupsikolog.data.local.Session;
import com.example.adhit.bikubikupsikolog.data.model.Psikolog;
import com.example.adhit.bikubikupsikolog.ui.home.account.AccountView;
import com.qiscus.sdk.Qiscus;

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

    public void logout(){

        SaveUserData.getInstance().removePsikolog();
        Session.getInstance().setLogin(false);
        Qiscus.clearUser();
        SaveUserToken.getInstance().removeUserToken();
        accountView.gotoLogin();
    }

}
