package com.classit.CourseChatUI.Adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.classit.CourseChatUI.Models.MessageModel;
import com.classit.CourseChatUI.ViewHolders.MessageViewHolder;
import com.classit.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;

public class MessageAdapter extends RecyclerView.Adapter<MessageViewHolder> {

    Context context;
    ArrayList<MessageModel> messageModelArrayList;

    public MessageAdapter(Context context, ArrayList<MessageModel> messageModelArrayList) {
        this.context = context;
        this.messageModelArrayList = messageModelArrayList;
    }

    public static final int MSG_TYPE_LEFT = 0;
    public static final int MSG_TYPE_RIGHT = 1;

    FirebaseUser firebaseUser;

    @NonNull
    @Override
    public MessageViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        if (viewType == MSG_TYPE_RIGHT) {
            View view = layoutInflater.inflate(R.layout.list_item_message_right, parent, false);
            return new MessageViewHolder(view);
        }
        else{
            View view = layoutInflater.inflate(R.layout.list_item_message_left,parent,false);
            return new MessageViewHolder(view);
        }
    }

    @Override
    public void onBindViewHolder(@NonNull MessageViewHolder holder, int position) {

        holder.message.setText(messageModelArrayList.get(position).getMessage());

        if(holder.getItemViewType() == 0){
            holder.userName.setText(messageModelArrayList.get(position).getSentUserName());
            holder.messageCard.setBackground(context.getDrawable(R.drawable.background_left));
        }
        else{
            holder.messageCard.setBackground(context.getDrawable(R.drawable.background_right));
        }

    }

    @Override
    public int getItemCount() {
        return messageModelArrayList.size();
    }

    @Override
    public int getItemViewType(int position) {
        firebaseUser = FirebaseAuth.getInstance().getCurrentUser();
        String userId = firebaseUser.getUid();

        if(messageModelArrayList.get(position).getSentUserId().equals(userId)){
            return MSG_TYPE_RIGHT;
        } else {
            return MSG_TYPE_LEFT;
        }
    }
}
