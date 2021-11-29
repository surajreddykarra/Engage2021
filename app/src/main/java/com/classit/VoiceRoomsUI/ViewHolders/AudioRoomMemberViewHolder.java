package com.classit.VoiceRoomsUI.ViewHolders;

import android.view.View;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.classit.R;

import de.hdodenhof.circleimageview.CircleImageView;

public class AudioRoomMemberViewHolder extends RecyclerView.ViewHolder {

    public CircleImageView memberImage;

    public AudioRoomMemberViewHolder(@NonNull View itemView) {
        super(itemView);

        memberImage = itemView.findViewById(R.id.memberImage);

    }
}
