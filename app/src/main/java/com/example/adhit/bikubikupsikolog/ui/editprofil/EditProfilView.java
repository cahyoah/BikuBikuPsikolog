package com.example.adhit.bikubikupsikolog.ui.editprofil;

import com.example.adhit.bikubikupsikolog.data.model.Psikolog;

public interface EditProfilView {
    void getDataAccount(Psikolog psikolog);

    void onSuccessChangeDataAccount(String message);

    void onFailedChangeDataAccount(String message);
}
