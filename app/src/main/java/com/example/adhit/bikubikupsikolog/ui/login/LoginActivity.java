package com.example.adhit.bikubikupsikolog.ui.login;

import android.content.Intent;
import android.support.design.widget.CoordinatorLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.example.adhit.bikubikupsikolog.R;
import com.example.adhit.bikubikupsikolog.presenter.LoginPresenter;
import com.example.adhit.bikubikupsikolog.ui.home.HomeActivity;
import com.example.adhit.bikubikupsikolog.util.ShowAlert;

public class LoginActivity extends AppCompatActivity implements View.OnClickListener, LoginView {

    private EditText etUsername, etPassword;
    private CoordinatorLayout coordinatorLayout;
    private TextView tvForgotPassword;
    private Button btnLogin;
    private LoginPresenter loginPresenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        coordinatorLayout = findViewById(R.id.coordinator);
        etUsername = findViewById(R.id.et_username);
        etPassword = findViewById(R.id.et_password);
        tvForgotPassword = findViewById(R.id.tv_forgot_password);
        btnLogin = findViewById(R.id.btn_login);
        btnLogin.setOnClickListener(this);
        tvForgotPassword.setOnClickListener(this);
        initView();
    }

    @Override
    public void onClick(View view) {
        if(view.getId() == R.id.btn_login){
            String username = etUsername.getText().toString().trim();
            String password = etPassword.getText().toString().trim();
            if (username.isEmpty()){
                etUsername.setError(getResources().getString(R.string.text_username_empty));
                etUsername.requestFocus();
            }else if(password.isEmpty()){
                etPassword.setError(getResources().getString(R.string.text_password_empty));
                etPassword.requestFocus();
            }else{
                loginPresenter.Login(this, username, password);
            }
        }
        if(view.getId() == R.id.tv_forgot_password){
            ShowAlert.showSnackBar(coordinatorLayout, "Coming Soon");
        }
    }

    public  void initView(){
        loginPresenter = new LoginPresenter(this);
        loginPresenter.checkLogin();
    }

    @Override
    public void gotoHome() {
        Intent intent = new Intent(this, HomeActivity.class);
        startActivity(intent);
        finish();
    }

    @Override
    public void showMessageSnackbar(String message) {
        ShowAlert.showSnackBar(coordinatorLayout, message);
    }

    @Override
    public void showMessage(String s) {
        ShowAlert.showToast(this, s);
    }
}
