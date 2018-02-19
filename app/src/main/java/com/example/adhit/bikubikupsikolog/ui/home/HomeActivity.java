package com.example.adhit.bikubikupsikolog.ui.home;

import android.content.Intent;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;

import com.example.adhit.bikubikupsikolog.R;
import com.example.adhit.bikubikupsikolog.adapter.TabFragmentPagerAdapter;
import com.example.adhit.bikubikupsikolog.data.model.Fraggment;
import com.example.adhit.bikubikupsikolog.presenter.HomePresenter;
import com.example.adhit.bikubikupsikolog.service.NewTransactionTask;
import com.example.adhit.bikubikupsikolog.service.RoomChatService;

import java.util.ArrayList;

public class HomeActivity extends AppCompatActivity implements HomeView {
    private ViewPager pager;
    private TabLayout tabs;
    private TabFragmentPagerAdapter adapter;
    private Toolbar toolbar;
    private HomePresenter homePresenter;
    private NewTransactionTask mSchedulerTask;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        pager = (ViewPager)findViewById(R.id.pager);
        tabs = (TabLayout)findViewById(R.id.tabs);
        toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        setTitle("");
        mSchedulerTask = new NewTransactionTask(getApplicationContext());
        mSchedulerTask.createPeriodicTask();
        initView();
    }

    public void initView(){
        adapter = new TabFragmentPagerAdapter(this, getFragmentManager());
        pager.setAdapter(adapter);
        tabs.setTabTextColors(getResources().getColor(R.color.colorGrey300),
                getResources().getColor(R.color.colorWhite));
        tabs.setupWithViewPager(pager);
        tabs.setTabGravity(TabLayout.GRAVITY_FILL);
        homePresenter = new HomePresenter(this);
        homePresenter.showFragmentList();
    }


    @Override
    public void showData(ArrayList<Fraggment> fraggmentArrayList) {
        adapter.setData(fraggmentArrayList);
        tabs.getTabAt(0).setIcon(fraggmentArrayList.get(0).getImage());
        tabs.getTabAt(1).setIcon(fraggmentArrayList.get(1).getImage());
        tabs.getTabAt(2).setIcon(fraggmentArrayList.get(2).getImage());
        tabs.getTabAt(3).setIcon(fraggmentArrayList.get(3).getImage());
    }



}
