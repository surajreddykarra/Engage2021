package com.classit.VoiceRoomsUI.Adapters;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.RecyclerView;

import com.classit.CourseInsideUI.Fragments.CourseMainFragment;
import com.classit.CoursesHelper.Models.CoursesModel;
import com.classit.CoursesHelper.ViewHolders.CourseViewHolder;
import com.classit.R;
import com.classit.VoiceRoomsUI.CallHappeningActivity;

import java.util.ArrayList;

public class VoiceRoomsAdapter extends RecyclerView.Adapter<CourseViewHolder> {

    Context context;
    ArrayList<CoursesModel> coursesModelArrayList;

    public VoiceRoomsAdapter(Context context, ArrayList<CoursesModel> coursesModelArrayList) {
        this.context = context;
        this.coursesModelArrayList = coursesModelArrayList;
    }

    @NonNull
    @Override
    public CourseViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(context);
        View view = layoutInflater.inflate(R.layout.list_item_voice_room, parent, false);
        return new CourseViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull CourseViewHolder holder, int position) {
        holder.courseCard.setBackground(context.getDrawable(R.drawable.glass_card_bg));

        holder.courseId.setText(coursesModelArrayList.get(position).getCourseId());
        holder.courseName.setText(coursesModelArrayList.get(position).getCourseName());

        holder.courseCard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, CallHappeningActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                context.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return coursesModelArrayList.size();
    }
}
