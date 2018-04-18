package com.example.adhit.bikubikupsikolog.ui.editprofil;

import android.support.design.widget.CoordinatorLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;

import com.example.adhit.bikubikupsikolog.R;
import com.example.adhit.bikubikupsikolog.data.model.Psikolog;
import com.example.adhit.bikubikupsikolog.presenter.EditProfilPresenter;
import com.example.adhit.bikubikupsikolog.util.ShowAlert;

import java.util.ArrayList;

public class EditProfilActivity extends AppCompatActivity implements View.OnClickListener, EditProfilView {
    private EditText etName, etUsername, etEmail, etWa, etIdLine, etBio;
    private Spinner spnAim;
    private EditProfilPresenter editProfilPresenter;
    private Button btnSave;
    private CoordinatorLayout clAccountSettings;
    private Toolbar toolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_profil);
        etName = findViewById(R.id.et_name);
        etUsername = findViewById(R.id.et_username);
        etEmail = findViewById(R.id.et_email);
        etWa = findViewById(R.id.et_wa);
        etIdLine = findViewById(R.id.et_id_line);
        etBio = findViewById(R.id.et_bio);
        btnSave = findViewById(R.id.btn_save);
        btnSave.setOnClickListener(this);
        clAccountSettings = findViewById(R.id.cl_account_settings);
        toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        setTitle("Edit Profil");
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        initPresenter();
    }

    private void initPresenter() {
        editProfilPresenter = new EditProfilPresenter(this);
        editProfilPresenter.getDataAccount();
    }
    @Override
    public void getDataAccount(Psikolog user) {
        etName.setText(user.getNama());
        etUsername.setText(user.getUsername());
        etEmail.setText(user.getEmail());
        etWa.setText(user.getWa());
        etIdLine.setText(user.getIdLine());
        etBio.setText(user.getBio());
    }

    @Override
    public void onFailedChangeDataAccount(String s) {
        ShowAlert.closeProgresDialog();
        ShowAlert.showSnackBar(clAccountSettings, s);
    }

    @Override
    public void onSuccessChangeDataAccount(String message) {
        ShowAlert.closeProgresDialog();
        ShowAlert.showSnackBar(clAccountSettings, message);
        editProfilPresenter.getDataAccount();
    }

    @Override
    public void onClick(View view) {
        if(view.getId() == R.id.btn_save){
            String name = etName.getText().toString().trim();
            String userName =etUsername.getText().toString().trim();
            String email = etEmail.getText().toString().trim();
            String wa = etWa.getText().toString().trim();
            String idLine = etIdLine.getText().toString().trim();
            String bio = etBio.getText().toString().trim();
            if(name.isEmpty()){
                etName.setError(getResources().getString(R.string.text_cannot_empty));
                etName.requestFocus();
            }else  if(userName.isEmpty()){
                etUsername.setError(getResources().getString(R.string.text_cannot_empty));
                etUsername.requestFocus();
            }else if(email.isEmpty()){
                etEmail.setError(getResources().getString(R.string.text_cannot_empty));
                etEmail.requestFocus();
            }else if(wa.isEmpty()){
                etWa.setError(getResources().getString(R.string.text_cannot_empty));
                etWa.requestFocus();
            }else if(idLine.isEmpty()){
                etIdLine.setError(getResources().getString(R.string.text_cannot_empty));
                etIdLine.requestFocus();
            }else if(bio.isEmpty()){
                etBio.setError(getResources().getString(R.string.text_cannot_empty));
                etBio.requestFocus();
            }else {
                if(ShowAlert.dialog != null && ShowAlert.dialog.isShowing()){
                    ShowAlert.closeProgresDialog();
                }
                ShowAlert.showProgresDialog(EditProfilActivity.this);
                editProfilPresenter.changeDataAccount(name, userName, email, "psikolog", wa, idLine, bio);
            }
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle item selection
        switch (item.getItemId()) {
            case android.R.id.home:
                super.onBackPressed();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }
}
