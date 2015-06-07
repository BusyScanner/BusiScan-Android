package com.busyscanner.busyscanner;

import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

public class CardAdapter extends RecyclerView.Adapter<CardAdapter.MyViewHolder> {

    private List<BizCardResponse> cards;

    public CardAdapter() {
        cards = new ArrayList<>();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        public TextView nameView;
        public TextView companyView;
        public TextView emailView;
        public TextView phoneView;


        public MyViewHolder(View itemView) {
            super(itemView);
            this.nameView = (TextView) itemView.findViewById(R.id.list_name);
            this.companyView = (TextView) itemView.findViewById(R.id.list_company);
            this.emailView = (TextView) itemView.findViewById(R.id.list_email);
            this.phoneView = (TextView) itemView.findViewById(R.id.list_phone);
        }
    }

    @Override
    public int getItemCount() {
        return cards.size();
    }

    @Override
    public CardAdapter.MyViewHolder onCreateViewHolder(ViewGroup parent, int i) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.card_list_item, parent, false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(CardAdapter.MyViewHolder myViewHolder, int i) {
        BizCardResponse card = cards.get(i);
        if (TextUtils.isEmpty(card.getFullname())) {
            myViewHolder.nameView.setText("");
        } else {
            myViewHolder.nameView.setText("Name: " + card.getFullname());
        }
        if (TextUtils.isEmpty(card.getCompany())) {
            myViewHolder.companyView.setText("");
        } else {
            myViewHolder.companyView.setText("Company: " + card.getCompany());
        }
        if (TextUtils.isEmpty(card.getEmail())) {
            myViewHolder.emailView.setText("");
        } else {
            myViewHolder.emailView.setText("Email: " + card.getEmail());
        }
        if (TextUtils.isEmpty(card.getPhone())) {
            myViewHolder.phoneView.setText("");
        } else {
            myViewHolder.phoneView.setText("Phone: " + card.getPhone());
        }
    }

    public void setCards(List<BizCardResponse> cards) {
        this.cards = cards;
        notifyDataSetChanged();
    }
}
