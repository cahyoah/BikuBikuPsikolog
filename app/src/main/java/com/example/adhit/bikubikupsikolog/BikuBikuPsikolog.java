package com.example.adhit.bikubikupsikolog;

import android.app.Application;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.Parcelable;
import android.support.multidex.MultiDex;
import android.support.v4.app.NotificationCompat;
import android.support.v4.app.RemoteInput;
import android.util.Log;

import com.example.adhit.bikubikupsikolog.data.model.ChatRoom;
import com.example.adhit.bikubikupsikolog.data.model.ChatRoomHistory;
import com.example.adhit.bikubikupsikolog.data.model.Transaction;
import com.example.adhit.bikubikupsikolog.notification.NotificationBuilderInterceptor;
import com.example.adhit.bikubikupsikolog.presenter.HistoryPresenter;
import com.example.adhit.bikubikupsikolog.presenter.RoomChatPresenter;
import com.example.adhit.bikubikupsikolog.presenter.TransactionPresenter;
import com.example.adhit.bikubikupsikolog.receiver.RoomChatUpdateHistoryReceiver;
import com.example.adhit.bikubikupsikolog.receiver.RoomChatUpdateReceiver;
import com.example.adhit.bikubikupsikolog.service.NewTransactionTask;
import com.example.adhit.bikubikupsikolog.ui.chat.ChatActivity;
import com.example.adhit.bikubikupsikolog.ui.chat.ChatFragment;
import com.example.adhit.bikubikupsikolog.ui.home.history.HistoryView;
import com.example.adhit.bikubikupsikolog.ui.home.roomchat.RoomChatView;
import com.example.adhit.bikubikupsikolog.util.Constant;
import com.qiscus.sdk.Qiscus;
import com.qiscus.sdk.data.model.NotificationClickListener;
import com.qiscus.sdk.data.model.NotificationTitleHandler;
import com.qiscus.sdk.data.model.QiscusChatRoom;
import com.qiscus.sdk.data.model.QiscusComment;
import com.qiscus.sdk.data.remote.QiscusApi;
import com.qiscus.sdk.event.QiscusChatRoomEvent;
import com.qiscus.sdk.event.QiscusCommentReceivedEvent;
import com.qiscus.sdk.event.QiscusUserStatusEvent;
import com.qiscus.sdk.util.QiscusRxExecutor;
import com.qiscus.sdk.util.QiscusTextUtil;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import static com.qiscus.sdk.util.QiscusPushNotificationUtil.KEY_NOTIFICATION_REPLY;

/**
 * Created by adhit on 03/01/2018.
 */

public class BikuBikuPsikolog extends Application implements RoomChatView, HistoryView {
    private static Context sContext;
    private NewTransactionTask mSchedulerTask;



    public static Context getContext() {
        return sContext;
    }
    public static BikuBikuPsikolog instance;

    @Override
    protected void attachBaseContext(Context base) {
        super.attachBaseContext(base);

        MultiDex.install(this);
    }

    @Override
    public void onCreate() {
        super.onCreate();
        sContext = this;
        setupQiscus();
        if(!EventBus.getDefault().isRegistered(this)){
            EventBus.getDefault().register(this);

        }
        instance = this;
        mSchedulerTask = new NewTransactionTask(getApplicationContext());
        mSchedulerTask.createPeriodicTask();

    }

    @Override
    public Context getApplicationContext() {
        return super.getApplicationContext();
    }
    public static BikuBikuPsikolog getInstance(){
        return  instance;
    }

    private void setupQiscus() {
        Qiscus.init(this, Constant.APP_ID_QISCUS);
        Qiscus.getChatConfig()
                .setEnableFcmPushNotification(true)
                .setSwipeRefreshColorScheme(R.color.colorPrimary, R.color.colorAccent)
                .setNotificationBigIcon(R.drawable.logo)
                .setNotificationSmallIcon(R.drawable.logo)
                .setOnlyEnablePushNotificationOutsideChatRoom(false)
                .setNotificationTitleHandler(new NotificationTitleHandler() {
                    @Override
                    public String getTitle(QiscusComment qiscusComment) {
                        return qiscusComment.getSender();
                    }
                })
                .setNotificationClickListener(new NotificationClickListener() {
                    @Override
                    public void onClick(Context context, QiscusComment qiscusComment) {
                        System.out.println("jalan");
                        QiscusRxExecutor.execute(QiscusApi.getInstance().getChatRoom(qiscusComment.getRoomId()),
                                new QiscusRxExecutor.Listener<QiscusChatRoom>() {
                                    @Override
                                    public void onSuccess(QiscusChatRoom qiscusChatRoom) {

                                        Intent intent = ChatActivity.generateIntent(getApplicationContext(), qiscusChatRoom, false);
                                        startActivity(intent);
                                    }
                                    @Override
                                    public void onError(Throwable throwable) {
                                        throwable.printStackTrace();
                                    }
                                });
                    }
                })
                .setNotificationBuilderInterceptor(new NotificationBuilderInterceptor())
                .setLeftBubbleTextColor(R.color.qiscus_primary_text)
                .setLeftBubbleTimeColor(R.color.qiscus_secondary_text)
                .setLeftLinkTextColor(R.color.qiscus_primary_text)
                .setLeftProgressFinishedColor(R.color.colorPrimary)
                .setSelectedBubbleBackgroundColor(R.color.colorPrimary)
                .setReadIconColor(R.color.colorPrimary)
                .setAppBarColor(R.color.colorPrimary)
                .setStatusBarColor(R.color.colorPrimaryDark)
                .setAccentColor(R.color.colorAccent)
                .setAccountLinkingTextColor(R.color.colorPrimary)
                .setButtonBubbleTextColor(R.color.colorPrimary)
                .setReplyBarColor(R.color.colorPrimary)
                .setReplySenderColor(R.color.colorPrimary)
                .setSendButtonIcon(R.drawable.ic_qiscus_send_on)
                .setShowAttachmentPanelIcon(R.drawable.ic_qiscus_send_off)
                .setEnableAddFile(true)
                .setEnablePushNotification(true);
    }
    @Subscribe
    public void onReceivedComment(QiscusCommentReceivedEvent event) {
        JSONObject payload = null;
        HistoryPresenter historyPresenter = new HistoryPresenter(this);
        historyPresenter.getChattingHistoryList();
        RoomChatPresenter roomChatPresenter = new RoomChatPresenter(this);
        roomChatPresenter.getChattingRoomList();


    }

    @Subscribe
    public void onUserChanged(QiscusChatRoomEvent qiscusChatRoomEvent){
        if(qiscusChatRoomEvent.isTyping()){
            ChatFragment chatFragment = new ChatFragment();
            chatFragment.onUserTyping(qiscusChatRoomEvent.getUser(), true);
        }else{
            ChatFragment chatFragment = new ChatFragment();
            chatFragment.onUserTyping(qiscusChatRoomEvent.getUser(), false);
        }
    }

    @Subscribe
    public  void onUserTyping(QiscusUserStatusEvent qiscusUserStatusEvent){
        if(qiscusUserStatusEvent.isOnline()){
            ChatFragment.onUserChanged(true);
        }else {
            ChatFragment.onUserChanged(false);
        }
    }


    @Override
    public void onFailure(String s) {

    }

    @Override
    public void showData(List<ChatRoom> carList) {
        Intent intent = new Intent();
        intent.setAction(RoomChatUpdateReceiver.TAG);
        intent.putParcelableArrayListExtra("room_chat", (ArrayList<? extends Parcelable>) carList);
        sendBroadcast(intent);
    }

    @Override
    public void openRoomChat(QiscusChatRoom qiscusChatRoom) {

    }

    @Override
    public void onFailureOpenRoomChat(String string) {

    }

    @Override
    public void onFailureHistoryList(String s) {

    }

    @Override
    public void showDataHistoryList(List<ChatRoomHistory> chatRoomPsychologyHistoryList1) {
        Intent intent = new Intent();
        intent.setAction(RoomChatUpdateHistoryReceiver.TAG);
        intent.putParcelableArrayListExtra("room_chat_history", (ArrayList<? extends Parcelable>) chatRoomPsychologyHistoryList1);
        sendBroadcast(intent);
    }
}
