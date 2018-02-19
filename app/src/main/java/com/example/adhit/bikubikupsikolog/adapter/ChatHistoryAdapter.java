package com.example.adhit.bikubikupsikolog.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.adhit.bikubikupsikolog.R;
import com.example.adhit.bikubikupsikolog.data.local.SaveUserData;
import com.example.adhit.bikubikupsikolog.data.model.ChatRoomHistory;
import com.qiscus.sdk.data.model.QiscusChatRoom;
import com.squareup.picasso.Picasso;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

/**
 * Created by adhit on 11/01/2018.
 */

public class ChatHistoryAdapter extends  RecyclerView.Adapter<ChatHistoryAdapter.RoomChatHistoryViewHolder>{

    private Context context;
    private List<ChatRoomHistory> RoomChatList;
    private OnDetailListener onDetailListener;

    public ChatHistoryAdapter(Context context) {
        this.context = context;
    }

    public ChatHistoryAdapter() {

    }
    public void setData(List<ChatRoomHistory> items){
        RoomChatList = items;
        notifyDataSetChanged();
    }

    @Override
    public RoomChatHistoryViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_card_chat_history, parent, false);
        return new RoomChatHistoryViewHolder(view);
    }

    @Override
    public void onBindViewHolder(RoomChatHistoryViewHolder holder, final int position) {
        holder.tvRoomChatName.setText(RoomChatList.get(position).getNamaBiquers());
        holder.tvDate.setText(RoomChatList.get(position).getCreateDate());
        holder.tvLastMessage.setText("CLOSED");
        Picasso.with(context)
                .load(R.drawable.avatar1);
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if(RoomChatList.get(position).getIdRoom()== null){
                    onDetailListener.showMessage();
                }else {
                    onDetailListener.onItemDetailClicked((Integer.parseInt( RoomChatList.get(position).getIdRoom())));
                }
            }
        });

    }

    public void setOnClickDetailListener(OnDetailListener onDetailListener){
        this.onDetailListener = onDetailListener;
    }

    @Override
    public boolean onFailedToRecycleView(RoomChatHistoryViewHolder holder) {
        return super.onFailedToRecycleView(holder);
    }

    @Override
    public int getItemCount() {
        if(RoomChatList == null) return 0;
        return RoomChatList.size();
    }

    public class RoomChatHistoryViewHolder extends RecyclerView.ViewHolder{
        TextView tvRoomChatName, tvLastMessage, tvDate;
        ImageView imgAvatarRoomChat;


        public RoomChatHistoryViewHolder(final View itemView) {
            super(itemView);
            tvRoomChatName = itemView.findViewById(R.id.tv_room_chat_name);
            tvLastMessage = itemView.findViewById(R.id.tv_last_message);
            tvDate = itemView.findViewById(R.id.tv_date);
            imgAvatarRoomChat = itemView.findViewById(R.id.img_avatar_room_chat);

        }
    }

    public interface OnDetailListener{
        void onItemDetailClicked(int idRoom);

        void showMessage();
    }

    public  String unixTimestamptoDate(int unixTimestamp){
        Date date = new Date(unixTimestamp * 1000L);
        SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy HH:mm");
        String formattedDate = sdf.format(date);
        return formattedDate;
    }

    public  String dateToString(Date date1){
        Date date = new Date(String.valueOf(date1));
        SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy HH:mm");
        String formattedDate = sdf.format(date);
        return formattedDate;
    }
}
