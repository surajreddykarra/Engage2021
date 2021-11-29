package com.classit.CourseChatUI.ViewHolders;

import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.classit.R;


public class MessageViewHolder extends RecyclerView.ViewHolder {
    public TextView message, userName;
    public CardView messageCard;

    public MessageViewHolder(@NonNull View itemView) {
        super(itemView);

        message = itemView.findViewById(R.id.message);
        userName = itemView.findViewById(R.id.userName);
        messageCard = itemView.findViewById(R.id.messageCard);

    }
}
