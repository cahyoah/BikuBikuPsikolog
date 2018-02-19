package com.example.adhit.bikubikupsikolog.data.local;


import com.example.adhit.bikubikupsikolog.util.Constant;
import com.example.adhit.bikubikupsikolog.util.SharedPrefUtil;

/**
 * Created by adhit on 03/01/2018.
 */

public class Session {

    private static Session ourInstance;

    private Session() {
    }

    public static Session getInstance() {
        if (ourInstance == null) ourInstance = new Session();
        return ourInstance;
    }

    public boolean isLogin() {
        return SharedPrefUtil.getBoolean(Constant.IS_LOGIN);
    }

    public void setLogin(boolean isLogin) {
        SharedPrefUtil.saveBoolean(Constant.IS_LOGIN, isLogin);
    }

}
