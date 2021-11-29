package com.classit.CourseAnnouncementsUI.Adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.classit.CourseAnnouncementsUI.Models.AnnouncementModel;
import com.classit.CourseAnnouncementsUI.ViewHolders.AnnouncementViewHolder;
import com.classit.R;

import java.lang.reflect.Array;
import java.util.ArrayList;

public class AnnouncementsAdapter extends RecyclerView.Adapter<AnnouncementViewHolder> {

    Context context;
    ArrayList<AnnouncementModel> announcementModelArrayList;

    public AnnouncementsAdapter(Context context, ArrayList<AnnouncementModel> announcementModelArrayList) {
        this.context = context;
        this.announcementModelArrayList = announcementModelArrayList;
    }

    @NonNull
    @Override
    public AnnouncementViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(context);
        View view = layoutInflater.inflate(R.layout.list_item_announcement, parent, false);
        return new AnnouncementViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull AnnouncementViewHolder holder, int position) {
        holder.title.setText(announcementModelArrayList.get(position).getTitle());
        holder.content.setText(announcementModelArrayList.get(position).getContent());
        holder.announcementCard.setBackground(context.getDrawable(R.drawable.glass_card_bg));
    }

    @Override
    public int getItemCount() {
        return announcementModelArrayList.size();
    }
}
