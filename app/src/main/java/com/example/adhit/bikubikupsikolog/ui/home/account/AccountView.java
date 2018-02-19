package com.example.adhit.bikubikupsikolog.ui.home.account;

import com.example.adhit.bikubikupsikolog.data.model.Psikolog;

/**
 * Created by adhit on 08/01/2018.
 */

public interface AccountView {

    void showDataProfile(Psikolog psikolog);

    void gotoLogin();
}
