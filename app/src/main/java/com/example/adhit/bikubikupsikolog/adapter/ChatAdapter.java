package com.example.adhit.bikubikupsikolog.adapter;

import android.content.Context;
import android.view.ViewGroup;

import com.example.adhit.bikubikupsikolog.R;
import com.example.adhit.bikubikupsikolog.ui.chat.CloseChatViewHolder;
import com.example.adhit.bikubikupsikolog.ui.chat.UserTestViewHolder;
import com.qiscus.sdk.data.model.QiscusComment;
import com.qiscus.sdk.ui.adapter.QiscusChatAdapter;
import com.qiscus.sdk.ui.adapter.viewholder.QiscusBaseMessageViewHolder;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by adhit on 07/01/2018.
 */

public class ChatAdapter extends QiscusChatAdapter {

    private static final int TYPE_USER_TEST = 2304;
    private static final int CLOSE_CHAT_PSYCHOLOGY = 2305;


    public ChatAdapter(Context context, boolean groupChat) {
        super(context, groupChat);
    }

    @Override
    protected int getItemViewTypeCustomMessage(QiscusComment qiscusComment, int position) {
        try {
            JSONObject payload = new JSONObject(qiscusComment.getExtraPayload());
            if (payload.optString("type").equals("user_test")) {
                return TYPE_USER_TEST;
            }
            if (payload.optString("type").equals("closed_chat")) {
                return CLOSE_CHAT_PSYCHOLOGY;
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return super.getItemViewTypeCustomMessage(qiscusComment, position);
    }

    @Override
    protected int getItemResourceLayout(int viewType) {
        switch (viewType) {
            case TYPE_USER_TEST:
                return R.layout.item_card_user_test;
            case CLOSE_CHAT_PSYCHOLOGY:
                return R.layout.item_close_chat;
            default:
                return super.getItemResourceLayout(viewType);
        }
    }

    @Override
    public QiscusBaseMessageViewHolder<QiscusComment> onCreateViewHolder(ViewGroup parent, int viewType) {
        switch (viewType) {
            case TYPE_USER_TEST:
                return new UserTestViewHolder(getView(parent, viewType), itemClickListener, longItemClickListener);
            case CLOSE_CHAT_PSYCHOLOGY:
                return new CloseChatViewHolder(getView(parent, viewType), itemClickListener, longItemClickListener);
            default:
                return super.onCreateViewHolder(parent, viewType);
        }
    }
}
