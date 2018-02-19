package com.example.adhit.bikubikupsikolog.presenter;

import com.example.adhit.bikubikupsikolog.R;
import com.example.adhit.bikubikupsikolog.data.model.Fraggment;
import com.example.adhit.bikubikupsikolog.ui.home.HomeView;
import com.example.adhit.bikubikupsikolog.ui.home.account.AccountFragment;
import com.example.adhit.bikubikupsikolog.ui.home.roomchat.RoomChatFragment;
import com.example.adhit.bikubikupsikolog.ui.home.history.HistoryFragment;
import com.example.adhit.bikubikupsikolog.ui.home.transaction.TransactionFragment;

import java.util.ArrayList;

/**
 * Created by adhit on 05/01/2018.
 */

public class HomePresenter {
    private HomeView homeView;
    public HomePresenter(HomeView homeView){
        this.homeView = homeView;
    }

    public void showFragmentList(){
        ArrayList<Fraggment> fraggmentArrayList = new ArrayList<>();
        fraggmentArrayList.add(new Fraggment(new TransactionFragment(), "Baru", R.drawable.ic_fiber_new_white_24dp ));
        fraggmentArrayList.add(new Fraggment(new RoomChatFragment(), "Chat", R.drawable.ic_chat_bubble_white_24dp));
        fraggmentArrayList.add(new Fraggment(new HistoryFragment(), "History", R.drawable.ic_history_white_24dp));
        fraggmentArrayList.add(new Fraggment(new AccountFragment(), "Akun", R.drawable.ic_person_white_24dp));
        homeView.showData(fraggmentArrayList);
    }
}
