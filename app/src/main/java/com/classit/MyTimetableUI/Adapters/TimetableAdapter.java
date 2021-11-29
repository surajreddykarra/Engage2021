package com.classit.MyTimetableUI.Adapters;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.classit.MyTimetableUI.Models.TimetableModel;
import com.classit.MyTimetableUI.ViewHolders.TimetableViewHolder;
import com.classit.R;

import java.util.ArrayList;

public class TimetableAdapter extends RecyclerView.Adapter<TimetableViewHolder> {

    Context context;
    ArrayList<TimetableModel> timetableModelArrayList;

    public TimetableAdapter(Context context, ArrayList<TimetableModel> timetableModelArrayList) {
        this.context = context;
        this.timetableModelArrayList = timetableModelArrayList;
    }

    @NonNull
    @Override
    public TimetableViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(context);
        View view = layoutInflater.inflate(R.layout.list_item_timetable, parent, false);
        return new TimetableViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull TimetableViewHolder holder, int position) {
        holder.courseTime.setText(timetableModelArrayList.get(position).getCourseTime());
        holder.courseId.setText(timetableModelArrayList.get(position).getCourseId());
        holder.courseName.setText(timetableModelArrayList.get(position).getCourseName());
        holder.courseFaculty.setText(timetableModelArrayList.get(position).getCourseFaculty());

        holder.timetableCard.setBackground(context.getDrawable(R.drawable.glass_card_bg));

        holder.joinClass.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String url = "https://meet.google.com/bgw-prug-syx";
                Intent i = new
                        Intent(Intent.ACTION_VIEW);
                i.setData(Uri.parse(url));
                i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                context.startActivity(i);
            }
        });
    }

    @Override
    public int getItemCount() {
        return timetableModelArrayList.size();
    }
}
