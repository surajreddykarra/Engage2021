package com.classit.CoursesHelper.ViewHolders;

import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.classit.R;

public class CourseViewHolder extends RecyclerView.ViewHolder {

    public TextView courseId, courseName, courseFaculty;
    public CardView courseCard;

    public CourseViewHolder(@NonNull View itemView) {
        super(itemView);

        courseId = itemView.findViewById(R.id.courseId);
        courseName = itemView.findViewById(R.id.courseName);
        courseFaculty = itemView.findViewById(R.id.courseFaculty);

        courseCard = itemView.findViewById(R.id.courseCard);

    }
}
