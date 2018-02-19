package com.example.adhit.bikubikupsikolog.ui.chat;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.TextView;

import com.example.adhit.bikubikupsikolog.R;
import com.example.adhit.bikubikupsikolog.data.local.SaveUserData;
import com.qiscus.sdk.data.model.QiscusChatRoom;
import com.qiscus.sdk.ui.QiscusBaseChatActivity;
import com.qiscus.sdk.ui.fragment.QiscusBaseChatFragment;
import com.qiscus.sdk.ui.view.QiscusCircularImageView;
import java.util.Date;

public class ChatActivity extends QiscusBaseChatActivity {

    public static TextView mTitle, mSubtitle;
    private QiscusCircularImageView qiscusCircularImageView;
    private static boolean isHistory1;

    public static Intent generateIntent(Context context, QiscusChatRoom qiscusChatRoom, boolean isHistory) {
        Intent intent = new Intent(context, ChatActivity.class);
        intent.putExtra(CHAT_ROOM_DATA, qiscusChatRoom);
        isHistory1 = isHistory;
        return intent;
    }

    @Override
    protected int getResourceLayout() {
        return R.layout.activity_chat;
    }

    @Override
    protected void onLoadView() {
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        mTitle = (TextView) findViewById(R.id.tv_title);
        mSubtitle = findViewById(R.id.tv_subtitle);
        mSubtitle.setVisibility(View.VISIBLE);
        qiscusCircularImageView = findViewById(R.id.profile_picture);
        findViewById(com.qiscus.sdk.R.id.back).setOnClickListener(v -> onBackPressed());

    }

    @Override
    protected QiscusBaseChatFragment onCreateChatFragment() {
        return ChatFragment.newInstance(qiscusChatRoom, isHistory1);
    }

    @Override
    protected void onViewReady(Bundle savedInstanceState) {
        super.onViewReady(savedInstanceState);
        for(int i=0; i<qiscusChatRoom.getMember().size();i++){
            if(!qiscusChatRoom.getMember().get(i).getEmail().equals(SaveUserData.getInstance().getPsikolog().getId())){
                mTitle.setText(qiscusChatRoom.getMember().get(i).getUsername());
            }
        }

        //qiscusCircularImageView.setImageUrl(qiscusChatRoom.getAvatarUrl());
    }

    @Override
    public void onUserStatusChanged(String user, boolean online, Date lastActive) {
        if(online){
            mSubtitle.setText(getResources().getString(R.string.text_online));
        }else{
            mSubtitle.setText("");
        }
    }

    @Override
    protected void onPause() {
        super.onPause();
        finish();
    }

    @Override
    public void onUserTyping(String user, boolean typing) {
        if(!typing){
            mSubtitle.setText(getResources().getString(R.string.text_typing));
        }
    }

    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
    }
}
