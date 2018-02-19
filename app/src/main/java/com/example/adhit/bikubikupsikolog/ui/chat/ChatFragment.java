package com.example.adhit.bikubikupsikolog.ui.chat;


import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.support.v4.widget.SwipeRefreshLayout;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.adhit.bikubikupsikolog.R;
import com.example.adhit.bikubikupsikolog.adapter.ChatAdapter;
import com.qiscus.sdk.Qiscus;
import com.qiscus.sdk.data.model.QiscusChatRoom;
import com.qiscus.sdk.data.model.QiscusComment;
import com.qiscus.sdk.ui.adapter.QiscusChatAdapter;
import com.qiscus.sdk.ui.fragment.QiscusBaseChatFragment;
import com.qiscus.sdk.ui.view.QiscusAudioRecorderView;
import com.qiscus.sdk.ui.view.QiscusMentionSuggestionView;
import com.qiscus.sdk.ui.view.QiscusRecyclerView;
import com.qiscus.sdk.ui.view.QiscusReplyPreviewView;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 */
public class ChatFragment extends QiscusBaseChatFragment<ChatAdapter> {


    private ImageView mAttachButton;
    private LinearLayout mAddPanel;
    private View mInputPanel;
    public static boolean isHistory1;
    private static TextView tvSubtitle;


    public static ChatFragment newInstance(QiscusChatRoom qiscusChatRoom, boolean isHistory) {
        ChatFragment fragment = new ChatFragment();
        Bundle bundle = new Bundle();
        bundle.putParcelable(CHAT_ROOM_DATA, qiscusChatRoom);
        fragment.setArguments(bundle);
        isHistory1 = isHistory;
        return fragment;
    }

    @Override
    protected int getResourceLayout() {
        return R.layout.fragment_chat;
    }

    @Override
    protected void onLoadView(View view) {
        super.onLoadView(view);
        swipeRefreshLayout.setProgressViewOffset(false, 0, 128);
        mInputPanel = view.findViewById(R.id.input_panel1);
        View clickConsumer = view.findViewById(R.id.click_consumer);
        mAttachButton = (ImageView) view.findViewById(R.id.button_attach);
        mAddPanel = (LinearLayout) view.findViewById(R.id.add_panel);
        tvSubtitle = getActivity().findViewById(R.id.tv_subtitle);
        mAttachButton.setOnClickListener(v -> {
            if (mAddPanel.getVisibility() == View.GONE) {
                mAddPanel.startAnimation(animation);
                mAddPanel.setVisibility(View.VISIBLE);
                clickConsumer.setVisibility(View.VISIBLE);
            } else {
                mAddPanel.setVisibility(View.GONE);
                clickConsumer.setVisibility(View.GONE);
            }
        });

        clickConsumer.setOnClickListener(v -> {
            if (mAddPanel.getVisibility() == View.VISIBLE) {
                mAddPanel.setVisibility(View.GONE);
                clickConsumer.setVisibility(View.GONE);
            }
        });

    }

    @Override
    protected void onCreateChatComponents(Bundle savedInstanceState) {
        super.onCreateChatComponents(savedInstanceState);
        if(isHistory1){
            mInputPanel.setVisibility(View.GONE);

        }
    }

    @Override
    protected void onApplyChatConfig() {
        super.onApplyChatConfig();
        if (addImageButton != null) {
            addImageButton.setBackground(ContextCompat.getDrawable(Qiscus.getApps(),
                    R.drawable.bt_qiscus_selector_grey));
        }
        if (takeImageButton != null) {
            takeImageButton.setBackground(ContextCompat.getDrawable(Qiscus.getApps(),
                    R.drawable.bt_qiscus_selector_grey));
        }
        if (addFileButton != null) {
            addFileButton.setBackground(ContextCompat.getDrawable(Qiscus.getApps(),
                    R.drawable.bt_qiscus_selector_grey));
        }
        if (recordAudioButton != null) {
            recordAudioButton.setBackground(ContextCompat.getDrawable(Qiscus.getApps(),
                    R.drawable.bt_qiscus_selector_grey));
        }
    }

    @NonNull
    @Override
    protected ViewGroup getRootView(View view) {
        return (ViewGroup) view.findViewById(R.id.root_view);
    }

    @Nullable
    @Override
    protected ViewGroup getEmptyChatHolder(View view) {
        return (ViewGroup) view.findViewById(R.id.empty_chat);
    }

    @NonNull
    @Override
    protected SwipeRefreshLayout getSwipeRefreshLayout(View view) {
        return (SwipeRefreshLayout) view.findViewById(R.id.swipe_layout);
    }

    @NonNull
    @Override
    protected QiscusRecyclerView getMessageRecyclerView(View view) {
        return (QiscusRecyclerView) view.findViewById(R.id.list_message);
    }

    @Nullable
    @Override
    protected ViewGroup getMessageInputPanel(View view) {
        return (ViewGroup) view.findViewById(R.id.input_panel);
    }

    @Nullable
    @Override
    protected ViewGroup getMessageEditTextContainer(View view) {
        return null;
    }

    @NonNull
    @Override
    protected EditText getMessageEditText(View view) {
        EditText editText = view.findViewById(R.id.field_message);
        return editText;
    }

    @NonNull
    @Override
    protected ImageView getSendButton(View view) {
        return (ImageView) view.findViewById(R.id.button_send);
    }

    @NonNull
    @Override
    protected QiscusMentionSuggestionView getMentionSuggestionView(View view) {
        return null;
    }

    @Nullable
    @Override
    protected View getNewMessageButton(View view) {
        return view.findViewById(R.id.button_new_message);
    }

    @NonNull
    @Override
    protected View getLoadMoreProgressBar(View view) {
        return view.findViewById(R.id.progressBar);
    }

    @Nullable
    @Override
    protected ImageView getEmptyChatImageView(View view) {
        return (ImageView) view.findViewById(R.id.empty_chat_icon);
    }

    @Nullable
    @Override
    protected TextView getEmptyChatTitleView(View view) {
        return (TextView) view.findViewById(R.id.empty_chat_title);
    }

    @Nullable
    @Override
    protected TextView getEmptyChatDescView(View view) {
        return (TextView) view.findViewById(R.id.empty_chat_desc);
    }

    @Nullable
    @Override
    protected ViewGroup getAttachmentPanel(View view) {
        return null;
    }

    @Nullable
    @Override
    protected View getAddImageLayout(View view) {
        return null;
    }

    @Nullable
    @Override
    protected ImageView getAddImageButton(View view) {
        return (ImageView) view.findViewById(R.id.button_add_image);
    }

    @Nullable
    @Override
    protected View getTakeImageLayout(View view) {
        return null;
    }

    @Nullable
    @Override
    protected ImageView getTakeImageButton(View view) {
        return (ImageView) view.findViewById(R.id.button_pick_picture);
    }

    @Nullable
    @Override
    protected View getAddFileLayout(View view) {
        return null;
    }

    @Nullable
    @Override
    protected ImageView getAddFileButton(View view) {
        return (ImageView) view.findViewById(R.id.button_add_file);
    }

    @Nullable
    @Override
    protected View getRecordAudioLayout(View view) {
        return null;
    }

    @Nullable
    @Override
    protected ImageView getRecordAudioButton(View view) {
        return (ImageView) view.findViewById(R.id.button_add_audio);
    }

    @Nullable
    @Override
    protected View getAddContactLayout(View view) {
        return null;
    }

    @Nullable
    @Override
    protected ImageView getAddContactButton(View view) {
        return null;
    }

    @Nullable
    @Override
    protected View getAddLocationLayout(View view) {
        return null;
    }

    @Nullable
    @Override
    protected ImageView getAddLocationButton(View view) {
        return null;
    }

    @Nullable
    @Override
    public ImageView getHideAttachmentButton(View view) {
        return null;
    }

    @Nullable
    @Override
    protected ImageView getToggleEmojiButton(View view) {
        return null;
    }

    @Nullable
    @Override
    protected QiscusAudioRecorderView getRecordAudioPanel(View view) {
        return (QiscusAudioRecorderView) view.findViewById(R.id.record_panel);
    }

    @Nullable
    @Override
    protected QiscusReplyPreviewView getReplyPreviewView(View view) {
        return null;
    }

    @Nullable
    @Override
    protected View getGotoBottomButton(View view) {
        return null;
    }

    @Override
    protected ChatAdapter onCreateChatAdapter() {
        //return new QiscusChatAdapter(getActivity(), qiscusChatRoom.isGroup());
        return new ChatAdapter(getActivity(), qiscusChatRoom.isGroup());
    }

        @Override
        public void onUserTyping(String user, boolean typing) {
            if(typing){
                ChatActivity.mSubtitle.setText("Typing....");
            }else{
                ChatActivity.mSubtitle.setText("");
            }
        }

        public static void onUserChanged(boolean online){
            if(online){
                ChatActivity.mSubtitle.setText("Online");
            }else{
                ChatActivity.mSubtitle
                        .setText("");
            }
        }

    protected void recordAudio() {
        super.recordAudio();
        mAddPanel.setVisibility(View.GONE);
    }

    @Override
    public void showComments(List<QiscusComment> qiscusComments) {
        super.showComments(qiscusComments);
        for(int i=0; i<qiscusComments.size();i++){
            JSONObject payload = null;

            try {
                payload = new JSONObject(qiscusComments.get(i).getExtraPayload());
                if(payload != null){
                    System.out.println(payload.getString("type"));
                    if (payload.getString("type").equals("closed_chat")) {
                        mInputPanel.setVisibility(View.GONE);

                    }
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }

    }

    @Override
    public void onNewComment(QiscusComment qiscusComment) {
        super.onNewComment(qiscusComment);
        JSONObject payload = null;
        try {
            payload = new JSONObject(qiscusComment.getExtraPayload());

            if (payload.optString("type").equals("closed_chat")) {
                mInputPanel.setVisibility(View.GONE);
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }
}
