package com.example.adhit.bikubikupsikolog.data.local;


import com.example.adhit.bikubikupsikolog.data.model.Psikolog;
import com.example.adhit.bikubikupsikolog.util.Constant;
import com.example.adhit.bikubikupsikolog.util.SharedPrefUtil;

/**
 * Created by adhit on 04/01/2018.
 */

public class SaveUserData {
    private static SaveUserData ourInstance;

    private SaveUserData() {
    }

    public static SaveUserData getInstance() {
        if (ourInstance == null) ourInstance = new SaveUserData();
        return ourInstance;
    }

    public Psikolog getPsikolog() {
        return (Psikolog) SharedPrefUtil.getObject(Constant.KEY_PSIKOLOG, Psikolog.class);
    }

    public void savePsikolog(Psikolog psikolog) {
        SharedPrefUtil.saveObject(Constant.KEY_PSIKOLOG, psikolog);
    }

    public void removePsikolog(){
        SharedPrefUtil.remove(Constant.KEY_PSIKOLOG);
    }
}
