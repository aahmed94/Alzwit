package com.teamapple.alzwit;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.teamapple.models.Notification;

import java.util.ArrayList;

public class CardViewAdapter extends RecyclerView.Adapter<CardViewAdapter.ViewHolder> {

    private ArrayList<Notification> list = new ArrayList<>();
    private Context context;

    public CardViewAdapter(ArrayList<Notification> list, Context context) {
        this.list = list;
        this.context = context;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        View layoutView = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.activity_notification, null);
        ViewHolder viewHolder = new ViewHolder(layoutView);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull CardViewAdapter.ViewHolder viewHolder, int i) {
        viewHolder.title.setText(list.get(i).getLabel());
        viewHolder.description.setText(list.get(i).getDescription());
    }

    @Override
    public int getItemCount() {
        return this.list.size();
    }
    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        TextView title, description;
        CardView cardView;


        public ViewHolder(View itemView) {
            super(itemView);
            title = itemView.findViewById(R.id.notificationTitle);
            description = itemView.findViewById(R.id.notificationDescription);
            cardView = itemView.findViewById(R.id.card_view);
        }
        @Override
        public void onClick(View v) {

        }
    }
}
