package com.classit.CourseAnnouncementsUI.ViewHolders;

import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.classit.R;

public class AnnouncementViewHolder extends RecyclerView.ViewHolder {

    public TextView title, content;
    public CardView announcementCard;

    public AnnouncementViewHolder(@NonNull View itemView) {
        super(itemView);

        title = itemView.findViewById(R.id.title);
        content = itemView.findViewById(R.id.content);
        announcementCard = itemView.findViewById(R.id.announcementCard);

    }
}
