package com.classit.CourseInsideUI.Adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.classit.CourseInsideUI.Models.CourseEnrolledStudentsModel;
import com.classit.CourseInsideUI.ViewHolders.CourseEnrolledStudentsViewHolder;
import com.classit.R;
import com.squareup.picasso.Callback;
import com.squareup.picasso.NetworkPolicy;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class CourseEnrolledStudentsAdapter extends RecyclerView.Adapter<CourseEnrolledStudentsViewHolder> {

    Context context;
    ArrayList<CourseEnrolledStudentsModel> courseEnrolledStudentsModelArrayList;

    public CourseEnrolledStudentsAdapter(Context context, ArrayList<CourseEnrolledStudentsModel> courseEnrolledStudentsModelArrayList) {
        this.context = context;
        this.courseEnrolledStudentsModelArrayList = courseEnrolledStudentsModelArrayList;
    }

    @NonNull
    @Override
    public CourseEnrolledStudentsViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(context);
        View view = layoutInflater.inflate(R.layout.list_item_course_enrolled_student, parent, false);
        return new CourseEnrolledStudentsViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull CourseEnrolledStudentsViewHolder holder, int position) {
        Picasso.get()
                .load(courseEnrolledStudentsModelArrayList.get(position).getStudentPhoto())
                .networkPolicy(NetworkPolicy.OFFLINE)
                .into(holder.studentImage, new Callback() {
                    @Override
                    public void onSuccess() {

                    }

                    @Override
                    public void onError(Exception e) {
                        Picasso.get()
                                .load(courseEnrolledStudentsModelArrayList.get(position).getStudentPhoto())
                                .into(holder.studentImage);
                    }
                });

        holder.studentName.setText(courseEnrolledStudentsModelArrayList.get(position).getStudentName());
        holder.studentCard.setBackground(context.getDrawable(R.drawable.glass_card_bg));
    }

    @Override
    public int getItemCount() {
        return courseEnrolledStudentsModelArrayList.size();
    }
}
