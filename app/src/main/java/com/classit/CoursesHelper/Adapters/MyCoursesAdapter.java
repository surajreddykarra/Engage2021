package com.classit.CoursesHelper.Adapters;

import android.content.Context;
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

import java.util.ArrayList;

public class MyCoursesAdapter extends RecyclerView.Adapter<CourseViewHolder> {

    Context context;
    ArrayList<CoursesModel> coursesModelArrayList;

    public MyCoursesAdapter(Context context, ArrayList<CoursesModel> coursesModelArrayList) {
        this.context = context;
        this.coursesModelArrayList = coursesModelArrayList;
    }

    @NonNull
    @Override
    public CourseViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(context);
        View view = layoutInflater.inflate(R.layout.list_item_course, parent, false);
        return new CourseViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull CourseViewHolder holder, int position) {
        holder.courseCard.setBackground(context.getDrawable(R.drawable.glass_card_bg));

        holder.courseId.setText(coursesModelArrayList.get(position).getCourseId());
        holder.courseName.setText(coursesModelArrayList.get(position).getCourseName());
        holder.courseFaculty.setText(coursesModelArrayList.get(position).getCourseFaculty());

        holder.courseCard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Bundle bundle = new Bundle();
                bundle.putSerializable("course", coursesModelArrayList.get(holder.getAdapterPosition()));

                CourseMainFragment courseMainFragment = new CourseMainFragment();
                courseMainFragment.setArguments(bundle);

                AppCompatActivity appCompatActivity = (AppCompatActivity) v.getContext();
                FragmentManager fragmentManager = appCompatActivity.getSupportFragmentManager();
                FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                fragmentTransaction.replace(R.id.main_frame,courseMainFragment);
                fragmentTransaction.addToBackStack(null);
                fragmentTransaction.commit();
            }
        });

    }

    @Override
    public int getItemCount() {
        return coursesModelArrayList.size();
    }
}
