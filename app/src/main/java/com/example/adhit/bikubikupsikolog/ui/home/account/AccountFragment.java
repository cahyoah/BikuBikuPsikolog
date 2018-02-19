package com.example.adhit.bikubikupsikolog.ui.home.account;


import android.app.Fragment;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Switch;
import android.widget.TextView;

import com.example.adhit.bikubikupsikolog.R;
import com.example.adhit.bikubikupsikolog.data.model.Psikolog;
import com.example.adhit.bikubikupsikolog.presenter.AccountPresenter;
import com.example.adhit.bikubikupsikolog.service.NewTransactionTask;
import com.example.adhit.bikubikupsikolog.service.RoomChatService;
import com.example.adhit.bikubikupsikolog.ui.login.LoginActivity;
import com.example.adhit.bikubikupsikolog.util.ShowAlert;

/**
 * A simple {@link Fragment} subclass.
 */
public class AccountFragment extends Fragment implements AccountView, View.OnClickListener {

    private TextView tvName, tvEmail, tvEditProfil;
    private Switch swOnOff;
    private Button btnLogout;
    private AccountPresenter accountPresenter;


    public AccountFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view =  inflater.inflate(R.layout.fragment_account, container, false);
        tvName = view.findViewById(R.id.tv_name);
        tvEmail = view.findViewById(R.id.tv_email);
        tvEditProfil = view.findViewById(R.id.tv_edit_profil);
        btnLogout = view.findViewById(R.id.btn_logout);
        initView();
        return view;
    }

    public  void initView(){
        btnLogout.setOnClickListener(this);
        tvEditProfil.setOnClickListener(this);
       accountPresenter = new AccountPresenter(this);
       accountPresenter.showDataProfile();
    }

    @Override
    public void showDataProfile(Psikolog psikolog) {
        tvName.setText(psikolog.getNama());
        tvEmail.setText(psikolog.getEmail());
    }

    @Override
    public void gotoLogin() {
        Intent intent = new Intent(getActivity(), LoginActivity.class);
        startActivity(intent);
        getActivity().finish();
    }

    @Override
    public void onClick(View view) {
        if(view.getId() == R.id.btn_logout){
            getActivity().stopService(new Intent(getActivity(), RoomChatService.class));
            NewTransactionTask schedulerTask = new NewTransactionTask(getActivity());
            schedulerTask.cancelPeriodicTask();
            accountPresenter.logout();
        }
        if(view.getId() == R.id.tv_edit_profil){
            ShowAlert.showToast(getActivity(),"Coming Soon");
        }
    }
}
