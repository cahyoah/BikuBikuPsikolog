package com.example.adhit.bikubikupsikolog.adapter;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.adhit.bikubikupsikolog.R;
import com.example.adhit.bikubikupsikolog.data.local.SaveUserData;
import com.example.adhit.bikubikupsikolog.data.model.ChatRoom;
import com.qiscus.sdk.data.model.QiscusChatRoom;
import com.squareup.picasso.Picasso;

import java.util.List;

/**
 * Created by adhit on 05/01/2018.
 */

public class RoomChatAdapter extends RecyclerView.Adapter<RoomChatAdapter.RoomChatViewHolder> {

    private Context context;
    private List<ChatRoom> qiscusChatRoomList;
    private OnDetailListener onDetailListener;

    public RoomChatAdapter(Context context) {
        this.context = context;
    }

    public RoomChatAdapter() {

    }
    public void setData(List<ChatRoom> items){
        qiscusChatRoomList = items;
        notifyDataSetChanged();
    }

    @Override
    public RoomChatAdapter.RoomChatViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_card_room_chat, parent, false);
        return new RoomChatViewHolder(view);
    }

    @Override
    public void onBindViewHolder(RoomChatViewHolder holder, final int position) {
        holder.tvRoomChatName.setText(qiscusChatRoomList.get(position).getNamaBiquers());
        holder.tvLastMessage.setText(qiscusChatRoomList.get(position).getLastMessage());
        if(qiscusChatRoomList.get(position).getUnreadCount()!= null){
            if(qiscusChatRoomList.get(position).getUnreadCount()>0){
                holder.tvUnreadMessage.setVisibility(View.VISIBLE);
                holder.tvUnreadMessage.setText(Integer.toString(qiscusChatRoomList.get(position).getUnreadCount()));
            }else{
                holder.tvUnreadMessage.setVisibility(View.GONE);

            }
        }

//        Picasso.with(context)
//                .load(qiscusChatRoomList.get(position).getAvatarUrl()).into(holder.imgAvatarRoomChat);
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if(qiscusChatRoomList.get(position).getIdRoom()== null){
                    onDetailListener.showMessageFailure();
                }else {
                    onDetailListener.onItemDetailClicked((Integer.parseInt((String) qiscusChatRoomList.get(position).getIdRoom())));
                }
            }
        });
    }
    public void setOnClickDetailListener(OnDetailListener onDetailListener){
        this.onDetailListener = onDetailListener;
    }


    @Override
    public boolean onFailedToRecycleView(RoomChatViewHolder holder) {
        return super.onFailedToRecycleView(holder);
    }

    @Override
    public int getItemCount() {
        if(qiscusChatRoomList == null) return 0;
        return qiscusChatRoomList.size();
    }

    public class RoomChatViewHolder extends RecyclerView.ViewHolder{
        TextView tvRoomChatName, tvLastMessage, tvUnreadMessage;
        ImageView imgAvatarRoomChat;


        public RoomChatViewHolder(final View itemView) {
            super(itemView);
            tvRoomChatName = itemView.findViewById(R.id.tv_room_chat_name);
            tvLastMessage = itemView.findViewById(R.id.tv_last_message);
            tvUnreadMessage = itemView.findViewById(R.id.tv_unread_message);
            imgAvatarRoomChat = itemView.findViewById(R.id.img_avatar_room_chat);

        }
    }

    public interface OnDetailListener{
        void onItemDetailClicked(int idRoom);

        void showMessageFailure();
    }
}