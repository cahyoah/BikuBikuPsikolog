package com.example.adhit.bikubikupsikolog.ui.chat;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.adhit.bikubikupsikolog.R;
import com.example.adhit.bikubikupsikolog.presenter.ChatPresenter;
import com.qiscus.sdk.data.model.QiscusComment;
import com.qiscus.sdk.ui.adapter.OnItemClickListener;
import com.qiscus.sdk.ui.adapter.OnLongItemClickListener;
import com.qiscus.sdk.ui.adapter.viewholder.QiscusBaseMessageViewHolder;

import org.json.JSONObject;

/**
 * Created by adhit on 07/01/2018.
 */

public class UserTestViewHolder extends QiscusBaseMessageViewHolder<QiscusComment> implements ChatView {

    private TextView tvUser, tvUserResult;
    private ChatPresenter chatPresenter;

    public UserTestViewHolder(View itemView, OnItemClickListener itemClickListener, OnLongItemClickListener longItemClickListener) {
        super(itemView, itemClickListener, longItemClickListener);
        tvUser = itemView.findViewById(R.id.tv_name_user_test);
        tvUserResult = itemView.findViewById(R.id.tv_test_result);

    }

    @Nullable
    @Override
    protected ImageView getFirstMessageBubbleIndicatorView(View itemView) {
        return null;
    }

    @NonNull
    @Override
    protected View getMessageBubbleView(View itemView) {
        return itemView.findViewById(R.id.cv_user_test);
    }

    @Nullable
    @Override
    protected TextView getDateView(View itemView) {
        return null;
    }

    @Nullable
    @Override
    protected TextView getTimeView(View itemView) {
        return null;
    }

    @Nullable
    @Override
    protected ImageView getMessageStateIndicatorView(View itemView) {
        return null;
    }

    @Nullable
    @Override
    protected ImageView getAvatarView(View itemView) {
        return null;
    }

    @Nullable
    @Override
    protected TextView getSenderNameView(View itemView) {
        return itemView.findViewById(R.id.tv_name_user_test);
    }

    @Override
    protected void showMessage(QiscusComment qiscusComment) {
       chatPresenter = new ChatPresenter(this);
       chatPresenter.showMessage(qiscusComment);
    }

    @Override
    public void showMessage(String s, String s1) {
        tvUser.setText(s);
        tvUserResult.setText(s1);
    }

    @Override
    public void showError(String message) {
        tvUserResult.setText(message);
    }

    @Override
    public void showMessage(String sukses) {

    }
}
