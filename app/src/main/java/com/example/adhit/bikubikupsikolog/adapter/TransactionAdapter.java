package com.example.adhit.bikubikupsikolog.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.adhit.bikubikupsikolog.R;
import com.example.adhit.bikubikupsikolog.data.model.Transaction;

import java.util.List;

/**
 * Created by adhit on 05/01/2018.
 */

public class TransactionAdapter extends RecyclerView.Adapter<TransactionAdapter.NewRequestViewHolder> {
    private Context context;
    private List<Transaction> transactionList;
    private OnDetailListener onDetailListener;

    public TransactionAdapter(Context context){
        this.context = context;
    }

    public void setData(List<Transaction> transactionList){
        this.transactionList = transactionList;
        notifyDataSetChanged();
    }

    @Override
    public NewRequestViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_card_new_request, parent, false);
        return new NewRequestViewHolder(view);
    }

    @Override
    public void onBindViewHolder(NewRequestViewHolder holder, int position) {
        final Transaction transaction = transactionList.get(position);
        holder.tvName.setText(transaction.getNamaBiquers());
        holder.btnAccept.setOnClickListener(v-> onDetailListener.onItemDetailClicked(transaction, "accept"));
        holder.btnReject.setOnClickListener(v-> onDetailListener.onItemDetailClicked(transaction, "cancel"));
//        Picasso.with(context)
//                .load(car.getImgUrl())
//                .error(R.mipmap.ic_launcher)
//                .into(holder.imgAvatar);
//
    }

    public void setOnClickDetailListener(OnDetailListener onDetailListener){
        this.onDetailListener = onDetailListener;
    }

    @Override
    public int getItemCount() {
        if(transactionList == null) return 0;
        return transactionList.size();
    }

    public class NewRequestViewHolder extends RecyclerView.ViewHolder {
        TextView tvName;
        ImageView imgAvatar;
        Button btnReject, btnAccept;

        public NewRequestViewHolder(View itemView) {
            super(itemView);
            tvName = itemView.findViewById(R.id.tv_name);
            btnAccept = itemView.findViewById(R.id.btn_accept);
            btnReject = itemView.findViewById(R.id.btn_reject);
            imgAvatar = itemView.findViewById(R.id.cimg_profile_photo);
        }
    }

    public interface OnDetailListener{
        void onItemDetailClicked(Transaction transaction, String status);
    }
}